package com.npmtt.ticketclient.dto.response;

import lombok.Data;

@Data
public class NhanVienResponseDTO {
    private String maNhanVien;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String vaiTro;
}
