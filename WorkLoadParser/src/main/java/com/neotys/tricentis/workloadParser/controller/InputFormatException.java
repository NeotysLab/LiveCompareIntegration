package com.neotys.tricentis.workloadParser.controller;

public class InputFormatException extends RuntimeException {

    InputFormatException(String date) {
        super("Date needs to have the format yyyy-MM-dd'T'HH:mm:ss'Z' " + date);
    }
}