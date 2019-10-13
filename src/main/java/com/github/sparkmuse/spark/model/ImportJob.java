package com.github.sparkmuse.spark.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Document
public class ImportJob {

    @Id
    private String id;
    private Status status;

    public enum Status{
        RUNNING,
        OTHER
    }

    @Builder
    private ImportJob(Status status) {
        this.id = UUID.randomUUID().toString();
        this.status = status;
    }
}
