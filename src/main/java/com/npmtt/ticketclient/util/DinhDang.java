package com.npmtt.ticketclient.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DinhDang {

    public static final String DINH_DANG_SDT = "^0\\d{9}$";

    public static final String DINH_DANG_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static final String MAT_KHAU_MANH = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{8,20}$";

    public static final String DATE_VN = "dd/MM/yyyy";

    public static final String DATE_TIME_VN = "dd/MM/yyyy HH:mm";

    public static final Locale LOCALE_VN = Locale.forLanguageTag("vi-VN");

    public static final NumberFormat TIEN_VN = NumberFormat.getCurrencyInstance(LOCALE_VN);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_VN);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_VN);

    public static String formatNgayVN(String sqlDate) {
        if (sqlDate == null || sqlDate.isEmpty()) return "";
        try {
            LocalDate date = LocalDate.parse(sqlDate);
            return date.format(DATE_FORMATTER);
        } catch (Exception e) {
            e.printStackTrace();
            return sqlDate;
        }
    }

    public static String formatNgayVN(LocalDate date) {
        if (date == null) return "";
        return date.format(DATE_FORMATTER);
    }

    public static String formatNgayGioVN(String sqlDateTime) {
        if (sqlDateTime == null || sqlDateTime.isEmpty()) return "";
        try {
            LocalDateTime dateTime = LocalDateTime.parse(sqlDateTime, DATE_TIME_FORMATTER);
            return dateTime.format(DATE_TIME_FORMATTER);
        } catch (Exception e) {
            e.printStackTrace();
            return sqlDateTime;
        }
    }

    public static String formatNgayGioVN(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static String formatTienVN(double tien) {
        return TIEN_VN.format(tien);
    }

    public static double parseTienVN(String tienString) {
        try {
            return TIEN_VN.parse(tienString).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}