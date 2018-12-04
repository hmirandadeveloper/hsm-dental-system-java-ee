package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.email.EmailFactory;
import gui.util.torpedo.TorpedoUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.constante.enums.ESituacaoPaciente;
import negocio.constante.mensagens.MensagemErro;
import negocio.constante.mensagens.MensagemInformativa;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IPacienteFachada;
import negocio.util.DataUtil;
import negocio.util.email.EmailSimples;
import dto.FuncionarioDTO;
import dto.MsgPreTorpedoDTO;
import dto.PacienteDTO;
import dto.factory.DTOFactory;

@ViewScoped
@ManagedBean
public class PacienteMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|PACIENTE-MB|";
	
	@EJB
	private IPacienteFachada pacienteSB;

	private PacienteDTO pacienteDTO;
	
	//Torpedo
	private MsgPreTorpedoDTO msgPreTorpedoDTO;
	private boolean torpedoCustomizado;
	
	private String campoInteligenteBusca;
	private String campoInteligenteBuscaTitular;
	private boolean IDHabilitado;
	
	//Filtros Busca
	private String nomeFiltro;
	private String cpfFiltro;
	private String rgFiltro;
	private Long idFiltro;
	
	//Lista de Pacientes Selecionados
	private List<PacienteDTO> pacientesSelecionados;
	
	public void habilitarID()
	{
		this.IDHabilitado = true;
		enviarMenssagemInformativa("Definição de ID - ATIVADA");
	}
	
	public void desabilitarID()
	{
		this.IDHabilitado = false;
		getPacienteDTO().setIdPaciente(null);
		enviarMenssagemInformativa("Definição de ID - DESATIVADA");
	}
	
	public void incluirPacienteEmLista(PacienteDTO pacienteSelecionadoDTO)
	{
		if(!getPacientesSelecionados().contains(pacienteSelecionadoDTO))
		{
			getPacientesSelecionados().add(pacienteSelecionadoDTO);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_PACIENTE_DUPLICADO_LISTA);
		}
	}
	
	public void removerPacienteDeLista(PacienteDTO pacienteSelecionadoDTO)
	{
		getPacientesSelecionados().remove(pacienteSelecionadoDTO);
	}
	
	public void removerSelecaoTorpedo()
	{
		this.msgPreTorpedoDTO = null;
	}
	
	public void selecionarTorpedo(MsgPreTorpedoDTO msgPreTorpedoDTO)
	{
		this.msgPreTorpedoDTO = msgPreTorpedoDTO;
	}
	
	public void enviarTorpedoPaciente()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}
		
		if(!getMsgPreTorpedoDTO().getMsg().isEmpty() 
				&& getPacientesSelecionados().size() > 0)
		{			
			int totalEnvios = 0;
			String pacientesErro = "";
			String retornoEnvio = "";					
			
			for(PacienteDTO pc :  getPacientesSelecionados())
			{		
				retornoEnvio = TorpedoUtil
						.enviarTorpedo(
								getAtributoOperacionalSelecionado(),
								pc.getCel01(), 
								getMsgPreTorpedoDTO().getMsg(), 
								pc.getNome(),
								null,
								null);				
			
				
				if(retornoEnvio.contains(
						TorpedoUtil
						.getRetornoSucessoOperadora(
								getAtributoOperacionalSelecionado()
								.getProvedorSMS())))
				{
					totalEnvios++;
				}
				else
				{
					pacientesErro = (pacientesErro.length() == 0 
							? pc.getIdPaciente().toString() 
							: pacientesErro + ", " + 
							pc.getIdPaciente().toString());
				}
				
			}
			
			
			if(totalEnvios == getPacientesSelecionados().size())
			{
				
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
			incluirLogOperacional(
					"ENVIOU TORPEDOS PARA PACIENTES", 
					"ENVIOU TORPEDOS PARA TODOS OS PACIENTES SELECIONADOS"
					);
/*
 * FIM DO REGISTRO DE LOG
*/				
				
				if(totalEnvios > 1)
				{
					enviarMenssagemInformativa(MensagemInformativa.MSG_ENVIO_TORPEDOS);
				}
				else if(totalEnvios == 1)
				{
					enviarMenssagemInformativa(MensagemInformativa.MSG_ENVIO_TORPEDO);
				}
			}
			else
			{
				enviarMenssagemErro(MensagemErro.MSG_ERRO_ENVIO_TORPEDO);
				enviarMenssagemAlerta((getPacientesSelecionados().size() - totalEnvios < 10 ? "0" : "")
						+(getPacientesSelecionados().size() - totalEnvios)
						+ " Paciente"
						+(getPacientesSelecionados().size() - totalEnvios > 1 ? "s" : "")
						+ " NÃO receberam Torpedo. (O"
						+(getPacientesSelecionados().size() - totalEnvios > 1 ? "s" : "")
						+ " seu"
						+(getPacientesSelecionados().size() - totalEnvios > 1 ? "s" : "")
						+ " Código "
						+(getPacientesSelecionados().size() - totalEnvios > 1 ? "s são:" : " é:")
						+ pacientesErro
						+ ") - Favor verificar se há algo ERRADO no Cadastro do CELULAR 01 do Paciente"
						);
			}
		}
	}	
	
	private boolean enviarEmail()
	{
		boolean enviado = false;

		if(getPacienteDTO().getEmail().isEmpty() || getPacienteDTO().getEmail() == null)
		{
			enviarMenssagemAlerta("O Paciente não possui Email cadastrado, sendo assim, não recebberá nenhuma informação sobre o seu cadastro.");
			return false;
		}
		String msg = "-----------------------------------------------------\n" +
				"INFORMAÇÕES DO PACIENTE:\n" +
				"-----------------------------------------------------\n" +
				"  - NOME: " + this.pacienteDTO.getNome() + "\n" +
				"  - SOBRENOME: " + this.pacienteDTO.getSobrenome() + "\n" + (this.pacienteDTO.isContratante() ?
				"  - CPF: " + this.pacienteDTO.getCpf() + "\n" : "") + (this.pacienteDTO.isContratante() ?
				"  - RG: " + this.pacienteDTO.getRg() + " " + this.pacienteDTO.getRgOrgao() + "/" + this.pacienteDTO.getRgUf().name() + "\n" : "") +
				"  - SEXO: " + this.pacienteDTO.getSexoFormatado() + "\n" +
				"  - TITULAR: " + (this.pacienteDTO.isContratante() ? "SIM" : "NÃO" ) + "\n" +
				"  - DATA DE NASCIMENTO: " + DataUtil.getDataFormatada(this.pacienteDTO.getDataNascimento()) + "\n" +
				"  - DATA DE CADASTRO: " + DataUtil.getDataFormatada(this.pacienteDTO.getDataCadastro()) + "\n \n" +
				(!this.pacienteDTO.isContratante() ?
				"-----------------------------------------------------\n" +
				"INFORMAÇÕES DO TITULAR:\n" +
				"-----------------------------------------------------\n" +
				"  - CÓDIGO DE CADASTRO: " + this.pacienteDTO.getPaciente().getIdPaciente() + "\n" +
				"  - NOME: " + this.pacienteDTO.getPaciente().getNomeCompleto() + "\n" +
				"  - CPF: " + this.pacienteDTO.getPaciente().getCpf() + "\n" +
				"  - TELEFONE: " + this.pacienteDTO.getPaciente().getTelResidencial() + "\n" +
				"  - CELULAR 01: " + "("+ this.pacienteDTO.getPaciente().getOperadoraCel01().getNomeOperadora() + ") " + this.pacienteDTO.getPaciente().getCel01() + "\n" + ((!this.pacienteDTO.getPaciente().getCel02().equals("") && this.pacienteDTO.getPaciente().getOperadoraCel02() != null) ?
				"  - CELULAR 02: " + "("+ this.pacienteDTO.getPaciente().getOperadoraCel02().getNomeOperadora() + ") " + this.pacienteDTO.getPaciente().getCel02() + "\n" : "") + ((!this.pacienteDTO.getPaciente().getCel03().equals("") && this.pacienteDTO.getPaciente().getOperadoraCel03() != null) ? 
				"  - CELULAR 03: " + "("+ this.pacienteDTO.getPaciente().getOperadoraCel03().getNomeOperadora() + ") " + this.pacienteDTO.getPaciente().getCel03() + "\n" : "") +							
				"  - EMAIL: " + this.pacienteDTO.getPaciente().getEmail() + "\n \n" : "") +					
				"-----------------------------------------------------\n" +
				"INFORMAÇÕES DO ENDEREÇO:\n" +
				"-----------------------------------------------------\n" +
				"  - LOGRADOURO: " + this.pacienteDTO.getEndereco().getLogradouro() + "\n" +
				"  - NUMERO: " + this.pacienteDTO.getEndereco().getNumero() + "\n" +
				"  - COMPLEMENTO: " + this.pacienteDTO.getEndereco().getComplemento() + "\n" +
				"  - BAIRRO: " + this.pacienteDTO.getEndereco().getBairro() + "\n" +
				"  - CIDADE: " + this.pacienteDTO.getEndereco().getCidade() + "\n" +
				"  - UF: " + this.pacienteDTO.getEndereco().getUf() + "\n" +
				"  - CEP: " + this.pacienteDTO.getEndereco().getCep() + "\n \n" +
				"-----------------------------------------------------\n" +
				"INFORMAÇÕES DE COMUNICAÇÃO:\n" +
				"-----------------------------------------------------\n" +
				"  - EMAIL: " + this.pacienteDTO.getEmail() + "\n" +
				"  - TELEFONE RESIDENCIAL: " + this.pacienteDTO.getTelResidencial() + "\n" +
				"  - CELULAR 01: " + "("+ this.pacienteDTO.getOperadoraCel01().getNomeOperadora() + ") " + this.pacienteDTO.getCel01() + "\n" + ((!this.pacienteDTO.getCel02().equals("") && this.pacienteDTO.getOperadoraCel02() != null) ?
				"  - CELULAR 02: " + "("+ this.pacienteDTO.getOperadoraCel02().getNomeOperadora() + ") " + this.pacienteDTO.getCel02() + "\n" : "") + ((!this.pacienteDTO.getCel03().equals("") && this.pacienteDTO.getOperadoraCel03() != null) ? 
				"  - CELULAR 03: " + "("+ this.pacienteDTO.getOperadoraCel03().getNomeOperadora() + ") " + this.pacienteDTO.getCel03() + "\n" : "")+
				"-----------------------------------------------------\n" +
				"OUTRAS INFORMAÇÕES:\n" +
				"-----------------------------------------------------\n" +
				"  - DENTISTA: " + this.pacienteDTO.getDentistaDTO().getNomeComCROFormatado() + "\n" +
				"  - ESTABELECIMENTO: " + this.pacienteDTO.getEstabelecimentoDTO().getNomeEstabelecimentoFormatado() + "\n" +
				"\n \n \n \n * OBS.: FAVOR VERIFICAR SE OS DADOS ESTÃO CORRETOS. CASO \n" +
				"EXISTA ALGUMA DIVERGÊNCIA NOS DADOS, ENTRAR EM CONTATO \n" +
				" COM O ATENDIMENTO DA SODONTO!";
			
		String tituloMsg = "DADOS CADASTRADOS NO SISTEMA";
		String tituloEmail = ConstantesSodontoSystem.SISTEMA_EMAIL_TITULO + "CADASTRO PACIENTE";
		
		EmailSimples email = EmailFactory.getEmail(getPacienteDTO().getEmail(), tituloEmail, tituloMsg, msg);
		
		enviado = enviarEmail(email);
		
		return enviado;
	}
	
	public void reenviarEmail(PacienteDTO pacienteDTOAlteracao)
	{
		this.pacienteDTO = pacienteDTOAlteracao;
		enviarEmail();
		enviarMenssagemInformativa("Os Dados de Cadastro foram enviados para o Email: " + pacienteDTO.getEmail());
	}
	
	public void isTitular()
	{
		System.out.println("[SODONTO SYSTEM][MB] Paciente Titular: " + getPacienteDTO().isContratante());
		if(getPacienteDTO().isContratante())
		{
			this.pacienteDTO.setPaciente(DTOFactory.getPacienteDTO());
		}
	}
	
	public boolean isTelefoneAtualizado(PacienteDTO pacienteDTOValidacao)
	{
		boolean validacao = true;
		if(DataUtil.getDiferencaDiasPelaDataAtual(pacienteDTOValidacao.getDataMov()) > getAtributoOperacionalSelecionado().getTempoValidacaoTelefone())
		{
			validacao = false;
		}
		
		return validacao;
	}
	
	public ESituacaoPaciente[] getSituacoesPaciente()
	{
		return ESituacaoPaciente.values();
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}
	
	public void selecionarPaciente(PacienteDTO pacienteTitularDTO)
	{
		getPacienteDTO().setPaciente(pacienteTitularDTO);
	}

	public String salvar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();	
			return "";
		}
		
		try {
			System.out.println("[SODONTO SYSTEM][MB] Iniciando persistência de dados...");
			System.out.println("[SODONTO SYSTEM][MB] Novo Paciente...");
			if(getFuncionarioLogado() != null)
			{
				this.pacienteDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			}
			this.pacienteDTO.setDataCadastro(new Date());
			this.pacienteDTO.setSituacaoPaciente(ESituacaoPaciente.E);
			System.out.println("[SODONTO SYSTEM][MB] Iniciando acesso camada SB...");
			this.pacienteSB.salvar(this.pacienteDTO);
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"INCLUSÃO DE PACIENTE", 
				"INCLUIU O NOVO PACIENTE: "
				+ getPacienteDTO().getNomeCompleto()
				+ " NO BANCO DE DADOS"
				);
