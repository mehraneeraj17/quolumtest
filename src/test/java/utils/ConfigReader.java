package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static Properties readProperties() {
        try {
            FileReader reader = new FileReader(System.getProperty("user.dir") + File.separator + "/src/test/resources/testconfiguration.properties");
            Properties p = new Properties();
            p.load(reader);
            return p;
        } catch (IOException ioException) {
            System.out.println("invalid path of the file");
        }
        return null;
    }
}