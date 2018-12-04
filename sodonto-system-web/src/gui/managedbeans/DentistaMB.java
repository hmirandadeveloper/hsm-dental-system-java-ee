package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IDentistaFachada;
import dto.DentistaDTO;
import dto.FuncionarioDTO;
import dto.factory.DTOFactory;

@ViewScoped
@ManagedBean
public class DentistaMB extends ManagedBeanGenericBasic{

	public static final String MBName = "|DENTISTA-MB|";
	
	@EJB
	private IDentistaFachada dentistaSB;

	private DentistaDTO dentistaDTO;
	
	public void limparMB()
	{
		this.dentistaDTO = null;
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}
	
	public void selecionarDentista(DentistaDTO dentistaSelecionadoDTO)
	{
		this.dentistaDTO = dentistaSelecionadoDTO;
	}

	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando persistência de dados...");
			this.dentistaDTO.setUsuario(getFuncionarioLogado().getUsuario());
			this.dentistaSB.salvar(this.dentistaDTO);
			this.dentistaDTO = null;
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			
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
		} catch (CpfInvalidoException e) {
			enviarMenssagemErro(e.getMessage());
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
			reportarErroAoAnalista(MBName + " SALVAR DENTISTA", e);
			
			e.printStackTrace();
		}

		return "";
	}

	public void alterarDentista(DentistaDTO dentistaDTOAlteracao)
	{
		this.dentistaDTO = dentistaDTOAlteracao;
	}

	public String alterar()
	{

		try {
			this.dentistaDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());			
			this.dentistaSB.alterar(this.dentistaDTO);
			this.dentistaDTO = null;
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			
		} catch (EntidadeInexistenteException e) {
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
			reportarErroAoAnalista(MBName + " ALTERAR DENTISTA", e);
			
			e.printStackTrace();
		}
		return ""; 
	}

	public void removerDentista(DentistaDTO dentistaDTOAlteracao)
	{
		this.dentistaDTO = dentistaDTOAlteracao;
		remover();
	}

	public String remover()
	{
		if(this.dentistaDTO.getIdDentista() != null)
		{
			try {
				this.dentistaSB.inativar(this.dentistaDTO.getIdDentista(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				this.dentistaDTO = null;
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}

	public List<DentistaDTO> getDentistas()
	{
		List<DentistaDTO>  dentistaDTOsBusca = null;

		dentistaDTOsBusca = this.dentistaSB.buscarAtivos();
		
		if(dentistaDTOsBusca != null)
		{
			if(dentistaDTOsBusca.size() <= 0)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}

		return dentistaDTOsBusca;
	}

	public DentistaDTO buscarPeloNome()
	{
		DentistaDTO dentistaDTOBusca = null;

		if(this.dentistaDTO.getIdDentista() != null)
		{
			dentistaDTOBusca = this.dentistaSB.getEntidadeFromList(
					this.dentistaSB.buscarPorNome(this.dentistaDTO.getNome())
					);
			if(dentistaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return dentistaDTOBusca;
	}

	public DentistaDTO buscarPeloCPF()
	{
		DentistaDTO dentistaDTOBusca = null;

		if(this.dentistaDTO.getIdDentista() != null)
		{
			dentistaDTOBusca = this.dentistaSB.getEntidadeFromList(
					this.dentistaSB.buscarPorCpf(this.dentistaDTO.getCpf())
					);
			if(dentistaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return dentistaDTOBusca;
	}
	
	public DentistaDTO buscarPeloCRO()
	{
		DentistaDTO dentistaDTOBusca = null;

		if(this.dentistaDTO.getIdDentista() != null)
		{
			dentistaDTOBusca = this.dentistaSB.getEntidadeFromList(
					this.dentistaSB.buscarPorCro(this.dentistaDTO.getCro())
					);
			if(dentistaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return dentistaDTOBusca;
	}

	public DentistaDTO buscarPeloId()
	{
		DentistaDTO dentistaDTOBusca = null;

		if(this.dentistaDTO.getIdDentista() != null)
		{
			dentistaDTOBusca = this.dentistaSB.buscarPorId(this.dentistaDTO.getIdDentista());
			if(dentistaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return dentistaDTOBusca;
	}

	public DentistaDTO getDentistaDTO() {
		if(dentistaDTO == null)
		{
			dentistaDTO = DTOFactory.getDentistaDTO();
		}

		return dentistaDTO;
	}

	public void setDentistaDTO(DentistaDTO dentistaDTO) {
		this.dentistaDTO = dentistaDTO;
	}
}
