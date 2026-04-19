package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.ToaTauRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.ToaTauResponse;
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
public class ToaTauApiClient {
    @Getter
    private static final ToaTauApiClient instance = new ToaTauApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/toa-tau";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<ToaTauResponse> getAllToaTau() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<ToaTauResponse>>>(){}.getType();
            ApiResponse<List<ToaTauResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>(){}.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public ToaTauResponse createToaTau(ToaTauRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<ToaTauResponse>>(){}.getType();
            ApiResponse<ToaTauResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>(){}.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public ToaTauResponse updateToaTau(ToaTauRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<ToaTauResponse>>(){}.getType();
            ApiResponse<ToaTauResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>(){}.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public boolean deleteToaTau(int id) throws Exception {
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
            Type errorType = new TypeToken<ApiResponse<Void>>(){}.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<ToaTauResponse> searchToaTau(String keyword) throws Exception {
        String url = API_URL + "/search?key=" + keyword;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<ToaTauResponse>>>(){}.getType();
            ApiResponse<List<ToaTauResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type errorType = new TypeToken<ApiResponse<Void>>(){}.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), errorType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}