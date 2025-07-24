package com.setsuna.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

@SpringBootTest
public class FileTest {

    @Test
    public void FilTest() throws IOException {
        URL url = new URL("https://avatars.githubusercontent.com/u/182218992?v=4");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int timeoutMillis = 5000;
        connection.setConnectTimeout(timeoutMillis); // 连接超时
        System.out.println("1");
        connection.setReadTimeout(timeoutMillis);    // 读取超时
        System.out.println("2");
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        System.out.println("3");
        try (InputStream in = connection.getInputStream()) {
            System.out.println("");
            BufferedImage image = ImageIO.read(in);
            System.out.println("Success");
            File outputFile = new File("output.png"); // 目标文件路径和文件名
            ImageIO.write(image, "png", outputFile); // "png" 可换成 "jpg"、"gif" 等格式
        } catch (SocketTimeoutException e) {
        } finally {
            connection.disconnect();
        }
    }

}
