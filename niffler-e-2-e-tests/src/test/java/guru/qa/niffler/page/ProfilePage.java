package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

  private final ElementsCollection titles = $$("h2");
  private final SelenideElement usernameField = $("input[name='username']");
  private final SelenideElement nameField = $("input[name='name']");
  private final SelenideElement saveBtn = $x("//button[text()='Save changes']");

  public ProfilePage checkPageIsOpen(String username, String name){
    titles.find(Condition.text("Profile")).shouldBe(visible);
    titles.find(Condition.text("Categories")).shouldBe(visible);
    usernameField.shouldBe(visible).shouldHave(value(username));
    nameField.shouldBe(visible).shouldHave(value(name));
    saveBtn.shouldBe(visible);
    return this;
  }
}
