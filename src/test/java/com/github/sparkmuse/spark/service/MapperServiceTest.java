package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.DeletionClean;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class MapperServiceTest {

    private static SparkSession sparksession;
    private MapperService mapperService;

    @BeforeAll
    static void initSparkSession() {
        sparksession = SparkSession.builder()
                .appName("Test spark")
                .master("local[1]")
                .getOrCreate();
    }

    @AfterAll
    static void closeSparkSession() {
        sparksession.close();
    }

    @BeforeEach
    void setUp() {
        mapperService = new MapperService();
    }

    @Test
    @DisplayName("maps to DeletionClean dataset from row dataset in a list")
    void canMapElements() {

        List<String> list = asList(
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en",
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en");
        Dataset<String> csvString = sparksession.createDataset(list, Encoders.STRING());
        Dataset<Row> csv = sparksession.read().csv(csvString);

        DeletionClean deletionClean1 = createDeletion();
        DeletionClean deletionClean2 = createDeletion();
        List<DeletionClean> expected = asList(deletionClean1, deletionClean2);

        Dataset<DeletionClean> actualDataSet = mapperService.map(csv);

        List<DeletionClean> actualList = actualDataSet.collectAsList();

        assertThat(actualList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private DeletionClean createDeletion() {
        return DeletionClean.builder()
                .creationDateTime("2019-12-01T00:00:01")
                .creator("creator")
                .deletionDateTime("2019-12-01T00:00:01")
                .deletor("deletor")
                .subject("subject")
                .predicate("predicate")
                .object("object")
                .languageCode("en")
                .build();
    }
}