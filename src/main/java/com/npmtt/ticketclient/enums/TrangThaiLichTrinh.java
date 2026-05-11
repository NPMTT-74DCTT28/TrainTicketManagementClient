package com.npmtt.ticketclient.enums;

public enum TrangThaiLichTrinh {
    CHO("Chờ"),
    DANG_CHAY("Đang chạy"),
    HOAN_THANH("Hoàn thành"),
    HUY("Hủy");

    private final String ten;

    TrangThaiLichTrinh(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten;
    }

}
