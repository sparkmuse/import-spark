
package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class Application {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .appName("Importer Application")
                .master("local[1]")
                .getOrCreate();

        String path = "/Users/alfredo/Downloads/deletions/schema.csv";

        String[] headers = {"creationTimestamp", "creator", "deletionTimestamp", "deletor",
                "subject", "predicate", "object", "languageCode"};

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "root");
        properties.put("Driver", com.mysql.cj.jdbc.Driver.class.getName());
        sparkSession
                .read()
                .csv(path)
                .toDF(headers)
                .as(Encoders.bean(Deletion.class))
                .map((MapFunction<Deletion, DeletionClean>) DeletionConverter::from, Encoders.bean(DeletionClean.class))
                .write()
                .mode(SaveMode.Append)
                .jdbc("jdbc:mysql://127.0.0.1:3306/DataHorizontal?serverTimezone=Europe/Berlin", "deletions", properties);
        ;
    }
}