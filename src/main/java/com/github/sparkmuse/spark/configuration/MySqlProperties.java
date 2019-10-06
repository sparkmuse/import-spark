package com.github.sparkmuse.spark.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Slf4j
public class MySqlProperties {

    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "Driver";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String DATA_BASE = "database";
    public static final String TABLE = "table";
    public static final String SERVER_TIME_ZONE = "serverTimezone";

    private String host;
    private int port;
    private String dataBase;
    private String table;
    private String serverTimezone;

    private final Properties connectionProperties;

    public MySqlProperties() {
        this.connectionProperties = new Properties();
    }

    public void load(String name) throws FileNotFoundException, InvalidPortNumberException {
        ClassLoader classLoader = MySqlProperties.class.getClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(name)) {
            Properties rawProperties = new Properties();

            if (stream == null) {
                throw new FileNotFoundException();
            }
            rawProperties.load(stream);

            this.connectionProperties.put(USER, rawProperties.getProperty(USER));
            this.connectionProperties.put(PASSWORD, rawProperties.getProperty(PASSWORD));
            this.connectionProperties.put(DRIVER, rawProperties.getProperty(DRIVER));

            this.host = rawProperties.getProperty(HOST);
            this.port = Integer.parseInt(rawProperties.getProperty(PORT));
            this.dataBase = rawProperties.getProperty(DATA_BASE);
            this.table = rawProperties.getProperty(TABLE);
            this.serverTimezone = rawProperties.getProperty(SERVER_TIME_ZONE);

        } catch (IOException ex) {
            log.error("Missing {} file", name, ex);
            throw new FileNotFoundException();
        } catch (NumberFormatException ex) {
            log.error("Invalid port number", ex);
            throw new InvalidPortNumberException();
        }
    }

    public String getUrlString() {
        return String.format("jdbc:mysql://%s:%d/%s?serverTimezone=%s", this.host, this.port, this.dataBase, this.serverTimezone);
    }
}
