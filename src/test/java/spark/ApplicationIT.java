package spark;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationIT {

    @Test
    @DisplayName("application runs")
    void main() throws Exception{

        String[] args = new String[]{};
        Application.main(args);
    }
}