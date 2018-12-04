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
import negocio.fachada.local.IPlanoFachada;
import negocio.fachada.local.IPlanoMensalidadeFachada;
import dto.PlanoDTO;
import dto.PlanoMensalidadeDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class PlanoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|PLANO-MB|";
	
	@EJB
	private IPlanoFachada planoSB;
	@EJB
	private IPlanoMensalidadeFachada planoMensalidadeSB;
	
	private PlanoDTO planoDTO;
	private PlanoDTO planoDTOSelecionado;
	private PlanoDTO planoDTOAlteracao;
	private List<PlanoMensalidadeDTO> planoMensalidades;
	
	private String nomePlanoFiltro;
	private Date dataFiltro;
	
	public void limparMB()
	{
		this.planoDTO = null;
	}
	
	public void limparSelecaoPlano()
	{
		this.planoDTOSelecionado = null;
	}
	
	public void selecionarPlano(PlanoDTO planoDTOSelecionado, boolean isAlteracao)
	{
		if(!isAlteracao)
		{
			this.planoDTOSelecionado = planoDTOSelecionado;
		}
		else
		{
			this.planoDTOAlteracao = planoDTOSelecionado;
		}
	}
	
	public List<PlanoMensalidadeDTO> carregarMensalidadesPlano()
	{
		if(getPlanoDTOSelecionado().getIdPlano() == null)
		{
			return new ArrayList<PlanoMensalidadeDTO>();
		}
		
		if(getPlanoMensalidades().size() == this.planoDTOSelecionado.getTotalMeses())
		{
			return this.planoMensalidades;
		}
		
		this.planoMensalidades = this.planoMensalidadeSB.buscarPorPlano(this.planoDTOSelecionado.getIdPlano());
		int diferencaMesalidade = this.planoDTOSelecionado.getTotalMeses() - this.planoMensalidades.size();
		
		if(diferencaMesalidade == 0)
		{
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Não há diferença nas Mensalidades em relação ao Plano...");
			return this.planoMensalidades;
		}
		
		else if (diferencaMesalidade > 0)
		{
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Plano faltando mensalidades...");
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Total de Meses do Plano: " + this.planoDTOSelecionado.getTotalMeses());
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Criando mensalidades para o Plano...");
			
			
			for(int i = 0; i < this.planoDTOSelecionado.getTotalMeses(); i++)
			{
				System.out.println("[SODONTO SYSTEM][2.0.00][MB] Verificando se a mensalidade de Índice (" + i + ") é nula...");
				if(this.planoMensalidades.size() < i ? this.planoMensalidades.get(i) == null : true)
				{
					System.out.println("[SODONTO SYSTEM][2.0.00][MB] A Mensalidade de Índice ("+ i +") É NULA...");
					System.out.println("[SODONTO SYSTEM][2.0.00][MB] Criando nova mensalidade para o Índice...");
					
					this.planoMensalidades.add(DTOFactory.getPlanoMensalidadeDTO());
					this.planoMensalidades.get(i).setMes(i + 1);
					this.planoMensalidades.get(i).setPlano(this.planoDTOSelecionado);
					this.planoMensalidades.get(i).setObs("");
				}
				else
				{
					System.out.println("[SODONTO SYSTEM][2.0.00][MB] A mensalidade de Índice ("+ i +") NÃO É NULA...");
				}
			}
			
			salvarMensalidades();
		}
		else if (diferencaMesalidade < 0)
		{
			// Remover Ultimas Mensalidades
		}
		
		return this.planoMensalidades;
	}
	
	public void removerMensalidade(PlanoMensalidadeDTO planoMensalidadeDTOSelecionado)
	{
		if(planoMensalidadeDTOSelecionado.getIdPlanoMensalidade() != null)
		{
			try {
				this.planoMensalidadeSB.inativar(planoMensalidadeDTOSelecionado.getIdPlanoMensalidade(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.planoDTOSelecionado.setTotalMeses(this.planoDTOSelecionado.getTotalMeses() - 1);
				planoMensalidadeDTOSelecionado = null;
				this.planoMensalidades = this.planoMensalidadeSB.buscarPorPlano(this.planoDTOSelecionado.getIdPlano());
				recalcularValorTotalPlano();	
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void salvarMensalidades()
	{

		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando inserção de multiplas mensalidades...");
		
		for(PlanoMensalidadeDTO pm : this.planoMensalidades)
		{
			if(pm.getIdPlanoMensalidade() == null)
			{
				salvarMensalidade(pm);
			}
		}
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Recarregando mensalidades do Plano...");
		this.planoMensalidades = this.planoMensalidadeSB.buscarPorPlano(this.planoDTOSelecionado.getIdPlano());
		
		recalcularValorTotalPlano();
	}
	
	public void atualizarMensalidades()
	{		
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando atualização de multiplas mensalidades...");
		for(PlanoMensalidadeDTO pm : this.planoMensalidades)
		{
			alterarMensalidade(pm);
		}
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Recarregando mensalidades do Plano...");
		this.planoMensalidades = this.planoMensalidadeSB.buscarPorPlano(this.planoDTOSelecionado.getIdPlano());
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando rotina de recalculo do Valor Total do Plano...");
		recalcularValorTotalPlano();
	}
	
	private void salvarMensalidade(PlanoMensalidadeDTO planoMensalidadeDTO)
	{
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Salvando Mensalidade para o Plano de Código: " + planoMensalidadeDTO.getPlano().getIdPlano());
			this.planoMensalidadeSB.salvar(planoMensalidadeDTO);
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
			reportarErroAoAnalista(MBName + " SALVAR MENSALIDADE DO PLANO", e);
			
			e.printStackTrace();
		}
	}
	
	private void alterarMensalidade(PlanoMensalidadeDTO planoMensalidadeDTO)
	{
		try {
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Atualizando Mensalidade para o Plano de Código: " + planoMensalidadeDTO.getPlano().getIdPlano());
			this.planoMensalidadeSB.alterar(planoMensalidadeDTO);
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
			reportarErroAoAnalista(MBName + " ALTERAR MENSALIDADE DO PLANO", e);
			
			e.printStackTrace();
		}
	}
	
	public void recalcularValorTotalPlano()
	{
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Recalculando Valor Total do Plano...");
		this.planoDTOSelecionado.setValorTotal(0);
		for(PlanoMensalidadeDTO pm : this.planoMensalidades)
		{
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Valor da Mensalidade: " + (pm.getValorReajustado() <= 0 ? pm.getValorMes() : pm.getValorReajustado()));
			this.planoDTOSelecionado.setValorTotal(this.planoDTOSelecionado.getValorTotal() + (pm.getValorReajustado() <= 0 ? pm.getValorMes() : pm.getValorReajustado()));
		}
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Valor Total do Plano atualizado: " + this.planoDTOSelecionado.getValorTotal());
		System.out.println("[SODONTO SYSTEM][2.0.00][MB] Salvando Alterações...");
		alterar(false);
	}
	
	public String salvar()
	{
		try {

			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.planoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.planoSB.salvar(this.planoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.planoDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR PLANO", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarPlano(PlanoDTO planoDTOAlteracao)
	{
		this.planoDTO = planoDTOAlteracao;
	}
	
	public void alterar(boolean anularPlano)
	{	
		
		try {
			if(anularPlano)
			{
				this.planoSB.alterar(this.planoDTOAlteracao);
				this.planoDTOSelecionado = null;
			}
			else
			{
				this.planoSB.alterar(this.planoDTOSelecionado);
			}
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
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
			reportarErroAoAnalista(MBName + " ALTERAR PLANO", e);
			
			e.printStackTrace();
		}
	}
	
	public void removerPlano(PlanoDTO planoDTOAlteracao)
	{
		this.planoDTOSelecionado = planoDTOAlteracao;
		
		remover();
	}
	
	public String remover()
	{
		if(this.planoDTOSelecionado.getIdPlano() != null)
		{
			try {
				this.planoSB.inativar(this.planoDTOSelecionado.getIdPlano(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.planoDTOSelecionado = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<PlanoDTO> getPlanosFiltrados()
	{

		if(!getNomePlanoFiltro().equals(""))
		{
			setNomePlanoFiltro(!getNomePlanoFiltro().equals("") ? "%" + this.nomePlanoFiltro + "%" : "");
			return buscarPlanosPeloNome();
		}
		else
		{
			return buscarPlanos();
		}
		
	}
	
	public List<PlanoDTO> buscarPlanos()
	{
		List<PlanoDTO>  planoDTOsBusca = null;
			planoDTOsBusca = this.planoSB.buscarAtivos();
			
		return planoDTOsBusca != null 
				? planoDTOsBusca 
						: new ArrayList<PlanoDTO>();
	}
	
	public List<PlanoDTO> buscarPlanosVigentes()
	{
		List<PlanoDTO>  planoDTOsBusca = null;
		
		if(this.getPlanoDTO().getEstabelecimentoDTO().getIdEstabelecimento() != null)
		{
			planoDTOsBusca = this.planoSB.buscarVigentes(this.getPlanoDTO().getEstabelecimentoDTO().getIdEstabelecimento());

		}
			
		return planoDTOsBusca != null 
				? planoDTOsBusca 
						: new ArrayList<PlanoDTO>();
	}
	
	public List<PlanoDTO> buscarPlanosPeloNome()
	{
		List<PlanoDTO>  planoDTOsBusca = null;
		
		if(!this.getNomePlanoFiltro().equals(""))
		{
			planoDTOsBusca = this.planoSB.buscarPorNome(this.nomePlanoFiltro);

		}
		return planoDTOsBusca != null 
				? planoDTOsBusca 
						: new ArrayList<PlanoDTO>();
	}
	
	public List<PlanoDTO> buscarPlanosPelaValidade()
	{
		List<PlanoDTO>  planoDTOsBusca = null;
		
		if(getDataFiltro() != null)
		{
			planoDTOsBusca = this.planoSB.buscarPorValidade(getDataFiltro(), getDataFiltro());

		}
		return planoDTOsBusca != null 
				? planoDTOsBusca 
						: new ArrayList<PlanoDTO>();
	}
	
	public PlanoDTO buscarPeloId()
	{
		PlanoDTO planoDTOBusca = null;
		
		if(this.planoDTO.getIdPlano() != null)
		{
			planoDTOBusca = this.planoSB.buscarPorId(this.planoDTO.getIdPlano());
			if(planoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return planoDTOBusca;
	}
	
	public PlanoDTO getPlanoDTO() {
		if(planoDTO == null)
		{
			planoDTO = DTOFactory.getPlanoDTO();
		}
		
		return planoDTO;
	}

	public void setPlanoDTO(PlanoDTO planoDTO) {
		this.planoDTO = planoDTO;
	}

	public PlanoDTO getPlanoDTOSelecionado() {
		if(planoDTOSelecionado == null)
		{
			planoDTOSelecionado = DTOFactory.getPlanoDTO();
		}
		
		return planoDTOSelecionado;
	}

	public void setPlanoDTOSelecionado(PlanoDTO planoDTOSelecionado) {
		this.planoDTOSelecionado = planoDTOSelecionado;
	}

	public PlanoDTO getPlanoDTOAlteracao() {
		if(planoDTOAlteracao == null)
		{
			planoDTOAlteracao = DTOFactory.getPlanoDTO();
		}
		
		return planoDTOAlteracao;
	}

	public void setPlanoDTOAlteracao(PlanoDTO planoDTOAlteracao) {
		this.planoDTOAlteracao = planoDTOAlteracao;
	}

	public List<PlanoMensalidadeDTO> getPlanoMensalidades() {
		if(this.planoMensalidades == null)
		{
			return new ArrayList<PlanoMensalidadeDTO>();
		}
		
		return planoMensalidades;
	}

	public void setPlanoMensalidades(List<PlanoMensalidadeDTO> planoMensalidades) {
		this.planoMensalidades = planoMensalidades;
	}

	public String getNomePlanoFiltro() {
		if(nomePlanoFiltro == null)
		{
			this.nomePlanoFiltro = "";
		}
		return nomePlanoFiltro;
	}

	public void setNomePlanoFiltro(String nomePlanoFiltro) {
		this.nomePlanoFiltro = nomePlanoFiltro;
	}

	public Date getDataFiltro() {
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}
}
