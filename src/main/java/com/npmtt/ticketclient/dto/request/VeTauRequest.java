package com.npmtt.ticketclient.dto.request;

import lombok.Data;

@Data
public class VeTauRequest {
    private int id;
    private String maVe;
    private int idKhachHang;
    private int idLichTrinh;
    private int idGhe;
    private int idNhanVien;
    private double giaVe;
    private String trangThai;

    public VeTauRequest(String maVe, int idKhachHang, int idLichTrinh, int idGhe, int idNhanVien, double giaVe, String trangThai) {
        this.maVe = maVe;
        this.idKhachHang = idKhachHang;
        this.idLichTrinh = idLichTrinh;
        this.idGhe = idGhe;
        this.idNhanVien = idNhanVien;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
    }
}
