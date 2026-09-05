package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocatorReader {
    private Properties locators;

    public LocatorReader(String filePath) {
        locators = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            locators.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLocator(String key) {
        return locators.getProperty(key);
    }
}
