package com.npmtt.ticketclient.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhanVienRequestDTO {
    private int id;
    private String maNhanVien;
    private String matKhau;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String vaiTro;

    public NhanVienRequestDTO(String maNhanVien, String hoTen, String ngaySinh, String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
    }

    public NhanVienRequestDTO(String maNhanVien, String matKhau, String hoTen, String ngaySinh, String gioiTinh, String sdt, String email, String diaChi, String vaiTro) {
        this.matKhau = matKhau;
        this(maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, email, diaChi, vaiTro);
    }
}
