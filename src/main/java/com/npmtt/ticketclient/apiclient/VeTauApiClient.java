package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.VeTauRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.VeTauResponse;
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
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class VeTauApiClient {
    @Getter
    private static final VeTauApiClient instance = new VeTauApiClient();

    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/ve-tau";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    private List<VeTauResponse> getListVeTauResponse(HttpResponse<String> response) throws Exception {
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<VeTauResponse>>>() {

            }.getType();
            ApiResponse<List<VeTauResponse>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        }else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    private VeTauResponse getVeTauResponse(HttpRequest request) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<VeTauResponse>>() {

            }.getType();
            ApiResponse<VeTauResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        }else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<VeTauResponse> getAllVeTau() throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return getListVeTauResponse(response);
    }

    public VeTauResponse createVeTau(VeTauRequest requestDTO) throws Exception {
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
            Type responseType = new TypeToken<ApiResponse<VeTauResponse>>() {

            }.getType();
            ApiResponse<VeTauResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        }else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public VeTauResponse updateVeTau(VeTauRequest requestDTO) throws Exception{
        if (requestDTO == null) return null;

        String jsonBody = gson.toJson(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return getVeTauResponse(request);
    }

    public boolean deleteVeTau(int id) throws Exception{
        if (id < 1) return false;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        }else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {

            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<VeTauResponse> searchVeTau(String keyword, String maVe, LocalDateTime ngayDatVe) throws Exception{
        StringBuilder urlBuilder = new StringBuilder(API_URL + "/search?");

        if (keyword != null && !keyword.isEmpty()){
            urlBuilder.append("keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8)).append("&");
        }
        if (maVe != null && !maVe.trim().isEmpty()) {
            urlBuilder.append("maVe=").append(maVe).append("&");
        }
        if (ngayDatVe != null && ngayDatVe.isBefore(LocalDateTime.now())){
            urlBuilder.append("ngayDatVe=").append(ngayDatVe);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlBuilder.toString()))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return getListVeTauResponse(response);
    }
}
