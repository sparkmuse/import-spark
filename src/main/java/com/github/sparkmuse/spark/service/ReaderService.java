package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.configuration.FileProperties;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@RequiredArgsConstructor
public class ReaderService {

    private final FileProperties fileProperties;

    public Dataset<Row> read(SparkSession sparkSession) {
        return sparkSession
                .read()
                .csv(fileProperties.getPath());
    }
}