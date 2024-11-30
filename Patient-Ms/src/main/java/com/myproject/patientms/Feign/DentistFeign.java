package com.myproject.patientms.Feign;

import com.example.commonms.Dto.DentistResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.commonms.Constant.FeignConstants.DENTIST_SERVICE;
import static com.example.commonms.Constant.FeignConstants.DENTIST_SERVICE_URL;

@FeignClient(name = DENTIST_SERVICE, url = DENTIST_SERVICE_URL)
public interface DentistFeign {

    @GetMapping("/findById/{id}")
    DentistResponseDto findById(@PathVariable("id") Long id);
}