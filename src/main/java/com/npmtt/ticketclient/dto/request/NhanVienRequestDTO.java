package com.npmtt.ticketclient.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NhanVienRequestDTO {
    private String maNhanVien;
    private String matKhau;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String vaiTro;
}
