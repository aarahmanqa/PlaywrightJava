package basicTests;

import base.BaseFunctions;
import base.TLDriverFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BestSellers;
import pages.HomePage;
import pages.SearchResultsPage;

import java.lang.reflect.Method;
import java.util.List;

public class HomePageTestCases {

    @BeforeMethod
    public void launchPage(ITestContext ctx) {
        TLDriverFactory.launchBrowser(TLDriverFactory.BROWSERS.CHROMIUM, false,false);
        new BaseFunctions().launchUrl("https://amazon.in");
    }

    @AfterMethod
    public void afterMethod(Method method) {
        TLDriverFactory.closeTraceIfPresent(method);
    }

    @Test(enabled = true)
    public void validateGlobalSearch() {
        System.out.println("validateGlobalSearch starts");
        HomePage homePage = new HomePage();
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        homePage.globalSearch("Apple iPhone 13", 0);
        List<String> searchResultsTitle = searchResultsPage.getSearchResultsTitle();
        searchResultsTitle.forEach(System.out::println);
        System.out.println("validateGlobalSearch ends");
    }

    @Test(enabled = true)
    public void validateBestSellers() {
        System.out.println("validateBestSellers starts");
        BaseFunctions bf = new BaseFunctions();
        HomePage homePage = new HomePage();
        bf.click(homePage.getSelHamburgerMenu());
        bf.click(homePage.getSelBestSellers());

        BestSellers bestSellers = new BestSellers();
        List<List<String>> allCarouselItems = bestSellers.getAllCarouselItems(0);
        allCarouselItems.stream().forEach(list -> {
            list.forEach(x -> System.out.print(x + " - "));
            System.out.println();
        });
        System.out.println("validateBestSellers ends");
    }
}
