package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.NhanVienRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
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
public class NhanVienApiClient {
    @Getter
    private static final NhanVienApiClient instance = new NhanVienApiClient();

    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/nhan-vien";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<NhanVienResponse> getAllNhanVien() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<NhanVienResponse>>>() {
            }.getType();
            ApiResponse<List<NhanVienResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public NhanVienResponse createNhanVien(NhanVienRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;

        String jsonBody = gson.toJson(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            Type responseType = new TypeToken<ApiResponse<NhanVienResponse>>() {
            }.getType();
            ApiResponse<NhanVienResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public NhanVienResponse updateNhanVien(NhanVienRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;

        String jsonBody = gson.toJson(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<NhanVienResponse>>() {
            }.getType();
            ApiResponse<NhanVienResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public boolean deleteNhanVien(int id) throws Exception {
        if (id < 1) return false;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200)
            return true;
        else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<NhanVienResponse> searchNhanVien(String keyword, String gioiTinh, String vaiTro) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL + "/search?");

        if (keyword != null && !keyword.isEmpty()) {
            urlBuilder.append("keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8)).append("&");
        }
        if (gioiTinh != null && !gioiTinh.isEmpty() && !gioiTinh.equals("Tất cả")) {
            urlBuilder.append("gioiTinh=").append(URLEncoder.encode(gioiTinh, StandardCharsets.UTF_8)).append("&");
        }
        if (vaiTro != null && !vaiTro.isEmpty() && !vaiTro.equals("Tất cả")) {
            urlBuilder.append("vaiTro=").append(URLEncoder.encode(vaiTro, StandardCharsets.UTF_8));
        }

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (httpResponse.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<NhanVienResponse>>>() {
            }.getType();
            ApiResponse<List<NhanVienResponse>> apiResponse = gson.fromJson(httpResponse.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(httpResponse.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}
