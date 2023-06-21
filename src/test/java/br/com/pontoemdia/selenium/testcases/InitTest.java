package br.com.pontoemdia.selenium.testcases;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.pontoemdia.selenium.pages.LogarETrocarFoto;
import br.com.pontoemdia.selenium.utils.GerenciamentoDriver;

public class InitTest {

	private WebDriver driver;
	GerenciamentoDriver gerenciamentoDriver;
	LogarETrocarFoto logarETrocarFoto;

	@Before
	public void setup() {
		gerenciamentoDriver = new GerenciamentoDriver();
		driver = GerenciamentoDriver.browser("chrome");
		logarETrocarFoto = new LogarETrocarFoto(driver);
	}

	@Test
	public void initTest() {
		driver.get("http://localhost:8080");
		logarETrocarFoto.automacao();
	}

	public void endTest() {
		// driver.quit();
	}
}
