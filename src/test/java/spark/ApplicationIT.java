package spark;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class ApplicationIT {

    @Test
    @DisplayName("application runs")
    void main() {

        String[] args = new String[]{};
        Application.main(args);
    }
}