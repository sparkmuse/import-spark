package com.github.sparkmuse.spark.config;

import com.github.sparkmuse.spark.properties.SparkProperties;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean(destroyMethod = "close")
    public SparkSession sparkSession(SparkProperties sparkProperties) {
        return SparkSession.builder()
                .appName(sparkProperties.getName())
                .master(sparkProperties.getUri())
                .getOrCreate();
    }
}
