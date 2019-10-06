package com.github.sparkmuse.spark.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilePropertiesTest {

    private FileProperties fileProperties;

    @BeforeEach
    void setUp() {
        fileProperties = new FileProperties();
    }

    @Test
    @DisplayName("loads properties from file")
    void loadProperties() throws Exception {

        fileProperties.load("file-test.properties");

        assertThat(fileProperties.getPath()).isEqualTo("test.csv");
    }

    @Test
    @DisplayName("throws exception when file not found")
    void missingFile() {

        assertThatThrownBy(() -> fileProperties.load("invalid-file-name"))
                .isInstanceOf(FileNotFoundException.class);
    }
}