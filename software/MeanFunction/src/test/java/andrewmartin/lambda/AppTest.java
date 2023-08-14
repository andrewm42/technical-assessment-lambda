package andrewmartin.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppTest {
    @Test
    public void testHandleRequest_Success() throws JsonProcessingException {
        App app = new App();
        ObjectMapper objectMapper = new ObjectMapper();

        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        List<Double> numbers = Arrays.asList(1.0, 2.0, 3.0, 4.0);
        requestEvent.setBody(objectMapper.writeValueAsString(numbers));

        Context context = mock(Context.class);
        APIGatewayProxyResponseEvent responseEvent = app.handleRequest(requestEvent, context);

        assertNotNull(responseEvent);
        assertEquals(200, responseEvent.getStatusCode());

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "application/json");
        assertEquals(expectedHeaders, responseEvent.getHeaders());

        Map<String, Double> responseData = objectMapper.readValue(responseEvent.getBody(),
                new TypeReference<Map<String, Double>>() {
                });
        assertTrue(responseData.containsKey("mean"));
        assertEquals(2.5, responseData.get("mean"));
    }

    @Test
    public void testHandleRequest_InvalidJSON() {
        App app = new App();

        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setBody("invalid-json"); // Invalid JSON format

        Context context = mock(Context.class);

        APIGatewayProxyResponseEvent responseEvent = app.handleRequest(requestEvent, context);

        assertNotNull(responseEvent);
        assertEquals(400, responseEvent.getStatusCode());
        assertEquals("{\"message\": \"Invalid JSON format\"}", responseEvent.getBody());
    }
}
