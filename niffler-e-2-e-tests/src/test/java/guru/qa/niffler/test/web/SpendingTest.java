package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.*;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.RegisterPage;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Random;

@ExtendWith(BrowserExtension.class)
public class SpendingTest {

  private LoginPage loginPage = new LoginPage();
  private MainPage mainPage = new MainPage();
  private ProfilePage profilePage = new ProfilePage();
  private RegisterPage registerPage = new RegisterPage();

  @Spend(
      username = "admin",
      category = "Обучение",
      description = "Обучение Niffler 2.0",
      amount = 89000.00,
      currency = CurrencyValues.RUB
  )
  @Test
  void spendingDescriptionShouldBeUpdatedByTableAction(SpendJson spend) {
    final String newDescription = "Обучение Niffler NG";

    loginPage
        .doLogin("admin", "admin")
        .editSpending(spend.description())
        .editDescription(newDescription);

    mainPage.checkThatTableContains(newDescription);
  }

  @Test
  void shouldRegisterNewUser(){
    String randomName =  "alex" + new Random().nextInt(1000);// нужно дернуть ручку для зачистки этого мусора после теста

    loginPage
            .ClickCreateNewAccountBtn()
            .setName(randomName).setPassword(randomName)
            .setPasswordSubmit(randomName).clickSubmitBtn()
            .checkSuccessRegister();
  }

  @Test
  void shouldNotRegisterUserWithExistingUsername(){
    String randomName =  "alex" + new Random().nextInt(1000);// нужно дернуть ручку для зачистки этого мусора после теста

    String error = String.format("Username `%s` already exists", randomName);
    loginPage
            .ClickCreateNewAccountBtn()
            .setName(randomName).setPassword(randomName)
            .setPasswordSubmit(randomName).clickSubmitBtn()
            .checkSuccessRegister()
            .ClickCreateNewAccountBtn()
            .setName(randomName)
            .setPassword(randomName)
            .setPasswordSubmit(randomName)
            .clickSubmitBtn()
            .checkError(error);
  }

  @Test
  void shouldShowErrorIfPasswordsAreNotEqual(){
    String randomName =  "alex" + new Random().nextInt(1000);// нужно дернуть ручку для зачистки этого мусора после теста

    String error = "Passwords should be equal";
    loginPage
            .ClickCreateNewAccountBtn()
            .setName(randomName).setPassword(randomName)
            .setPasswordSubmit(randomName).clickSubmitBtn()
            .checkSuccessRegister()
            .ClickCreateNewAccountBtn()
            .setName(randomName)
            .setPassword(randomName)
            .setPasswordSubmit(randomName + "1")
            .clickSubmitBtn()
            .checkError(error);
  }

  @Test
  void mainPageShouldBeDisplayedAfterSuccessLogin(){
    loginPage.doLogin("admin", "admin");
    mainPage.checkPageIsOpen();
  }

  @Category(
          username = "admin",
          archived = true
  )
  @Test
  void userShouldStayOnLoginPageAfterLoginWithBadCreditionals(){
    loginPage.doLogin("admin", "damin");
    loginPage.checkLoginErrorUserNotFound();
  }



}
