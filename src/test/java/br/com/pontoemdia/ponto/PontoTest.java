package br.com.pontoemdia.ponto;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pontoemdia.model.Ponto;
import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.PontoService;
import br.com.pontoemdia.repository.PontoRepository;
import br.com.pontoemdia.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PontoTest {

	@SpyBean
	private PontoService pontoService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PontoRepository pontoRepository;

	@Test
	public void registrarPonto() throws ServletException, IOException {

		Optional<Usuario> user = usuarioRepository.findById(1l);

		pontoService.registrarPonto(user.get());
		
		List<Ponto> findAll = pontoRepository.findAll();
		
		assertTrue(findAll.stream().filter(ponto -> ponto.getUser_id().equals(user.get().getId())).findFirst().isPresent());
	}
	
	@Test
	public void buscarPontos() throws ServletException, IOException {

		Optional<Usuario> user = usuarioRepository.findById(1l);

		pontoService.registrarPonto(user.get());
		
		List<Ponto> findByUserId = pontoRepository.findByUserId(user.get().getId());
		
		assertTrue(!findByUserId.isEmpty());
	}

}
