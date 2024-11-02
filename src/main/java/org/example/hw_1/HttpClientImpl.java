package org.example.hw_1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientImpl implements HttpClient {

    public static void main(String[] args) {
        HttpClient client = new HttpClientImpl();
        //Get - запрос
        Map<String, String> headers = Map.of("Content-Type", "application/json");

        String getResponse = client.get("https://jsonplaceholder.typicode.com/posts/1/comments?postId=1", headers, null);
        System.out.println(getResponse);

        //Post - запрос
        Map<String, String> postHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        Map<String, String> postData = Map.of(
                "name", "Sen. Anala Iyer",
                "email", "dsen_anala_iyer123@stroman-leannon.test",
                "gender", "female",
                "status", "active"
        );
        String postResponse = client.post("https://jsonplaceholder.typicode.com/posts", postHeaders, postData);
        System.out.println(postResponse);

        //Put - запрос
        Map<String, String> putHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        Map<String, String> putData = Map.of(
                "name", "Updated Name",
                "email", "updated_email@example.com",
                "gender", "female",
                "status", "active"
        );
        String putResponse = client.put("https://jsonplaceholder.typicode.com/posts/1", putHeaders, putData);
        System.out.println(putResponse);

        //Delete - запрос
        Map<String, String> deleteHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        String deleteResponse = client.delete("https://jsonplaceholder.typicode.com/posts/1", deleteHeaders, null);
        System.out.println(deleteResponse);
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

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            System.out.println(readResponse(connection));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.disconnect();
            return readResponse(connection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL postUrl = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    postConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            postConnection.setDoOutput(true);

            if (data != null) {
                String jsonInput = mapToJsonFile(data);
                try (OutputStream outputStream = postConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }
            postConnection.disconnect();
            return readResponse(postConnection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL putUrl = new URL(url);
            HttpURLConnection putConnection = (HttpURLConnection) putUrl.openConnection();
            putConnection.setRequestMethod("PUT");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()){
                    putConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            putConnection.setDoOutput(true);
            if (data != null) {
                String jsonInput  = mapToJsonFile(data);
                try (OutputStream outputStream = putConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }
            putConnection.disconnect();
            return readResponse(putConnection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL deleteUrl = new URL(url);
            HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
            deleteConnection.setRequestMethod("DELETE");

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    deleteConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (data != null) {
                String jsonInput = mapToJsonFile(data);
                try (OutputStream outputStream = deleteConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }
            deleteConnection.disconnect();
            return readResponse(deleteConnection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mapToJsonFile(Map<String, String> map) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(map);
    }
}


