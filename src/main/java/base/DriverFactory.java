package base;

import com.microsoft.playwright.*;

public class DriverFactory {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public Page initBrowser(String browserName, boolean headless) {
        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(
                    new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(
                    new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                throw new IllegalArgumentException("Invalid browser: " + browserName);
        }

        context = browser.newContext();
        page = context.newPage();
        return page;
    }

    public void closeBrowser() {
        context.close();
        browser.close();
        playwright.close();
    }
}
