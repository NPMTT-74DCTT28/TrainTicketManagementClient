package com.npmtt.ticketclient.enums;

public enum GioiTinh {
    NAM("Nam"),
    NU("Nữ"),
    KHAC("Khác");

    private final String label;

    GioiTinh(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
