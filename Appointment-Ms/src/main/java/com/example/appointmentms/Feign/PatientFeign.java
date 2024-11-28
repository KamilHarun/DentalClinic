package com.example.appointmentms.Feign;

import com.example.commonms.Dto.PatientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.commonms.Constant.FeignConstants.PATIENT_SERVICE;

@FeignClient(value = PATIENT_SERVICE , configuration = FeignClientProperties.class)
public interface PatientFeign {
    @GetMapping("/api/v1/byId")
    PatientResponseDto findById(@RequestParam("id") Long id);
}