package com.stepwise.backend;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.stepwise")
public class StepwiseApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(StepwiseApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);

    System.out.println("Stepwise App is running...");
  }

}
