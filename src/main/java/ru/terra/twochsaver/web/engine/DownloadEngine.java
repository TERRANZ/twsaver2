package ru.terra.twochsaver.web.engine;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.terra.twochsaver.web.db.entity.Img;
import ru.terra.twochsaver.web.db.entity.Message;
import ru.terra.twochsaver.web.db.entity.Thr;
import ru.terra.twochsaver.web.db.repo.ImgRepo;
import ru.terra.twochsaver.web.db.repo.MessageRepo;
import ru.terra.twochsaver.web.db.repo.ThrRepo;
import ru.terra.twochsaver.web.engine.timer.TsaverTimerManager;
import ru.terra.twochsaver.web.engine.twochdto.TwochFile;
import ru.terra.twochsaver.web.engine.twochdto.TwochThread;
import ru.terra.twochsaver.web.web.dto.Show;
import ru.terra.twochsaver.web.web.dto.Stat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Date: 21.01.15
 * Time: 19:35
 */
@Service
@Scope("singleton")
public class DownloadEngine {

    private ExecutorService threadPool = Executors.newFixedThreadPool(20);
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, CountDownLatch> countdowns = new HashMap<>();
    private Map<String, Integer> counts = new HashMap<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("d.M.y H:m");

    @Autowired
    private ThrRepo thrRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ImgRepo imgRepo;
    @Value("${service-base-url}")
    protected String serviceBaseUrl;


    public DownloadEngine() {
        List<File> files = new ArrayList<>();

        addTree(new File("download"), files);
        files.stream().filter(File::isDirectory).forEach(file -> {
            counts.put(file.getName(), file.list().length);
            countdowns.put(file.getName(), new CountDownLatch(0));
        });

        logger.info("Starting download engine");
    }

    public void runUnFinished() {
        StreamSupport.stream(thrRepo.findAll().spliterator(), false)
                .filter(t -> t.getFinished() == 0)
                .forEach(t -> TsaverTimerManager.getInstance().addUpdateThreadJob(t.getUrl(), this));
    }

