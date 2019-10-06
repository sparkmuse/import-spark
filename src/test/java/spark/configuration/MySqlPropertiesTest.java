package spark.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MySqlPropertiesTest {

    @Test
    @DisplayName("loads properties from file")
    void loadProperties() throws Exception {

        MySqlProperties mySqlProperties = new MySqlProperties();
        mySqlProperties.load("mysql.properties");

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "root");
        connectionProperties.put("Driver", "com.mysql.cj.jdbc.Driver");

        assertThat(mySqlProperties.getHost()).isEqualTo("localhost");
        assertThat(mySqlProperties.getPort()).isEqualTo(3306);
        assertThat(mySqlProperties.getServerTimezone()).isEqualTo("Europe/Berlin");
        assertThat(mySqlProperties.getDataBase()).isEqualTo("DataHorizontal");
        assertThat(mySqlProperties.getTable()).isEqualTo("deletions");
        assertThat(mySqlProperties.getConnectionProperties()).containsExactlyEntriesOf(connectionProperties);
    }

    @Test
    @DisplayName("throws exception when file not found")
    void missingFile() {

        MySqlProperties mySqlProperties = new MySqlProperties();

        assertThatThrownBy(() -> mySqlProperties.load("invalid-file-name"))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    @DisplayName("throws exception when port cannot be parsed")
    void unparsablePort() {

        MySqlProperties mySqlProperties = new MySqlProperties();

        assertThatThrownBy(() -> mySqlProperties.load("mysql-invalid-port.properties"))
                .isInstanceOf(InvalidPortNumberException.class);
    }
}