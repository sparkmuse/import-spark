package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.repository.ImportJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SparkImportServiceTest {

    @Mock
    private ReaderService readerService;

    @Mock
    private MapperService mapperService;

    @Mock
    private WritterService writterService;

    @Mock
    private ImportJobRepository importJobRepository;

    private SparkImportService sparkImportService;

    @BeforeEach
    void setUp() {
        this.sparkImportService =
                new SparkImportService(readerService, mapperService, writterService, importJobRepository);
    }

    @Test
    @DisplayName("calls all other methods .... Do we even need this 🧐?")
    void process() {

        sparkImportService.process();

        verify(readerService).read();
        verify(mapperService).map(any());
        verify(writterService).write(any());
        verify(importJobRepository).deleteAll();

    }
}