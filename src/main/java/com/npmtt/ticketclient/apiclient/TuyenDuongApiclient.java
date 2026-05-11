package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.TuyenDuongRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.TuyenDuongResponse;
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
public class TuyenDuongApiclient {
    @Getter
    private static final TuyenDuongApiclient instance = new TuyenDuongApiclient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/tuyen_duong";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<TuyenDuongResponse> getAllTuyenDuong() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<TuyenDuongResponse>>>() {
            }.getType();
            ApiResponse<List<TuyenDuongResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responeType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public TuyenDuongResponse createTuyenDuong(TuyenDuongRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<TuyenDuongResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responeType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public TuyenDuongResponse updateTuyenDuong(TuyenDuongRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<TuyenDuongResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responeType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public boolean deleteTuyenDuong(int id) throws Exception {
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
            Type responeType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<TuyenDuongResponse> searchTuyenDuong(String keyword) throws Exception {
        String url = API_URL + "/search?key=" + keyword;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<TuyenDuongResponse>>>() {
            }.getType();
            ApiResponse<List<TuyenDuongResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responeType = new TypeToken<ApiResponse<TuyenDuongResponse>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

}
