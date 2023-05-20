package br.com.pontoemdia.model.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoemdia.model.TipoUsuario;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.repository.UsuarioRepository;
import br.com.pontoemdia.web.form.AlterarPerfilForm;
import br.com.pontoemdia.web.form.CadastrarUsuarioForm;

/**
 * 
 * @author Guilherme2800
 *
 */
@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public String exibirPerfil(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Usuario user = (Usuario) req.getSession().getAttribute("usuario");
		Usuario userFull = this.buscarUsuario(user.getId());
		req.setAttribute("alterarPerfilForm", userFull);
		return "forward:perfil.xhtml";
	}
	
	public String alterarPerfil(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		AlterarPerfilForm userEdit = (AlterarPerfilForm) req.getSession().getAttribute("scopedTarget.alterarPerfilForm");
		
		Usuario userInDataBase = this.buscarUsuario(userEdit.getId());
		userInDataBase.setCelular(userEdit.getCelular());
		userInDataBase.setEmail(userEdit.getEmail());
		userInDataBase.setUrlImagem(userEdit.getUrlImagem());

		usuarioRepository.save(userInDataBase);
		
		atualizarUsuarioSession(req, userEdit.getUrlImagem());
		
		return "redirect:usuario?acao=ExibirPerfil";
	}

	private void atualizarUsuarioSession(HttpServletRequest req, String urlImagem) {
		Usuario user = (Usuario) req.getSession().getAttribute("usuario");
		user.setUrlImagem(urlImagem);
	}
	
	public Usuario buscarUsuario(String login, String senha) {
		return usuarioRepository.findByLoginAndSenha(login.toString(), senha.toString());
	}

	public Usuario buscarUsuario(Long id) {
		return usuarioRepository.findById(id).get();
	}


	public List<Usuario> buscarTodosUsuario() {
		return usuarioRepository.findAll();
	}
	
	public String cadastrarUsuario(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CadastrarUsuarioForm userEdit = (CadastrarUsuarioForm) req.getSession().getAttribute("scopedTarget.cadastrarUsuarioForm");
		
		Usuario usuario = new Usuario();
		usuario.setNome(userEdit.getNome());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			usuario.setDataNascimento(sdf.parse(userEdit.getNascimento()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		usuario.setLogin(userEdit.getLogin());
		usuario.setSenha(userEdit.getSenha());
		usuario.setTipoUsuario(TipoUsuario.valueOf(userEdit.getTipoUsuario()));
		usuario.setCargo(userEdit.getCargo());
		usuario.setCpf(userEdit.getCpf());
		
		usuarioRepository.save(usuario);
		
		req.getSession().removeAttribute("scopedTarget.cadastrarUsuarioForm");
		
		return "forward:cadastrarUsuario.xhtml";
	}

}
