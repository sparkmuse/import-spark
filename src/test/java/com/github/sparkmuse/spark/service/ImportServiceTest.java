package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.ImportJob;
import com.github.sparkmuse.spark.repository.ImportJobRepository;
import com.github.sparkmuse.spark.service.exception.JobAlreadyRunningException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.sparkmuse.spark.model.ImportJob.Status.RUNNING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportServiceTest {

    @Mock
    private ImportJobRepository repository;

    @Mock
    private SparkImportService sparkImportService;

    private ImportService importService;

    @BeforeEach
    void setUp() {
        this.importService = new ImportService(sparkImportService, repository);
    }

    @Test
    @DisplayName("throws JobAlreadyRunningException when there is a job running")
    void foundJob() {

        ImportJob importJob = ImportJob.builder().status(RUNNING).build();
        when(repository.findOneByStatus(RUNNING)).thenReturn(Optional.of(importJob));

        assertThatThrownBy(() -> importService.process())
                .isInstanceOf(JobAlreadyRunningException.class)
                .hasMessage("Only one job can be running");

    }

    @Test
    @DisplayName("starts processing the job")
    void process() {

        ImportJob importJob = ImportJob.builder().status(RUNNING).build();
        when(repository.findOneByStatus(RUNNING)).thenReturn(Optional.empty());

        importService.process();

        verify(repository).findOneByStatus(RUNNING);
        verify(repository).save(any(ImportJob.class));
    }
}