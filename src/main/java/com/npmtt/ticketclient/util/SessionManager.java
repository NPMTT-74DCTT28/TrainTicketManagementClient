package com.npmtt.ticketclient.util;

import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import lombok.Getter;
import lombok.Setter;

public class SessionManager {
    @Getter
    @Setter
    private static NhanVienResponse currentUser;
}
