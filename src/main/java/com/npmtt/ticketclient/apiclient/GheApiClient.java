package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.GheRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.GheResponse;
import com.npmtt.ticketclient.util.ConfigLoader;
import com.npmtt.ticketclient.util.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class GheApiClient {
    @Getter
    private static final GheApiClient instance = new GheApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/ghe";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<GheResponse> getAllGhe() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<List<GheResponse>>() {
            }.getType();
            return gson.fromJson(response.body(), responseType);

        } else {
            handleError(response);
            return null;
        }
    }

    public GheResponse createGhe(GheRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;
        String jsonBody = gson.toJson(requestDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201 || response.statusCode() == 200) {
            Type responseType = new TypeToken<GheResponse>() {
            }.getType();
            return gson.fromJson(response.body(), responseType);
        } else {
            handleError(response);
            return null;
        }
    }

    public GheResponse updateGhe(GheRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;
        String jsonBody = gson.toJson(requestDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<GheResponse>() {
            }.getType();
            return gson.fromJson(response.body(), responseType);
        } else {
            handleError(response);
            return null;
        }
    }

    public boolean deleteGhe(int id) throws Exception {
        if (id < 1) return false;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .DELETE()
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        } else {
            handleError(response);
            return false;
        }
    }

    public List<GheResponse> searchGhe(String keyword) throws Exception {
        String url = API_URL + "/search?keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<List<GheResponse>>() {
            }.getType();
            return gson.fromJson(response.body(), responseType);
        } else {
            handleError(response);
            return null;
        }
    }

    private void handleError(HttpResponse<String> response) throws Exception {
        Type responseType = new TypeToken<ApiResponse<Void>>() {
        }.getType();
        ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
        String message = (errorResponse != null && errorResponse.getMessage() != null)
                ? errorResponse.getMessage()
                : "Lỗi không xác định từ hệ thống (Status: " + response.statusCode() + ")";
        throw new Exception(message);
    }
}