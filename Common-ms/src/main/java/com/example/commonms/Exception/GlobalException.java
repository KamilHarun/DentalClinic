package com.example.commonms.Exception;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalException {

    private final MessageSource messageSource;


    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequest badRequest) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", messageSource.getMessage(badRequest.getLocalizedMessage(), null, Locale.getDefault()));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(400)
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);

    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> PatientExistHandler(PatientNotFoundException notFoundException, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message :", notFoundException.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.CONTINUE.value())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PatientNotFoundWithNameException.class)
    public ResponseEntity<ErrorResponse> patientNotFoundWithName(PatientNotFoundWithNameException nameException, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", nameException.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.CONTINUE.value())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> appointmentNotFoundException(AppointmentNotFoundException notFoundException, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message", notFoundException.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DentistAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> dentistAlreadyExistException(DentistAlreadyExistException existException, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message", existException.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(errorResponse, CONFLICT);

    }

    @ExceptionHandler(DentistNotFoundException.class)
    public ResponseEntity<ErrorResponse> dentistNotFoundException(DentistNotFoundException notFoundException, HttpServletRequest httpRequest) {
        Map<String, String> error = new HashMap<>();
        error.put("error message", notFoundException.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(DentistUnavailableException.class)
    public ResponseEntity<ErrorResponse> dentistUnavailableException(
            DentistUnavailableException unavailableException, HttpServletRequest request) {

        Map<String, String> error = new HashMap<>();
        error.put("error message", unavailableException.getErrorMessage().getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> infoNotFoundException(InfoNotFoundException exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", exception.getErrorMessage().getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(error)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .iat(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyExistException(EmailAlreadyExistException exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", exception.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(error)
                .statusCode(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .iat(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", exception.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(error)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .iat(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(OktaRegistrationException.class)
    public ResponseEntity<ErrorResponse> oktaRegistrationException(OktaRegistrationException exception, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", exception.getErrorMessage().getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(error)
                .statusCode(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .iat(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, CONFLICT);

    }
}


