package com.npmtt.ticketclient.apiclient;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.npmtt.ticketclient.dto.response.ApiResponse;
import com.npmtt.ticketclient.dto.thongke.*;
import com.npmtt.ticketclient.util.ConfigLoader;
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
                    (JsonSerializer<LocalDate>) (src, typOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .create();

    public List<DoanhThuTheoNgayDTO> getDoanhThuTheoNgay(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        if (ngayBatDau == null || ngayKetThuc == null || ngayBatDau.isAfter(ngayKetThuc)) return null;

        String url = API_URL + "/doanh-thu-ngay?ngayBatDau=" + ngayBatDau + "&ngayKetThuc=" + ngayKetThuc;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        if (response.statusCode() == 200) {
            Type responseType = new TypeToken<ApiResponse<List<DoanhThuTheoNgayDTO>>>() {
            }.getType();
            ApiResponse<List<DoanhThuTheoNgayDTO>> apiResponse = gson.fromJson(response.body(), responseType);
            return apiResponse.getData();
        } else {
            Type responseType = new TypeToken<ApiResponse<Void>>() {
            }.getType();
            ApiResponse<Void> apiResponse = gson.fromJson(response.body(), responseType);
            throw new Exception(apiResponse.getMessage());
        }
    }

    public List<DoanhThuTheoTuyenDTO> getDoanhThuTheoTuyen(LocalDate tuNgay, LocalDate denNgay) throws Exception {
        return List.of();
    }

    public List<TyLeLapDayDTO> getTyLeLapDay(LocalDate tuNgay, LocalDate denNgay) throws Exception {
        return List.of();
    }

    public List<KhachHangVipDTO> getKhachHangVIP(int soLuong) throws Exception {
        return List.of();
    }

    public List<DoanhSoDTO> getDoanhSo(int thang, int nam) throws Exception {
        return List.of();
    }

    public DefaultCategoryDataset getDoanhThuBayNgay() throws Exception {
        return new DefaultCategoryDataset();
    }
}
