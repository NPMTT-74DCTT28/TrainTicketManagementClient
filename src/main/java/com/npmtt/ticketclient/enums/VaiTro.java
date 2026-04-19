package com.npmtt.ticketclient.enums;

public enum VaiTro {
    ADMIN("Quản trị viên"),
    NHAN_VIEN("Nhân viên");

    private final String label;

    VaiTro(String label) {
        this.label = label;
    }

    public static VaiTro fromLabel(String label) {
        for (VaiTro v : VaiTro.values()) {
            if (v.label.equalsIgnoreCase(label)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
