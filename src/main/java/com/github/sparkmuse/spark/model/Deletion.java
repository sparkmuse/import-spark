package com.github.sparkmuse.spark.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deletion implements Serializable {
    private String creationDateTime;
    private String creator;
    private String deletionDateTime;
    private String deletor;
    private String subject;
    private String predicate;
    private String object;
    private String languageCode;
}