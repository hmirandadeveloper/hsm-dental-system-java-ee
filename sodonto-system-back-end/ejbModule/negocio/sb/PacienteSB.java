package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IPacienteFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.PacienteDAO;
import dto.PacienteDTO;
import dto.conversor.conversores.PacienteConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.PacienteAtributoValidador;
import dto.validador.validadores.util.ValidadorAtributosEspeciais;
import entidade.Paciente;

@Stateless
@Remote(IPacienteFachada.class)
public class PacienteSB implements IPacienteFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private PacienteDAO pacienteDAO;
	
	private PacienteConversorDTO pacienteConversorDTO;
	private PacienteAtributoValidador pacienteAtributoValidador;
	
	public PacienteSB()
	{
		this.pacienteConversorDTO = ConversorDTOFactory.getPacienteConversorDTO();
		this.pacienteAtributoValidador = AtributoValidadorFactory.getPacienteAtributoValidador();
	}
	
	@Override
	public void salvar(PacienteDTO pacienteDTO) throws EntidadeCadastradaException,
			AtributoIncompletoException, CpfInvalidoException {
		System.out.println("[SODONTO SYSTEM][SB] Acessando camada de Negócio...");
		GerenciadorAtributo gerenciadorAtributos = this.pacienteAtributoValidador.validarAtributosEmEntidade(pacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			System.out.println("[SODONTO SYSTEM][SB] Atributos validadados com sucesso...");
			if(ValidadorAtributosEspeciais.validarCpf(pacienteDTO.getCpf()))
			{
				System.out.println("[SODONTO SYSTEM][SB] CPF validadado com sucesso...");
				List<Paciente> pacientesBusca = this.pacienteDAO.buscarPorCpfOuRg(
						pacienteDTO.getCpf(), pacienteDTO.getRg(), 50);
				Paciente pacienteBusca = pacientesBusca.size() > 0 && pacientesBusca.size() < 2 ? 
						pacientesBusca.get(0) : null;
				
				if(pacienteBusca != null && !pacienteDTO.getCpf().equals("000.000.000-00"))
				{
					if(!pacienteBusca.isAtivo())
					{
						pacienteDTO.setIdPaciente(pacienteBusca.getIdPaciente());
						pacienteDTO.setAtivo(true);
						pacienteDTO.getUsuario().setAtivo(true);
						this.pacienteDAO.atualizar(this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO));
					}
					else
					{
						throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
					}
				}
				else
				{
					pacienteDTO.setAtivo(true);
					pacienteDTO.getUsuario().setAtivo(true);
					
					if(pacienteDTO.getIdPaciente() != null)
					{
						Paciente pacienteComID = this.pacienteDAO.buscar(pacienteDTO.getIdPaciente());
						
						if(pacienteComID != null)
						{
							if(pacienteComID.getIdPaciente() != null)
							{
								throw new EntidadeCadastradaException("[ID] " + MensagemErro.MSG_ERRO_INF_DUPLICADA);
							}
						}
						else
						{
							this.pacienteDAO.salvarComID(this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO));
						}
					}
					else
					{
						pacienteDTO.setIdPaciente(this.pacienteDAO.buscarUltimoID() + 1);
						this.pacienteDAO.salvar(this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO));
					}
				}
			}
			else
			{
				throw new CpfInvalidoException(MensagemErro.MSG_ERRO_CPF_INVALIDO);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
	}
	
	@Override
	public PacienteDTO alterar(PacienteDTO pacienteDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		System.out.println("[SODONTO SYSTEM][2.0.00][SB] Iniciando Alteração!");
		PacienteDTO pacienteRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.pacienteAtributoValidador.validarAtributosEmEntidade(pacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			Paciente pacienteBusca = this.pacienteDAO.buscar(pacienteDTO.getIdPaciente());
			
			if(pacienteBusca != null)
			{
				pacienteBusca = this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO);
				this.pacienteDAO.atualizar(pacienteBusca);
				
				pacienteRetornoDTO = pacienteDTO;
			}
			else if(pacienteDTO.getIdPaciente() != null)
			{
				pacienteBusca = this.pacienteConversorDTO.converterDTOEmEntidade(buscarPorId(pacienteDTO.getIdPaciente()));
				
				if(pacienteBusca != null)
				{
					if(pacienteBusca.isContratante())
					{
						pacienteBusca = this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO);
						this.pacienteDAO.atualizar(pacienteBusca);
					}
					else
					{
						System.out.println("[SODONTO SYSTEM][SB] Paciente se tornou Titular...");
						pacienteBusca = this.pacienteConversorDTO.converterDTOEmEntidade(pacienteDTO);
						this.pacienteDAO.atualizar(pacienteBusca);
					}
				}
				else
				{
					throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
				}
				
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		System.out.println("[SODONTO SYSTEM][2.0.00][SB] Atualização concluída!");
		return pacienteRetornoDTO;
	}

	@Override
	public void inativar(Long idPaciente, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Paciente pacienteBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A || perfilUsuario == EPerfilUsuario.G)
		{
			pacienteBusca = this.pacienteDAO.buscar(idPaciente);
			if(pacienteBusca != null)
			{
				pacienteBusca.setAtivo(false);
				this.pacienteDAO.atualizar(pacienteBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else if (perfilUsuario == EPerfilUsuario.G)
		{
			pacienteBusca = this.pacienteDAO.buscar(idPaciente);
			if(pacienteBusca != null)
			{
				pacienteBusca.setAtivo(false);
				this.pacienteDAO.atualizar(pacienteBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public PacienteDTO buscarPorId(Long idPaciente) {
		return this.pacienteConversorDTO.converterEntidadeEmDTO(
				this.pacienteDAO.buscar(idPaciente)
				);
	}

	@Override
	public List<PacienteDTO> buscarPorDentista(Long idDentista) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorDentista(idDentista)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarTitularPorCpf(String cpf, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarTitularPorCpfOuRg(cpf, "", limite)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarTitularPorRg(String rg, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarTitularPorCpfOuRg("", rg, limite)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarPorCpf(String cpf, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorCpfOuRg(cpf, "", limite)
				);
	}

	@Override
	public List<PacienteDTO> buscarPorRg(String rg, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorCpfOuRg("", rg, limite)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarPorNome(String nome, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorNome(nome, limite)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarTitularPorNome(String nome, int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarTitularPorNome(nome, limite)
				);
	}
	
	@Override
	public PacienteDTO buscarTitularPorId(Long idPaciente) {
		return this.pacienteConversorDTO.converterEntidadeEmDTO(
				this.pacienteDAO.buscarTitularPorId(idPaciente)
				);
	}	

	@Override
	public List<PacienteDTO> buscarPorPR(Long idPaciente) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorPR(idPaciente)
				);
	}
	
	@Override
	public List<PacienteDTO> buscarTitularAtivo(int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarTitularPorCondicao(true, limite)
				);
	}

	@Override
	public List<PacienteDTO> buscarAtivos(int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorCondicao(true, limite)
				);
	}

	@Override
	public List<PacienteDTO> buscarInativos(int limite) {
		return this.pacienteConversorDTO.converterListEntidadeEmListDTO(
				this.pacienteDAO.buscarPorCondicao(false, limite)
				);
	}

	@Override
	public PacienteDTO getEntidadeFromList(List<PacienteDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
