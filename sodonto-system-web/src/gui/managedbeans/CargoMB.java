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
import negocio.fachada.local.ICargoFachada;
import dto.CargoDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class CargoMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|CARGO-MB|";
	
	@EJB
	private ICargoFachada cargoSB;
	
	private CargoDTO cargoDTO;	
	
	private String nomeCargoFiltro;
	
	public void limparMB()
	{
		this.cargoDTO = null;
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SGPM][MB] Iniciando Camada de Visualização...");
			this.cargoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.cargoSB.salvar(this.cargoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_CADASTRAR_SUCESSO);
			this.cargoDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR CARGO", e);
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void alterarCargo(CargoDTO cargoDTOAlteracao)
	{
		this.cargoDTO = cargoDTOAlteracao;
	}
	
	public String alterar()
	{
		try {
			this.cargoDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			this.cargoSB.alterar(this.cargoDTO);
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			this.cargoDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR CARGO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public void removerCargo(CargoDTO cargoDTOAlteracao)
	{
		this.cargoDTO = cargoDTOAlteracao;
		remover();
	}
	
	public String remover()
	{
		if(this.cargoDTO.getIdCargo() != null)
		{
			try {
				this.cargoSB.inativar(this.cargoDTO.getIdCargo(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
				this.cargoDTO = null;
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<CargoDTO> getCargosFiltrados()
	{

		if(!getNomeCargoFiltro().equals(""))
		{
			setNomeCargoFiltro(!getNomeCargoFiltro().equals("") ? "%" + this.nomeCargoFiltro + "%" : "");
			return buscarCargosPeloNome();
		}
		else
		{
			return buscarCargos();
		}
		
	}
	
	public List<CargoDTO> buscarCargos()
	{
		List<CargoDTO>  cargoDTOsBusca = null;
			cargoDTOsBusca = this.cargoSB.buscarAtivos();
			
		return cargoDTOsBusca != null 
				? cargoDTOsBusca 
						: new ArrayList<CargoDTO>();
	}
	
	public List<CargoDTO> buscarCargosPeloNome()
	{
		List<CargoDTO>  cargoDTOsBusca = null;
		
		if(!this.getNomeCargoFiltro().equals(""))
		{
			cargoDTOsBusca = this.cargoSB.buscarPorNome(this.nomeCargoFiltro);

		}
		return cargoDTOsBusca != null 
				? cargoDTOsBusca 
						: new ArrayList<CargoDTO>();
	}
	
	public CargoDTO buscarPeloId()
	{
		CargoDTO cargoDTOBusca = null;
		
		if(this.cargoDTO.getIdCargo() != null)
		{
			cargoDTOBusca = this.cargoSB.buscarPorId(this.cargoDTO.getIdCargo());
			if(cargoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return cargoDTOBusca;
	}
	
	public CargoDTO getCargoDTO() {
		if(cargoDTO == null)
		{
			cargoDTO = DTOFactory.getCargoDTO();
		}
		
		return cargoDTO;
	}

	public void setCargoDTO(CargoDTO cargoDTO) {
		this.cargoDTO = cargoDTO;
	}

	public String getNomeCargoFiltro() {
		if(nomeCargoFiltro == null)
		{
			this.nomeCargoFiltro = "";
		}
		return nomeCargoFiltro;
	}

	public void setNomeCargoFiltro(String nomeCargoFiltro) {
		this.nomeCargoFiltro = nomeCargoFiltro;
	}
}
