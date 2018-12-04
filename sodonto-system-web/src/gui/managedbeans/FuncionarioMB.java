package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.UsuarioUtil;
import gui.util.email.EmailUtil;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IFuncionarioFachada;

import org.apache.commons.mail.EmailException;

import dto.FuncionarioDTO;
import dto.factory.DTOFactory;

@ViewScoped
@ManagedBean
public class FuncionarioMB extends ManagedBeanGenericBasic{

	public static final String MBName = "|FUNCIONÁRIO-MB|";
	
	@EJB
	private IFuncionarioFachada funcionarioSB;

	private FuncionarioDTO funcionarioDTO;
	private String passwordTemp;
	
	public void limparMB()
	{
		this.funcionarioDTO = null;
	}
	
	private boolean enviarEmail()
	{
		boolean enviado = false;
		try {
			EmailUtil.enviarEmail(this.funcionarioDTO.getEmail(), 
					ConstantesSodontoSystem.SISTEMA_NOME + ": Senha para Validação", 
					ConstantesSodontoSystem.SISTEMA_NOME + " - v. "+ ConstantesSodontoSystem.SISTEMA_VERSAO  +"\n \n" +
					".:DADOS DO CADASTRO:. \n \n" +
					"-----------------------------------------------------\n" +
					"DADOS DO FUNCIONÁRIO:\n" +
					"-----------------------------------------------------\n" +
					"  - NOME: " + this.funcionarioDTO.getNome() + " " + this.funcionarioDTO.getSobrenome() + "\n" +
					"  - RG: " + this.funcionarioDTO.getRg()+ " " +this.funcionarioDTO.getRgOrgao() + " " + this.funcionarioDTO.getRgUf().name() + "\n" +
					"  - CPF: " + this.funcionarioDTO.getCpf() + "\n \n" +
					"-----------------------------------------------------\n" +
					"DADOS DE COMUNICAÇÃO:\n" +
					"-----------------------------------------------------\n" +
					"  - EMAIL: " + this.funcionarioDTO.getEmail() + "\n" +
					"  - TELEFONE: " + this.funcionarioDTO.getTelResidencial() + "\n" +
					"  - CELULAR: " + "(" + this.funcionarioDTO.getOperadoraCel01().getNomeOperadora() + ") " + this.funcionarioDTO.getCel01() + "\n \n" +
					"-----------------------------------------------------\n" +
					"DADOS DO CARGO:\n" +
					"-----------------------------------------------------\n" +
					"  - ESTABELECIMENTO: " + this.funcionarioDTO.getEstabelecimentoDTO().getNomeEstabelecimentoFormatado() + "\n" +
					"  - CARGO: " + this.funcionarioDTO.getCargo().getNomeCargo() + "\n \n" +
					"-----------------------------------------------------\n" +
					"DADOS DO USUÁRIO:\n" +
					"-----------------------------------------------------\n" +
					"  - USUÁRIO: " + this.funcionarioDTO.getUsuarioPerfil().getUsuario() + "\n " +
					"  - PERFIL: " + this.funcionarioDTO.getUsuarioPerfil().getPerfilCadastro().getPerfil().toUpperCase() + "\n " +  
					"  - SENHA (TEMPORÁRIA): " + this.passwordTemp + 
					"\n \n \n \n * OBS.: PARA ATIVAR O PERFIL DE ACESSO, SERÁ \n" +
					"NECESSÁRIO LOGAR NO SISTEMA E ATIVAR O USUÁRIO!");
			enviado = true;
		} catch (EmailException e) {
			e.printStackTrace();
			enviado = false;
		}
		
		return enviado;
	}
	
	public void reenviarEmail(FuncionarioDTO funcionarioDTOAlteracao)
	{
		this.funcionarioDTO = funcionarioDTOAlteracao;
		gerarSenhaTempUsuario();
		alterar();
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}
	
	public boolean isOnlyFuncionario()
	{
		FacesContext context = getContext();
		HttpSession session = 
				(HttpSession)context.getExternalContext().getSession(false);

		if((session.getAttribute("paciente") != null))
		{
			return false;
		}
		
		return true;
	}
	
	private void gerarSenhaTempUsuario()
	{
		this.passwordTemp = UsuarioUtil.gerarSenha();
		this.funcionarioDTO.getUsuarioPerfil().setSenha(criptografarPassword(this.passwordTemp));
	}
	
	private String criptografarPassword(String pass)
	{
		String passCrypt = "";
		if(pass != null)
		{
			passCrypt = pass;
			passCrypt = UsuarioUtil.criptografarSenha(passCrypt);
		}		
		
		return passCrypt;
	}

