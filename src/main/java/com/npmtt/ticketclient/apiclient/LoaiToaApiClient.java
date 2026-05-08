package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.loaitoa.LoaiToaDTO;
import com.npmtt.ticketclient.dto.response.ApiResponse;
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
public class LoaiToaApiClient {
    @Getter
    private static final LoaiToaApiClient instance = new LoaiToaApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/loai-toa";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<LoaiToaDTO> getAllLoaiToa() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<LoaiToaDTO>>>() {
            }.getType();
            ApiResponse<List<LoaiToaDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            handleErrorResponse(response);
            return null;
        }
    }

    public LoaiToaDTO createLoaiToa(LoaiToaDTO dto) throws Exception {
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            Type responseType = new TypeToken<ApiResponse<LoaiToaDTO>>() {
            }.getType();
            ApiResponse<LoaiToaDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            handleErrorResponse(response);
            return null;
        }
    }

    public LoaiToaDTO updateLoaiToa(LoaiToaDTO dto) throws Exception {
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<LoaiToaDTO>>() {
            }.getType();
            ApiResponse<LoaiToaDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            handleErrorResponse(response);
            return null;
        }
    }

    public boolean deleteLoaiToa(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        } else {
            handleErrorResponse(response);
            return false;
        }
    }


    public List<LoaiToaDTO> searchLoaiToa(String keyword) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL + "/search?");

        if (keyword != null && !keyword.trim().isEmpty()) {
            urlBuilder.append("key=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8)).append("&");
        }

        String url = urlBuilder.toString();
        if (url.endsWith("&")) {
            url = url.substring(0, url.length() - 1);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<LoaiToaDTO>>>() {
            }.getType();
            ApiResponse<List<LoaiToaDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            handleErrorResponse(response);
            return null;
        }
    }

    private void handleErrorResponse(HttpResponse<String> response) throws Exception {
        Type responseType = new TypeToken<ApiResponse<Object>>() {
        }.getType();
        ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
        String message = (errorResponse != null && errorResponse.getMessage() != null)
                ? errorResponse.getMessage()
                : "Lỗi hệ thống không xác định (Status: " + response.statusCode() + ")";
        throw new Exception(message);
    }
}