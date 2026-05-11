package com.npmtt.ticketclient.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToaTauRequest {
    private Integer id;
    private String maToa;
    private Integer idTau;
    private Integer idLoaiToa;

    public ToaTauRequest(String maToa, Integer idTau, Integer idLoaiToa) {
        this.maToa = maToa;
        this.idTau = idTau;
        this.idLoaiToa = idLoaiToa;
    }
}