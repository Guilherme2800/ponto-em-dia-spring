package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class BuscarPontosUsuarioForm implements Serializable {

	private String dataInicial;
	private String dataFinal;

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public void buscar() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		externalContext.redirect("/ponto?acao=historicoUsuario");
	}

}
