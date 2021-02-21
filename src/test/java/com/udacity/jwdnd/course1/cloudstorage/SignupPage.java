package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signupButton")
    private WebElement signupButton;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public Map<String, String> createUser(String firstnameInput, String lastnameInput, String usernameInput, String passwordInput) throws Exception {

        firstname.clear();
        firstname.sendKeys(firstnameInput);

        lastname.clear();
        lastname.sendKeys(lastnameInput);

        username.clear();
        username.sendKeys(usernameInput);

        password.clear();
        password.sendKeys(passwordInput);
        signupButton.click();
        Thread.sleep(500);

        Map<String, String> userAndPassword = new HashMap<>();
        userAndPassword.put(usernameInput, passwordInput);

        return userAndPassword;
    }

}
