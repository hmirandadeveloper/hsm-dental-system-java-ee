package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.ETipoObsPaciente;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IObsPacienteFachada;
import dto.ObsPacienteDTO;
import dto.PacienteDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class ObsPacienteMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|OBSERVAÇÃO DE PACIENTE-MB|";
	
	@EJB
	private IObsPacienteFachada obsPacienteSB;
	
	private ObsPacienteDTO obsPacienteDTO;	
	private PacienteDTO pacienteDTO;
	private ETipoObsPaciente tipoObs;
	
	public void selecionarPaciente(PacienteDTO pacienteDTO, String tipoObsPaciente)
	{
		this.pacienteDTO = pacienteDTO;
		this.tipoObs = ETipoObsPaciente.valueOf(tipoObsPaciente);		
	}
	
	public void limparMB()
	{
		this.obsPacienteDTO = null;
	}
	
	public void setTipoObsPorString(String tipoObs)
	{
		this.tipoObs = ETipoObsPaciente.valueOf(tipoObs);
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			getObsPacienteDTO().setTipo(getTipoObs());
			this.obsPacienteDTO.setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.obsPacienteDTO.setPacienteDTO(getPacienteDTO());
			this.obsPacienteDTO.setData(new Date());
			if(this.obsPacienteDTO.getTipo() == ETipoObsPaciente.A)
			{
				this.obsPacienteDTO.setObs(this.obsPacienteDTO.getObs() + " - [Acordo realizado por: "+ getFuncionarioLogado().getNomeCompleto() +"]");
			}
			this.obsPacienteSB.salvar(this.obsPacienteDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.obsPacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR OBSERVAÇÃO DE PACIENTE", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarObsPaciente(ObsPacienteDTO obsPacienteDTOAlteracao)
	{
		this.obsPacienteDTO = obsPacienteDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.obsPacienteDTO.setUsuarioDTO(getFuncionarioLogado().getUsuarioPerfil());
			this.obsPacienteDTO.setPacienteDTO(getPacienteDTO());
			this.obsPacienteSB.alterar(this.obsPacienteDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.obsPacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR OBSERVAÇÃO DE PACIENTE", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerObsPaciente(ObsPacienteDTO obsPacienteDTOAlteracao)
	{
		this.obsPacienteDTO = obsPacienteDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.obsPacienteDTO.getIdObsPaciente() != null)
		{
			try {
				this.obsPacienteSB.inativar(this.obsPacienteDTO.getIdObsPaciente(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.obsPacienteDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<ObsPacienteDTO> buscarObsPacientes()
	{
		List<ObsPacienteDTO>  obsPacienteDTOsBusca = null;
			obsPacienteDTOsBusca = this.obsPacienteSB.buscarAtivos();
			
		return obsPacienteDTOsBusca != null 
				? obsPacienteDTOsBusca 
						: new ArrayList<ObsPacienteDTO>();
	}
	
	public List<ObsPacienteDTO> buscarObsPacientesPeloPaciente()
	{
		List<ObsPacienteDTO> obsPacienteDTOsBusca = null;
		
		if(getPacienteDTO().getIdPaciente() != null)
		{
			obsPacienteDTOsBusca = this.obsPacienteSB.buscarPorPaciente(this.pacienteDTO.getIdPaciente());

		}
		return obsPacienteDTOsBusca != null 
				? obsPacienteDTOsBusca 
						: new ArrayList<ObsPacienteDTO>();
	}
	
	public List<ObsPacienteDTO> buscarObsPacientesPeloTipoEPaciente()
	{
		List<ObsPacienteDTO> obsPacienteDTOsBusca = null;
		
		if(getPacienteDTO().getIdPaciente() != null && !getTipoObs().equals(""))
		{
			obsPacienteDTOsBusca = this.obsPacienteSB.buscarPorPacienteETipo(getPacienteDTO().getIdPaciente(), getTipoObs().name());

		}
		return obsPacienteDTOsBusca != null 
				? obsPacienteDTOsBusca 
						: new ArrayList<ObsPacienteDTO>();
	}
	
	public List<ObsPacienteDTO> buscarObsPacientesPeloTipoEPaciente(String tipoObs, PacienteDTO pacienteDTO)
	{
		List<ObsPacienteDTO> obsPacienteDTOsBusca = null;
		
		if(pacienteDTO.getIdPaciente() != null && !tipoObs.equals(""))
		{
			obsPacienteDTOsBusca = this.obsPacienteSB.buscarPorPacienteETipo(pacienteDTO.getIdPaciente(), tipoObs);

		}
		return obsPacienteDTOsBusca != null 
				? obsPacienteDTOsBusca 
						: new ArrayList<ObsPacienteDTO>();
	}
	
	public ObsPacienteDTO buscarPeloId()
	{
		ObsPacienteDTO obsPacienteDTOBusca = null;
		
		if(this.obsPacienteDTO.getIdObsPaciente() != null)
		{
			obsPacienteDTOBusca = this.obsPacienteSB.buscarPorId(this.obsPacienteDTO.getIdObsPaciente());
			if(obsPacienteDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return obsPacienteDTOBusca;
	}
	
	public ObsPacienteDTO getObsPacienteDTO() {
		if(obsPacienteDTO == null)
		{
			obsPacienteDTO = DTOFactory.getObsPacienteDTO();
		}
		
		return obsPacienteDTO;
	}

	public void setObsPacienteDTO(ObsPacienteDTO obsPacienteDTO) {
		this.obsPacienteDTO = obsPacienteDTO;
	}

	public PacienteDTO getPacienteDTO() {
		if(pacienteDTO == null)
		{
			pacienteDTO = DTOFactory.getPacienteDTO();
		}
		
		return pacienteDTO;
	}

	public void setPacienteDTO(PacienteDTO pacienteDTO) {
		this.pacienteDTO = pacienteDTO;
	}

	public ETipoObsPaciente getTipoObs() {
		if(tipoObs == null)
		{
			tipoObs = ETipoObsPaciente.C;
		}
		
		return tipoObs;
	}

	public void setTipoObs(ETipoObsPaciente tipoObs) {
		this.tipoObs = tipoObs;
	}
}
