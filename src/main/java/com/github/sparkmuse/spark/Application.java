
package com.github.sparkmuse.spark;

import com.github.sparkmuse.spark.configuration.FileProperties;
import com.github.sparkmuse.spark.configuration.InvalidPortNumberException;
import com.github.sparkmuse.spark.configuration.MySqlProperties;
import com.github.sparkmuse.spark.service.MapperService;
import com.github.sparkmuse.spark.service.ReaderService;
import com.github.sparkmuse.spark.service.WritterService;
import org.apache.spark.sql.SparkSession;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException, InvalidPortNumberException {

        SparkSession sparkSession = SparkSession.builder()
                .appName("Importer Application")
                .master("local[1]")
                .getOrCreate();

        WritterService writterService = new WritterService(new MySqlProperties("mysql.properties"));
        ReaderService readerService = new ReaderService(new FileProperties("file.properties"));
        MapperService mapperService = new MapperService();

        ImportService importService = new ImportService(sparkSession, readerService, mapperService, writterService);
        importService.process();
    }
}