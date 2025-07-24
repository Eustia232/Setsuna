package com.setsuna.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "setsuna")
public class SetsunaConfig {

    private static String filePath;

    public static String getFilePath() {
        return filePath;
    }

}
