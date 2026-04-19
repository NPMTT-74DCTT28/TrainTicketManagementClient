package com.npmtt.ticketclient.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String maNhanVien;
    private String matKhau;
}
