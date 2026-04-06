package com.npmtt.ticketclient.util;

import com.npmtt.ticketclient.dto.response.NhanVienResponse;
import com.npmtt.ticketclient.enums.VaiTro;
import lombok.Getter;

public class SessionManager {

    @Getter
    private static NhanVienResponse currentUser;

    public static void startSession(NhanVienResponse nhanVien) {
        currentUser = nhanVien;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static boolean isAdmin() {
        if (currentUser != null && currentUser.getVaiTro() != null) {
            return currentUser.getVaiTro().equalsIgnoreCase(VaiTro.ADMIN.toString());
        }
        return false;
    }
}
