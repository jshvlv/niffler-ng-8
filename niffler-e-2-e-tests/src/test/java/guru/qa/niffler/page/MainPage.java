package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

  private final ElementsCollection tableRows = $$("#spendings tbody tr");
  private final ElementsCollection titles = $$("h2");
  private final SelenideElement searchInput = $("input[placeholder='Search'][type='text']");
  private final SelenideElement newSpendingBtn = $x("//a[text() = 'New spending']");
  private final SelenideElement profileBtn = $(".MuiAvatar-root");
  private final SelenideElement profileBtnInMenu = $x("//a[text() = 'Profile']");

  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .$$("td")
        .get(5)
        .click();
    return new EditSpendingPage();
  }

  public void checkThatTableContains(String spendingDescription) {
    tableRows.find(text(spendingDescription))
        .should(visible);
  }

  public void checkPageIsOpen(){
    titles.find(Condition.text("Statistics")).shouldBe(visible);
    titles.find(Condition.text("History of Spendings")).shouldBe(visible);
    searchInput.shouldBe(visible);
    profileBtn.shouldBe(visible);
  }

  public ProfilePage clickProfileBtn() {
    profileBtn.click();
    profileBtnInMenu.click();
    return new ProfilePage();
  }

}
