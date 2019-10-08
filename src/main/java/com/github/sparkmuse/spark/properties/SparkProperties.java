package com.github.sparkmuse.spark.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spark")
@Getter
@Setter
public class SparkProperties {
    private String name;
    private String uri;
}