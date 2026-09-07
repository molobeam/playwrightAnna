package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import utils.LocatorReader;

public class LoginPage {
    private Page page;
    private LocatorReader locatorReader;
    private String baseUrl;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
        this.locatorReader = new LocatorReader("src/test/resources/locators.properties");
    }

    public void navigateToLogin() {
        page.navigate(baseUrl);
        page.waitForLoadState();
    }

    public void enterUsername(String username) {
        page.locator(locatorReader.getLocator("login.username")).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(locatorReader.getLocator("login.password")).fill(password);
    }

    public void clickLogin() {
        page.locator(locatorReader.getLocator("login.button")).click();
        page.waitForLoadState();
    }

    public Locator getDashboardHeader() {
        return page.locator(locatorReader.getLocator("login.dashboard"));
    }

    // ✅ Corrected error message handling
    public Locator getErrorMessage() {
       String errorSelector = locatorReader.getLocator("login.error");

    if (errorSelector == null || errorSelector.isEmpty()) {
        throw new RuntimeException(
            "Locator 'login.error' is missing in locators.properties"
        );
    }

    Locator error = page.locator(errorSelector);

    assertThat(error).isVisible();

    return error;
    }
    public Locator getRequiredError() {
    return page.locator(
        locatorReader.getLocator("login.required")
    ).first();
}

    public String getExpectedErrorText(String key) {
        return locatorReader.getLocator("login.errorText." + key);
    }

    public void logout() {
        page.locator(locatorReader.getLocator("logout.dropdown")).click();
        page.locator(locatorReader.getLocator("logout.option")).click();
        page.waitForLoadState();
        page.waitForSelector(locatorReader.getLocator("login.username"));
    }
}
