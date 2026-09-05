package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import utils.LocatorReader;

public class LoginPage {
    private Page page;
    private LocatorReader locatorReader;

    public LoginPage(Page page) {
        this.page = page;
        this.locatorReader = new LocatorReader("src/test/resources/locators.properties");
    }

    public void navigateToLogin(String url) {
        page.navigate(url);
    }

    public void enterUsername(String username) {
        page.locator(locatorReader.getLocator("login.username")).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(locatorReader.getLocator("login.password")).fill(password);
    }

    public void clickLogin() {
        page.locator(locatorReader.getLocator("login.button")).click();
    }

    public Locator getDashboardHeader() {
        return page.locator(locatorReader.getLocator("login.dashboard"));
    }

    public Locator getErrorMessage() {
        return page.locator(locatorReader.getLocator("login.error"));
    }
}
