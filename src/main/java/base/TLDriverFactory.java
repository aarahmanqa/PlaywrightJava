package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;

import java.awt.*;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TLDriverFactory {
    public static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    public static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    public static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    public static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    public static ThreadLocal<Boolean> tlTrace = new ThreadLocal<>();

    public enum BROWSERS{
        CHROMIUM,
        FIREFOX,
        SAFARI
    }

    public static void launchBrowser(BROWSERS browsers, boolean headless, boolean enableTrace) {
        Playwright playwright = Playwright.create();
        Browser browser = null;
        Page page = null;
        BrowserContext browserContext = null;
        List<String> args = new ArrayList<>();
        args.add("--start-maximized");
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.args = args;
        launchOptions.setHeadless(headless);
        switch(browsers) {
            case FIREFOX:
                browser = playwright.firefox().launch(launchOptions);
                break;
            case SAFARI:
                browser = playwright.webkit().launch(launchOptions);
                break;
            default:
                browser = playwright.chromium().launch(launchOptions);
        }
        Browser.NewContextOptions newContextOptions = setMaximize();
        browserContext = browser.newContext(newContextOptions);
        page = browserContext.newPage();
        tlTrace.set(enableTrace);
        if(enableTrace) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
        }
        tlPlaywright.set(playwright);
        tlBrowserContext.set(browserContext);
        tlBrowser.set(browser);
        tlPage.set(page);
    }

    public static Browser.NewContextOptions setMaximize() {
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        return new Browser.NewContextOptions().setViewportSize(new ViewportSize(width,height));
    }

    public static void closeTraceIfPresent(Method method) {
        if(tlTrace.get() != null && tlTrace.get()) {
            TLDriverFactory.tlBrowserContext.get().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("traces/" + method.getName() + ".zip")));
        }
    }
}
