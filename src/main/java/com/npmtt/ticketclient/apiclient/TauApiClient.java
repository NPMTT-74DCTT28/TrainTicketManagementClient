package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.tau.TauDTO;
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
public class TauApiClient {
    @Getter
    private static final TauApiClient instance = new TauApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/tau";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<TauDTO> getAllTau() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<TauDTO>>>() {
            }.getType();
            ApiResponse<List<TauDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public TauDTO createTau(TauDTO dto) throws Exception {
        if (dto == null) return null;
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201 || response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<TauDTO>>() {
            }.getType();
            ApiResponse<TauDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public TauDTO updateTau(TauDTO dto) throws Exception {
        if (dto == null) return null;
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<TauDTO>>() {
            }.getType();
            ApiResponse<TauDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public boolean deleteTau(Integer id) throws Exception {
        String url = API_URL + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<TauDTO> searchTau(String keyword) throws Exception {
        String url = API_URL + "/search?key=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<TauDTO>>>() {
            }.getType();
            ApiResponse<List<TauDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}