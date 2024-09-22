package com.tomatoclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/profile")
    //@PreAuthorize("isAuthenticated()")
    public void profile() {

    }

    @GetMapping("/statistics")
    public void statistics() {

    }

}
