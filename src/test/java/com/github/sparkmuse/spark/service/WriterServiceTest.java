package com.github.sparkmuse.spark.service;

import com.github.sparkmuse.spark.config.PropertiesConfig;
import com.github.sparkmuse.spark.config.SparkConfig;
import com.github.sparkmuse.spark.model.Deletion;
import com.github.sparkmuse.spark.properties.MySqlProperties;
import com.github.sparkmuse.spark.properties.SparkProperties;
import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        MySqlProperties.class, SparkConfig.class, SparkProperties.class, PropertiesConfig.class, WriterService.class
})
@ActiveProfiles("test")
class WriterServiceTest {

    @Autowired
    private MySqlProperties properties;

    @Autowired
    private WriterService writerService;

    @Autowired
    private SparkSession sparkSession;

    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        connection = DriverManager.getConnection(
                properties.getUrl(),
                properties.getConnection().getUser(),
                properties.getConnection().getPassword());

        String sql = FileUtils.readFileToString(ResourceUtils.getFile("classpath:schema.sql"));
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    @AfterEach
    void close() throws Exception {
        connection.close();
    }

    @Test
    @DisplayName("saves dataset to the database")
    void save() throws Exception {

        List<Deletion> expected = singletonList(createDeletion());
        Dataset<Deletion> dataSet = sparkSession.createDataset(expected, Encoders.bean(Deletion.class));
        writerService.write(dataSet);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from deletions");
        List<Deletion> actual = getDeletionCleans(resultSet);
        resultSet.close();

        assertThat(actual)
                .usingFieldByFieldElementComparator()
                .usingElementComparatorIgnoringFields("creationDateTime", "deletionDateTime")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("saves NOT empty dataset to the database")
    void saveNot() throws Exception {

        Dataset<Deletion> dataSet = sparkSession.emptyDataset(Encoders.bean(Deletion.class));
        writerService.write(dataSet);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from deletions");
        List<Deletion> actual = getDeletionCleans(resultSet);
        resultSet.close();

        assertThat(actual).isEmpty();
    }

    private static List<Deletion> getDeletionCleans(ResultSet resultSet) throws SQLException {
        List<Deletion> result = new ArrayList<>();
        while (resultSet.next()) {
            final Deletion deletion = Deletion.builder()
                    .creationDateTime(resultSet.getString("creationDateTime"))
                    .creator(resultSet.getString("creator"))
                    .deletionDateTime(resultSet.getString("deletionDateTime"))
                    .deletor(resultSet.getString("deletor"))
                    .subject(resultSet.getString("subject"))
                    .predicate(resultSet.getString("predicate"))
                    .object(resultSet.getString("object"))
                    .languageCode(resultSet.getString("languageCode"))
                    .build();
            result.add(deletion);
        }
        return result;
    }

    private static Deletion createDeletion() {
        return Deletion.builder()
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