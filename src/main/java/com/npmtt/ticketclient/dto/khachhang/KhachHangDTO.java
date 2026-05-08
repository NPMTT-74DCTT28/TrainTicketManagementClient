package com.npmtt.ticketclient.dto.khachhang;

import lombok.Data;

@Data
public class KhachHangDTO {
    private Integer id;
    private String cccd;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String diaChi;

    public KhachHangDTO(String cccd, String hoTen, String ngaySinh, String gioiTinh, String sdt, String diaChi) {
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public String toString() {
        return hoTen;
    }
}
