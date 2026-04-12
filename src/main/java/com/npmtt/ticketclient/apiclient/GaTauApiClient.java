package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.gatau.GaTauDTO;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.util.ConfigLoader;
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
public class GaTauApiClient {
    @Getter
    private static final GaTauApiClient instance = new GaTauApiClient();
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/ga_tau";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<GaTauDTO> getAllGaTau() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<GaTauDTO>>>() {
            }.getType();
            ApiResponse<List<GaTauDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
    public GaTauDTO createGaTau(GaTauDTO dto) throws Exception {
        if (dto == null) return null;
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<GaTauDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
    public GaTauDTO updateGaTau(GaTauDTO dto) throws Exception {
        if (dto == null) return null;
        String jsonBody = gson.toJson(dto);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<GaTauDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
    public boolean deleteGaTau(Integer id) throws Exception {
        if (id < 1) return false;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;
        } else {
            Type responseType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
    public List<GaTauDTO> searchGaTau(String keyword) throws Exception {
        String url = API_URL + "/search?key=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<GaTauDTO>>>() {
            }.getType();
            ApiResponse<List<GaTauDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        }else{
            Type responeType = new TypeToken<ApiResponse<GaTauDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responeType);
            throw new Exception(errorResponse.getMessage());
        }
    }

}
