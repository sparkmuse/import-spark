
package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.configuration.FileProperties;
import com.github.sparkmuse.spark.configuration.InvalidPortNumberException;
import com.github.sparkmuse.spark.configuration.MySqlProperties;
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

        ImportService importService = new ImportService(sparkSession, fileProperties, mySqlProperties);
        importService.process();
    }
}