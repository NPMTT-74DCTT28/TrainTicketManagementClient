package com.npmtt.ticketclient.apiclient;

import com.npmtt.ticketclient.dto.thongke.*;
import lombok.Getter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import java.util.List;

public class ThongKeApiClient {
    @Getter
    private static final ThongKeApiClient instance = new ThongKeApiClient();

    public List<DoanhThuTheoNgayDTO> getDoanhThuTheoNgay(LocalDate tuNgay, LocalDate denNgay) throws Exception {
        return List.of();
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
