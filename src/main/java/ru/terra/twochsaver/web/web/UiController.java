package ru.terra.twochsaver.web.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.terra.twochsaver.web.engine.DownloadEngine;

/**
 * Created by terranz on 11.10.16.
 */
@Controller
@RequestMapping("/")
public class UiController {
    @Autowired
    private DownloadEngine downloadEngine;

    @RequestMapping("/")
    public String main() {
        return "main";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String start(@RequestParam(name = "url") String url, Model model) {
        downloadEngine.start(url, true);
        model.addAttribute("data", downloadEngine.getStat());
        return "list";
    }

    @RequestMapping(value = "/show")
    public String show(@RequestParam(name = "url") String url, Model model) {
        model.addAttribute("data", downloadEngine.getShow(url));
        return "show";
    }

    @RequestMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("data", downloadEngine.getStat());
        return "list";
    }

    @RequestMapping(value = "/pack")
    public String pack(@RequestParam(name = "url") String url) {
        return "show";
    }
}
