package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.NhanVienRequestDTO;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.NhanVienResponseDTO;
import com.npmtt.ticketclient.util.ConfigLoader;
import lombok.AllArgsConstructor;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@AllArgsConstructor
public class NhanVienApiClient {
    private static NhanVienApiClient instance;
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/nhan-vien";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public static NhanVienApiClient getInstance() {
        if (instance == null) {
            return new NhanVienApiClient();
        }
        return instance;
    }

    public List<NhanVienResponseDTO> getAllNhanVien() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<NhanVienResponseDTO>>>() {
            }.getType();
            ApiResponse<List<NhanVienResponseDTO>> apiResponse = gson.fromJson(response.body(), responseType);

            return apiResponse.getData();
        } else {
            throw new Exception("Lỗi khi gọi API. HTTP code: " + response.statusCode());
        }
    }

    public NhanVienResponseDTO createNhanVien(NhanVienRequestDTO requestDTO) throws Exception {
        String jsonBody = gson.toJson(requestDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            Type responseType = new TypeToken<ApiResponse<NhanVienResponseDTO>>() {
            }.getType();
            ApiResponse<NhanVienResponseDTO> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<NhanVienResponseDTO>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}
