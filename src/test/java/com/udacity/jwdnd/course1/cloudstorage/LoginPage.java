package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement loginButton;



    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(Map<String, String> usernameAndPassword) throws Exception {

        String username;
        String password;

        for(String user: usernameAndPassword.keySet()) {
            String pass = usernameAndPassword.get(user);
            username = user;
            password = pass;

            inputUsername.clear();
            inputUsername.sendKeys(username);
            inputPassword.sendKeys(password);

            loginButton.click();
            Thread.sleep(5000);
        }
    }

}
