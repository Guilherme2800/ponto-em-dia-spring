package br.com.pontoemdia.controllers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.pontoemdia.model.service.UsuarioService;

@WebServlet("/usuario")
public class UsuarioController extends HttpServlet {

	private UsuarioService usuarioService;

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		usuarioService = webApplicationContext.getBean(UsuarioService.class);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String metodo = "forward:perfil.xhtml";

		String acao = req.getParameter("acao");

		switch (acao) {
		case "ExibirPerfil":
		default:
			metodo = usuarioService.exibirPerfil(req, resp);
			break;
		case "alterarPerfil":
			metodo = usuarioService.alterarPerfil(req, resp);
			break;
		case "cadastrarUsuario":
			metodo = usuarioService.cadastrarUsuario(req, resp);
			break;
		}

		String[] split = metodo.split(":");
		if (split[0].equals("redirect")) {
			resp.sendRedirect(split[1]);
			return;
		} else if (split[0].equals("forward")) {
			req.getRequestDispatcher("views/" + split[1]).forward(req, resp);
			return;
		}
	}
	
}
