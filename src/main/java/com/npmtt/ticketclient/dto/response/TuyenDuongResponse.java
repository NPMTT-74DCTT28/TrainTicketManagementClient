package com.npmtt.ticketclient.dto.response;

import lombok.Data;

@Data
public class TuyenDuongResponse {
    private Integer id;
    private String maTuyen;
    private String tenTuyen;
    private Integer idGaDi;
    private Integer idGaDen;
    private double khoangCachKm;
    private double giaCoBan;

    @Override
    public String toString() {
        return tenTuyen;
    }
}
