package br.com.pontoemdia.web.form;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.repository.UsuarioRepository;

@Component
@SessionScope
public class CriarGrupoForm implements Serializable {

	private static final long serialVersionUID = 8750902203522198389L;
	private String administrador;
	private String nome;
	private String horarioEntrada;
	private String horariosaida;
	private int horasDiariasTrabalhadas;
	private List<Usuario> usuariosDoGrupo;

	List<Usuario> usuariosParaAdmin;
	List<Usuario> usuariosParaGrupo;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostConstruct
	public void init() {
		usuariosParaAdmin = usuarioRepository.findAll();
		usuariosParaGrupo = usuarioRepository.findAll().stream().filter(user -> user.getGrupo() == null).collect(Collectors.toList());
		System.out.println();
	}

	public List<Usuario> getUsuariosParaGrupo() {
		return usuariosParaGrupo;
	}

	public void setUsuariosParaGrupo(List<Usuario> usuariosParaGrupo) {
		this.usuariosParaGrupo = usuariosParaGrupo;
	}

	public List<Usuario> getUsuariosDoGrupo() {
		return usuariosDoGrupo;
	}

	public void setUsuariosDoGrupo(List<Usuario> usuariosDoGrupo) {
		this.usuariosDoGrupo = usuariosDoGrupo;
	}

	public List<Usuario> getUsuariosParaAdmin() {
		return usuariosParaAdmin;
	}

	public void setUsuariosParaAdmin(List<Usuario> usuariosParaAdmin) {
		this.usuariosParaAdmin = usuariosParaAdmin;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHorarioEntrada() {
		return horarioEntrada;
	}

	public void setHorarioEntrada(String horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}

	public String getHorariosaida() {
		return horariosaida;
	}

	public void setHorariosaida(String horariosaida) {
		this.horariosaida = horariosaida;
	}

	public int getHorasDiariasTrabalhadas() {
		return horasDiariasTrabalhadas;
	}

	public void setHorasDiariasTrabalhadas(int horasDiariasTrabalhadas) {
		this.horasDiariasTrabalhadas = horasDiariasTrabalhadas;
	}

	public void cadastrarGrupo() throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		externalContext.getFlash().setKeepMessages(true);

		if (validarCampos().size() > 0) {
			for (String mensagem : validarCampos()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensagem));
			}

			externalContext.redirect("/grupo?acao=ExibirCadastrarGrupo");

			return;
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Grupo cadastrado com sucesso"));

		externalContext.redirect("/grupo?acao=cadastrarGrupo");
	}

	protected List<String> validarCampos() {

		List<String> mensagens = new ArrayList<>();

		if (this.nome.isEmpty()) {
			mensagens.add("Nome é obrigatório");
		}

		if (horasDiariasTrabalhadas == 0) {
			mensagens.add("Horas trabalhadas por dia é obrigatório");
		}

		if (this.horarioEntrada.isEmpty()) {
			mensagens.add("Horário entrada é obrigatório");
		}

		if (!horarioEntrada.isEmpty() && (Integer.parseInt(horarioEntrada.split(":")[0]) > 24 || Integer.parseInt(horarioEntrada.split(":")[1]) > 59)) {
			mensagens.add("Horário de entrada invalido");
		}

		if (this.horariosaida.isEmpty()) {
			mensagens.add("Horário saída é obrigatório");
		}

		if (!horarioEntrada.isEmpty() &&  (Integer.parseInt(horariosaida.split(":")[0]) > 24 || Integer.parseInt(horariosaida.split(":")[1]) > 59)) {
			mensagens.add("Horário de entrada invalido");
		}
		
		if(!horarioEntrada.isEmpty() &&  (Integer.parseInt(horariosaida.split(":")[0]) < Integer.parseInt(horarioEntrada.split(":")[0]))) {
			mensagens.add("Horários invalidos");
		}
		
		if(!horarioEntrada.isEmpty() &&  (Integer.parseInt(horariosaida.split(":")[0]) == Integer.parseInt(horarioEntrada.split(":")[0])) 
				&& (Integer.parseInt(horariosaida.split(":")[1]) < Integer.parseInt(horarioEntrada.split(":")[1]))) {
			mensagens.add("Horários invalidos");
		}

		return mensagens;
	}
}
