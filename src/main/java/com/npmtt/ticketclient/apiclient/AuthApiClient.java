package com.npmtt.ticketclient.apiclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.request.ChangePasswordRequest;
import com.npmtt.ticketclient.dto.request.LoginRequest;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.util.ConfigLoader;
import com.npmtt.ticketclient.util.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class AuthApiClient {
    @Getter
    private static final AuthApiClient instance = new AuthApiClient();

    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/auth";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public NhanVienResponse login(LoginRequest loginRequest) throws Exception {
        String jsonBody = gson.toJson(loginRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<NhanVienResponse>>() {
            }.getType();
            ApiResponse<NhanVienResponse> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            throw new Exception("Lỗi khi gọi API đăng nhập. HTTP code: " + response.statusCode());
        }
    }

    public void changePassword(ChangePasswordRequest cpRequest) throws Exception {
        String jsonBody = gson.toJson(cpRequest);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/change-pw"))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}
