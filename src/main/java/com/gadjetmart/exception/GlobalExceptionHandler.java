package com.gadjetmart.exception;

import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.exception.types.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ResponseDto> userNotFoundException(UserNotFoundException ex) {
        ex.printStackTrace();
        log.error("Given user id not found!");
        return new ResponseEntity<>(new ResponseDto("500","Given User ID Not Found!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public final ResponseEntity<ResponseDto> emailNotFoundException(EmailNotFoundException ex) {
        ex.printStackTrace();
        log.error("The Given Email Address Not Found!");
        return new ResponseEntity<>(new ResponseDto("500","The Given Email Address Not Found!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public final ResponseEntity<ResponseDto> emailNotValidException(EmailNotValidException ex) {
        ex.printStackTrace();
        log.error("Please Insert a Valid Email Address!");
        return new ResponseEntity<>(new ResponseDto("500","Please Insert a Valid Email Address!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}