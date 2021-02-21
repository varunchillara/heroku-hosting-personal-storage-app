package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(xpath = "//*[@id=\"add-new-note\"]")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div[2]/div[2]/div/div/div[3]/button[2]")
    private WebElement saveNoteChangesButton;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div[2]/div[1]/table/tbody/tr/td[1]/button")
    private WebElement editNoteButton;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div[2]/div[1]/table/tbody/tr/td[1]/a")
    private WebElement deleteNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement cretentialTab;

    @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/button")
    private WebElement addNewCredentialButton;

    @FindBy(xpath = "//*[@id=\"credential-url\"]")
    private WebElement credentialUrlInput;

    @FindBy(xpath = "//*[@id=\"credential-username\"]")
    private WebElement credentialUsernameInput;

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement credentialPasswordInput;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div/div[3]/div[2]/div/div/div[3]/button[2]")
    private WebElement saveCredentialButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement credentialEditButton;

    @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[1]/button")
    private WebElement credentialEditButton1;

    @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[1]/td[1]/a")
    private WebElement credentialDeleteButton;

    @FindBy(xpath = "/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[2]/td[1]/a")
    private WebElement credentialDeleteButton1;




    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void createNote(WebDriver driver, String inputTitle, String inputDescription) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
        Thread.sleep(5000);

        noteTitleInput.clear();
        noteTitleInput.sendKeys(inputTitle);

        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(inputDescription);
        Thread.sleep(5000);

        saveNoteChangesButton.click();
        Thread.sleep(5000);

        jse.executeScript("arguments[0].click()", notesTab);
        Thread.sleep(5000);
    }

    public void editNote(WebDriver driver, String inputTitle, String inputDescription) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        Thread.sleep(5000);

        noteTitleInput.clear();
        noteTitleInput.sendKeys(inputTitle);

        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(inputDescription);

        saveNoteChangesButton.click();
        Thread.sleep(5000);

        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void deleteNote(WebDriver driver) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", notesTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
        Thread.sleep(5000);
        jse.executeScript("arguments[0].click()", notesTab);
        Thread.sleep(5000);
    }

    public void createCredentialSet(WebDriver driver, String urlInput, String usernameInput, String passwordInput,
                                             String urlInput1, String usernameInput1, String passwordInput1) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", cretentialTab);
        Thread.sleep(2500);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNewCredentialButton)).click();
        Thread.sleep(5000);

        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(urlInput);

        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(usernameInput);

        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(passwordInput);
        Thread.sleep(5000);

        saveCredentialButton.click();
        Thread.sleep(5000);

        jse.executeScript("arguments[0].click()", cretentialTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNewCredentialButton)).click();
        Thread.sleep(5000);

        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(urlInput1);

        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(usernameInput1);

        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(passwordInput1);
        Thread.sleep(5000);

        saveCredentialButton.click();
        Thread.sleep(5000);

        jse.executeScript("arguments[0].click()", cretentialTab);
        Thread.sleep(5000);
    }

    public void editCredential(WebDriver driver, String urlInput,
                               String usernameInput, String urlInput1, String usernameInput1) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click()", cretentialTab);
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(credentialEditButton)).click();
            Thread.sleep(5000);

            credentialUrlInput.clear();
            credentialUrlInput.sendKeys(urlInput);

            credentialUsernameInput.clear();
            credentialUsernameInput.sendKeys(usernameInput);

            //credentialUsernameInput.clear();
            //credentialUsernameInput.sendKeys(passwordInput);

            saveCredentialButton.click();
            Thread.sleep(5000);

            jse.executeScript("arguments[0].click()", cretentialTab);

            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(credentialEditButton1)).click();
            Thread.sleep(5000);

            credentialUrlInput.clear();
            credentialUrlInput.sendKeys(urlInput1);

            credentialUsernameInput.clear();
            credentialUsernameInput.sendKeys(usernameInput1);

            //credentialUsernameInput.clear();
            //credentialUsernameInput.sendKeys(passwordInput1);

            saveCredentialButton.click();
            Thread.sleep(5000);

            jse.executeScript("arguments[0].click()", cretentialTab);
            Thread.sleep(5000);


        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCredentials(WebDriver driver) {

        try {

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("arguments[0].click()", cretentialTab);

            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(credentialDeleteButton)).click();
            Thread.sleep(3000);

            jse.executeScript("arguments[0].click()", cretentialTab);

            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(credentialDeleteButton)).click();
            Thread.sleep(3000);


            jse.executeScript("arguments[0].click()", cretentialTab);
            Thread.sleep(3000);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}