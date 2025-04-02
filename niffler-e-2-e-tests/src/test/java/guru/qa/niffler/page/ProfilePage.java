package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private final ElementsCollection titles = $$("h2");
    private final SelenideElement usernameField = $("input[name='username']");
    private final SelenideElement nameField = $("input[name='name']");
    private final SelenideElement saveBtn = $x("//button[text()='Save changes']");
    private final SelenideElement showArchivedCheckbox = $(".MuiSwitch-switchBase");
    private final SelenideElement archiveBtnDialog = $x("//button[text()='Archive']");
    private final SelenideElement unarchiveBtnDialog = $x("//button[text()='Unarchive']");
    private final SelenideElement categoryAlertMessage = $(".MuiAlert-message");
    private final String categoryRowButtonsXpath = "//div[contains(@class, 'MuiBox-root') and contains(., '%s')]//button";

    public ProfilePage checkPageIsOpen(String username, String name) {
        titles.find(Condition.text("Profile")).shouldBe(visible);
        titles.find(Condition.text("Categories")).shouldBe(visible);
        usernameField.shouldBe(visible).shouldHave(value(username));
        nameField.shouldBe(visible).shouldHave(value(name));
        saveBtn.shouldBe(visible);
        return this;
    }

    public ProfilePage archiveCategory(String name) {
        showArchivedCheckbox.click();
        showArchivedCheckbox.shouldHave(cssClass("Mui-checked"));
        $$x(String.format(categoryRowButtonsXpath, name)).last().shouldHave(attribute("aria-label", "Archive category")).click();
        archiveBtnDialog.shouldBe(visible).click();
        categoryAlertMessage.shouldHave(text(String.format("Category %s is archived", name)));
        return this;
    }

    public ProfilePage unArchiveCategory(String name) {
        showArchivedCheckbox.click();
        showArchivedCheckbox.shouldHave(cssClass("Mui-checked"));
        $$x(String.format(categoryRowButtonsXpath, name)).last().shouldHave(attribute("aria-label", "Unarchive category")).click();
        unarchiveBtnDialog.click();
        categoryAlertMessage.shouldHave(text(String.format("Category %s is unarchived", name)));
        return this;
    }
}
