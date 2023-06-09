package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.pontoemdia.model.TipoUsuario;

@Component
@SessionScope
public class CadastrarUsuarioForm implements Serializable {

	private String login;
	private String senha;
	private String nome;
	private String nascimento;
	private String cargo;
	private String tipoUsuario;
	private String urlImagem;
	private Long celular;
	private String email;
	private Long cpf;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Long getCelular() {
		return celular;
	}

	public void setCelular(Long celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public void cadastrarUsuario() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		externalContext.getFlash().setKeepMessages(true);
		
		if(validarCampos().size() > 0) {
			for(String mensagem : validarCampos()) {				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
						mensagem));
			}
					
					externalContext.redirect("/entrada?acao=ExibirCadastrarUsuario");
					
					return;
		}
		
		formataData();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", 
				"Usuário cadastrado com sucesso"));

		externalContext.redirect("/usuario?acao=cadastrarUsuario");
	}
	
	protected List<String> validarCampos() {
		
		List<String> mensagens = new ArrayList<>();

		if(!validarCPF()) {
			mensagens.add("CPF inválido");
		}
		
		if(this.login.isEmpty()) {
			mensagens.add("Login é obrigatório");
		}
		
		if(this.nascimento.isEmpty()) {
			mensagens.add("A data de nascimento é obrigatória");
		} else if(this.nascimento.length() != 10){
			mensagens.add("A data de nascimento é inválida");
		}
		
		if(this.nome.isEmpty()) {
			mensagens.add("O nome é obrigatório");
		}
		
		if(this.tipoUsuario.isEmpty()) {
			mensagens.add("O tipo de usuário é obrigatório");
		}
		
		if(this.senha.isEmpty()) {
			mensagens.add("A senha de acesso é obrigatória");
		}
		return mensagens;
	}
	
	protected boolean validarCPF() {
		boolean retorno = false;
		
        if(this.cpf != 0) {
        	if (!this.cpf.toString().matches("(\\d)\\1{10}")) {
        		if(!(this.cpf < new Long("10000000000")) || !(this.cpf < new Long("99999999999"))) {	
        			// Calcular o primeiro dígito verificador
        	        int sum = 0;
        	        for (int i = 0; i < 9; i++) {
        	            sum += (this.cpf.toString().charAt(i) - '0') * (10 - i);
        	        }
        	        int digit1 = 11 - (sum % 11);
        	        if (digit1 > 9) {
        	            digit1 = 0;
        	        }

        	        // Calcular o segundo dígito verificador
        	        sum = 0;
        	        for (int i = 0; i < 10; i++) {
        	            sum += (this.cpf.toString().charAt(i) - '0') * (11 - i);
        	        }
        	        int digit2 = 11 - (sum % 11);
        	        if (digit2 > 9) {
        	            digit2 = 0;
        	        }

        	        // Verificar se os dígitos calculados são iguais aos dígitos informados
        	        retorno = (digit1 == this.cpf.toString().charAt(9) - '0') && (digit2 == this.cpf.toString().charAt(10) - '0');	
    			}
            }
        }
		
		return retorno;
	}
	
	protected void formataData()
	{
		String day = this.nascimento.substring(0, 2);
        String month = this.nascimento.substring(3, 5);
        String year = this.nascimento.substring(6, 10);
        
        this.nascimento = year + "-" + month + "-" + day;
	}

}
