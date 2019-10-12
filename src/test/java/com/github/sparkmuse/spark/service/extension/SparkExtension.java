package com.github.sparkmuse.spark.service.extension;

import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class SparkExtension implements BeforeAllCallback, AfterAllCallback {

    private static final Namespace NAMESPACE = Namespace.create("com", "spark");

    private static SparkSession sparksession;

    public static SparkSession getSparksession() {
        return sparksession;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        sparksession.close();

    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        sparksession = SparkSession.builder()
                .appName("Test spark")
                .master("local[1]")
                .getOrCreate();
    }
}
