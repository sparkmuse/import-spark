package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.model.DeletionConverter;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    private static final String[] headers = {"creationTimestamp", "creator", "deletionTimestamp", "deletor",
            "subject", "predicate", "object", "languageCode"};

    public Dataset<DeletionClean> map(Dataset<Row> csv) {
        return csv.toDF(headers)
                .as(Encoders.bean(Deletion.class))
                .map((MapFunction<Deletion, DeletionClean>) DeletionConverter::from, Encoders.bean(DeletionClean.class));
    }
}