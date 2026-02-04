package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Файл testdata.properties не найден!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}