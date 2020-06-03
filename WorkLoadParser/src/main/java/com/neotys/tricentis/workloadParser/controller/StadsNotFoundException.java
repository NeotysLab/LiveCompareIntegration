package com.neotys.tricentis.workloadParser.controller;

public class StadsNotFoundException extends RuntimeException {

    StadsNotFoundException(String date) {
        super("Could not find any statistics on date " + date);
    }
}