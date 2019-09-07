package com.german.dungeons.and.dragons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

  private static final Class<Application> applicationClass = Application.class;

  public static void main(String[] args) {
    SpringApplication.run(applicationClass, args);
  }
}