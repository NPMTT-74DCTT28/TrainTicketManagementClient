package com.npmtt.ticketclient.dto.thongke;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DoanhThuTheoNgayDTO {
    private LocalDate ngay;
    private int soVeBan;
    private double doanhThu;
}
