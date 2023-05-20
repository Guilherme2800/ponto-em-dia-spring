package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoginForm implements Serializable {

	private String login;
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void enviar() throws ServletException, IOException {
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute("login", this.login);
        request.setAttribute("senha", this.senha);
        
        externalContext.redirect("/login?login=" + this.login + "&senha=" + this.senha);
    }

}
