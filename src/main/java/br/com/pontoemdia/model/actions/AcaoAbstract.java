package br.com.pontoemdia.model.actions;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Guilherme2800
 *
 */
public abstract class AcaoAbstract extends HttpServlet {

	public abstract String executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	
	public AcaoAbstract() {
		
	}
	
}
