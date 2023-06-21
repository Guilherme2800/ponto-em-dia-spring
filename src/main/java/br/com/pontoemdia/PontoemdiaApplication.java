package br.com.pontoemdia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.pontoemdia.converter.UsuarioConverter;

@SpringBootApplication
public class PontoemdiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontoemdiaApplication.class, args);
	}

	@Bean
    public UsuarioConverter usuarioConverter() {
        return new UsuarioConverter();
    }
}
