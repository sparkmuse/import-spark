package com.github.sparkmuse.spark.repository;

import com.github.sparkmuse.spark.model.ImportJob;
import com.github.sparkmuse.spark.model.ImportJob.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImportJobRepository extends MongoRepository<ImportJob, String> {
    Optional<ImportJob> findOneByStatus(Status status);
}
