package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.EUf;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CnpjInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IEstabelecimentoFachada;
import dto.EstabelecimentoDTO;
import dto.FuncionarioDTO;
import dto.factory.DTOFactory;

@ViewScoped
@ManagedBean
public class EstabelecimentoMB extends ManagedBeanGenericBasic{

	public static final String MBName = "|ESTABELECIMENTO-MB|";
	
	@EJB
	private IEstabelecimentoFachada estabelecimentoSB;

	private EstabelecimentoDTO estabelecimentoDTO;
	
	private String nomeFiltro;
	
	public void limparMB()
	{
		this.estabelecimentoDTO = null;
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}

	public String salvar()
	{
		try {
			System.out.println("[SODONTO SYSTEM][MB] Iniciando persistência de dados...");
			System.out.println("[SODONTO SYSTEM][MB] Novo Estabelecimento...");
			if(getFuncionarioLogado() != null)
			{
				this.estabelecimentoDTO.setUsuarioDTO(getFuncionarioLogado().getUsuario());
			}
			this.estabelecimentoSB.salvar(this.estabelecimentoDTO);

			this.estabelecimentoDTO = null;
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
		} catch (CnpjInvalidoException e) {
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
			reportarErroAoAnalista(MBName + " SALVAR ESTABELECIMENTO", e);
			
			e.printStackTrace();
		}

		return "";
	}

	public void alterarEstabelecimento(EstabelecimentoDTO estabelecimentoDTOAlteracao)
	{
		this.estabelecimentoDTO = estabelecimentoDTOAlteracao;
	}

	public String alterar()
	{

		try {
			this.estabelecimentoDTO.setUsuarioDTO(getFuncionarioLogado().getUsuario());
			this.estabelecimentoSB.alterar(this.estabelecimentoDTO);
			this.estabelecimentoDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR ESTABELECIMENTO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}

	public void inativarEstabelecimento(EstabelecimentoDTO estabelecimentoDTOAlteracao)
	{
		this.estabelecimentoDTO = estabelecimentoDTOAlteracao;
		inativar();
	}

	public String inativar()
	{
		if(this.estabelecimentoDTO.getIdEstabelecimento() != null)
		{
			try {
				this.estabelecimentoSB.inativar(this.estabelecimentoDTO.getIdEstabelecimento(), 
						getFuncionarioLogado().getUsuario().getPerfilAtivo());
				this.estabelecimentoDTO = null;
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}

	public List<EstabelecimentoDTO> getEstabelecimentos()
	{
		List<EstabelecimentoDTO>  estabelecimentoDTOsBusca = null;

		estabelecimentoDTOsBusca = this.estabelecimentoSB.buscarAtivos();
		
		if(estabelecimentoDTOsBusca != null)
		{
			if(estabelecimentoDTOsBusca.size() <= 0)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}

		return estabelecimentoDTOsBusca;
	}

	public EstabelecimentoDTO buscarPeloNome()
	{
		EstabelecimentoDTO estabelecimentoDTOBusca = null;

		if(!this.getNomeFiltro().equals(""))
		{
			estabelecimentoDTOBusca = this.estabelecimentoSB.getEntidadeFromList(
					this.estabelecimentoSB.buscarPorNome(
							this.getNomeFiltro())
					);
			if(estabelecimentoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return estabelecimentoDTOBusca;
	}

	public EstabelecimentoDTO buscarPeloCpf()
	{
		EstabelecimentoDTO estabelecimentoDTOBusca = null;

		if(this.estabelecimentoDTO.getCnpj() != null)
		{
			estabelecimentoDTOBusca = this.estabelecimentoSB.getEntidadeFromList(
					this.estabelecimentoSB.buscarPorCnpj(this.estabelecimentoDTO.getCnpj())
					);
			if(estabelecimentoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return estabelecimentoDTOBusca;
	}

	public EstabelecimentoDTO buscarPeloId()
	{
		EstabelecimentoDTO estabelecimentoDTOBusca = null;

		if(this.estabelecimentoDTO.getIdEstabelecimento() != null)
		{
			estabelecimentoDTOBusca = this.estabelecimentoSB.buscarPorId(this.estabelecimentoDTO.getIdEstabelecimento());
			if(estabelecimentoDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return estabelecimentoDTOBusca;
	}
	
	public EUf[] getUfs()
	{
		return EUf.values(); 
	}

	public EstabelecimentoDTO getEstabelecimentoDTO() {
		if(estabelecimentoDTO == null)
		{
			estabelecimentoDTO = DTOFactory.getEstabelecimentoDTO();
		}

		return estabelecimentoDTO;
	}

	public void setEstabelecimentoDTO(EstabelecimentoDTO estabelecimentoDTO) {
		this.estabelecimentoDTO = estabelecimentoDTO;
	}

	public String getNomeFiltro() {
		if(this.nomeFiltro == null)
		{
			this.nomeFiltro = "";
		}
		
		return nomeFiltro;
	}

	public void setNomeFiltro(String nomeFiltro) {
		this.nomeFiltro = nomeFiltro;
	}
}
