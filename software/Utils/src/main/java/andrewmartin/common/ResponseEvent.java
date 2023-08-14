package andrewmartin.common;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ResponseEvent extends APIGatewayProxyResponseEvent {
    private static ResponseEvent createResponse(String json, Integer statusCode) {
        APIGatewayProxyResponseEvent response = new ResponseEvent();
        response.setHeaders(
            new HashMap<String, String>() {{
            put("Content-Type", "application/json");
        }});
        response.setBody(json);
        response.setStatusCode(statusCode);

        return (ResponseEvent) response;
    }

    public static ResponseEvent success(String json) {
        return ResponseEvent.createResponse(json, 200);
    }

    public static ResponseEvent fail(String message) {
        return ResponseEvent.createResponse(String.format("{\"message\": \"%s\"}", message), 400);
    }
}
