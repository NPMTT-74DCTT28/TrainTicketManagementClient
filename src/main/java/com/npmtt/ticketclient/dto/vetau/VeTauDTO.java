package com.npmtt.ticketclient.dto.vetau;

import lombok.Data;

@Data
public class VeTauDTO {
    private Integer id;
    private String maVe;
    private Integer idKhachHang;
    private Integer idLichTrinh;
    private Integer idGhe;
    private Integer idNhanVien;
    private String ngayDatVe;
    private double giaVe;
    private String trangThai;

    public VeTauDTO(String maVe, Integer idKhachHang, Integer idLichTrinh, Integer idGhe, Integer idNhanVien, String ngayDatVe, double giaVe, String trangThai) {
        this.maVe = maVe;
        this.idKhachHang = idKhachHang;
        this.idLichTrinh = idLichTrinh;
        this.idGhe = idGhe;
        this.idNhanVien = idNhanVien;
        this.ngayDatVe = ngayDatVe;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return maVe;
    }
}
