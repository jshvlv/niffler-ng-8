package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement passwordSubmitInput = $("input[name='passwordSubmit']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement successRegisterText = $(".form__paragraph.form__paragraph_success");
  private final SelenideElement signInBtn = $(".form_sign-in");
  private final SelenideElement formErrorText = $(".form__error");

  public RegisterPage setName(String username) {
    usernameInput.shouldBe(Condition.visible).setValue(username);
    return this;
  }

  public RegisterPage setPassword(String password){
    passwordInput.shouldBe(Condition.visible).setValue(password);
    return this;
  }

  public RegisterPage setPasswordSubmit(String passwordSubmit) {
    passwordSubmitInput.shouldBe(Condition.visible).setValue(passwordSubmit);
    return this;
  }

  public RegisterPage clickSubmitBtn() {
    submitBtn.click();
    return this;
  }

  public LoginPage checkSuccessRegister() {
    successRegisterText.shouldHave(Condition.text("Congratulations! You've registered!"));
    signInBtn.click();
    return new LoginPage();
  }

  public RegisterPage checkError(String errorText) {
    formErrorText.shouldHave(Condition.text(errorText));
    return this;
  }

}
