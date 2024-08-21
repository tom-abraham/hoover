package com.robotics.hoover.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HooverExceptionHandler {
    Logger Log= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler
    public ResponseEntity <String> exceptionHandler(NullPointerException e){
        Log.error(e.getMessage());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String>  exceptionHandler(IllegalArgumentException e){
        Log.error(e.getMessage());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String>  exceptionHandler(Exception e){
        Log.error(e.getMessage());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
