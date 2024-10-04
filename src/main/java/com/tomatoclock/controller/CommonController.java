package com.tomatoclock.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
@Log4j2
public class CommonController {

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

}
