package com.github.sparkmuse.spark.repository;

import com.github.sparkmuse.spark.model.ImportJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static com.github.sparkmuse.spark.model.ImportJob.Status.OTHER;
import static com.github.sparkmuse.spark.model.ImportJob.Status.RUNNING;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class ImportJobRepositoryTest {

    @Autowired
    private ImportJobRepository repository;

    @Autowired
    private MongoOperations operations;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("writes to database")
    void write() {

        ImportJob importJob = ImportJob.builder().status(RUNNING).build();
        repository.save(importJob);

        Query query = Query.query(Criteria.where("status").is(RUNNING));
        ImportJob actual = operations.findOne(query, ImportJob.class);

        assertThat(actual)
                .isEqualToComparingFieldByField(importJob);
    }

    @Test
    @DisplayName("returns optional of the running job from database")
    void findByStatus() {

        ImportJob importJob = ImportJob.builder().status(RUNNING).build();
        operations.save(importJob);

        Optional<ImportJob> actual = repository.findOneByStatus(RUNNING);

        assertThat(actual)
                .isPresent()
                .get()
                .isEqualToComparingFieldByField(importJob);
    }

    @Test
    @DisplayName("returns optional empty when the job was not found")
    void notFoundId() {

        Optional<ImportJob> actual = repository.findOneByStatus(OTHER);
        assertThat(actual).isEmpty();
    }
}