    static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                all.add(child);
                addTree(child, all);
            }
        }
    }

    public synchronized void start(final String url, final boolean force) {
        if (url.isEmpty())
            return;

        logger.info("Starting: " + url);
        threadPool.submit(() -> {

            Thr thr = thrRepo.findByUrl(url);
            if (thr == null) {
                thr = new Thr();
                thr.setUpdated(new Date());
                thr.setAdded(new Date());
                thr.setChecked(0);
                thr.setFinished(0);
                thr.setCount(0);
                thr.setImgList(new ArrayList<>());
                thr.setUrl(url);
            } else {
                if (thr.getChecked() > 30)
                    thr.setFinished(1);
                try {
                    thrRepo.save(thr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!force)
                if (thr.getFinished() == 1) {
                    TsaverTimerManager.getInstance().removeThreadJob(url);
                    return;
                }

            final Integer thread = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
            thr.setId(thread);
            String resUrl = url.substring(0, url.indexOf("/res"));
            final String board = resUrl.substring(resUrl.lastIndexOf("/") + 1);
            thr.setBoard(board);
            URLConnection conn = null;
            try {
                conn = new URL("https://2ch.hk/makaba/mobile.fcgi?task=get_thread&board=" + board + "&thread=" + thread + "&num=" + thread).openConnection();
            } catch (IOException e) {
                logger.error("Unable to connect to server", e);
            }
            conn.setConnectTimeout(10000);
            ObjectMapper mapper = new ObjectMapper();
            TwochThread[] readedThread = new TwochThread[0];
            boolean error = false;
            try {
                readedThread = mapper.readValue(conn.getInputStream(), TwochThread[].class);
            } catch (IOException e) {
                error = true;
                TsaverTimerManager.getInstance().removeThreadJob(url);
                thr.setFinished(1);
                try {
                    thrRepo.save(thr);
                } catch (Exception e1) {
                    logger.error("Unable to update thread", e1);
                }
                logger.error("Unable to read json", e);
            }
            if (!error) {
                Integer imagesCount = 0;
                for (TwochThread twochThread : readedThread)
                    imagesCount += twochThread.getFiles().size();

                logger.info("Images count: " + imagesCount);

                if (thr.getCount() == imagesCount)
                    thr.setChecked(thr.getChecked() + 1);
                else
                    thr.setChecked(0);

                thr.setCount(imagesCount);
                try {
                    thrRepo.save(thr);
                } catch (Exception e) {
                    logger.error("Unable to update thread", e);
                }

                counts.put(board + thread, imagesCount);
                countdowns.put(board + thread, new CountDownLatch(imagesCount));

                final String folderName = "download/" + board + thread;
                new File(folderName).mkdirs();
                new Thread(() -> addBtSyncFolder(folderName)).start();

                final String finalResUrl = resUrl + "/";
                for (TwochThread twochThread : readedThread) {
                    Message message = new Message();
                    message.setMsg(twochThread.getComment());
                    message.setAdded(new Date());
                    message.setSubject(twochThread.getName());
                    message.setThread(thr);
                    message.setId(Integer.parseInt(twochThread.getNum()));
                    int i = 0;

                    for (final TwochFile file : twochThread.getFiles()) {
                        final String finalImageUrl = finalResUrl + file.getPath();
                        switch (i) {
                            case 0: {
                                message.setImage1(finalImageUrl);
                                message.setMd51(file.getMd5());
                            }
                            break;
                            case 1: {
                                message.setImage2(finalImageUrl);
                                message.setMd52(file.getMd5());
                            }
                            break;
                            case 2: {
                                message.setImage3(finalImageUrl);
                                message.setMd53(file.getMd5());
                            }
                            break;
                            case 3: {
                                message.setImage4(finalImageUrl);
                                message.setMd54(file.getMd5());
                            }
                            break;
                        }
                        i++;
                        final Thr finalThr = thr;
                        threadPool.submit(() -> {
                            if (imgRepo.findOne(finalImageUrl) == null) {
                                downloadImage(folderName, finalImageUrl);
                                Img img = new Img();
                                img.setThrId(finalThr);
                                img.setUrl(finalImageUrl);
                                img.setMd5hash(file.getMd5());

                                imgRepo.save(img);
                            }

                            CountDownLatch countDownLatch = getCountDown(board + thread);
                            if (countDownLatch != null)
                                countDownLatch.countDown();
                        });
                    }
                    try {
                        messageRepo.save(message);
                    } catch (Exception e) {
                        logger.error("Unable to save message", e);
                    }
                }
            }
        });
    }

    private void addBtSyncFolder(String folder) {
//        try {
//            String jarDir = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//            String currentDir = new File(jarDir).getParent();
//
//            String name = Config.getConfig().getValue("btsync.name", "");
//            String password = Config.getConfig().getValue("btsync.pass", "");
//            String host = Config.getConfig().getValue("btsync.host", "http://xn--80aafhfrpg0adapheyc1nya.xn--p1ai:8888");
//            String authString = name + ":" + password;
//            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes(Charset.defaultCharset()));
//            logger.debug("Encoded auth string = " + authStringEnc);
//
//            URLConnection btsConn = new URL(host + "/api?method=add_folder&dir=" + currentDir + "/" + folder).openConnection();
//            btsConn.setRequestProperty("Authorization", "Basic " + authStringEnc);
//            btsConn.setConnectTimeout(10000);
//            ObjectMapper mapper = new ObjectMapper();
//            SyncDTO res = mapper.readValue(btsConn.getInputStream(), SyncDTO.class);
//            logger.debug("Sync for folder " + currentDir + "/" + folder + " reported: " + res.error + " message: " + res.message);
//        } catch (IOException e) {
//            logger.error("Unable to add folder to sync", e);
//        }
    }

    private CountDownLatch getCountDown(String bt) {
        synchronized (countdowns) {
            return countdowns.get(bt);
        }
    }

    private void downloadImage(String folder, String url) {
        for (int i = 0; i <= 2; i++) {
            try {
                URL imageUrl = new URL(url);
                Path path = Paths.get(folder + url.substring(url.lastIndexOf("/")));
                if (!path.toFile().exists())
                    Files.copy(imageUrl.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Downloaded: " + url);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized List<Stat> getStat() {
        return StreamSupport.stream(thrRepo.findAll().spliterator(), false)
                .map(thr ->
                        new Stat(
                                thr.getUrl(),
                                thr.getCount(),
                                thr.getCount() - imgRepo.findByThrId(thr).size(),
                                thr.getFinished(),
                                thr.getChecked(),
                                sdf.format(thr.getAdded()),
                                sdf.format(thr.getUpdated()),
                                messageRepo.findByThread(thr).size()
                        )).collect(Collectors.toList()
                );
    }

    public List<Show> getShow(String url) {
        Thr thr = thrRepo.findByUrl(url);
        if (thr == null)
            return null;

        return imgRepo.findByThrId(thr).stream().map(img -> new Show(img.getUrl(), prepareUrl(thr, img.getUrl()))).collect(Collectors.toList());
    }

    public String prepareUrl(Thr thr, String url) {
        String ret = serviceBaseUrl;
        ret += thr.getBoard();
        ret += thr.getId();
        ret += url.substring(url.lastIndexOf("/"), url.length());
        return ret;
    }
}
