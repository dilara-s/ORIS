package org.example.hw_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        // GET-запрос
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts/1/comments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            System.out.println(readResponse(connection));
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // POST-запрос
        try {
            URL postUrl = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setRequestProperty("Accept", "application/json");
            postConnection.setRequestProperty("Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7");
            postConnection.setDoOutput(true);
            String jsonInput = "{\"name\": \"Sen. Anala Iyer\",\"email\": \"dsen_anala_iyer123@stroman-leannon.test\",\"gender\": \"female\",\"status\": \"active\"}";
            try (OutputStream outputStream = postConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            System.out.println(postConnection.getResponseCode());
            System.out.println(readResponse(postConnection));
            postConnection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // PUT-запрос
        try {
            URL putUrl = new URL("https://jsonplaceholder.typicode.com/posts/1");
            HttpURLConnection putConnection = (HttpURLConnection) putUrl.openConnection();
            putConnection.setRequestMethod("PUT");
            putConnection.setRequestProperty("Content-Type", "application/json");
            putConnection.setRequestProperty("Accept", "application/json");
            putConnection.setRequestProperty("Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7");
            putConnection.setDoOutput(true);
            String jsonInput = "{\"name\": \"Updated Name\",\"email\": \"updated_email@example.com\",\"gender\": \"female\",\"status\": \"active\"}";
            try (OutputStream outputStream = putConnection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            System.out.println(putConnection.getResponseCode());
            System.out.println(readResponse(putConnection));
            putConnection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // DELETE-запрос
        try {
            URL deleteUrl = new URL("https://jsonplaceholder.typicode.com/posts/1");
            HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
            deleteConnection.setRequestMethod("DELETE");
            deleteConnection.setRequestProperty("Content-Type", "application/json");
            deleteConnection.setRequestProperty("Accept", "application/json");
            deleteConnection.setRequestProperty("Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7");
            System.out.println(deleteConnection.getResponseCode());
            System.out.println(readResponse(deleteConnection));
            deleteConnection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readResponse(HttpURLConnection connection) throws IOException {
        if (connection != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                return content.toString();
            }
        }
        return null;
    }
}
