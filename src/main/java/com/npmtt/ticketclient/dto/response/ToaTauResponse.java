package com.npmtt.ticketclient.dto.response;

import lombok.Data;

@Data
public class ToaTauResponse {
    private Integer id;
    private String maToa;
    private Integer idTau;
    private Integer idLoaiToa;
    @Override
    public String toString() {
        return this.maToa;
    }
}

