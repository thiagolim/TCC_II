package escola.musica.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import escola.musica.dao.JPAUtil;

@WebFilter(urlPatterns= { "/* " })
public class FilterAutenticacao implements Filter {

	@Override
	public void destroy() {
		}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// M�todo sera executado em todas requisi��es
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuariologado");
		 	
		String url = req.getServletPath();		
		
			if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null ||
					(usuarioLogado != null && usuarioLogado.trim().isEmpty())) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
				dispatcher.forward(request, response);
				return;
			}else {
				chain.doFilter(request, response);
			}
			
		}


	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Executado quando se inicia o servidor
		
		JPAUtil.getEntityManager();
		
	}
	
	

}