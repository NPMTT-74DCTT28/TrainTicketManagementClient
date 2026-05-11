package com.npmtt.ticketclient.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TuyenDuongRequest {
    private Integer id;
    private String maTuyen;
    private String tenTuyen;
    private int idGaDi;
    private int idGaDen;
    private double khoangCach;
    private double giaCoBan;

    public TuyenDuongRequest(String maTuyen, String tenTuyen, int idgadi, int idgaden, double khoangcach, double giaCB) {
        this.maTuyen = maTuyen;
        this.tenTuyen = tenTuyen;
        this.idGaDi = idgadi;
        this.idGaDen = idgaden;
        this.khoangCach = khoangcach;
        this.giaCoBan = giaCB;
    }
}
