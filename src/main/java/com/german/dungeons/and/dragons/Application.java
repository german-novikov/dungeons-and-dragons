package com.german.dungeons.and.dragons;

import com.german.dungeons.and.dragons.services.DragonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    private static final Class<Application> applicationClass = Application.class;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(applicationClass, args);
        DragonService dragonService = applicationContext.getBean(DragonService.class);
        if(args.length > 0) {
            dragonService.startGame(args[0].toString());
        }else{
            dragonService.startGame("German Novikov");
        }
        System.exit(1);
    }
}