package org.therapist.bot.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class APIClient {
    private static final String BASE_URL = "http://localhost:8080/v1/chat/completions";
    private static final String MODEL = "LLaMA_CPP";
    private static final String AUTH_KEY = "Bearer no-key";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String fetchResponse(String language, String emotion) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Construct the prompt
        String prompt = "Generate a response in " + language + " for the emotion: " + emotion;

        // Construct the request body as a JSON map
        Map<String, Object> requestBody = Map.of(
                "model", MODEL,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        // Convert the request body to JSON
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        System.out.println("Generated JSON Payload: " + requestBodyJson);

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", AUTH_KEY) // Set authorization header
                .header("Content-Type", "application/json") // Set content type
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();


        // Send the HTTP request and return the response body
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status code: " + response.statusCode());
        System.out.println("API response: " + response.body());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error: " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }

}




