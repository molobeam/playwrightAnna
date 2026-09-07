package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import utils.JsonReader;
import utils.ScreenShotUtils;
import java.util.Map;

public class LoginTest extends BaseTest {

    private Map<String, Map<String, String>> loginData;
    private LoginPage loginPage;

    @BeforeClass
    public void start() {
        setup();
        loginData = JsonReader.getLoginData("testdata/loginData.json");
        loginPage = new LoginPage(page, config.getProperty("baseUrl"));
    }

    @Test(priority = 1)
    public void testValidLogin() {
        loginPage.navigateToLogin();
        loginPage.enterUsername(loginData.get("valid").get("username"));
        loginPage.enterPassword(loginData.get("valid").get("password"));
        loginPage.clickLogin();

        assertThat(loginPage.getDashboardHeader()).isVisible();
        new ScreenShotUtils(page).takeScreenshotWithTimestamp("validLogin");

        loginPage.logout();
    }

    @DataProvider(name = "invalidDataProvider")
    public Object[][] invalidDataProvider() {
        return new Object[][] {
            { "invalid", loginData.get("invalid").get("username"), loginData.get("invalid").get("password"), "invalid" },
            { "emptyUsername", loginData.get("emptyUsername").get("username"), loginData.get("emptyUsername").get("password"), "emptyUsername" },
            { "emptyPassword", loginData.get("emptyPassword").get("username"), loginData.get("emptyPassword").get("password"), "emptyPassword" }
        };
    }

    @Test(dataProvider = "invalidDataProvider", priority = 2)
    public void testInvalidScenarios(String type, String username, String password, String expectedKey) {
        loginPage.navigateToLogin();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

       // Check the correct error message
    if (type.equals("invalid")) {

        assertThat(loginPage.getErrorMessage())
            .hasText(loginPage.getExpectedErrorText(expectedKey));

    } else {

        assertThat(loginPage.getRequiredError())
            .hasText("Required");
    }

        new ScreenShotUtils(page).takeScreenshotWithTimestamp(type + "Login");
    }

    @AfterClass
    public void stop() {
        teardown();
    }
}
