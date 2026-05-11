package com.npmtt.ticketclient.enums;

public enum TrangThaiVe {
    CHO("Chờ thanh toán"),
    DA_THANH_TOAN("Đã thanh toán"),
    HUY("Đã hủy");

    private final String ten;

    TrangThaiVe(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten;
    }

}
