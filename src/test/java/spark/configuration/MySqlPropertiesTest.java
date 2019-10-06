package spark.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MySqlPropertiesTest {

    @Test
    @DisplayName("loads properties from file")
    void loadProperties() {

        MySqlProperties mySqlProperties = new MySqlProperties();
        mySqlProperties.load("mysql.properties");
        assertNotNull(mySqlProperties.getTable());
    }
}