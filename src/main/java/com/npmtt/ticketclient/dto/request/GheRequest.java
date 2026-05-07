package com.npmtt.ticketclient.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GheRequest {
    private Integer id;
    private String soGhe;
    private Integer idToaTau;
    public GheRequest(String soGhe, int idToaTau) {
        this.soGhe = soGhe;
        this.idToaTau = idToaTau;
    }
}
