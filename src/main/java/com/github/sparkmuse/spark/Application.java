
package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.configuration.FileProperties;
import com.github.sparkmuse.spark.configuration.InvalidPortNumberException;
import com.github.sparkmuse.spark.configuration.MySqlProperties;
import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.model.DeletionConverter;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException, InvalidPortNumberException {

        SparkSession sparkSession = SparkSession.builder()
                .appName("Importer Application")
                .master("local[1]")
                .getOrCreate();

        MySqlProperties mySqlProperties = new MySqlProperties();
        mySqlProperties.load("mysql.properties");

        FileProperties fileProperties = new FileProperties();
        fileProperties.load("file.properties");

        String[] headers = {"creationTimestamp", "creator", "deletionTimestamp", "deletor",
                "subject", "predicate", "object", "languageCode"};

        sparkSession
                .read()
                .csv( fileProperties.getPath())
                .toDF(headers)
                .as(Encoders.bean(Deletion.class))
                .map((MapFunction<Deletion, DeletionClean>) DeletionConverter::from, Encoders.bean(DeletionClean.class))
                .write()
                .mode(SaveMode.Append)
                .jdbc(mySqlProperties.getUrlString(), mySqlProperties.getTable(), mySqlProperties.getConnectionProperties());
    }
}