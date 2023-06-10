package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
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
		
		if(!(validarDatas().equalsIgnoreCase(""))){
			
			externalContext.getFlash().setKeepMessages(true);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					validarDatas()));
			
			externalContext.redirect("/entrada?acao=ExibirHistoricoUsuario");
			
			return;
		}

		externalContext.redirect("/ponto?acao=historicoUsuario");
	}
	
	public String validarDatas(){
		
		String mensagem = "";
		
		if(this.dataInicial.isEmpty()) {
			this.dataInicial = "2023-01-01";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(this.dataFinal.isEmpty()) {
			Date currentDate = new Date();
			
	        this.dataFinal = sdf.format(currentDate);   
		} 

        try {
            Date dtInicio = sdf.parse(this.dataInicial);
    		Date dtFinal = sdf.parse(this.dataFinal);
            if(dtInicio.getTime() > dtFinal.getTime()) {
    			mensagem = "A data final não pode ser menor que a inicial";				
    		}
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		return mensagem;
	}
}
