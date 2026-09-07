package utils;

import com.microsoft.playwright.Page;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.nio.file.Paths;

public class ScreenShotUtils {
    private final Page page;

    public ScreenShotUtils(Page page) {
        this.page = page;
    }

    // Ensure screenshots folder exists and return absolute path
    private String getScreenshotsFolder() {
        File folder = new File("screenshots");
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Failed to create screenshots folder");
        }
        return folder.getAbsolutePath();
    }

    // 📸 Take screenshot with a fixed filename
    public void takeScreenshot(String fileName) {
        String folderPath = getScreenshotsFolder();
        String filePath = folderPath + File.separator + fileName;
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get(filePath))
            .setFullPage(true));
        System.out.println("Screenshot saved at: " + filePath);
    }

    // 📸 Take screenshot with timestamped filename
    public void takeScreenshotWithTimestamp(String baseName) {
        String folderPath = getScreenshotsFolder();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = folderPath + File.separator + baseName + "_" + timestamp + ".png";
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get(filePath))
            .setFullPage(true));
        System.out.println("Screenshot saved at: " + filePath);
    }
    
}
