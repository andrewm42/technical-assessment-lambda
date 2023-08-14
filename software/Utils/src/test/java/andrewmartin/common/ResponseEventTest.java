package andrewmartin.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ResponseEventTest {

    @Test
    public void testSuccess() {
        String json = "{\"median\":5.0}";
        ResponseEvent responseEvent = ResponseEvent.success(json);

        assertEquals(200, responseEvent.getStatusCode());
        assertEquals(json, responseEvent.getBody());
        assertTrue(responseEvent.getHeaders().containsKey("Content-Type"));
        assertEquals("application/json", responseEvent.getHeaders().get("Content-Type"));
    }

    @Test
    public void testFail() {
        String message = "Invalid JSON format";
        ResponseEvent responseEvent = ResponseEvent.fail(message);

        assertEquals(400, responseEvent.getStatusCode());
        assertEquals(String.format("{\"message\": \"%s\"}", message), responseEvent.getBody());
        assertTrue(responseEvent.getHeaders().containsKey("Content-Type"));
        assertEquals("application/json", responseEvent.getHeaders().get("Content-Type"));
    }
}
