package br.com.pontoemdia.web.form;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
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

		externalContext.redirect("/usuario?acao=alterarPerfil");
	}

	public void uploadFoto() throws IOException {

		if(file == null) {
			this.setUrlImagem("");
			alterarPerfil();
			return;
		}
		
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
					&& !getExtensao(file.getSubmittedFileName()).equalsIgnoreCase(".jpg")) {

				this.setUrlImagem("");

				alterarPerfil();
			}

			// Gere um nome único para o arquivo
			String nomeArquivo = this.getCpf() + getExtensao(file.getSubmittedFileName());

			// Crie o caminho completo para o arquivo
			String caminhoCompleto = diretorio + File.separator + nomeArquivo;

			Files.copy(input, new File(caminhoCompleto).toPath(), StandardCopyOption.REPLACE_EXISTING);

			this.setUrlImagem("assets/usuario-foto/" + nomeArquivo);

			alterarPerfil();

			// Exibir uma mensagem de sucesso
		} catch (IOException e) {
			// Tratar erros
			e.printStackTrace();
			// Exibir uma mensagem de erro
		}
	}

	protected String getExtensao(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
	}

}
