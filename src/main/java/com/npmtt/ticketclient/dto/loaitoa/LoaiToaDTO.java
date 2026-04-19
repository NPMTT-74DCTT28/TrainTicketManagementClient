package com.npmtt.ticketclient.dto.loaitoa;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoaiToaDTO {
    private Integer id;
    private String tenLoai;
    private BigDecimal heSoGia;

    public LoaiToaDTO(String tenLoai, BigDecimal heSoGia) {
        this.tenLoai = tenLoai;
        this.heSoGia = heSoGia;
    }
}
