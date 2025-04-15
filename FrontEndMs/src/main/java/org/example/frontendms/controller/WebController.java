package org.example.frontendms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

    @GetMapping("/home")
    public String home() {
        return "home"; // templates/home.html
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // templates/about.html
    }

    @GetMapping("/service")
    public String service() {
        return "service"; // templates/service.html
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // templates/contact.html
    }

    @GetMapping("/appointment")
    public String appointment() {
        return "appointment"; // templates/appointment.html
    }

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/team")
    public String team() {
        return "team";
    }
}


