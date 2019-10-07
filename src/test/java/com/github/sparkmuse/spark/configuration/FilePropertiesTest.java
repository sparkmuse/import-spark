package com.github.sparkmuse.spark.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilePropertiesTest {


    @Test
    @DisplayName("loads properties from file")
    void loadProperties() throws Exception {

        FileProperties fileProperties = new FileProperties("file-test.properties");

        assertThat(fileProperties.getPath()).isEqualTo("test.csv");
    }

    @Test
    @DisplayName("throws exception when file not found")
    void missingFile() {

        assertThatThrownBy(() -> new FileProperties("invalid-file-name"))
                .isInstanceOf(FileNotFoundException.class);
    }
}