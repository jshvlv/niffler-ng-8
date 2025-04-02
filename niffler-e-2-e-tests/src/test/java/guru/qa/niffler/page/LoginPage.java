package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement registerBtn = $(".form__register");
  private final SelenideElement formErrorText = $(".form__error");


  public MainPage doLogin(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitBtn.click();
    return new MainPage();
  }

  public RegisterPage ClickCreateNewAccountBtn(){
    registerBtn.click();
    return new RegisterPage();
  }

  public void checkLoginErrorUserNotFound(){
    String error = "Неверные учетные данные пользователя";
    formErrorText.shouldHave(Condition.text(error));
    submitBtn.shouldBe(Condition.visible);
    registerBtn.shouldBe(Condition.visible);
  }
}
