package com.boot.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The purpose of this REST Controller is to just see whether or not the server
 * is current operational, without doing anything complicated
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "The server is alive!";
    }
}
