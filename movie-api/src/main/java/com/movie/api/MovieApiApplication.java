package com.movie.api;

import com.CoreScanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan("com")
@Import(CoreScanConfiguration.class)
public class MovieApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieApiApplication.class, args);
  }

}

