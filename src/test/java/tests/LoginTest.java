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
        setup(); // 🔹 initialize config, driverFactory, page
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
        System.out.println("▶ Running " + type + " login test with username: " + username);

        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLogin(config.getProperty("baseUrl"));
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (type.equals("valid")) {
            assertThat(loginPage.getDashboardHeader()).hasText("Dashboard");
            System.out.println("✔ Valid login passed");
        } else {
            assertThat(loginPage.getErrorMessage()).hasText("Invalid credentials");
            System.out.println("✘ Invalid login handled correctly");
        }
    }

    @AfterClass
    public void stop() {
        teardown();
    }
}
