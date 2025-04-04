package org.example.frontendms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {
    @GetMapping
    public String index(Model model) {
        // Örnek veri - Gerçek uygulamada servis katmanından alınmalı
        model.addAttribute("todayAppointments", 8);
        return "index";
    }

    @GetMapping("/appointments")
    public String appointments() {
        return "appointments";
    }

    @GetMapping("/patients")
    public String patients() {
        return "patients";
    }

    @GetMapping("/dentists")
    public String dentists() {
        return "dentists";
    }
}

