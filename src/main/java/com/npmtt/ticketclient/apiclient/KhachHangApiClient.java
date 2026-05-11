package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.KhachHangRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.KhachHangResponse;
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
public class KhachHangApiClient {
    @Getter
    private static final KhachHangApiClient instance = new KhachHangApiClient();

    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/khach-hang";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private List<KhachHangResponse> getListKhachHangResponse(HttpResponse<String> response) throws Exception {
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<KhachHangResponse>>>() {

            }.getType();
            ApiResponse<List<KhachHangResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    private KhachHangResponse getKhachHangResponse(HttpRequest request) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<KhachHangResponse>>() {

            }.getType();
            ApiResponse<KhachHangResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<KhachHangResponse> getAllKhachHang() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return getListKhachHangResponse(response);
    }

    public KhachHangResponse createKhachHang(KhachHangRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<KhachHangResponse>>() {

            }.getType();
            ApiResponse<KhachHangResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public KhachHangResponse updateKhachHang(KhachHangRequest requestDTO) throws Exception {
        if (requestDTO == null) return null;

        String jsonBody = gson.toJson(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .build();

        return getKhachHangResponse(request);
    }

    public boolean deleteKhachHang(int id) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<KhachHangResponse> searchKhachHang(String keyword, String gioiTinh) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL + "/search?");

        if (keyword != null && !keyword.isEmpty()) {
            urlBuilder.append("keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8)).append("&");
        }
        if (gioiTinh != null && !gioiTinh.isEmpty() && !gioiTinh.equals("Tất cả")) {
            urlBuilder.append("gioiTinh=").append(URLEncoder.encode(gioiTinh, StandardCharsets.UTF_8));
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return getListKhachHangResponse(response);
    }
}
