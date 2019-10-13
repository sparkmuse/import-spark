package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.properties.MySqlProperties;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WritterService {

    private final MySqlProperties mySqlProperties;

    public void write(Dataset<Deletion> deletions) {
        deletions.write()
                .mode(SaveMode.Append)
                .jdbc(mySqlProperties.getUrl(), mySqlProperties.getTable(), mySqlProperties.getConnectionProperties());
    }
}