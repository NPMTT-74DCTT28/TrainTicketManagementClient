package com.npmtt.ticketclient.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KhachHangRequest {
    private int id;
    private String cccd;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String diaChi;

    public KhachHangRequest(String cccd, String hoTen, String ngaySinh, String gioiTinh, String sdt, String diaChi) {
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public KhachHangRequest(String cccd, String hoTen, String ngaySinh, String gioiTinh, String sdt, String diaChi, String token) {
        this.cccd = cccd;
        this(cccd, hoTen, ngaySinh, gioiTinh, sdt, diaChi);

    }
}
