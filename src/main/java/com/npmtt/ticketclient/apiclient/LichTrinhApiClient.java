package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.LichTrinhRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.LichTrinhResponse;
import com.npmtt.ticketclient.util.ConfigLoader;
import com.npmtt.ticketclient.util.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@RequiredArgsConstructor
public class LichTrinhApiClient {
    @Getter
    private static final LichTrinhApiClient instance = new LichTrinhApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/lich-trinh";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<LichTrinhResponse> getAllLichTrinh() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<LichTrinhResponse>>>() {
            }.getType();
            ApiResponse<List<LichTrinhResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public LichTrinhResponse createLichTrinh(LichTrinhRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;
        String jsonBody = gson.toJson(requestDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            Type responseType = new TypeToken<ApiResponse<LichTrinhResponse>>() {
            }.getType();
            ApiResponse<LichTrinhResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public LichTrinhResponse updateLichTrinh(LichTrinhRequest requestDTO) throws Exception {
        if (requestDTO == null || requestDTO.getId() <= 0) return null;
        String jsonBody = gson.toJson(requestDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<LichTrinhResponse>>() {
            }.getType();
            ApiResponse<LichTrinhResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public boolean deleteLichTrinh(int id) throws Exception {
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
            Type errorType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<LichTrinhResponse> searchLichTrinh(String keyword) throws Exception {
        String url = API_URL + "/search?key=" + (keyword != null ? keyword : "");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<LichTrinhResponse>>>() {
            }.getType();
            ApiResponse<List<LichTrinhResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}