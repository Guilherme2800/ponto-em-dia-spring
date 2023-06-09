package br.com.pontoemdia.web.form;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.UsuarioService;

@Component
@SessionScope
public class AlterarPerfilForm implements Serializable {

	private Long id;
	private String nome;
	private String urlImagem;
	private Long celular;
	private String email;
	private Long cpf;
	private Part file;
	protected int status;

	@Autowired
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		Usuario userFull = this.usuarioService.buscarUsuario(user.getId());

		this.setId(userFull.getId());
		this.setCelular(userFull.getCelular());
		this.setCpf(userFull.getCpf());
		this.setNome(userFull.getNome());
		this.setEmail(userFull.getEmail());
		this.setUrlImagem(userFull.getUrlImagem());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void alterarPerfil() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		
		externalContext.getFlash().setKeepMessages(true);
		
		if(this.status == - 1) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
				"Erro ao enviar foto"));
				
				externalContext.redirect("/usuario?acao=ExibirPerfil");
				
				return;
		} 
		
		if(validarCampos().size() > 0) {
			for(String mensagem : validarCampos()) {				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
						mensagem));
			}
					
					externalContext.redirect("/usuario?acao=ExibirPerfil");
					
					return;
		}
		
		this.status = 0;
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", 
				"Sucesso ao realizar operação"));

		externalContext.redirect("/usuario?acao=alterarPerfil");
	}

	public void uploadFoto() throws IOException {

		if(file == null) {
			
			 this.status = -1;
			 
		} else {
			
			try {
				InputStream input = file.getInputStream();
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				// Diretório onde os arquivos serão salvos dentro do projeto
				String diretorio = externalContext.getRealPath("assets/usuario-foto");

				// Crie o diretório se não existir
				File diretorioFile = new File(diretorio);
				if (!diretorioFile.exists()) {
					diretorioFile.mkdirs();
				}
				
				if (!getExtensao(file.getSubmittedFileName()).equalsIgnoreCase(".png")
						&& !getExtensao(file.getSubmittedFileName()).equalsIgnoreCase(".jpg")
						&& !getExtensao(file.getSubmittedFileName()).equalsIgnoreCase(".jpeg")) {

					this.status = -1;
				}

				// Gere um nome único para o arquivo
				String nomeArquivo = this.getCpf() + getExtensao(file.getSubmittedFileName());

				// Crie o caminho completo para o arquivo
				String caminhoCompleto = diretorio + File.separator + nomeArquivo;

				Files.copy(input, new File(caminhoCompleto).toPath(), StandardCopyOption.REPLACE_EXISTING);

				this.setUrlImagem("assets/usuario-foto/" + nomeArquivo);
				
				this.status = 1;


				// Exibir uma mensagem de sucesso
			} catch (IOException e) {
				// Tratar erros
				e.printStackTrace();
				// Exibir uma mensagem de erro
			}
			
		}
		
		alterarPerfil();
	}

	protected String getExtensao(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
	}
	
	protected List<String> validarCampos() {
		
		List<String> mensagens = new ArrayList<>();

		if(this.celular != 0) {
			if((this.celular < new Long("10000000000") || this.celular < new Long("99999999999"))) {				
				mensagens.add("Celular inválido");
			}
		}
		if(!this.email.isEmpty()) {
			String EMAIL_PATTERN = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
			Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			
			if(!matcher.matches()) {				
				mensagens.add("Email inválido");
			}
		}
		
		return mensagens;
	}
	

}