/*
 * FIM DO REGISTRO DE LOG
*/			
			
			if(pacienteDTO.getCpf().equals("000.000.000-00") && pacienteDTO.isContratante())
			{
				enviarMenssagemAlerta("O Paciente Titular ("+ pacienteDTO.getNomeCompleto() +"), foi salvo com o CPF Provisório [000.000.000-00], mas lembre-se de atualizar o CPF deste Paciente. Pois, você será lembrado até que essa informação INCORRETA seja alterada.");
			}
			if(this.pacienteDTO.getEmail().indexOf("@") > 0)
			{
				if(enviarEmail())
				{
					enviarMenssagemInformativa("Os Dados de Cadastro foram enviados para o Email: " + pacienteDTO.getEmail());
				}
				else
				{
					enviarMenssagemAlerta("Erro ao enviar Email com os Dados de Cadastro do Paciente!");
				}
			}
			else
			{
				enviarMenssagemAlerta("O Paciente não possui Email cadastrado, sendo assim, não receberá a confirmação do cadastro!");
			}
			this.pacienteDTO = null;
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
			reportarErroAoAnalista(MBName + " SALVAR PACIENTE", e);
			
			e.printStackTrace();
		}

		return "";
	}

	public void alterarPaciente(PacienteDTO pacienteDTOAlteracao)
	{
		this.pacienteDTO = pacienteDTOAlteracao;
	}

	public String alterar()
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();			
			return "";
		}
		
		try {
			this.pacienteDTO.setUsuario(getFuncionarioLogado().getUsuarioPerfil());
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Iniciando Atualização...");
			this.pacienteSB.alterar(this.pacienteDTO);
			
/*
 * INÍCIO DO REGISTRO DE LOG
*/
		
		incluirLogOperacional(
				"ALTERAÇÃO DE PACIENTE", 
				"ALTEROU DADOS DO PACIENTE: "
				+ getPacienteDTO().getNomeCompleto()
				);
/*
 * FIM DO REGISTRO DE LOG
*/			
			
			System.out.println("[SODONTO SYSTEM][2.0.00][MB] Atualização concluída!");
			enviarMenssagemInformativa(MensagemInformativa.MSG_ATUALIZAR_SUCESSO);
			if(this.pacienteDTO.getEmail().indexOf("@") > 0)
			{
				if(enviarEmail())
				{
					enviarMenssagemInformativa("Os Dados de Cadastro foram enviados para o Email: " + pacienteDTO.getEmail());
				}
				else
				{
					enviarMenssagemAlerta("Erro ao enviar Email com os Dados de Cadastro do Paciente!");
				}
			}
			else
			{
				enviarMenssagemAlerta("O Paciente não possui Email cadastrado, sendo assim, não receberá a confirmação do cadastro!");
			}
			this.pacienteDTO = null;
			
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
			reportarErroAoAnalista(MBName + " ALTERAR PACIENTE", e);
			e.printStackTrace();
		}
		return ""; 
	}

	public void inativarPaciente(PacienteDTO pacienteDTOAlteracao)
	{
		this.pacienteDTO = pacienteDTOAlteracao;
		inativar();
	}

	public String inativar()
	{
		if(this.pacienteDTO.getIdPaciente() != null)
		{
			try {
				this.pacienteSB.inativar(this.pacienteDTO.getIdPaciente(), 
						getFuncionarioLogado().getUsuarioPerfil().getPerfilAtivo());
				this.pacienteDTO = null;
				enviarMenssagemInformativa(MensagemInformativa.MSG_REMOVER_SUCESSO);
			} catch (EntidadeInexistenteException e) {
				enviarMenssagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public List<PacienteDTO> buscaInteligente()
	{
		List<PacienteDTO> listaPaciente = null;
		System.out.println("[SODONTO SYSTEM][MB] Plavara de Busca: " + getCampoInteligenteBusca());
		if(getCampoInteligenteBusca().length() > 0)
		{
			try
			{
				this.idFiltro = Long.parseLong(campoInteligenteBusca);
				//ID
				System.out.println("[SODONTO SYSTEM][MB] Filtro tipo ID...");
				listaPaciente =  new ArrayList<PacienteDTO>();
				PacienteDTO pacienteDTOBuscaId = buscarPeloId();
				if(pacienteDTOBuscaId != null)
				{
					System.out.println("Localizado Paciente pelo ID...");
					listaPaciente.add(buscarPeloId());
				}
				else
				{
					setIdFiltro(null);
				}
			}
			catch(Exception e)
			{
				if(campoInteligenteBusca.contains("."))
				{
					if(campoInteligenteBusca.indexOf(".") == 3)
					{
						//CPF
						System.out.println("[SODONTO SYSTEM][MB] Filtro tipo CPF...");
						setCpfFiltro(!getCampoInteligenteBusca().equals("") ? "%" + getCampoInteligenteBusca() + "%" : "");
						listaPaciente = buscarPeloCpf();
					}
					else if(campoInteligenteBusca.indexOf(".") == 1)
					{
						//RG
						System.out.println("[SODONTO SYSTEM][MB] Filtro tipo RG...");
						setRgFiltro(!getCampoInteligenteBusca().equals("") ? "%" + getCampoInteligenteBusca() + "%" : "");
						listaPaciente = buscarPorRg();
					}
				}
				else
				{
				//NOME
					System.out.println("[SODONTO SYSTEM][MB] Filtro tipo Nome...");
					setNomeFiltro(!getCampoInteligenteBusca().equals("") ? "%" + getCampoInteligenteBusca() + "%" : "");
					listaPaciente = buscarPeloNome();
				}
			}
			
			return listaPaciente;
		}
		else
		{
			return getPacientes();
		}
	}
	
	public List<PacienteDTO> buscaInteligenteTitular()
	{
		
		List<PacienteDTO> listaPaciente = null;
		System.out.println("[SODONTO SYSTEM][MB] Plavara de Busca para Titular: " + getCampoInteligenteBuscaTitular());
		if(getCampoInteligenteBuscaTitular().length() > 0)
		{
			try
			{
				this.idFiltro = Long.parseLong(campoInteligenteBuscaTitular);
				//ID
				System.out.println("[SODONTO SYSTEM][MB] Filtro tipo ID...");
				listaPaciente =  new ArrayList<PacienteDTO>();
				PacienteDTO pacienteDTOBuscaId = buscarTitularPeloId();
				if(pacienteDTOBuscaId != null)
				{
					System.out.println("Localizado Paciente pelo ID...");
					listaPaciente.add(buscarPeloId());
				}
				else
				{
					setIdFiltro(null);
				}
			}
			catch(Exception e)
			{
				if(campoInteligenteBuscaTitular.contains("."))
				{
					if(campoInteligenteBuscaTitular.indexOf(".") == 3)
					{
						//CPF
						System.out.println("[SODONTO SYSTEM][MB] Filtro tipo CPF...");
						setCpfFiltro(!getCampoInteligenteBuscaTitular().equals("") ? "%" + getCampoInteligenteBuscaTitular() + "%" : "");
						listaPaciente = buscarTitularPeloCpf();
					}
					else if(campoInteligenteBuscaTitular.indexOf(".") == 1)
					{
						//RG
						System.out.println("[SODONTO SYSTEM][MB] Filtro tipo RG...");
						setRgFiltro(!getCampoInteligenteBuscaTitular().equals("") ? "%" + getCampoInteligenteBuscaTitular() + "%" : "");
						listaPaciente = buscarTitularPorRg();
					}
				}
				else
				{
				//NOME
					System.out.println("[SODONTO SYSTEM][MB] Filtro tipo Nome...");
					setNomeFiltro(!getCampoInteligenteBuscaTitular().equals("") ? "%" + getCampoInteligenteBuscaTitular() + "%" : "");
					listaPaciente = buscarTitularPeloNome();
				}
			}
			
			return listaPaciente;
		}
		else
		{
			return getTitulares();
		}
	}

	public List<PacienteDTO> getPacientes()
	{
		List<PacienteDTO>  pacienteDTOsBusca = null;
		
		pacienteDTOsBusca = this.pacienteSB.buscarAtivos(getAtributoOperacionalSelecionado().getLimitQuery());

		return pacienteDTOsBusca != null ? 
				pacienteDTOsBusca
				: new ArrayList<PacienteDTO>();
	}
	
	public List<PacienteDTO> getTitulares()
	{
		List<PacienteDTO>  pacienteDTOsBusca = null;
		
		pacienteDTOsBusca = this.pacienteSB.buscarTitularAtivo(getAtributoOperacionalSelecionado().getLimitQuery());

		return pacienteDTOsBusca != null ? 
				filtroLista(pacienteDTOsBusca)
				: new ArrayList<PacienteDTO>();
	}

	public List<PacienteDTO> buscarPeloNome()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.nomeFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarPorNome(
					this.nomeFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}
	

	public List<PacienteDTO> buscarTitularPeloNome()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.nomeFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarTitularPorNome(
					this.nomeFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}

	public List<PacienteDTO> buscarTitularPeloCpf()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.cpfFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarTitularPorCpf(this.cpfFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}
	
	public List<PacienteDTO> buscarPeloCpf()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.cpfFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarPorCpf(this.cpfFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}
	
	public List<PacienteDTO> buscarPorRg()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.rgFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarPorRg(this.rgFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}
	
	public List<PacienteDTO> buscarTitularPorRg()
	{
		List<PacienteDTO> pacientesDTOBusca = null;

		if(!this.rgFiltro.equals(""))
		{
			pacientesDTOBusca = this.pacienteSB.buscarTitularPorRg(this.rgFiltro,
					getAtributoOperacionalSelecionado().getLimitQuery());
		}
		return pacientesDTOBusca != null ? 
				filtroLista(pacientesDTOBusca)
				: new ArrayList<PacienteDTO>();
	}

	public PacienteDTO buscarPeloId()
	{
		PacienteDTO pacienteDTOBusca = null;

		if(this.idFiltro != null)
		{
			pacienteDTOBusca = (this.pacienteSB.buscarPorId(this.idFiltro).isAtivo() ? this.pacienteSB.buscarPorId(this.idFiltro) : null);
		}
		return pacienteDTOBusca;
	}
	
	public PacienteDTO buscarTitularPeloId()
	{
		PacienteDTO pacienteDTOBusca = null;

		if(this.idFiltro != null)
		{
			pacienteDTOBusca = this.pacienteSB.buscarTitularPorId(this.idFiltro);
		}
		return pacienteDTOBusca;
	}	
	
	public List<PacienteDTO> filtroLista(List<PacienteDTO> lista)
	{
		if(getPacienteDTO().getIdPaciente() != null)
		{
			lista.remove(this.pacienteDTO);
		}
		
		return lista;
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

	public MsgPreTorpedoDTO getMsgPreTorpedoDTO() {
		
		if(msgPreTorpedoDTO == null)
		{
			msgPreTorpedoDTO = DTOFactory.getMsgPreTorpedoDTO();
		}
		
		if(torpedoCustomizado)
		{
			String header = ConstantesSodontoSystem.SISTEMA_SMS_HEADER;
			
			if(this.msgPreTorpedoDTO.getMsg() == null)
			{
				this.msgPreTorpedoDTO.setMsg("");
			}
			
			if(!this.msgPreTorpedoDTO.getMsg().contains(header))
			{
				this.msgPreTorpedoDTO.setMsg(header);
			}
		}
		
		return msgPreTorpedoDTO;
	}

	public void setMsgPreTorpedoDTO(MsgPreTorpedoDTO msgPreTorpedoDTO) {
		
		this.msgPreTorpedoDTO = msgPreTorpedoDTO;
		
		if(torpedoCustomizado)
		{
			String header = ConstantesSodontoSystem.SISTEMA_SMS_HEADER;
			
			if(this.msgPreTorpedoDTO.getMsg() == null)
			{
				this.msgPreTorpedoDTO.setMsg("");
			}
			
			if(!this.msgPreTorpedoDTO.getMsg().contains(header))
			{
				this.msgPreTorpedoDTO.setMsg(header);
			}
		}
	}

	public boolean isTorpedoCustomizado() {
		return torpedoCustomizado;
	}

	public void setTorpedoCustomizado(boolean torpedoCustomizado) {
		this.torpedoCustomizado = torpedoCustomizado;
	}

	public String getCampoInteligenteBusca() {
		if(this.campoInteligenteBusca == null)
		{
			this.campoInteligenteBusca = "";
		}
		
		return campoInteligenteBusca;
	}

	public void setCampoInteligenteBusca(String campoInteligenteBusca) {
		this.campoInteligenteBusca = campoInteligenteBusca;
	}
	
	public String getCampoInteligenteBuscaTitular() {
		if(this.campoInteligenteBuscaTitular == null)
		{
			this.campoInteligenteBuscaTitular = "";
		}
		
		return campoInteligenteBuscaTitular;
	}

	public void setCampoInteligenteBuscaTitular(
			String campoInteligenteBuscaTitular) {
		this.campoInteligenteBuscaTitular = campoInteligenteBuscaTitular;
	}

	public boolean isIDHabilitado() {
		return IDHabilitado;
	}

	public void setIDHabilitado(boolean IDHabilitado) {
		this.IDHabilitado = IDHabilitado;
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

	public String getCpfFiltro() {
		if(this.cpfFiltro == null)
		{
			this.cpfFiltro = "";
		}
		
		return cpfFiltro;
	}

	public void setCpfFiltro(String cpfFiltro) {
		this.cpfFiltro = cpfFiltro;
	}

	public String getRgFiltro() {
		if(this.rgFiltro == null)
		{
			this.rgFiltro = "";
		}
		
		return rgFiltro;
	}

	public void setRgFiltro(String rgFiltro) {
		this.rgFiltro = rgFiltro;
	}

	public Long getIdFiltro() {
		return idFiltro;
	}

	public void setIdFiltro(Long idFiltro) {
		this.idFiltro = idFiltro;
	}

	public List<PacienteDTO> getPacientesSelecionados() {
		if(this.pacientesSelecionados == null)
		{
			this.pacientesSelecionados = new ArrayList<PacienteDTO>();
		}
		
		return this.pacientesSelecionados;
	}

	public void setPacientesSelecionados(List<PacienteDTO> pacientesSelecionados) {
		this.pacientesSelecionados = pacientesSelecionados;
	}	
}
