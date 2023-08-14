package andrewmartin.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class StatsTest {

    @Test
    public void testGetMedian() {
        double medianValue = 5.0;
        Stats stats = new Stats(medianValue);
        assertEquals(medianValue, stats.getMedian());
    }

    @Test
    public void testToJsonSuccess() throws JsonProcessingException {
        double medianValue = 5.0;
        Stats stats = new Stats(medianValue);

        String expectedJson = "{\"median\":5.0}";

        String actualJson = stats.toJson();

        assertEquals(expectedJson, actualJson);
    }
}