package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.configuration.MySqlProperties;
import com.github.sparkmuse.spark.model.DeletionClean;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;

@RequiredArgsConstructor
public class WritterService {

    private final MySqlProperties mySqlProperties;

    public void write(Dataset<DeletionClean> deletions) {
        deletions.write()
                .mode(SaveMode.Append)
                .jdbc(mySqlProperties.getUrlString(), mySqlProperties.getTable(), mySqlProperties.getConnectionProperties());
    }
}