package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Admin on 27/05/2016.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}