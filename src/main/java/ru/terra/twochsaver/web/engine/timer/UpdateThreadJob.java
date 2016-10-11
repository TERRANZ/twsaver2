package ru.terra.twochsaver.web.engine.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import ru.terra.twochsaver.web.engine.DownloadEngine;

/**
 * Date: 31.03.15
 * Time: 13:10
 */
@Component
public class UpdateThreadJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        String url = jec.getMergedJobDataMap().getString("url");
        DownloadEngine downloadEngine = (DownloadEngine) jec.getMergedJobDataMap().get("de");
        downloadEngine.start(url, false);
    }
}
