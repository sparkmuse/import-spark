package com.github.sparkmuse.spark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deletionclean")
public class DeletionCleanController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDeletionClean() {
    }
}
