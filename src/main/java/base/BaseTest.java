package base;

import com.microsoft.playwright.Page;
import utils.ConfigReader;

public class BaseTest {
    protected Page page;
    protected DriverFactory driverFactory;
    protected ConfigReader config;

    public void setup() {
        config = new ConfigReader(); // assign to field
        driverFactory = new DriverFactory();
        page = driverFactory.initBrowser(config.getProperty("browser"), true);
    }

    public void teardown() {
    if (driverFactory != null) {
        driverFactory.closeBrowser();
    }
}

}
