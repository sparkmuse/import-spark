package com.github.sparkmuse.spark.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "mysql")
@Getter
@Setter
public class MySqlProperties {

    private String host;
    private int port;
    private String dataBase;
    private String table;
    private String serverTimezone;
    private Connection connection;

    @Setter
    private static class Connection {
        private String user;
        private String password;
        private String driver;
    }

    public String getUrlString() {
        return String.format("jdbc:mysql://%s:%d/%s?serverTimezone=%s",
                this.host, this.port, this.dataBase, this.serverTimezone);
    }

    public Properties getConnectionProperties() {
        final Properties properties = new Properties();
        properties.put("user", this.connection.user);
        properties.put("password", this.connection.password);
        properties.put("Driver", connection.driver);
        return properties;
    }
}
