package andrewmartin.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

public class StatsTest {

    @Test
    public void testGetMean() {
        double meanValue = 5.0;
        Stats stats = new Stats(meanValue);
        assertEquals(meanValue, stats.getMean());
    }

    @Test
    public void testToJsonSuccess() throws JsonProcessingException {
        double meanValue = 5.0;
        Stats stats = new Stats(meanValue);

        String expectedJson = "{\"mean\":5.0}";

        String actualJson = stats.toJson();

        assertEquals(expectedJson, actualJson);
    }
}