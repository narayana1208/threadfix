////////////////////////////////////////////////////////////////////////
//
//     Copyright (c) 2009-2013 Denim Group, Ltd.
//
//     The contents of this file are subject to the Mozilla Public License
//     Version 2.0 (the "License"); you may not use this file except in
//     compliance with the License. You may obtain a copy of the License at
//     http://www.mozilla.org/MPL/
//
//     Software distributed under the License is distributed on an "AS IS"
//     basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
//     License for the specific language governing rights and limitations
//     under the License.
//
//     The Original Code is ThreadFix.
//
//     The Initial Developer of the Original Code is Denim Group, Ltd.
//     Portions created by Denim Group, Ltd. are Copyright (C)
//     Denim Group, Ltd. All Rights Reserved.
//
//     Contributor(s): Denim Group, Ltd.
//
////////////////////////////////////////////////////////////////////////
package com.denimgroup.threadfix.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class LoginPage extends BasePage {

	public static String url = "http://localhost:8080/threadfix/";

	public LoginPage(WebDriver webdriver) {
		super(webdriver);
		
		String maybeUrl = System.getProperty("url");
		if (maybeUrl != null) {
			url = maybeUrl;
		}
		
		webdriver.get(url);

        System.out.println("Retrieving " + url);
        System.out.println("Got " + webdriver.getPageSource());

		if(webdriver instanceof InternetExplorerDriver){
			driver.get("javascript:document.getElementById('overridelink').click();");
		}
		//rememberCheckbox = driver.findElementById("checkbox");
	}
	
	/*----------------perform functions----------------*/
	public static LoginPage open(WebDriver webdriver) {
		return new LoginPage(webdriver);
	}
	
	public DashboardPage login(String user, String password) {
		return setUsername(user).setPassword(password).clickLogin();
	}
	
	public LoginPage loginInvalid(String user, String password) {
		setUsername(user).setPassword(password);
		driver.findElementById("login").click();
		sleep(3000);
		return new LoginPage(driver);
	}
	
	/*----------------get Functions----------------*/
	public boolean isloginError(){
		return driver.findElementById("loginError").getText().trim().equals("Error: Username or Password incorrect");
	}
	
	public boolean isLoggedOut(){
		return driver.getCurrentUrl().contains("login");
	}
	
	public boolean isUserNameFieldPresent(){
		return driver.findElementById("username").isDisplayed();
	}
	
	public String getUserNameInput(){
		return driver.findElementById("username").getAttribute("value");
	}
	
	public boolean isPasswordFieldPresent(){
		return driver.findElementById("password").isDisplayed();
	}
	
	public String getLoginInput(){
		return driver.findElementById("password").getAttribute("value");
	}
	
	public boolean isLoginButtonPresent(){
		return driver.findElementById("login").isDisplayed();
	}
	
	public boolean isLoginButtonClickable(){
		return ExpectedConditions.elementToBeClickable(By.id("login")) != null;
	}
	
	public boolean isRememberMeCheckBoxPresent(){
		return driver.findElementByName("_spring_security_remember_me").isDisplayed();
	}
	
	public boolean isRememeberMeCheckBoxSelected(){
		return driver.findElementByName("_spring_security_remember_me").isSelected();
	}
	
	
	
	/*----------------set functions----------------*/
	public LoginPage setUsername(String user) {
		driver.findElementById("username").sendKeys(user);
        //sleep(1000);
        return this;
	}
	
	public LoginPage setPassword(String password) {
		driver.findElementById("password").sendKeys(password);
        //sleep(1000);
        return this;
	}
	
	/*----------------click Functions----------------*/
	private DashboardPage clickLogin() {
        //sleep(3000);
        driver.findElementById("login").click();
        driver.findElementById("login").click();
        //driver.findElementById("password").sendKeys(Keys.ENTER);
		waitForElement(driver.findElementById("main-content"));
		return new DashboardPage(driver);
	}
	
	public LoginPage checkRememberCheckbox() {
		driver.findElementByName("_spring_security_remember_me").click();
		return this;
	}
}
