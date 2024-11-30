package com.myproject.patientms.Feign;

import com.example.commonms.Dto.AppointmentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.commonms.Constant.FeignConstants.APPOINTMENT_SERVICE;
import static com.example.commonms.Constant.FeignConstants.APPOINTMENT_SERVICE_URL;

@FeignClient(name = APPOINTMENT_SERVICE , url = APPOINTMENT_SERVICE_URL)
public interface AppointmentFeign {
    @GetMapping("/{id}")
    AppointmentResponseDto findById(@RequestParam Long id);

    @GetMapping("/byPatients/{patientId}")
    Page<AppointmentResponseDto> getAppointmentByPatient(@PathVariable Long patientId, Pageable pageable);

}

