package com.example;

import okhttp3.*;

import java.io.IOException;

public class LaravelApiClient {
    private static final String API_URL = "http://localhost:8000/api/tweets";
    private static final String TOKEN = "2|IuwM6XeIjBXmnxmBEzaVpQyYAXHW6u7tD7S8yiMO0822e704";  // Genera uno nuevo en Laravel

    private final OkHttpClient client;

    public LaravelApiClient() {
        this.client = new OkHttpClient();
    }

    // Obtener todos los tweets
    public void getTweets() throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("✅ Tweets: " + response.body().string());
            } else {
                System.err.println("❌ Error al obtener tweets: " + response.message());
            }
        }
    }

    // Crear un nuevo tweet
    public void createTweet(String content) throws IOException {
        RequestBody body = RequestBody.create(
                "{\"content\":\"" + content + "\"}",
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Tweet creado con éxito.");
            } else {
                System.err.println("❌ Error al crear tweet: " + response.message());
            }
        }
    }

    // Eliminar un tweet
    public void deleteTweet(int tweetId) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL + "/" + tweetId)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Tweet eliminado con éxito.");
            } else {
                System.err.println("❌ Error al eliminar tweet: " + response.message());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        LaravelApiClient apiClient = new LaravelApiClient();

        // Llamadas a la API
        apiClient.getTweets();                  // Obtener tweets
        apiClient.createTweet("Hola desde Java!");  // Crear tweet
        apiClient.deleteTweet(1);               // Eliminar tweet con ID 1
    }
}
