package com.npmtt.ticketclient.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    private int id;
    private String oldPassword;
    private String newPassword;
}
