
package spark;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import spark.configuration.MySqlProperties;

public class Application {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
                .appName("Importer Application")
                .master("local[1]")
                .getOrCreate();

        MySqlProperties mySqlProperties = new MySqlProperties();

        String path = "/Users/alfredo/Downloads/deletions/schema.csv";

        String[] headers = {"creationTimestamp", "creator", "deletionTimestamp", "deletor",
                "subject", "predicate", "object", "languageCode"};


        sparkSession
                .read()
                .csv(path)
                .toDF(headers)
                .as(Encoders.bean(Deletion.class))
                .map((MapFunction<Deletion, DeletionClean>) DeletionConverter::from, Encoders.bean(DeletionClean.class))
                .write()
                .mode(SaveMode.Append)
                .jdbc(mySqlProperties.getUrlString(), mySqlProperties.getTable(), mySqlProperties.getConnectionProperties());
    }
}