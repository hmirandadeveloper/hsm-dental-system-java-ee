package negocio.sb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAtendimentoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.AtendimentoDAO;
import dto.AtendimentoDTO;
import dto.conversor.conversores.AtendimentoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.AtendimentoAtributoValidador;
import entidade.Atendimento;

@Stateless
@Remote(IAtendimentoFachada.class)
public class AtendimentoSB implements IAtendimentoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AtendimentoDAO atendimentoDAO;
	
	private AtendimentoConversorDTO atendimentoConversorDTO;
	private AtendimentoAtributoValidador atendimentoAtributoValidador;
	
	public AtendimentoSB()
	{
		this.atendimentoConversorDTO = ConversorDTOFactory.getAtendimentoConversorDTO();
		this.atendimentoAtributoValidador = AtributoValidadorFactory.getAtendimentoAtributoValidador();
	}
	
	@Override
	public void salvar(AtendimentoDTO atendimentoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.atendimentoAtributoValidador.validarAtributosEmEntidade(atendimentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Atendimento> atendimentosBusca = this.atendimentoDAO.buscarPorAgendamento(
					atendimentoDTO.getAgendamento().getIdAgendamento());
			Atendimento atendimentoBusca = atendimentosBusca.size() > 0 && atendimentosBusca.size() < 2 ? 
					atendimentosBusca.get(0) : null;

			if(atendimentoBusca != null)
			{
				if(!atendimentoBusca.isAtivo())
				{
					atendimentoDTO.setIdAtendimento(atendimentoBusca.getIdAtendimento());
					atendimentoDTO.setAtivo(true);
					this.atendimentoDAO.atualizar(this.atendimentoConversorDTO.converterDTOEmEntidade(atendimentoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				atendimentoDTO.setAtivo(true);
				this.atendimentoDAO.atualizar(this.atendimentoConversorDTO.converterDTOEmEntidade(atendimentoDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public AtendimentoDTO alterar(AtendimentoDTO atendimentoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		AtendimentoDTO atendimentoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.atendimentoAtributoValidador.validarAtributosEmEntidade(atendimentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Atendimento> atendimentosBusca = this.atendimentoDAO.buscarPorAgendamento(
					atendimentoDTO.getAgendamento().getIdAgendamento());
			Atendimento atendimentoBusca = atendimentosBusca.size() > 0 && atendimentosBusca.size() < 2 ? 
					atendimentosBusca.get(0) : null;
			
			if(atendimentoBusca != null)
			{
				atendimentoBusca = this.atendimentoConversorDTO.converterDTOEmEntidade(atendimentoDTO);
				this.atendimentoDAO.atualizar(atendimentoBusca);
				
				atendimentoRetornoDTO = atendimentoDTO;
			}
			else if(atendimentosBusca.size() > 1)
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
		
		return atendimentoRetornoDTO;
	}

	@Override
	public void inativar(Long idAtendimento, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Atendimento atendimentoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			atendimentoBusca = this.atendimentoDAO.buscar(idAtendimento);
			if(atendimentoBusca != null)
			{
				try
				{
					this.atendimentoDAO.remover(atendimentoBusca);
				}
				catch(Exception e)
				{
					atendimentoBusca.setAtivo(false);
					this.atendimentoDAO.atualizar(atendimentoBusca);
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
			atendimentoBusca = this.atendimentoDAO.buscar(idAtendimento);
			if(atendimentoBusca != null)
			{
				atendimentoBusca.setAtivo(false);
				this.atendimentoDAO.atualizar(atendimentoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public AtendimentoDTO buscarPorId(Long idAtendimento) {
		return this.atendimentoConversorDTO.converterEntidadeEmDTO(
				this.atendimentoDAO.buscar(idAtendimento)
				);
	}
	
	@Override
	public List<AtendimentoDTO> buscarPorAgendamento(Long idAgendamento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorAgendamento(idAgendamento)
				);
	}
	
	@Override
	public List<AtendimentoDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorData(dataI, dataF, idEstabelecimento)
				);
	}
	
	@Override
	public List<AtendimentoDTO> buscarPorPaciente(Long idPaciente, Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorPaciente(idPaciente, idEstabelecimento)
				);
	}
	
	@Override
	public List<AtendimentoDTO> buscarPorSituacao(String situacao, Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorSituacao(situacao, idEstabelecimento)
				);
	}
	
	@Override
	public List<AtendimentoDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorDentista(idDentista, idEstabelecimento)
				);
	}

	@Override
	public List<AtendimentoDTO> buscarAtivos(Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorCondicao(true, idEstabelecimento)
				);
	}

	@Override
	public List<AtendimentoDTO> buscarInativos(Long idEstabelecimento) {
		return this.atendimentoConversorDTO.converterListEntidadeEmListDTO(
				this.atendimentoDAO.buscarPorCondicao(false, idEstabelecimento)
				);
	}

	@Override
	public AtendimentoDTO getEntidadeFromList(List<AtendimentoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
