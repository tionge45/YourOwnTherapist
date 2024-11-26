package org.therapist.bot.api;


import java.net.http.*;
import java.net.URI;

public class APIClient {
    private static final String BASE_URL = "https://api.example.com/generate"; // Replace with actual URL

    public String fetchResponse(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString("{\"prompt\": \"" + prompt + "\"}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
