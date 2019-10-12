package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.model.DeletionClean;
import com.github.sparkmuse.spark.service.extension.SparkExtension;
import com.github.sparkmuse.spark.service.extension.SparkTest;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@SparkTest
class MapperServiceTest {

    private final SparkSession SPARK_SESSION = SparkExtension.getSparksession();

    private MapperService mapperService;

    @BeforeEach
    void setUp() {
        mapperService = new MapperService(SPARK_SESSION);
    }

    @Test
    @DisplayName("maps to DeletionClean dataset from row dataset in a list")
    void canMapElements() {

        List<String> list = asList(
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en",
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en");
        Dataset<String> csvString = SPARK_SESSION.createDataset(list, Encoders.STRING());
        Dataset<Row> csv = SPARK_SESSION.read().csv(csvString);

        List<DeletionClean> expected = asList(
                createDeletion(),
                createDeletion());

        Dataset<DeletionClean> actualDataSet = mapperService.map(csv);

        List<DeletionClean> actualList = actualDataSet.collectAsList();

        assertThat(actualList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("maps to empty dataset from empty list")
    void canMapEmptyList() {

        Dataset<Row> csv = SPARK_SESSION.emptyDataFrame();
        List<DeletionClean> expected = emptyList();

        Dataset<DeletionClean> actualDataSet = mapperService.map(csv);

        List<DeletionClean> actualList = actualDataSet.collectAsList();

        assertThat(actualList).isEmpty();
    }

    private static DeletionClean createDeletion() {
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