	public String salvar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();			
			return "";
		}
		
		try {
			gerarSenhaTempUsuario();
			System.out.println("[SGPM][MB] Iniciando persistência de dados...");
			this.funcionarioDTO.getUsuarioPerfil().setPerfilAtivo(EPerfilUsuario.T);
			this.funcionarioDTO.setUsuario(getFuncionarioLogado().getUsuario());
			this.funcionarioSB.salvar(this.funcionarioDTO);
			if(enviarEmail())
			{
				System.out.println("Senha Temporária: " + this.passwordTemp);
				enviarMenssagemInformativa("A Senha de Validação foi enviada para o Email: " + funcionarioDTO.getEmail());
			}
			else
			{
				enviarMenssagemInformativa("Erro ao enviar Email de Validação de Senha!");
			}
			this.funcionarioDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR FUNCIONÁRIO", e);
			
			e.printStackTrace();
		}

		return "";
	}

	public void alterarFuncionario(FuncionarioDTO funcionarioDTOAlteracao)
	{
		this.funcionarioDTO = funcionarioDTOAlteracao;
	}

	public String alterar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();			
			return "";
		}
		
		try {
			gerarSenhaTempUsuario();
			this.funcionarioDTO.getUsuarioPerfil().setPerfilAtivo(EPerfilUsuario.T);
			this.funcionarioDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());			
			this.funcionarioSB.alterar(this.funcionarioDTO);
			if(enviarEmail())
			{
				enviarMenssagemInformativa("A Senha de Validação foi enviada para o Email: " + funcionarioDTO.getEmail());
			}
			else
			{
				enviarMenssagemInformativa("Erro ao enviar Email de Validação de Senha!");
			}
			this.funcionarioDTO = null;
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
			reportarErroAoAnalista(MBName + " ALTERAR FUNCIONÁRIO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}
	
	public String alterarDados()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();			
			return "";
		}
		
		try {

			this.funcionarioSB.alterar(getFuncionarioLogado());
			
			this.funcionarioDTO = null;
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
		} catch (EntidadeInexistenteException e) {
			enviarMenssagemErro(e.getMessage());
			e.printStackTrace();
		} catch (AtributoIncompletoException e) {
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
			reportarErroAoAnalista(MBName + " ALTERAR FUNCIONÁRIO", e);
			
			e.printStackTrace();
		}
		return ""; 
	}

	public void removerFuncionario(FuncionarioDTO funcionarioDTOAlteracao)
	{
		this.funcionarioDTO = funcionarioDTOAlteracao;
		remover();
	}

	public String remover()
	{
		if(this.funcionarioDTO.getIdFuncionario() != null)
		{
			try {
				this.funcionarioSB.inativar(this.funcionarioDTO.getIdFuncionario(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				this.funcionarioDTO = null;
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}

	public List<FuncionarioDTO> getFuncionarios()
	{
		List<FuncionarioDTO>  funcionarioDTOsBusca = null;

		funcionarioDTOsBusca = this.funcionarioSB.buscarAtivos();
		
		if(funcionarioDTOsBusca != null)
		{
			if(funcionarioDTOsBusca.size() <= 0)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}

		return filtroLista(funcionarioDTOsBusca);
	}

	public FuncionarioDTO buscarPeloUsuario()
	{
		FuncionarioDTO funcionarioDTOBusca = null;

		if(this.funcionarioDTO.getIdFuncionario() != null)
		{
			funcionarioDTOBusca = this.funcionarioSB.getEntidadeFromList(
					this.funcionarioSB.buscarPorIdUsuario(this.funcionarioDTO.getUsuario().getIdUsuario())
					);
			if(funcionarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return funcionarioDTOBusca;
	}
	
	public FuncionarioDTO buscarPeloIdUsuario(Long idUsuario)
	{
		FuncionarioDTO funcionarioDTOBusca = null;

		if(idUsuario != null)
		{
			funcionarioDTOBusca = this.funcionarioSB.getEntidadeFromList(
					this.funcionarioSB.buscarPorIdUsuario(idUsuario)
					);
			if(funcionarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return funcionarioDTOBusca;
	}

	public FuncionarioDTO buscarPeloCPF()
	{
		FuncionarioDTO funcionarioDTOBusca = null;

		if(this.funcionarioDTO.getIdFuncionario() != null)
		{
			funcionarioDTOBusca = this.funcionarioSB.getEntidadeFromList(
					this.funcionarioSB.buscarPorCpf(this.funcionarioDTO.getCpf())
					);
			if(funcionarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return funcionarioDTOBusca;
	}

	public FuncionarioDTO buscarPeloId()
	{
		FuncionarioDTO funcionarioDTOBusca = null;

		if(this.funcionarioDTO.getIdFuncionario() != null)
		{
			funcionarioDTOBusca = this.funcionarioSB.buscarPorId(this.funcionarioDTO.getIdFuncionario());
			if(funcionarioDTOBusca == null)
			{
				enviarMenssagemAlerta(MensagemInformativa.MSG_BUSCA_VAZIA);
			}
		}
		return funcionarioDTOBusca;
	}
	
	public List<FuncionarioDTO> filtroLista(List<FuncionarioDTO> lista)
	{
		lista.remove(getFuncionarioLogado());
		
		return lista;
	}

	public FuncionarioDTO getFuncionarioDTO() {
		if(funcionarioDTO == null)
		{
			funcionarioDTO = DTOFactory.getFuncionarioDTO();
		}

		return funcionarioDTO;
	}

	public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
		this.funcionarioDTO = funcionarioDTO;
	}
}
