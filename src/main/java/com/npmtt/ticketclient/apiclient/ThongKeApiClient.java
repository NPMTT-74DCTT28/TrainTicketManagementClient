package com.npmtt.ticketclient.apiclient;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.thongke.*;
import com.npmtt.ticketclient.util.ConfigLoader;
import com.npmtt.ticketclient.util.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jfree.data.category.DefaultCategoryDataset;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ThongKeApiClient {
    @Getter
    private static final ThongKeApiClient instance = new ThongKeApiClient();

    private static final String API_URL = ConfigLoader.getBaseApiUrl() + "/thong-ke";
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, context) ->
                            LocalDate.parse(json.toString().replace("\"", "").trim()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .create();

    public List<DoanhThuTheoNgayDTO> getDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String url = API_URL + "/doanh-thu-ngay?ngayBatDau=" + ngayBatDau + "&ngayKetThuc=" + ngayKetThuc;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<DoanhThuTheoNgayDTO>>>() {
            }.getType();
            ApiResponse<List<DoanhThuTheoNgayDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> apiResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(apiResponse.getMessage());
        }
    }

    public List<DoanhThuTheoTuyenDTO> getDoanhThuTheoTuyen(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String url = API_URL + "/doanh-thu-tuyen?ngayBatDau=" + ngayBatDau + "&ngayKetThuc=" + ngayKetThuc;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<DoanhThuTheoTuyenDTO>>>() {
            }.getType();
            ApiResponse<List<DoanhThuTheoTuyenDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> apiResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(apiResponse.getMessage());
        }
    }

    public List<TyLeLapDayDTO> getTyLeLapDay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String url = API_URL + "/ty-le-lap-day?ngayBatDau=" + ngayBatDau + "&ngayKetThuc=" + ngayKetThuc;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<TyLeLapDayDTO>>>() {
            }.getType();
            ApiResponse<List<TyLeLapDayDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> apiResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(apiResponse.getMessage());
        }
    }

    public List<KhachHangVipDTO> getKhachHangVIP(int soLuong) throws Exception {
        String url = API_URL + "/khach-hang-than-thiet?soLuong=" + soLuong;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<KhachHangVipDTO>>>() {
            }.getType();
            ApiResponse<List<KhachHangVipDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public List<DoanhSoDTO> getDoanhSo(int thang, int nam) throws Exception {
        String url = API_URL + "/doanh-so?thang=" + thang + "&nam=" + nam;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<DoanhSoDTO>>>() {
            }.getType();
            ApiResponse<List<DoanhSoDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }

    public DefaultCategoryDataset getDoanhThuBayNgay() throws Exception {
        String url = API_URL + "/doanh-thu-7d";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessionManager.getCurrentUser().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            Type responseType = new TypeToken<ApiResponse<List<DoanhThuBayNgayDTO>>>() {
            }.getType();
            ApiResponse<List<DoanhThuBayNgayDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            List<DoanhThuBayNgayDTO> data = apiResponse.getData();
            for (DoanhThuBayNgayDTO dto : data) {
                dataset.addValue(dto.getDoanhThu(), "ngay", dto.getNgay());
            }
            return dataset;
        } else {
            Type responseType = new TypeToken<ApiResponse<Object>>() {
            }.getType();
            ApiResponse<Object> errorResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(errorResponse.getMessage());
        }
    }
}
