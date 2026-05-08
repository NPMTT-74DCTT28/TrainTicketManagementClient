package com.npmtt.ticketclient.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VeTauRequest {
    private int id;
    private String maVe;
    private int idKhachHang;
    private int idLichTrinh;
    private int idGhe;
    private int idNhanVien;
    private LocalDateTime ngayDatVe;
    private double giaVe;
    private String trangThai;

    public VeTauRequest(String maVe, int idKhachHang, int idLichTrinh, int idGhe, int idNhanVien, LocalDateTime ngayDatVe, double giaVe, String trangThai) {
        this.maVe = maVe;
        this.idKhachHang = idKhachHang;
        this.idLichTrinh = idLichTrinh;
        this.idGhe = idGhe;
        this.idNhanVien = idNhanVien;
        this.ngayDatVe = ngayDatVe;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
    }
}
