package br.com.pontoemdia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pontoemdia.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query(value = "SELECT * FROM usuario WHERE login = :login AND senha = :senha", nativeQuery = true)
	public Usuario findByLoginAndSenha(String login, String senha);
	
}
