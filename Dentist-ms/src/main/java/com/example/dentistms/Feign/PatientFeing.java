package com.example.dentistms.Feign;

import com.example.commonms.Dto.PatientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.commonms.Constant.FeignConstants.PATIENT_SERVICE;
import static com.example.commonms.Constant.FeignConstants.PATIENT_SERVICE_URL;

@FeignClient(name = PATIENT_SERVICE , url = PATIENT_SERVICE_URL)
public interface PatientFeing {

        @GetMapping("/by-dentist/{dentistId}")
        List<PatientResponseDto> getPatientsByDentist(@PathVariable("dentistId") Long dentistId);
    }
