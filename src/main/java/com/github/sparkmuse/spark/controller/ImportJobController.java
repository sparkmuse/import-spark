package com.github.sparkmuse.spark.controller;

import com.github.sparkmuse.spark.model.ImportJob;
import com.github.sparkmuse.spark.service.ImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/importjobs")
@RequiredArgsConstructor
public class ImportJobController {

    private final ImportService importService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImportJob createDeletion() {
        return importService.process();
    }
}