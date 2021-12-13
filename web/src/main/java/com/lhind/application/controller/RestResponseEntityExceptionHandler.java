package com.lhind.application.controller;

import com.lhind.application.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        logger.info("Handling not found exception.");

        ObjectError error = new ObjectError("resourceNotFound", "Resource not found.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception e, WebRequest request) {
        logger.info("Handling bad request exception.");

        ObjectError error = new ObjectError("badRequest",
                e.getMessage() == null ? "Cannot process this request." : e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(Exception e, WebRequest request) {
        logger.info("Handling number format exception.");

        ObjectError error = new ObjectError("invalidParameter", "Not a valid request parameter");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDateTimeException.class})
    public ResponseEntity<Object> handleInvalidDateTimeException(Exception e, WebRequest request) {
        logger.info("Handling Invalid DateTime Exception.");

        ObjectError error = new ObjectError("invalidDateTime", "Not a valid datetime.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(Exception e, WebRequest request) {
        logger.info("Handling validation exception.");

        ObjectError error = new ObjectError("validatingDates", e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotLoggedInException.class})
    public ResponseEntity<Object> handleUserNotLoggedInException() {
        logger.info("Handling not logged in exception.");

        ObjectError error = new ObjectError("authentication", "User is not logged in.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<Object> handleInvalidTokenRequestException(Exception e) {
        log.info("Handling invalid token request exception");

        ObjectError error = new ObjectError("invalidToken", e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("Handling http message not readable exception.");

        ObjectError error;

        if (ex.getLocalizedMessage().contains("Date")) {
            error = new ObjectError("invalidDate", "Date is not is the correct format.");
        } else {
            error = new ObjectError("tripReason", "Trip reason must be a valid reason.");
        }

        return new ResponseEntity<>(error, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        logger.info("Handling method argument not valid exception.");

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.forEach(error -> System.out.println(error.toString()));

        return new ResponseEntity<>(errors, headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(HttpServletResponse response) {
        logger.info("Handling constraint violation exception.");

        ObjectError error = new ObjectError("invalidId", "Id should be greater than 0.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
