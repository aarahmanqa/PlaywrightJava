package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {
    String selSearchResultsText = "//div[contains(@class,'s-list-col-right')]//span[contains(@class,'a-text-normal')]";

    public List<String> getSearchResultsTitle() {
        Locator locator = bf.locator(selSearchResultsText);
        for(int i=0; i<10; i++) {
            if(locator.count() > 1) {
                break;
            }
            bf.minWait();
        }
        List<String> searchResultsTitle = new ArrayList<>();
        for(int i=0; i<locator.count(); i++) {
            searchResultsTitle.add(locator.nth(i).innerHTML());
        }
        return searchResultsTitle;
    }
}
