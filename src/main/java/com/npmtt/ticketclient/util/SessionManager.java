package com.npmtt.ticketclient.util;

import com.npmtt.ticketclient.dto.response.NhanVienResponseDTO;
import com.npmtt.ticketclient.enums.VaiTro;
import lombok.Getter;

public class SessionManager {

    @Getter
    private static NhanVienResponseDTO currentUser;

    public static void startSession(NhanVienResponseDTO nhanVien) {
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

    public static boolean hasAnyRole(String... roles) {
        if (currentUser == null || currentUser.getVaiTro() == null) {
            return false;
        }

        for (String role : roles) {
            if (role.equalsIgnoreCase(currentUser.getVaiTro())) {
                return true;
            }
        }
        return false;
    }
}
