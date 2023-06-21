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

import br.com.pontoemdia.model.service.GrupoService;

@WebServlet("/grupo")
public class GrupoController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7369028029074797114L;

	private GrupoService grupoService;

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		
		grupoService = webApplicationContext.getBean(GrupoService.class);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String metodo = "forward:historicoPontos.jsp";

		String acao = req.getParameter("acao");

		switch (acao) {
		case "ExibirCadastrarGrupo":
		default:
			metodo = grupoService.exibirCadastrarGrupo(req, resp);
			break;
		case "cadastrarGrupo":
			metodo = grupoService.cadastrarGrupo(req, resp);
			break;
		case "ExibirGerenciarGrupos":
			metodo = grupoService.exibirGerenciarGrupos(req, resp);
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
