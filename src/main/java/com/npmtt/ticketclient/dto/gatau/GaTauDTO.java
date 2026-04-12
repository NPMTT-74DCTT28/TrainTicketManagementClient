package com.npmtt.ticketclient.dto.gatau;

import lombok.Data;

@Data
public class GaTauDTO {
    private Integer id;
    private String maGa;
    private String tenGa;
    private String diaChi;
    private String thanhPho;

    public GaTauDTO(String maGa, String tenga, String diachi, String thanhpho) {
        this.maGa = maGa;
        this.tenGa = tenga;
        this.diaChi = diachi;
        this.thanhPho = thanhpho;
    }
}
