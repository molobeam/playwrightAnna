package utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.LoadState;

public class WaitUtils {
    private Page page;

    public WaitUtils(Page page) {
        this.page = page;
    }

    // Wait for element to be visible
    public void waitForVisibility(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE));
    }

    // Wait for element to be clickable (attached + visible)
    public void waitForClickable(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.ATTACHED));
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE));
    }

    // Wait for element to disappear
    public void waitForInvisibility(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.DETACHED));
    }

    // Default wait for page load
    public void waitForPageLoad() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    // Overloaded wait with custom timeout
    public void waitForPageLoad(int timeoutMs) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED, 
            new Page.WaitForLoadStateOptions().setTimeout(timeoutMs));
        page.waitForLoadState(LoadState.NETWORKIDLE, 
            new Page.WaitForLoadStateOptions().setTimeout(timeoutMs));
    }
    

}
