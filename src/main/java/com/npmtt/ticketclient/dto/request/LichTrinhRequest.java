package com.npmtt.ticketclient.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LichTrinhRequest {
    private Integer id;
    private String maLichTrinh;
    private Integer idTau;
    private Integer idTuyenDuong;
    private String ngayDi;
    private String ngayDen;
    private String trangThai;

    public LichTrinhRequest(String maLichTrinh, Integer idTau, Integer idTuyenDuong,String ngayDi,String ngayDen, String trangThai) {
        this.maLichTrinh = maLichTrinh;
        this.idTau = idTau;
        this.idTuyenDuong = idTuyenDuong;
        this.ngayDi = ngayDi;
        this.ngayDen = ngayDen;
        this.trangThai = trangThai;
    }
}
