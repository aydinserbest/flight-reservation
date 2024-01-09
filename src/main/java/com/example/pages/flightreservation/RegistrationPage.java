package com.example.pages.flightreservation;

import com.example.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends AbstractPage {
    @FindBy(id = "firstName")
    private WebElement firstNameInput;
    @FindBy(id = "Last Name")
    private WebElement lastNameInput;
    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(name = "street")
    private WebElement streetInput;
    @FindBy(name = "city")
    private WebElement cityInput;
    @FindBy(id = "inputState")
    private WebElement stateInput;
    @FindBy(name = "zip")
    private WebElement zipInput;
    @FindBy(id = "register-btn")
    private WebElement registerButton;


    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        return isDisplayed(firstNameInput);
    }

    public void enterUserDetails(String username, String password){
        type(firstNameInput, username);
        type(passwordInput, password);
    }
    public void selectState(String state) {
        Select stateDropdown = new Select(stateInput);
        stateDropdown.selectByVisibleText(state);
    }
}
