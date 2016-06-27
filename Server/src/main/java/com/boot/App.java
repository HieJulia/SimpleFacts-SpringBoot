package com.boot;

import java.util.Date;

import org.hibernate.Session;

import com.boot.model.Event;
import com.boot.model.Location;
import com.boot.util.HibernateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
