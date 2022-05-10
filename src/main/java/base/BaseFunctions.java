package base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;

import java.nio.file.Paths;

public class BaseFunctions {

    static long screenshotIndex = 0;

    public void launchUrl(String url) {
        TLDriverFactory.tlPage.get().navigate(url);
    }

    public void click(String selector) {
        TLDriverFactory.tlPage.get().click(selector);
    }

    public void fillValue(String selector,String value) {
        TLDriverFactory.tlPage.get().fill(selector,value);
    }

    public void takeScreenshot(String...screenshotNames) {
        String screenShotName = "";
        if(screenshotNames.length > 0) {
            screenShotName = screenshotNames[0].replaceAll(" ","_");
        } else {
            screenShotName = "Screenshot";
        }
        screenShotName += screenShotName + "_" + screenshotIndex++;
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        screenshotOptions.setFullPage(true);
        screenshotOptions.setType(ScreenshotType.PNG);
        screenshotOptions.setPath(Paths.get("screenshots/" + screenShotName + ".png"));
        TLDriverFactory.tlPage.get().screenshot(screenshotOptions.setFullPage(true));
    }

    public Locator locator(String selector) {
        return TLDriverFactory.tlPage.get().locator(selector);
    }

    public void minWait(int...timeout) {
        try {
            if(timeout.length > 1) {
                Thread.sleep(timeout[0]);
            } else {
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
        }
    }
}
