package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.properties.FileProperties;
import com.github.sparkmuse.spark.service.extension.SparkExtension;
import com.github.sparkmuse.spark.service.extension.SparkTest;
import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SparkTest
@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {

    private SparkSession SPARK_SESSION = SparkExtension.getSparksession();

    @Mock
    private FileProperties fileProperties;

    @TempDir
    Path tempDir;

    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderService(fileProperties, SPARK_SESSION);
    }

    @Test
    @DisplayName("reads data from csv")
    void readsFromfile() throws Exception {

        List<String> lines = asList(
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en",
                "1575158401000,/user/creator,1575158401000,/user/deletor,subject,predicate,object,en");
        File tempFile = new File(tempDir.toFile(), "data.csv");
        FileUtils.writeLines(tempFile, lines);

        when(fileProperties.getPath()).thenReturn(tempFile.getPath());

        List<Row> actual = readerService.read().collectAsList();

        assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("throws exception when file cannot be found")
    void readsError() {

        when(fileProperties.getPath()).thenReturn("/no-file-sorry");

        assertThatThrownBy(() -> readerService.read().collectAsList())
                .isInstanceOf(AnalysisException.class)
                .hasMessageContaining("Path does not exist");
    }
}