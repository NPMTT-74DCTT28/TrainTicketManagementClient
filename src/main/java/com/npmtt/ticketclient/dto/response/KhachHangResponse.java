package com.npmtt.ticketclient.dto.response;

import lombok.Data;

@Data
public class KhachHangResponse {
    private int id;
    private String cccd;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
}
