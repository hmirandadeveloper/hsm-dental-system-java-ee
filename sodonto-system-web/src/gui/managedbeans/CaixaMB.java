package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.ICaixaFachada;
import dto.CaixaDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class CaixaMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|CAIXA-MB|";
	
	@EJB
	private ICaixaFachada caixaSB;
	
	private CaixaDTO caixaDTO;	
	
	private Date dataIFiltro;
	private Date dataFFiltro;
	
	public void limparMB()
	{
		this.caixaDTO = null;
	}
	
	public void abrirCaixa()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}

/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"ABERTURA DE CAIXA", 
				"ABRIU O UM CAIXA"
				);
/*
 * FIM DO REGISTRO DE LOG
*/		
		
		getCaixaDTO().setUsuarioAbertura(getFuncionarioLogado().getUsuarioPerfil());
		this.caixaDTO.setUsuarioFechamento(null);
		salvar();
	}
	
	
	public boolean permissaoAbrirCaixa()
	{
		boolean permissao = true;
		if(this.caixaSB.buscarCaixasEmAbertoPorUsuario(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario()).size()
				> 0)
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_PERMISSAO_ABRIR_CAIXA);
			permissao = false;
		}
			
		return permissao;
	}
	
	public double valorTotalCaixaDia()
	{
		double valorTotal = 0;
		if(getCaixaDTO().getDataCaixa() != null)
		{
			for(CaixaDTO cx : buscarCaixasPorDataUnica())
			{
				valorTotal += cx.getValorTotal();
			}
		}
		
		return valorTotal;
	}
	
	public String salvar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.caixaDTO.setNumeroOrdem(buscarCaixasPorMaxOrdemData());
			System.out.println("[SODONTO SYSTEM][MB] Iniciando acesso camada SB...");
			this.caixaSB.salvar(this.caixaDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.caixaDTO = null;
		} catch (EntidadeCadastradaException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		} catch (AtributoIncompletoException e) {
			enviarMenssagemErro(e.getMsgRetorno());
			for(String s : e.getAtributoIncompleto().getAtibutosNaoPreenchidos())
			{
				enviarMenssagemAlerta("Atributo obrigatório: " + s + ";");
			}
			e.printStackTrace();
		} catch (EJBTransactionRolledbackException e) {
			if(e.getMessage().contains("ConstraintViolation"))
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_INF_DUPLICADA);
			}
			else
			{
				enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			}
			e.printStackTrace();
		} catch (Exception e) {
			enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			reportarErroAoAnalista(MBName + " SALVAR CAIXA", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarCaixa(CaixaDTO caixaDTOAlteracao)
	{
		this.caixaDTO = caixaDTOAlteracao;
	}
	
	public String alterar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return "";
		}
		
		try {
			this.caixaSB.alterar(this.caixaDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.caixaDTO = null;
		} catch (EntidadeCadastradaException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		} catch (AtributoIncompletoException e) {
			enviarMenssagemErro(e.getMsgRetorno());
			for(String s : e.getAtributoIncompleto().getAtibutosNaoPreenchidos())
			{
				enviarMenssagemAlerta("Atributo obrigatório: " + s + ";");
			}
			e.printStackTrace();
		} catch (EJBTransactionRolledbackException e) {
			if(e.getMessage().contains("ConstraintViolation"))
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_INF_DUPLICADA);
			}
			else
			{
				enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			}
			e.printStackTrace();
		} catch (Exception e) {
			enviarMenssagemErroGrave(MensagemErro.MSG_ERRO_SISTEMA);
			reportarErroAoAnalista(MBName + " ALTERAR CAIXA", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	
	public void removerCaixa(CaixaDTO caixaDTOAlteracao)
	{
		this.caixaDTO = caixaDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.caixaDTO.getIdCaixa() != null)
		{
			try {
				this.caixaSB.inativar(this.caixaDTO.getIdCaixa(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.caixaDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<CaixaDTO> buscarCaixas()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
			caixaDTOsBusca = this.caixaSB.buscarAtivos();
			
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasPorData()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(this.dataIFiltro != null && this.dataFFiltro != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorData(this.dataIFiltro, this.dataFFiltro);

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasPorDataUnica()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getCaixaDTO().getDataCaixa() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorData(getCaixaDTO().getDataCaixa(), getCaixaDTO().getDataCaixa());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasPorDataEOrdem()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getCaixaDTO().getDataCaixa() != null && getCaixaDTO().getNumeroOrdem() > 0)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorDataEOrdem(
					this.caixaDTO.getDataCaixa(), this.caixaDTO.getNumeroOrdem());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public int buscarCaixasPorMaxOrdemData()
	{
		int  maxOrdemData = 0;
		
		if(getCaixaDTO().getDataCaixa() != null)
		{
			maxOrdemData = this.caixaSB.buscarPorMaxOrdemData(this.caixaDTO.getDataCaixa());

		}
		
		return maxOrdemData + 1;
	}
	
	public List<CaixaDTO> buscarCaixasPorUsuarioAbertura()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getCaixaDTO().getUsuarioAbertura().getIdUsuario() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorUsuarioA(this.caixaDTO.getUsuarioAbertura().getIdUsuario());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasPorUsuarioAberturaEmData()
	{
		List<CaixaDTO> caixaDTOsBusca = null;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario() != null
				&& getCaixaDTO().getDataCaixa() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorUsuarioAEmData(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario(),
					this.caixaDTO.getDataCaixa());			
		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasPorUsuarioFechamento()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getCaixaDTO().getUsuarioFechamento().getIdUsuario() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorUsuarioF(this.caixaDTO.getUsuarioFechamento().getIdUsuario());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasAbertosPorUsuarioLogado()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorUsuarioA(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public List<CaixaDTO> buscarCaixasFechadosPorUsuarioLogado()
	{
		List<CaixaDTO>  caixaDTOsBusca = null;
		
		if(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario() != null)
		{
			caixaDTOsBusca = this.caixaSB.buscarPorUsuarioF(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario());

		}
		return caixaDTOsBusca != null 
				? caixaDTOsBusca 
						: new ArrayList<CaixaDTO>();
	}
	
	public CaixaDTO buscarPeloId()
	{
		CaixaDTO caixaDTOBusca = null;
		
		if(this.caixaDTO.getIdCaixa() != null)
		{
			caixaDTOBusca = this.caixaSB.buscarPorId(this.caixaDTO.getIdCaixa());
			if(caixaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return caixaDTOBusca;
	}
	
	public CaixaDTO getCaixaDTO() {
		if(caixaDTO == null)
		{
			caixaDTO = DTOFactory.getCaixaDTO();
		}
		
		return caixaDTO;
	}

	public void setCaixaDTO(CaixaDTO caixaDTO) {
		this.caixaDTO = caixaDTO;
	}

	public Date getDataIFiltro() {

		return dataIFiltro;
	}

	public void setDataIFiltro(Date dataIFiltro) {
		this.dataIFiltro = dataIFiltro;
	}
	
	public Date getDataFFiltro() {

		return dataFFiltro;
	}

	public void setDataFFiltro(Date dataFFiltro) {
		this.dataFFiltro = dataFFiltro;
	}
}
