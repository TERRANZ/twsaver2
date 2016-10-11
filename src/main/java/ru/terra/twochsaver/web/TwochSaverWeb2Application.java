package ru.terra.twochsaver.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import ru.terra.twochsaver.web.engine.DownloadEngine;

@SpringBootApplication
public class TwochSaverWeb2Application extends SpringBootServletInitializer {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TwochSaverWeb2Application.class, args);
        context.getBean(DownloadEngine.class).runUnFinished();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TwochSaverWeb2Application.class);
    }
}
