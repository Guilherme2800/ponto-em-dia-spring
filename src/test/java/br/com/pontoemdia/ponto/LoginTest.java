package br.com.pontoemdia.ponto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.UsuarioService;
import br.com.pontoemdia.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class LoginTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@SpyBean
	private UsuarioService usuarioService;
	
	@Test
	public void realizarLogin() {
		
		String login = "root";
		String senha = "root";
		
		Usuario buscarUsuario = usuarioService.buscarUsuario(login, senha);
		
		assertTrue(buscarUsuario != null);
		
	}

}
