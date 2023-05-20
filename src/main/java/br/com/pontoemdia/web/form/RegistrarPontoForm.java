package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class RegistrarPontoForm implements Serializable {

	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void registrar() throws ServletException, IOException {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		externalContext.redirect("/ponto?acao=registrarPonto");
	}

}
