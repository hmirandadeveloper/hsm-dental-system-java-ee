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
import negocio.fachada.local.IEspecialidadeDentistaFachada;
import dto.DentistaDTO;
import dto.EspecialidadeDentistaDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class EspecialidadeDentistaMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|ESPECIALIDADE DE DENTISTA-MB|";
	
	@EJB
	private IEspecialidadeDentistaFachada especialidadeDentistaSB;
	
	private EspecialidadeDentistaDTO especialidadeDentistaDTO;	
	
	public void limparMB()
	{
		this.especialidadeDentistaDTO = null;
	}
	
	public void vincularEspecialidade(DentistaDTO dentistaDTO)
	{		
		getEspecialidadeDentistaDTO().setDentista(dentistaDTO);
		
	}
	
	public String getEspecializacoesDoDentista(DentistaDTO dentistaDTO)
	{
		List<EspecialidadeDentistaDTO>  especialidadeDentistaDTOsBusca = null;
		String especializacoes = "";
		
		if(dentistaDTO != null)
		{		
			this.especialidadeDentistaSB.buscarPorDentista(dentistaDTO.getIdDentista());		
			
				especialidadeDentistaDTOsBusca = this.especialidadeDentistaSB.buscarPorDentista(
						dentistaDTO.getIdDentista());

		}	
		
		if(especialidadeDentistaDTOsBusca != null)
		{
			for(EspecialidadeDentistaDTO ed : especialidadeDentistaDTOsBusca)
			{
				if(especializacoes.equals(""))
				{
					especializacoes = ed.getEspecialidade().getNomeEspecialidade();
				}
				else
				{
					especializacoes += ", " + ed.getEspecialidade().getNomeEspecialidade();
				}
			}
		}
		
		return especializacoes.equals("") ? "NENHUMA ESPECIALIZAÇÃO VINCULADA" : especializacoes;
		
	}
	
	public String salvar()
	{
		try {
			System.out.println("[SODONTO SYSTEM][MB] Iniciando Camada de Visualização...");
			System.out.println("[SODONTO SYSTEM][MB] ID do Dentista: " + getEspecialidadeDentistaDTO().getDentista().getIdDentista());
			System.out.println("[SODONTO SYSTEM][MB] ID do Especialização: " + getEspecialidadeDentistaDTO().getEspecialidade().getIdEspecialidade());
			this.especialidadeDentistaSB.salvar(this.especialidadeDentistaDTO);
			this.especialidadeDentistaDTO.setEspecialidade(null);
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
			reportarErroAoAnalista(MBName + " SALVAR ESPECIALIDADE DO DENTISTA", e);
			
			e.printStackTrace();
		} finally
		{
			this.especialidadeDentistaDTO.setEspecialidade(DTOFactory.getEspecialidadeDTO());
		}
		
		return "";
	}
	
	public String remover(EspecialidadeDentistaDTO especialidadeDentistaDTOAlteracao)
	{
		if(especialidadeDentistaDTOAlteracao.getIdEspecialidadeDentista() != null)
		{
			try {
				this.especialidadeDentistaSB.inativar(especialidadeDentistaDTOAlteracao.getIdEspecialidadeDentista(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_ITEM);	
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<EspecialidadeDentistaDTO> getEspecialidadeDentistasFiltrados()
	{
		System.out.println("[SODONTO SYSTEM][MB] Dentista Selecionado: " + (getEspecialidadeDentistaDTO().getDentista().getIdDentista() != null ? "Sim" : "Não"));
		if(getEspecialidadeDentistaDTO().getDentista().getIdDentista() != null)
		{
			System.out.println("[SODONTO SYSTEM][MB] ID Dentista Selecionado: " + getEspecialidadeDentistaDTO().getDentista().getIdDentista());
			return buscarPorDentista();
		}
		else if(getEspecialidadeDentistaDTO().getEspecialidade().getIdEspecialidade() != null)
		{
			return buscarPorEspecialidade();
		}
		else
		{
			System.out.println("[SODONTO SYSTEM][MB] Retonando Todas as Especializações vinculados com Dentistas!!!");
			System.out.println("[SODONTO SYSTEM][MB] Total: " + buscarEspecialidadeDentistas().size());
			return buscarEspecialidadeDentistas();
		}
		
	}
	
	public List<EspecialidadeDentistaDTO> buscarEspecialidadeDentistas()
	{
		List<EspecialidadeDentistaDTO>  especialidadeDentistaDTOsBusca = null;
			especialidadeDentistaDTOsBusca = this.especialidadeDentistaSB.buscarAtivos();
			
		return especialidadeDentistaDTOsBusca != null 
				? especialidadeDentistaDTOsBusca 
						: new ArrayList<EspecialidadeDentistaDTO>();
	}
	
	public List<EspecialidadeDentistaDTO> buscarPorDentista()
	{
		List<EspecialidadeDentistaDTO>  especialidadeDentistaDTOsBusca = null;
		
		if(getEspecialidadeDentistaDTO().getDentista().getIdDentista() != null)
		{
			especialidadeDentistaDTOsBusca = this.especialidadeDentistaSB.buscarPorDentista(
					this.especialidadeDentistaDTO.getDentista().getIdDentista());

		}
		return especialidadeDentistaDTOsBusca != null 
				? especialidadeDentistaDTOsBusca 
						: new ArrayList<EspecialidadeDentistaDTO>();
	}
	
	public List<EspecialidadeDentistaDTO> buscarPorEspecialidade()
	{
		List<EspecialidadeDentistaDTO>  especialidadeDentistaDTOsBusca = null;
		
		if(getEspecialidadeDentistaDTO().getEspecialidade().getIdEspecialidade() != null)
		{
			especialidadeDentistaDTOsBusca = this.especialidadeDentistaSB.buscarPorEspecialidade(
					this.especialidadeDentistaDTO.getEspecialidade().getIdEspecialidade());

		}
		return especialidadeDentistaDTOsBusca != null 
				? especialidadeDentistaDTOsBusca 
						: new ArrayList<EspecialidadeDentistaDTO>();
	}
	
	public EspecialidadeDentistaDTO buscarPeloId()
	{
		EspecialidadeDentistaDTO especialidadeDentistaDTOBusca = null;
		
		if(this.especialidadeDentistaDTO.getIdEspecialidadeDentista() != null)
		{
			especialidadeDentistaDTOBusca = this.especialidadeDentistaSB.buscarPorId(this.especialidadeDentistaDTO.getIdEspecialidadeDentista());
			if(especialidadeDentistaDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return especialidadeDentistaDTOBusca;
	}
	
	public EspecialidadeDentistaDTO getEspecialidadeDentistaDTO() {
		if(this.especialidadeDentistaDTO == null)
		{
			this.especialidadeDentistaDTO = DTOFactory.getEspecialidadeDentistaDTO();
		}
		
		return especialidadeDentistaDTO;
	}

	public void setEspecialidadeDentistaDTO(EspecialidadeDentistaDTO especialidadeDentistaDTO) {
		this.especialidadeDentistaDTO = especialidadeDentistaDTO;
	}
}
