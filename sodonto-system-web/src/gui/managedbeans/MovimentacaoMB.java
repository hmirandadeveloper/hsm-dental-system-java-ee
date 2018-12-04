package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
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
import negocio.fachada.local.IMovimentacaoFachada;
import negocio.util.DataUtil;
import dto.CaixaDTO;
import dto.MensalidadePacienteDTO;
import dto.MovimentacaoDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class MovimentacaoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|MOVIMENTAÇÃO DE CAIXA-MB|";
	
	@EJB
	private IMovimentacaoFachada movimentacaoSB;
	@EJB
	private ICaixaFachada caixaSB;
	
	private MovimentacaoDTO movimentacaoDTO;
	private CaixaDTO caixaSelecionadoDTO;
	
	public void limparMB()
	{
		this.movimentacaoDTO = null;
	}
	
	public CaixaDTO getCaixaAberto()
	{
		CaixaDTO caixaDTO = this.caixaSB.getEntidadeFromList(
				this.caixaSB.buscarCaixasEmAbertoPorUsuario(getFuncionarioLogado().getUsuarioPerfil().getIdUsuario())
				);
		return caixaDTO == null ? new CaixaDTO() : caixaDTO;
	}
	
	public void incluirPagamentoMensalidadePaciente(MensalidadePacienteDTO mensalidadePacienteDTO)
	{
		String msg = "";
		if(getCaixaAberto().getIdCaixa() != null && mensalidadePacienteDTO != null)
		{
			msg = "Pagamento da Mensalidade: "+ mensalidadePacienteDTO.getPlanoMensalidade().getMes() + "/" + mensalidadePacienteDTO.getPlanoPaciente().getPlano().getTotalMeses() +", do Paciente: " + mensalidadePacienteDTO.getPlanoPaciente().getPaciente().getNomeCompleto() + ", referente ao Plano: " + mensalidadePacienteDTO.getPlanoPaciente().getPlano().getNomePlano() + " / Código: " + mensalidadePacienteDTO.getPlanoPaciente().getPlano().getIdPlano();
			
			getMovimentacaoDTO().setCaixa(getCaixaAberto());
			getMovimentacaoDTO().setRefMovimentacao("MENSALIDADE (" + mensalidadePacienteDTO.getPlanoMensalidade().getMes() + "/" + mensalidadePacienteDTO.getPlanoPaciente().getPlano().getTotalMeses() + ") DO PACIENTE: " + mensalidadePacienteDTO.getPlanoPaciente().getPaciente().getNomeCompleto());
			getMovimentacaoDTO().setObs(msg);
			getMovimentacaoDTO().setTipo("E");
			getMovimentacaoDTO().setValor(mensalidadePacienteDTO.getPlanoMensalidade().getValorReajustado() <= 0 ? mensalidadePacienteDTO.getPlanoMensalidade().getValorMes() : mensalidadePacienteDTO.getPlanoMensalidade().getValorReajustado());
		
			salvar();
			enviarMenssagemInformativa(msg + ". Foi realizada com sucesso!");
		}
	}
	
	public void incluirMovimentacao()
	{
		if(getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo().ordinal() > 1)
		{
			getMovimentacaoDTO().setTipo("E");
		}
		getMovimentacaoDTO().setCaixa(getCaixaAberto());
	}
	
	private boolean recalcularTotalCaixa()
	{
		boolean valorTotalCaixaRecalculado = false;
		if(getMovimentacaoDTO().getCaixa().getIdCaixa() != null)
		{
			valorTotalCaixaRecalculado = true;
			if(getMovimentacaoDTO().getTipo().equals("E"))
			{
				double valorTotalCaixa = getMovimentacaoDTO().getCaixa().getValorTotal();
				getMovimentacaoDTO().getCaixa().setValorTotal(valorTotalCaixa + this.movimentacaoDTO.getValor());
			}
			else
			{
				double valorTotalCaixa = getMovimentacaoDTO().getCaixa().getValorTotal();
				getMovimentacaoDTO().getCaixa().setValorTotal(valorTotalCaixa - this.movimentacaoDTO.getValor());
			}
		}
		System.out.println("[SODONTO SYSTEM][MB]Novo Valor Caixa: R$ " + getMovimentacaoDTO().getCaixa().getValorTotal());
		return valorTotalCaixaRecalculado;
	}
	
	public void fecharCaixaSelecionado()
	{
		if(getCaixaSelecionadoDTO().getIdCaixa() != null)
		{
			getCaixaSelecionadoDTO().setUsuarioFechamento(getFuncionarioLogado().getUsuarioPerfil());
			atualizarCaixa();
			enviarMenssagemInformativa(MensagemInformativa.MSG_CAIXA_FECHADO);
		}
	}
	
	public void reabrirCaixaSelecionado()
	{
		if(getCaixaSelecionadoDTO().getIdCaixa() != null)
		{
			getCaixaSelecionadoDTO().setUsuarioFechamento(null);
			atualizarCaixa();
			enviarMenssagemInformativa(MensagemInformativa.MSG_CAIXA_REABERTO);
		}
	}
	
	public void fecharCaixaUsuarioLogado()
	{		
		this.caixaSelecionadoDTO = getCaixaAberto();
		if(caixaSelecionadoDTO.getIdCaixa() != null)
		{
			caixaSelecionadoDTO.setUsuarioFechamento(getFuncionarioLogado().getUsuario());
	
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"FECHAMENTO DE CAIXA", 
				"FECHOU O CAIXA DO DIA:"
				+ DataUtil.getDataFormatada(caixaSelecionadoDTO.getDataCaixa())
				);
/*
 * FIM DO REGISTRO DE LOG
*/					
			
			atualizarCaixa();
			enviarMenssagemInformativa(MensagemInformativa.MSG_CAIXA_FECHADO);
		}
	}
	
	public void salvarCaixaSelecionado()
	{
		getMovimentacaoDTO().setCaixa(this.caixaSelecionadoDTO);
		salvar();
	}
	
	private boolean recalcularTotalCaixaRemocao()
	{
		boolean valorTotalCaixaRecalculado = false;
		if(getMovimentacaoDTO().getCaixa().getIdCaixa() != null)
		{
			valorTotalCaixaRecalculado = true;
			if(getMovimentacaoDTO().getTipo().equals("E"))
			{
				double valorTotalCaixa = getMovimentacaoDTO().getCaixa().getValorTotal();
				getMovimentacaoDTO().getCaixa().setValorTotal(valorTotalCaixa - this.movimentacaoDTO.getValor());
			}
			else
			{
				double valorTotalCaixa = getMovimentacaoDTO().getCaixa().getValorTotal();
				getMovimentacaoDTO().getCaixa().setValorTotal(valorTotalCaixa + this.movimentacaoDTO.getValor());
			}
		}
		System.out.println("[SODONTO SYSTEM][MB]Novo Valor Caixa após remoção: R$ " + getMovimentacaoDTO().getCaixa().getValorTotal());
		return valorTotalCaixaRecalculado;
	}
	
	public String salvar()
	{
		try {
			if(recalcularTotalCaixa())
			{
				System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
				System.out.println("[SODONTO SYSTEM][MB] Iniciando acesso camada SB...");
				this.movimentacaoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
				this.movimentacaoSB.salvar(this.movimentacaoDTO);
				this.movimentacaoDTO = null;
				enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			}
			else
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
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
			reportarErroAoAnalista(MBName + " SALVAR MOVIMENTAÇÃO DE CAIXA", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String atualizarCaixa()
	{
		try {
			this.caixaSB.alterar(this.caixaSelecionadoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.caixaSelecionadoDTO = null;
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
			reportarErroAoAnalista(MBName + " ATUALIZAR CAIXA", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void alterarMovimentacao(MovimentacaoDTO movimentacaoDTOAlteracao)
	{
		this.movimentacaoDTO = movimentacaoDTOAlteracao;
	}
	
	
	public void removerMovimentacao(MovimentacaoDTO movimentacaoDTOAlteracao)
	{
		this.movimentacaoDTO = movimentacaoDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.movimentacaoDTO.getIdMovimentacao() != null)
		{
			try {
				recalcularTotalCaixaRemocao();
				this.movimentacaoSB.inativar(this.movimentacaoDTO, 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_ITEM);
				this.movimentacaoDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<MovimentacaoDTO> buscarMovimentacoes()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarAtivos();
			
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorRefMovimentacao()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(!getMovimentacaoDTO().getRefMovimentacao().equals(""))
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorRefMovimentacao(
					this.movimentacaoDTO.getRefMovimentacao());

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorRefMovimentacaoECaixa()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(!getMovimentacaoDTO().getRefMovimentacao().equals("") &&
				getMovimentacaoDTO().getCaixa().getIdCaixa() != null)
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorRefMovimentacaoCaixaEData(
					this.movimentacaoDTO.getRefMovimentacao(), 
					this.movimentacaoDTO.getCaixa().getIdCaixa(),
					this.movimentacaoDTO.getCaixa().getDataCaixa());

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorTipo()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(!getMovimentacaoDTO().getTipo().equals(""))
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorTipo(
					this.movimentacaoDTO.getTipo()
					);

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorCaixaUsuarioLogado()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(getCaixaAberto().getIdCaixa() != null)
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorCaixa(
					getCaixaAberto().getIdCaixa());

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorCaixaSelecionado()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(getCaixaSelecionadoDTO().getIdCaixa() != null)
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorCaixa(
					getCaixaSelecionadoDTO().getIdCaixa());

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	public List<MovimentacaoDTO> buscarPorCaixa()
	{
		List<MovimentacaoDTO>  movimentacaoDTOsBusca = null;
		
		if(getCaixaAberto().getIdCaixa() != null)
		{
			movimentacaoDTOsBusca = this.movimentacaoSB.buscarPorCaixa(
					getCaixaSelecionadoDTO().getIdCaixa());

		}
		return movimentacaoDTOsBusca != null 
				? movimentacaoDTOsBusca 
						: new ArrayList<MovimentacaoDTO>();
	}
	
	
	public MovimentacaoDTO buscarPeloId()
	{
		MovimentacaoDTO movimentacaoDTOBusca = null;
		
		if(this.movimentacaoDTO.getIdMovimentacao() != null)
		{
			movimentacaoDTOBusca = this.movimentacaoSB.buscarPorId(this.movimentacaoDTO.getIdMovimentacao());
			if(movimentacaoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return movimentacaoDTOBusca;
	}
	
	public MovimentacaoDTO getMovimentacaoDTO() {
		if(this.movimentacaoDTO == null)
		{
			this.movimentacaoDTO = DTOFactory.getMovimentacaoDTO();
		}
		
		return movimentacaoDTO;
	}

	public void setMovimentacaoDTO(MovimentacaoDTO movimentacaoDTO) {
		this.movimentacaoDTO = movimentacaoDTO;
	}

	public CaixaDTO getCaixaSelecionadoDTO() {
		if(this.caixaSelecionadoDTO == null)
		{
			this.caixaSelecionadoDTO = DTOFactory.getCaixaDTO();
		}
		
		return caixaSelecionadoDTO;
	}

	public void setCaixaSelecionadoDTO(CaixaDTO caixaSelecionadoDTO) {
		this.caixaSelecionadoDTO = caixaSelecionadoDTO;
	}
}
