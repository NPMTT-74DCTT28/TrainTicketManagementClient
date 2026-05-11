package com.npmtt.ticketclient.dto.response;

import lombok.Data;

@Data
public class GheResponse {
    private Integer id;
    private String soGhe;
    private Integer idToaTau;

    @Override
    public String toString() {
        return this.soGhe;
    }
}
