package com.example.appointmentms.Controller;

import com.example.appointmentms.Dto.Request.AppointmentRequestDto;
import com.example.appointmentms.Dto.Response.AppointmentResponseDto;
import com.example.appointmentms.Enums.TreatmentType;
import com.example.appointmentms.Service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RequestMapping("/api/v1/appointment")
@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Create a new appointment", description = "Creates a new appointment based on the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, appointment creation failed")
    })
    @PostMapping("/create")
    public ResponseEntity<Long> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return new ResponseEntity<>(appointmentService.create(appointmentRequestDto), CREATED);
    }


    @Operation(summary = "Find appointment by ID", description = "Finds and returns the details of an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found with the given ID")
    })
    @GetMapping("findById/{id}")
    public AppointmentResponseDto findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }


    @Operation(summary = "Get all appointments", description = "Retrieves all appointments with pagination support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    @GetMapping("/all")
    public Page<AppointmentResponseDto> getAll(Pageable pageable) {
        return appointmentService.getAll(pageable);
    }

    @Operation(summary = "Update an existing appointment", description = "Updates the details of an existing appointment based on the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, appointment update failed"),
            @ApiResponse(responseCode = "404", description = "Appointment not found with the given ID")
    })
    @PutMapping("/update")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto,
                                                                    @RequestParam Long id) {
        return new ResponseEntity<>(appointmentService.update(appointmentRequestDto, id), OK);
    }

    @Operation(summary = "Delete an appointment", description = "Deletes an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found with the given ID")
    })
    @DeleteMapping("/delete/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId){
        appointmentService.delete(appointmentId);
    }

    @Operation(summary = "Get appointments by patient ID", description = "Retrieves all appointments for a specific patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No appointments found for the given patient ID")
    })

    @GetMapping("/byPatients/{patientId}")
    Page<AppointmentResponseDto> getAppointmentByPatient(@PathVariable Long patientId,Pageable pageable){
        return appointmentService.getAppointmentByPatient(patientId,pageable);
    }

    @GetMapping("/date{date}")
    public Page<AppointmentResponseDto> getAppointmentByDate(@PathVariable String date, Pageable pageable){
        return appointmentService.getAppointmentByHistory(date,pageable);
    }

    @GetMapping("/treatment-types")
    public ResponseEntity<List<String>> getTreatmentTypes() {
        List<String> types = Arrays.stream(TreatmentType.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(types);
    }








}
