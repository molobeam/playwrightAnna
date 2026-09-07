package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.*;
import utils.JsonReader;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.Map;

public class LoginTest extends BaseTest {

    private Map<String, Map<String, String>> loginData;

    @BeforeSuite
    public void loadData() {
        loginData = JsonReader.getLoginData("loginData.json");
    }

    @BeforeClass
    public void start() {
        setup(); // 🔹 initializes config, driverFactory, page
    }

    // 🔹 This runs before every @Test method
    @BeforeMethod
    public void resetPage() {
        page.navigate(config.getProperty("baseUrl"));
    }

    @DataProvider(name = "jsonDataProvider")
    public Object[][] jsonDataProvider() {
        return loginData.entrySet().stream()
                .map(entry -> new Object[] {
                        entry.getKey(),
                        entry.getValue().get("username"),
                        entry.getValue().get("password")
                })
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonDataProvider")
    public void testLogin(String type, String username, String password) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (type.equals("valid")) {
            assertThat(loginPage.getDashboardHeader()).hasText("Dashboard");
        } else {
            assertThat(loginPage.getErrorMessage()).hasText("Invalid credentials");
        }
    }

    @AfterClass
    public void stop() {
        teardown();
    }
}
