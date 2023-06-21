package br.com.pontoemdia.selenium.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Acoes {

	public static void click(WebDriver driver, By by, int espera) {
		WebElement element = (new WebDriverWait(driver, espera))
				.until(ExpectedConditions.elementToBeClickable(by));
		element.click();
	}
	
	public WebElement enviarDados(WebDriver driver, By by, String valor, int espera) {
		WebDriverWait wait = new WebDriverWait(driver, espera);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		WebElement element = driver.findElement(by);
		element.sendKeys(valor);
		return element;
	}
}
