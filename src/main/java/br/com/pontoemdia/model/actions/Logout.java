package br.com.pontoemdia.model.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Guilherme2800
 *
 */
public class Logout extends AcaoAbstract{

	@Override
	public String executar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		return "redirect:login.xhtml";
	}

}
