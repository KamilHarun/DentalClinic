package com.example.appointmentms.Feign;

import com.example.commonms.Dto.PatientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.commonms.Constant.FeignConstants.PATIENT_SERVICE;
import static com.example.commonms.Constant.FeignConstants.PATIENT_SERVICE_URL;

@FeignClient(name = PATIENT_SERVICE, url = PATIENT_SERVICE_URL)
public interface PatientFeign {
    @GetMapping("/byId{id}")
    PatientResponseDto findByPatientId(@PathVariable Long id);
}