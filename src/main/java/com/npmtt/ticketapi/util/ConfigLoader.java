package com.npmtt.ticketapi.util;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@RequiredArgsConstructor
public class ConfigLoader {
    private static final Properties properties = new Properties();
    private static final String fileName = "config.properties";

    static {
        try {
            File configFile = new File(fileName);
            FileInputStream fis = new FileInputStream(configFile);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Không tìm thấy file cấu hình " + fileName, e);
        }
    }

    public static String getBaseApiUrl() {
        return properties.getProperty("api.baseurl");
    }
}
