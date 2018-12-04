package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesControleSodontoSystem;
import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.enums.EPages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EEstadoCivil;
import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.enums.EPermissaoUsuario;
import negocio.constante.enums.EUf;
import negocio.util.DataUtil;
import dto.FuncionarioDTO;

@ManagedBean
@ViewScoped
public class PagesMB extends ManagedBeanGenericBasic {
	
	private EPages pageSelected;
	
	public int getTimePoll()
	{
		return ConstantesControleSodontoSystem.SISTEMA_TIME_POLL;
	}
	
	public int getAgendamentoTimePoll()
	{
		return ConstantesControleSodontoSystem.SISTEMA_TIME_POLL_AGENDAMENTO;
	}
	
	public int getCaixaTimePoll()
	{
		return ConstantesControleSodontoSystem.SISTEMA_TIME_POLL_CAIXA;
	}
	
	public int getLogTimePoll()
	{
		return ConstantesControleSodontoSystem.SISTEMA_TIME_POLL_LOG;
	}
	
	public String irParaPagina()
	{
		String pageUrl = "";
		
		if(this.pageSelected != null)
		{
			pageUrl = this.pageSelected.getUrl();
		}
		
		return pageUrl;
	}
	
	public List<EPages> allPages(String filtro)
	{
		List<EPages> pagesReturn = new ArrayList<EPages>();
		
		for(int i = 0; i < EPages.values().length; i++)
		{
			if(EPages.values()[i].getNome().toUpperCase().contains(filtro.toUpperCase()))
			{
				if(validarPermissao(EPages.values()[i].getPermissao()))
				{
					pagesReturn.add(EPages.values()[i]);
				}
			}
		}
		
		return pagesReturn;
	}
	
	public boolean validarPermissao(EPermissaoUsuario permissaoAcesso)
	{
		boolean permitido = false;
		
		switch (permissaoAcesso) {
		case ADM:
			permitido = isPermissaoAdministrador();
			break;
			
		case GES:
			permitido = isPermissaoGestor();
			break;
		
		case OPE:
			permitido = isPermissaoOperador();
			break;	
			
		case ATE:
			permitido = isPermissaoAtendente();
			break;
		
		case CON:
			permitido = isPermissaoAtendenteConsultorio();
			break;	
			
		case TOD:
			permitido = isPermissaoUsuarioAtivo();
			break;	
			
		case TEM:
			permitido = isPermissaoTemporario();
			break;				
		
		default:
			permitido = false;
			break;
		}
		
		return permitido;
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}
	
	public Date getDataAtual()
	{
		return DataUtil.getDataAtual();
	}
	
	public boolean inferiorDataAtual(Date data)
	{
		
		boolean resultado = false;
		Date dataAtual = DataUtil.setDataToFinalDia(new Date());
		Date novaData = DataUtil.setDataToFinalDia(data);
		
		if(novaData.getTime() <= dataAtual.getTime())
		{
			resultado = true;
		}
		
		return resultado;
	}
	
	public boolean igualDataAtual(Date data)
	{		
		return DataUtil.igualDataAtual(data);
	}
	
	public boolean igualMesAtual(Date data)
	{
		
		return DataUtil.igualMesAtual(data);
	}
	
	public boolean maiorQueMesAtual(Date data)
	{
		return DataUtil.maiorQueMesAtual(data);
	}
	
	public boolean menorQueMesAtual(Date data)
	{
		return DataUtil.menorQueMesAtual(data);
	}
	
	public EUf[] getUfs()
	{
		return EUf.values(); 
	}
	
	public EEstadoCivil[] getEstadosCivis()
	{
		return EEstadoCivil.values(); 
	}
	
	public String iniciarModuloGerencial()
	{
		logout();
		return "index-protected";
	}
	
	public void redirecionar()
	{
		redirecionar(ConstantesSodontoSystem.SISTEMA_NOME_FILE + "/pages/protected/index.xhtml");
	}
	
	public String getNomeSistema()
	{
		return ConstantesSodontoSystem.SISTEMA_NOME;
	}
	
	public String getVersaoSistema()
	{
		return ConstantesSodontoSystem.SISTEMA_VERSAO;
	}
	
	public String getVersaoDataSistema()
	{
		return ConstantesSodontoSystem.SISTEMA_VERSAO_DATA;
	}
	
	public String getAmbienteSistema()
	{
		return ConstantesSodontoSystem.SISTEMA_AMBIENTE;
	}
	
	public boolean isPermissaoTemporario()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.T)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoAdministrador()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoGestor()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.G)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoOperador()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.G ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.O)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoAtendente()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.G ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.O ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.D)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoAtendenteConsultorio()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.A ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.G ||
		     getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() == EPerfilUsuario.C)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoUsuarioAtivo()
	{
		boolean retornoValidacao = false;
		
		if(getFuncionarioLogado() == null)
		{
			return retornoValidacao;
		}
		
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo() != EPerfilUsuario.T)
		{
			retornoValidacao = true;
		}
		
		return retornoValidacao;
	}
	
	public boolean isPermissaoHelioMiranda()
	{
		return super.isPermissaoHelioMiranda();
	}

	public EPages getPageSelected() {
		return pageSelected;
	}

	public void setPageSelected(EPages pageSelected) {
		this.pageSelected = pageSelected;
	}
}
