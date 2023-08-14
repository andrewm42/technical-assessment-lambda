package andrewmartin.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import andrewmartin.common.Statistics;
import andrewmartin.common.ResponseEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Statistics statistics = new Statistics();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        // Extract the message body from the request
        String requestBody = input.getBody();

        try {
            // Deserialize the JSON array into a List of numbers
            List<Double> numbers = objectMapper.readValue(requestBody, new TypeReference<List<Double>>() {});

            // Calculate mean
            double median = statistics.calculateMedian(numbers);

            // Create a Stats instance
            Stats stats = new Stats(median);

            // Create the OK response
            return ResponseEvent.success(stats.toJson());
        } catch (JsonProcessingException e) {
            // Configure the Bad Request response
            return ResponseEvent.fail("Invalid JSON format");
        }
    }
}