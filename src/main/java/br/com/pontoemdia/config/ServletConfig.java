package br.com.pontoemdia.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.pontoemdia.controllers.EntradaController;
import br.com.pontoemdia.controllers.LoginController;
import br.com.pontoemdia.controllers.PontoController;
import br.com.pontoemdia.controllers.UsuarioController;
import br.com.pontoemdia.filters.AutenticadoFilter;

@Configuration
@ComponentScan(basePackages = "br.com.pontoemdia.model.actions.HistoricoUsuario")
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<LoginController> loginController() {
        return new ServletRegistrationBean<>(new LoginController(), "/login");
    }
    
    @Bean
    public ServletRegistrationBean<EntradaController> entradaController() {
        return new ServletRegistrationBean<>(new EntradaController(), "/entrada");
    }
    
    @Bean
    public ServletRegistrationBean<PontoController> pontoController() {
        return new ServletRegistrationBean<>(new PontoController(), "/ponto");
    }
    
    @Bean
    public ServletRegistrationBean<UsuarioController> usuarioController() {
        return new ServletRegistrationBean<>(new UsuarioController(), "/usuario");
    }
    
    @Bean
	public FilterRegistrationBean<AutenticadoFilter> autenticadoFilter() {
		FilterRegistrationBean<AutenticadoFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AutenticadoFilter());
		registrationBean.addUrlPatterns("/entrada");
		registrationBean.addUrlPatterns("/ponto");
		registrationBean.addUrlPatterns("/usuario");
		return registrationBean;
	}
}
