package com.myproject.patientms.Controller;

import com.myproject.patientms.Dto.Request.PatientRequestDto;
import com.myproject.patientms.Dto.Response.PatientResponseDto;
import com.myproject.patientms.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Create a new patient", description = "This endpoint creates a new patient and returns the patient ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/create")
    public ResponseEntity<Long> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        return new ResponseEntity<>(patientService.create(patientRequestDto), CREATED);
    }

    @Operation(summary = "Find a patient by ID", description = "Fetch patient details by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/byId")
    public PatientResponseDto findById(@RequestParam Long id) {
        return patientService.findById(id);
    }

    @Operation(summary = "Get all patients with pagination", description = "Returns a paginated list of all patients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients list fetched successfully")
    })
    @GetMapping("/getAll")
    public Page<PatientResponseDto> getAll(Pageable pageable) {
        return patientService.getAll(pageable);
    }

    @Operation(summary = "Update patient details", description = "Updates an existing patient's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto patientRequestDto) {
        return new ResponseEntity<>(patientService.update(id, patientRequestDto), OK);
    }

    @Operation(summary = "Delete a patient", description = "Deletes a patient by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Operation(summary = "Find patient by name", description = "Search for patients by their name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients found"),
            @ApiResponse(responseCode = "404", description = "No patients found with the given name")
    })
    @GetMapping("/name")
    public ResponseEntity<List<PatientResponseDto>> findByName(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        return new ResponseEntity<>(patientService.findByName(patientRequestDto), OK);
    }

    @Operation(summary = "Get patient history", description = "Retrieve the history of a patient by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/patientHistory")
    public Page<PatientResponseDto> getPatientHistory(@RequestParam Long id , Pageable pageable) {
        return patientService.getPatientHistory(id , pageable);
    }

    @Operation(summary = "Get patient statistics by age group", description = "Fetch statistics of patients grouped by age.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient statistics retrieved successfully")
    })
    @GetMapping("/statistics/age-group")
    public ResponseEntity<Map<String, Long>> getPatientStatisticsByAgeGroup() {
        return new ResponseEntity<>(patientService.getPatientStatisticsByAgeGroup(), OK);
    }

    @Operation(summary = "Update patient's contact information", description = "Update a patient's email or phone number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact information updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/updateContact{id}")
    public ResponseEntity<PatientResponseDto> updateContact(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long id) {
        return new ResponseEntity<>(patientService.updateContact(phone, email, id), OK);


    }
    @Operation(summary = "Update patient's Status ", description = "Update a patient's Status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PatientResponseDto> updatePatientStatus (@RequestParam Long id,
                                                                   @RequestBody PatientRequestDto patientRequestDto){
        return new ResponseEntity<>(patientService.updatePatientStatus(id,patientRequestDto) , OK);

    }
}

