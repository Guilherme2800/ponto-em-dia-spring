package br.com.pontoemdia.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.pontoemdia.model.actions.AcaoAbstract;

@WebServlet("/entrada")
public class EntradaController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7369028029074797114L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String acao = req.getParameter("acao");
		String metodo = null;
		
		try {
			AcaoAbstract newInstance = (AcaoAbstract) Class.forName("br.com.pontoemdia.model.actions." + acao).newInstance();
			metodo = newInstance.executar(req, resp);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		String[] split = metodo.split(":");
		if (split[0].equals("redirect")) {
			resp.sendRedirect(split[1]);
			return;
		}else if(split[0].equals("forward")){
			req.getRequestDispatcher("views/"+split[1]).forward(req, resp);
			return;
		}
	}

}
