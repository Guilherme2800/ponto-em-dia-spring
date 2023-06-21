package br.com.pontoemdia.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.Grupo;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.repository.GrupoRepository;
import br.com.pontoemdia.repository.UsuarioRepository;
import br.com.pontoemdia.web.form.CriarGrupoForm;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public String exibirCadastrarGrupo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "forward:criarGrupo.xhtml";
	}
	
	public String exibirGerenciarGrupos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "forward:gerenciarGrupos.xhtml";
	}

	public String cadastrarGrupo(HttpServletRequest req, HttpServletResponse resp) {
		
		CriarGrupoForm grupoEdit = (CriarGrupoForm) req.getSession().getAttribute("scopedTarget.criarGrupoForm");
		
		Usuario admin = usuarioRepository.findbyLogin(grupoEdit.getAdministrador().toString());
		
		List<Usuario> usuariosDoGrupo = new ArrayList<>();
		
		for(Usuario usuario : grupoEdit.getUsuariosDoGrupo()) {
			usuariosDoGrupo.add(usuarioRepository.findbyLogin(usuario.getLogin()));
		}
		
		Grupo grupo = new Grupo();
		grupo.setNome(grupoEdit.getNome());
		grupo.setAdmin(admin);
		grupo.setUsuarios(usuariosDoGrupo);
		
		String[] horarioEntrada = grupoEdit.getHorarioEntrada().split(":");
		grupo.setHorarioEntrada(Double.parseDouble(horarioEntrada[0]) + (Double.parseDouble("0." + horarioEntrada[1])));
		
		String[] horarioSaida = grupoEdit.getHorariosaida().split(":");
		grupo.setHorariosaida(Double.parseDouble(horarioSaida[0]) + (Double.parseDouble("0." + horarioSaida[1])));
		grupo.setHorasDiariasTrabalhadas(grupoEdit.getHorasDiariasTrabalhadas());
		
		Grupo grupoSave = grupoRepository.save(grupo);
		
		for(Usuario usuario : usuariosDoGrupo) {
			usuario.setGrupo(grupoSave);
			usuarioRepository.save(usuario);
		}
		
		
		req.getSession().removeAttribute("scopedTarget.criarGrupoForm");
		
		return "forward:criarGrupo.xhtml";
	}


}
