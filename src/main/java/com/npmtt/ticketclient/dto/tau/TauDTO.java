package com.npmtt.ticketclient.dto.tau;

import lombok.Data;

@Data
public class TauDTO {
    private Integer id;
    private String maTau;
    private String tenTau;

    public TauDTO(String maTau, String tenTau) {
        this.maTau = maTau;
        this.tenTau = tenTau;
    }
}
