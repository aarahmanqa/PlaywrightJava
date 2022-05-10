package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomePage extends BasePage {
    String selGlobalSearchBox = "input[id='twotabsearchtextbox']";
    String selSearchSuggestionList = "div.s-suggestion-container";

    String selHamburgerMenu = "a#nav-hamburger-menu";
    String selBestSellers = "//div[@id='hmenu-content']//a[text()='Best Sellers']";

    public void globalSearch(String value,int index) {
        bf.fillValue(selGlobalSearchBox,value);
        Locator suggestionList = bf.locator(selSearchSuggestionList);
        suggestionList.nth(index).click();
    }
}
