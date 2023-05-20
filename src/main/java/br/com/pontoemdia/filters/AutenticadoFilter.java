package br.com.pontoemdia.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AutenticadoFilter
 */
public class AutenticadoFilter extends HttpFilter {

	private static final long serialVersionUID = 6829750339736856574L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getSession().getAttribute("usuario") == null) {
			response.sendRedirect("/login.xhtml");
			return;
		}
		chain.doFilter(request, response);
	}

}
