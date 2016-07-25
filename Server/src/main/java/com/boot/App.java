package com.boot;


import java.util.concurrent.TimeUnit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class App implements CommandLineRunner {

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Autowired
    Receiver receiver;
    
    final static String queueName = "spring-boot";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
