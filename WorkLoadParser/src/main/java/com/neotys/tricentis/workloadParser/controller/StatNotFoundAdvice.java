package com.neotys.tricentis.workloadParser.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StatNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(StadsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String statNotFoundAdvice(StadsNotFoundException ex) {
        return ex.getMessage();
    }
}
