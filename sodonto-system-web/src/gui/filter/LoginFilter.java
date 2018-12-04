package gui.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import negocio.fachada.local.IFuncionarioFachada;
import negocio.fachada.local.IUsuarioFachada;

import org.jboss.security.SecurityContextAssociation;

import dto.FuncionarioDTO;
import dto.UsuarioDTO;

public class LoginFilter implements Filter{
	
	@EJB
	private IUsuarioFachada usuarioSB;
	@EJB
	private IFuncionarioFachada funcionarioSB;

	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {		
		
		System.out.println("[SODONTO SYSTEM][ATUTENTICAÇÃO] Iniciando Autenticação...");
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		request.setCharacterEncoding("UTF-8");
		
		
		String userName = SecurityContextAssociation.getPrincipal().getName();
		System.out.println("[SODONTO SYSTEM][ATUTENTICAÇÃO] Usuário logado: " + userName);

		UsuarioDTO usuarioDTO = this.usuarioSB.buscarPorUsuario(userName);	
		session.setAttribute("usuario", usuarioDTO);

		FuncionarioDTO funcionarioDTO = null;
		

		if(session.getAttribute("funcionario") == null)
		{
			funcionarioDTO = this.funcionarioSB.getEntidadeFromList(
					this.funcionarioSB.buscarPorIdUsuario(usuarioDTO.getIdUsuario()));
					

		}

		if(funcionarioDTO != null && session.getAttribute("funcionario") == null)
		{
			session.setAttribute("funcionario", funcionarioDTO);

		}
		
		filterChain.doFilter(request, response);

		System.out.println("[SODONTO SYSTEM][ATUTENTICAÇÃO] Processo de autenticacao finalizado!");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
