package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/nhan-vien";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

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
}
