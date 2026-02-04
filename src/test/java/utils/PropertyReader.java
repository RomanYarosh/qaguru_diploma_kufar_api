package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = PropertyReader.class.getClassLoader().getResourceAsStream("testdata.properties")) {
            if (is == null) {
                throw new RuntimeException("Файл testdata.properties не найден в src/test/resources/");
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла testdata.properties", e);
        }
    }

    public static String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isEmpty()) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }
}