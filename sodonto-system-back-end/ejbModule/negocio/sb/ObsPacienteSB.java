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
import negocio.fachada.local.IObsPacienteFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.ObsPacienteDAO;
import dto.ObsPacienteDTO;
import dto.conversor.conversores.ObsPacienteConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.ObsPacienteAtributoValidador;
import entidade.ObsPaciente;

@Stateless
@Remote(IObsPacienteFachada.class)
public class ObsPacienteSB implements IObsPacienteFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private ObsPacienteDAO obsPacienteDAO;
	
	private ObsPacienteConversorDTO obsPacienteConversorDTO;
	private ObsPacienteAtributoValidador obsPacienteAtributoValidador;
	
	public ObsPacienteSB()
	{
		this.obsPacienteConversorDTO = ConversorDTOFactory.getObsPacienteConversorDTO();
		this.obsPacienteAtributoValidador = AtributoValidadorFactory.getObsPacienteAtributoValidador();
	}
	
	@Override
	public void salvar(ObsPacienteDTO obsPacienteDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.obsPacienteAtributoValidador.validarAtributosEmEntidade(obsPacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<ObsPaciente> obsPacientesBusca = this.obsPacienteDAO.buscarPorPacienteTipoEObs(
					obsPacienteDTO.getPacienteDTO().getIdPaciente(), obsPacienteDTO.getTipo().name(),
					obsPacienteDTO.getObs());
			ObsPaciente obsPacienteBusca = obsPacientesBusca.size() > 0 && obsPacientesBusca.size() < 2 ? 
					obsPacientesBusca.get(0) : null;

			if(obsPacienteBusca != null)
			{
				if(!obsPacienteBusca.isAtivo())
				{
					obsPacienteDTO.setIdObsPaciente(obsPacienteBusca.getIdObsPaciente());
					obsPacienteDTO.setAtivo(true);
					this.obsPacienteDAO.atualizar(this.obsPacienteConversorDTO.converterDTOEmEntidade(obsPacienteDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				obsPacienteDTO.setAtivo(true);
				this.obsPacienteDAO.salvar(this.obsPacienteConversorDTO.converterDTOEmEntidade(obsPacienteDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public ObsPacienteDTO alterar(ObsPacienteDTO obsPacienteDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		ObsPacienteDTO obsPacienteRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.obsPacienteAtributoValidador.validarAtributosEmEntidade(obsPacienteDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<ObsPaciente> obsPacientesBusca = this.obsPacienteDAO.buscarPorPacienteTipoEObs(
					obsPacienteDTO.getPacienteDTO().getIdPaciente(), obsPacienteDTO.getTipo().name(),
					obsPacienteDTO.getObs());
			ObsPaciente obsPacienteBusca = obsPacientesBusca.size() > 0 && obsPacientesBusca.size() < 2 ? 
					obsPacientesBusca.get(0) : null;
			
			if(obsPacienteBusca != null || ( obsPacienteDTO.getIdObsPaciente() != null 
					&& obsPacienteBusca == null))
			{
				obsPacienteBusca = this.obsPacienteConversorDTO.converterDTOEmEntidade(obsPacienteDTO);
				this.obsPacienteDAO.atualizar(obsPacienteBusca);
				
				obsPacienteRetornoDTO = obsPacienteDTO;
			}
			else if(obsPacientesBusca.size() > 1)
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
		
		return obsPacienteRetornoDTO;
	}

	@Override
	public void inativar(Long idObsPaciente, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		ObsPaciente obsPacienteBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			obsPacienteBusca = this.obsPacienteDAO.buscar(idObsPaciente);
			if(obsPacienteBusca != null)
			{
				try
				{
					this.obsPacienteDAO.remover(obsPacienteBusca);
				}
				catch(Exception e)
				{
					obsPacienteBusca.setAtivo(false);
					this.obsPacienteDAO.atualizar(obsPacienteBusca);
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
			obsPacienteBusca = this.obsPacienteDAO.buscar(idObsPaciente);
			if(obsPacienteBusca != null)
			{
				obsPacienteBusca.setAtivo(false);
				this.obsPacienteDAO.atualizar(obsPacienteBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public ObsPacienteDTO buscarPorId(Long idObsPaciente) {
		return this.obsPacienteConversorDTO.converterEntidadeEmDTO(
				this.obsPacienteDAO.buscar(idObsPaciente)
				);
	}
	
	@Override
	public List<ObsPacienteDTO> buscarPorPacienteETipo(Long idPaciente, String tipo) {
		return this.obsPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.obsPacienteDAO.buscarPorPacienteETipo(idPaciente, tipo)
				);
	}
	
	@Override
	public List<ObsPacienteDTO> buscarPorPaciente(Long idPaciente) {
		return this.obsPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.obsPacienteDAO.buscarPorPaciente(idPaciente)
				);
	}

	@Override
	public List<ObsPacienteDTO> buscarAtivos() {
		return this.obsPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.obsPacienteDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<ObsPacienteDTO> buscarInativos() {
		return this.obsPacienteConversorDTO.converterListEntidadeEmListDTO(
				this.obsPacienteDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public ObsPacienteDTO getEntidadeFromList(List<ObsPacienteDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
