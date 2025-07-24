package com.setsuna.utils;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HtmlUtil {

    public static String loginHtml(String jsonData) throws IOException {
        InputStream in = HtmlUtil.class.getResourceAsStream("/static/loginCallback.html");
        String html = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
        // 替换占位符
        return html.replace("__LOGIN_VO_JSON__", jsonData);
    }

}