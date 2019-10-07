package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.configuration.FileProperties;
import com.github.sparkmuse.spark.configuration.MySqlProperties;
import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.model.DeletionConverter;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;

@RequiredArgsConstructor
public class ImportService {

    private final SparkSession sparkSession;
    private final FileProperties fileProperties;
    private final MySqlProperties mySqlProperties;

    public void process() {
        String[] headers = {"creationTimestamp", "creator", "deletionTimestamp", "deletor",
                "subject", "predicate", "object", "languageCode"};

        Dataset<Row> csv = sparkSession
                .read()
                .csv(fileProperties.getPath());

        Dataset<DeletionClean> deletions = csv.toDF(headers)
                .as(Encoders.bean(Deletion.class))
                .map((MapFunction<Deletion, DeletionClean>) DeletionConverter::from, Encoders.bean(DeletionClean.class));

        deletions.write()
                .mode(SaveMode.Append)
                .jdbc(mySqlProperties.getUrlString(), mySqlProperties.getTable(), mySqlProperties.getConnectionProperties());
    }
}
