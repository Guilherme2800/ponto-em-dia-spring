package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;
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
		
		System.out.println("AQUI->" + this.dataInicial);
		System.out.println("AQUI->" + this.dataFinal);
		
		if(!(validarDatas().equalsIgnoreCase(""))){
			
			externalContext.getFlash().setKeepMessages(true);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					validarDatas()));
			
			externalContext.redirect("/entrada?acao=ExibirHistoricoUsuario");
			
			return;
		}
		
		this.dataInicial = formatarData(this.dataInicial);
		this.dataFinal = formatarData(this.dataFinal);

		externalContext.redirect("/ponto?acao=historicoUsuario");
	}
	
	public String validarDatas(){
		
		String mensagem = "";
		
		if(this.dataInicial.isEmpty()) {
			this.dataInicial = "01/01/2000";
		}
		
		if(this.dataFinal.isEmpty()) {
			Date currentDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
	        this.dataFinal = sdf.format(currentDate);   
		} 
		
		
		if(new Date(this.dataInicial).getTime() > new Date(this.dataFinal).getTime()) {
			mensagem = "A data final não pode ser menor que a inicial";				
		}
		
		String regex = "(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}";
		
		if(!this.dataFinal.matches(regex)){
			mensagem = "A data final não é válida";	
		}
		
		if(!this.dataInicial.matches(regex)){
			mensagem = "A data inicial não é válida";	
		}
		System.out.println(this.dataInicial);
		System.out.println(this.dataFinal);
		return mensagem;
	}
	
	protected String formatarData(String data)
	{
		String day = data.substring(0, 2);
        String month = data.substring(3, 5);
        String year = data.substring(6, 10);
        
        return year + "-" + month + "-" + day;
	}

}
