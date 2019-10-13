package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.ImportJob;
import com.github.sparkmuse.spark.repository.ImportJobRepository;
import com.github.sparkmuse.spark.service.exception.JobAlreadyRunningException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.github.sparkmuse.spark.model.ImportJob.Status.RUNNING;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final SparkImportService sparkImportService;
    private final ImportJobRepository importJobRepository;

    public ImportJob process() {

        Optional<ImportJob> job = importJobRepository.findOneByStatus(RUNNING);
        if (job.isPresent()) {
            throw new JobAlreadyRunningException("Only one job can be running");
        }

        ImportJob runningJob = ImportJob.builder()
                .status(RUNNING)
                .build();
        importJobRepository.save(runningJob);

        sparkImportService.process();

        return runningJob;
    }
}
