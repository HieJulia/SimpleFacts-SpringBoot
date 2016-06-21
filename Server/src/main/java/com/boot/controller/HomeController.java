package com.boot.controller;

/**
 * Created by Admin on 27/05/2016.
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "The server is alive!";
    }
}
