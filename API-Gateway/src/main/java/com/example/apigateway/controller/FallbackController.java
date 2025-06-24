package com.example.apigateway.controller;

import com.example.apigateway.Model.FallBackResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/patientServiceFallBack")
    public FallBackResponse fallbackPatient() {
        return new FallBackResponse("Patient service is currently unavailable. Please try again later.");
    }

    @GetMapping("/appointmentServiceFallBack")
    public FallBackResponse fallbackAppointment() {
        return new FallBackResponse("Appointment service is currently unavailable. Please try again later.");
    }

    @GetMapping("/dentistServiceFallBack")
    public FallBackResponse fallbackDentist() {
        return new FallBackResponse("Dentist service is currently unavailable. Please try again later.");
    }

    @GetMapping("/authServiceFallback")
    public FallBackResponse fallbackAuth() {
        return new FallBackResponse("Auth service is currently unavailable. Please try again later.");
    }
}
