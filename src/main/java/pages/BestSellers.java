package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

public class BestSellers extends BasePage {
    String selCarouselGroup = "//div[contains(@id,'anonCarousel')]";
    String selCarouselCard = "//li[@class='a-carousel-card'][./div]";

    String selCarouselNextIcon = "//div[@class='a-carousel-row-inner']//div[contains(@class,'a-carousel-right')]//i";
    //String selCarouselNextIcon = "#a-autoid-1 i:has-text(\\\'Next page\\\')";
    String selCarouselPageCount = "//span[@class='a-carousel-page-count']";

    public List<List<String>> getAllCarouselItems(int index) {
        List<List<String>> allCarouselItems = new ArrayList<>();
        int pageCount = getPageCount(index);
        for(int x=0; x<pageCount; x++) {
            Locator carouselCard = bf.locator(selCarouselGroup).nth(index).locator(selCarouselCard);
            for (int i = 0; i < carouselCard.count(); i++) {
                List<String> carouselItems = new ArrayList<>();
                String number = carouselCard.nth(i).locator("//span[@class='zg-bdg-text']").innerText();
                String name = carouselCard.nth(i).locator("//a[@class='a-link-normal']/span/div").innerText();
                carouselItems.add(number);
                carouselItems.add(name);
                allCarouselItems.add(carouselItems);
            }
            if(isFinalPage(index)) {
                break;
            }
            bf.locator(selCarouselNextIcon).nth(index).click();
        }
        return allCarouselItems;
    }

    public int getPageCount(int index) {
        bf.locator(selCarouselPageCount).first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        String innerText = bf.locator(selCarouselPageCount).nth(index).innerText();
        String innerTextWithoutAlphabets = innerText.replaceAll("[A-Za-z]", " ").trim();
        String[] splitText = innerTextWithoutAlphabets.split("\\s+");
        return Integer.parseInt(splitText[1]);
    }

    public boolean isFinalPage(int index) {
        bf.locator(selCarouselPageCount).first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        String innerText = bf.locator(selCarouselPageCount).nth(index).innerText();
        String innerTextWithoutAlphabets = innerText.replaceAll("[A-Za-z]", " ").trim();
        String[] splitText = innerTextWithoutAlphabets.split("\\s+");
        return splitText[0].equals(splitText[1]);
    }
}
