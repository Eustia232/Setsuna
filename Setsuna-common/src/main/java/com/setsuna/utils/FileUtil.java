package com.setsuna.utils;

import com.setsuna.config.SetsunaConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class FileUtil {

    public static String uploadImage(URL imageUrl, String filename) throws IOException {
        BufferedImage image = ImageIO.read(imageUrl);
        String path = SetsunaConfig.getFilePath() + "/" + filename;
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IOException("创建目录失败：" + parent);
            }
        }
        if (file.exists()) {
            return uploadImage(imageUrl);
        }
        ImageIO.write(image, "png", file);
        return path;
    }

    public static String uploadImage(URL imageUrl) throws IOException {
        UUID uuid = UUID.randomUUID();
        return uploadImage(imageUrl, uuid.toString());
    }

}
