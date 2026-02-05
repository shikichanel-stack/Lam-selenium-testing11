package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
//    Lưu toàn bộ key và value đọc từ file config.properties
//    Vì sao lại dung Properties:
//    1. là class có sẵn trong java
//    2. Tốt nhất cho việc đọc file có dữ liệu dạng key=value
//    3. Đơn giản
    private static final Properties CONFIG = loadConfig();

    private static Properties loadConfig() {
        Properties p = new Properties();
        try (InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                p.load(in);
            } else {
                throw new IllegalStateException("config.properties not found on classpath");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;

    }

    public static String getLoginUrl() {
        return CONFIG.getProperty("app.login.url", "");
    }

    public static String getUserName() {
        return CONFIG.getProperty("app.login.username", "Admin");
    }

    public static String getPassword() {
        return CONFIG.getProperty("app.login.password", "admin123");
    }

}
