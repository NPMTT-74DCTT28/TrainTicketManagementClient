package com.npmtt.ticketapi.util;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RequiredArgsConstructor
public class ConfigLoader {
    private static final Properties properties = new Properties();
    private static final String fileName = "config.properties";

    static {
        try {
            InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Không tìm thấy file cấu hình " + fileName, e);
        }
    }

    public static String getBaseApiUrl() {
        return properties.getProperty("api.baseurl");
    }
}
