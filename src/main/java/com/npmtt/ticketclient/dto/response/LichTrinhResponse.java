package com.npmtt.ticketclient.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LichTrinhResponse {
    private Integer id;
    private String maLichTrinh;
    private Integer idTau;
    private Integer idTuyenDuong;
    private String ngayDi;
    private String ngayDen;
    private String trangThai;
}