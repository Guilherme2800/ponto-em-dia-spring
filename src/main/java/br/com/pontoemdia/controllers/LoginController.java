package br.com.pontoemdia.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.pontoemdia.model.Usuario;
import br.com.pontoemdia.model.service.UsuarioService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = -8943226810203227000L;

	private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        // Obtém o contexto do Spring
        ServletContext servletContext = getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        // Obtém a instância do UsuarioService
        usuarioService = webApplicationContext.getBean(UsuarioService.class);
    }
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String login = req.getParameter("login");
		String senha = req.getParameter("senha");

		if ((login == null || login.isEmpty()) || (senha == null || senha.isEmpty())) {
			resp.sendRedirect("/login.xhtml");
			return;
		}

		Usuario usuarioBucado = usuarioService.buscarUsuario(login, senha);
		if (usuarioBucado != null) {
			req.getSession().setAttribute("usuario", usuarioBucado);
		} else {
			resp.sendRedirect("/login.xhtml");
			return;
		}
		resp.sendRedirect("entrada?acao=ExibirRegistrar");

	}

}
