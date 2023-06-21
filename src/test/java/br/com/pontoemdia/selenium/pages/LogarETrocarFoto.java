package br.com.pontoemdia.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.pontoemdia.selenium.utils.Acoes;

public class LogarETrocarFoto extends Acoes{

	private WebDriver driver;
	
	public LogarETrocarFoto(WebDriver driver) {
		this.driver = driver;
	}
	
	public static final By inputLogin = By.xpath("//div[@class='wrap-input100 validate-input m-b-23']//input[@class='input100']");
	public static final By inputSenha = By.xpath("//div[@class='wrap-input100 validate-input']//input[@class='input100']");
	public static final By btnLogar = By.xpath("//input[@class='login100-form-btn']");
	public static final By btnMenu = By.xpath("//div[@class='header_toggle']");
	public static final By btnMeuPerfil = By.xpath("//nav//a[@class='nav_link']//span[text()='Meu perfil']");
	public static final By btnEnviar = By.xpath("//input[@class='col-sm-1']");
	public static final By btnDashboard = By.xpath("//nav//a[@class='nav_link']//span[text()='Dashbord']");
	
	
	public void automacao() {
		//colocar dados no login
		click(driver,inputLogin,20);
		enviarDados(driver, inputLogin, "admin", 20);
		
		//colocar dados na senha
		click(driver,inputSenha,20);
		enviarDados(driver, inputSenha, "admin", 20);
		
		//clicar no botão login
		click(driver,btnLogar,20);
		
		//clicar no botão do menu
		click(driver,btnMenu,20);
		
		//clicar no botão do meu perfil dentro do menu
		click(driver,btnMeuPerfil,20);
		
		//clicar no botão enviar
		click(driver,btnEnviar,20);
		
		//clicar no botão do menu
		click(driver,btnMenu,20);
		
		//clicar no botão dashboard dentro do menu
		click(driver,btnDashboard,20);
		
	}
}
