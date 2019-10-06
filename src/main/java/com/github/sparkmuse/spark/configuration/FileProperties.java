package com.github.sparkmuse.spark.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Slf4j
public class FileProperties {

    public static final String PATH_KEY = "file.path";

    private String path;

    public void load(String name) throws FileNotFoundException {
        ClassLoader classLoader = FileProperties.class.getClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(name)) {
            Properties rawProperties = new Properties();

            if (stream == null) {
                throw new FileNotFoundException();
            }
            rawProperties.load(stream);

            this.path = rawProperties.getProperty(PATH_KEY);

        } catch (IOException ex) {
            log.error("Missing {} file", name, ex);
            throw new FileNotFoundException();
        }
    }
}