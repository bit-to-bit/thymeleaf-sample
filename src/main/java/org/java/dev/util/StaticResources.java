package org.java.dev.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
public class StaticResources {
    private static final String STATIC_STORAGE = StaticResources.class.getClassLoader().getResource("static").toString();
    private static final String STATIC_STORAGE_CSS = STATIC_STORAGE + "/css";
    private static final String STATIC_STORAGE_IMG = STATIC_STORAGE + "/img";

    public static String getCss(String fileName) {
        String res = "";
        URI filePath = URI.create(STATIC_STORAGE_CSS + fileName);
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            try {
                res = new String(Files.readAllBytes(Path.of(filePath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            res = "Resource " + fileName + "does not exists";
        }
        return res;
    }
    public static InputStream getImage(String fileName) {
        URI filePath = URI.create(STATIC_STORAGE_IMG + fileName);
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            try {
                InputStream inputStream = new FileInputStream(file);
                return inputStream;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Image " + fileName + " does not exists");
        }
    }
}