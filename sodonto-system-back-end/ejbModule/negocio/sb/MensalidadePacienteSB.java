package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IMensalidadePacienteFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.MensalidadePacienteDAO;
import dto.MensalidadePacienteDTO;
import dto.conversor.conversores.MensalidadePacienteConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.MensalidadePacienteAtributoValidador;
import entidade.MensalidadePaciente;

@Stateless
@Remote(IMensalidadePacienteFachada.class)
public class MensalidadePacienteSB implements IMensalidadePacienteFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private MensalidadePacienteDAO mensalidadePacienteDAO;
	
	private MensalidadePacienteConversorDTO mensalidadePacienteConversorDTO;
	private MensalidadePacienteAtributoValidador mensalidadePacienteAtributoValidador;
	
	public MensalidadePacienteSB()
	{
		this.mensalidadePacienteConversorDTO = ConversorDTOFactory.getMensalidadePacienteConversorDTO();
		this.mensalidadePacienteAtributoValidador = AtributoValidadorFactory.getMensalidadePacienteAtributoValidador();
	}
	
	@Override
	public void salvar(MensalidadePacienteDTO mensalidadePacienteDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.mensalidadePacienteAtributoValidador.validarAtributosEmEntidade(mensalidadePacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MensalidadePaciente> mensalidadePacientesBusca = this.mensalidadePacienteDAO.buscarPorPlanoPacienteEMes(
					mensalidadePacienteDTO.getPlanoPaciente().getPlano().getIdPlano(), mensalidadePacienteDTO.getPlanoPaciente().getPaciente().getIdPaciente(), mensalidadePacienteDTO.getPlanoMensalidade().getMes());
			MensalidadePaciente mensalidadePacienteBusca = mensalidadePacientesBusca.size() > 0 && mensalidadePacientesBusca.size() < 2 ? 
					mensalidadePacientesBusca.get(0) : null;

			if(mensalidadePacienteBusca != null)
			{
				if(!mensalidadePacienteBusca.isAtivo())
				{
					mensalidadePacienteDTO.setIdMensalidadePaciente(mensalidadePacienteBusca.getIdMensalidadePaciente());
					mensalidadePacienteDTO.setAtivo(true);
					this.mensalidadePacienteDAO.atualizar(this.mensalidadePacienteConversorDTO.converterDTOEmEntidade(mensalidadePacienteDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				mensalidadePacienteDTO.setAtivo(true);
				this.mensalidadePacienteDAO.salvar(this.mensalidadePacienteConversorDTO.converterDTOEmEntidade(mensalidadePacienteDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public MensalidadePacienteDTO alterar(MensalidadePacienteDTO mensalidadePacienteDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		MensalidadePacienteDTO mensalidadePacienteRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.mensalidadePacienteAtributoValidador.validarAtributosEmEntidade(mensalidadePacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MensalidadePaciente> mensalidadePacientesBusca = this.mensalidadePacienteDAO.buscarPorPlanoPacienteEMes(
					mensalidadePacienteDTO.getPlanoPaciente().getPlano().getIdPlano(), mensalidadePacienteDTO.getPlanoPaciente().getPaciente().getIdPaciente(), mensalidadePacienteDTO.getPlanoMensalidade().getMes());
			MensalidadePaciente mensalidadePacienteBusca = mensalidadePacientesBusca.size() > 0 && mensalidadePacientesBusca.size() < 2 ? 
					mensalidadePacientesBusca.get(0) : null;
			
			if(mensalidadePacienteBusca != null)
			{
				mensalidadePacienteBusca = this.mensalidadePacienteConversorDTO.converterDTOEmEntidade(mensalidadePacienteDTO);
				this.mensalidadePacienteDAO.atualizar(mensalidadePacienteBusca);
				
				mensalidadePacienteRetornoDTO = mensalidadePacienteDTO;
			}
			else if(mensalidadePacientesBusca.size() > 1)
			{
				throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
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
		
		return mensalidadePacienteRetornoDTO;
	}

	@Override
	public void inativar(Long idMensalidadePaciente, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		MensalidadePaciente mensalidadePacienteBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			mensalidadePacienteBusca = this.mensalidadePacienteDAO.buscar(idMensalidadePaciente);
			if(mensalidadePacienteBusca != null)
			{
				try
				{
					this.mensalidadePacienteDAO.remover(mensalidadePacienteBusca);
				}
				catch(Exception e)
				{
					mensalidadePacienteBusca.setAtivo(false);
					this.mensalidadePacienteDAO.atualizar(mensalidadePacienteBusca);
					e.printStackTrace();
					System.out.println("[SB][SODONTO SYSTEM][ERRO]Item com vículos, impossível remover, " +
							"o mesmo foi, apenas, inativado! (Esse item só pode ser removido " +
							"diretamente no banco de dados)");
				}
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else if (perfilUsuario == EPerfilUsuario.G)
		{
			mensalidadePacienteBusca = this.mensalidadePacienteDAO.buscar(idMensalidadePaciente);
			if(mensalidadePacienteBusca != null)
			{
				mensalidadePacienteBusca.setAtivo(false);
				this.mensalidadePacienteDAO.atualizar(mensalidadePacienteBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public MensalidadePacienteDTO buscarPorId(Long idMensalidadePaciente) {
		return this.mensalidadePacienteConversorDTO.converterEntidadeEmDTO(
				this.mensalidadePacienteDAO.buscar(idMensalidadePaciente)
				);
	}
	
	@Override
	public int buscarTotalPorPlanoEPaciente(Long idPlano, Long idPaciente) {
		return this.mensalidadePacienteDAO.buscarTotalPorPlanoEPaciente(idPlano, idPaciente);
	}
	
	@Override
	public List<MensalidadePacienteDTO> buscarPorPlanoEPaciente(Long idPlano, Long idPaciente) {
		return this.mensalidadePacienteConversorDTO.converterListEntidadeEmListDTO(
				this.mensalidadePacienteDAO.buscarPorPlanoEPaciente(idPlano, idPaciente)
				);
	}
	
	@Override
	public List<MensalidadePacienteDTO> buscarPorPlanoPacienteEMes(Long idPlano, Long idPaciente, int mes) {
		return this.mensalidadePacienteConversorDTO.converterListEntidadeEmListDTO(
				this.mensalidadePacienteDAO.buscarPorPlanoPacienteEMes(idPlano, idPaciente, mes)
				);
	}
	
	@Override
	public List<MensalidadePacienteDTO> buscarPorSituacaoPlanoEPaciente(String situacao, Long idPlano, Long idPaciente) {
		return this.mensalidadePacienteConversorDTO.converterListEntidadeEmListDTO(
				this.mensalidadePacienteDAO.buscarPorSituacaoPlanoEPaciente(situacao, idPlano, idPaciente)
				);
	}

	@Override
	public List<MensalidadePacienteDTO> buscarAtivos() {
		return this.mensalidadePacienteConversorDTO.converterListEntidadeEmListDTO(
				this.mensalidadePacienteDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<MensalidadePacienteDTO> buscarInativos() {
		return this.mensalidadePacienteConversorDTO.converterListEntidadeEmListDTO(
				this.mensalidadePacienteDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public MensalidadePacienteDTO getEntidadeFromList(List<MensalidadePacienteDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
