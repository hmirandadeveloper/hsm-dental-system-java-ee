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
import negocio.exception.DataInvalidaException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IDentistaAgendaFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.DentistaAgendaDAO;
import dto.DentistaAgendaDTO;
import dto.conversor.conversores.DentistaAgendaConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.DentistaAgendaAtributoValidador;
import entidade.DentistaAgenda;

@Stateless
@Remote(IDentistaAgendaFachada.class)
public class DentistaAgendaSB implements IDentistaAgendaFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private DentistaAgendaDAO dentistaAgendaDAO;
	
	private DentistaAgendaConversorDTO dentistaAgendaConversorDTO;
	private DentistaAgendaAtributoValidador dentistaAgendaAtributoValidador;
	
	public DentistaAgendaSB()
	{
		this.dentistaAgendaConversorDTO = ConversorDTOFactory.getDentistaAgendaConversorDTO();
		this.dentistaAgendaAtributoValidador = AtributoValidadorFactory.getDentistaAgendaAtributoValidador();
	}
	
	@Override
	public void salvar(DentistaAgendaDTO dentistaAgendaDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException, DataInvalidaException{
		GerenciadorAtributo gerenciadorAtributos = this.dentistaAgendaAtributoValidador.validarAtributosEmEntidade(dentistaAgendaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			if(dentistaAgendaDTO.getHorarioF().getTime() > dentistaAgendaDTO.getHorarioI().getTime())
			{
			List<DentistaAgenda> dentistaAgendasBusca = this.dentistaAgendaDAO.buscarPorDentistaDataEHorario(
					dentistaAgendaDTO.getDentista().getIdDentista(), dentistaAgendaDTO.getDataAgenda(),
					dentistaAgendaDTO.getHorarioI(), dentistaAgendaDTO.getHorarioF(), dentistaAgendaDTO.getEstabelecimentoDTO().getIdEstabelecimento());
			DentistaAgenda dentistaAgendaBusca = dentistaAgendasBusca.size() > 0 && dentistaAgendasBusca.size() < 2 ? 
					dentistaAgendasBusca.get(0) : null;

			if(dentistaAgendaBusca != null)
			{
				if(!dentistaAgendaBusca.isAtivo())
				{
					dentistaAgendaDTO.setIdDentistaAgenda(dentistaAgendaBusca.getIdDentistaAgenda());
					dentistaAgendaDTO.setAtivo(true);
					this.dentistaAgendaDAO.atualizar(this.dentistaAgendaConversorDTO.converterDTOEmEntidade(dentistaAgendaDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				dentistaAgendaDTO.setAtivo(true);
				this.dentistaAgendaDAO.salvar(this.dentistaAgendaConversorDTO.converterDTOEmEntidade(dentistaAgendaDTO));
			}
			}
			else
			{
				throw new DataInvalidaException(MensagemErro.MSG_ERRO_DATA_INVALIDA);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public DentistaAgendaDTO alterar(DentistaAgendaDTO dentistaAgendaDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		DentistaAgendaDTO dentistaAgendaRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.dentistaAgendaAtributoValidador.validarAtributosEmEntidade(dentistaAgendaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<DentistaAgenda> dentistaAgendasBusca = this.dentistaAgendaDAO.buscarPorDentistaDataEHorario(
					dentistaAgendaDTO.getDentista().getIdDentista(), dentistaAgendaDTO.getDataAgenda(),
					dentistaAgendaDTO.getHorarioI(), dentistaAgendaDTO.getHorarioF(), dentistaAgendaDTO.getEstabelecimentoDTO().getIdEstabelecimento());
			DentistaAgenda dentistaAgendaBusca = dentistaAgendasBusca.size() > 0 && dentistaAgendasBusca.size() < 2 ? 
					dentistaAgendasBusca.get(0) : null;
			
			if(dentistaAgendaBusca != null)
			{
				dentistaAgendaBusca = this.dentistaAgendaConversorDTO.converterDTOEmEntidade(dentistaAgendaDTO);
				this.dentistaAgendaDAO.atualizar(dentistaAgendaBusca);
				
				dentistaAgendaRetornoDTO = dentistaAgendaDTO;
			}
			else if(dentistaAgendasBusca.size() > 1)
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
		
		return dentistaAgendaRetornoDTO;
	}

	@Override
	public void inativar(Long idDentistaAgenda, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		DentistaAgenda dentistaAgendaBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			dentistaAgendaBusca = this.dentistaAgendaDAO.buscar(idDentistaAgenda);
			if(dentistaAgendaBusca != null)
			{
				try
				{
					this.dentistaAgendaDAO.remover(dentistaAgendaBusca);
				}
				catch(Exception e)
				{
					dentistaAgendaBusca.setAtivo(false);
					this.dentistaAgendaDAO.atualizar(dentistaAgendaBusca);
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
			dentistaAgendaBusca = this.dentistaAgendaDAO.buscar(idDentistaAgenda);
			if(dentistaAgendaBusca != null)
			{
				dentistaAgendaBusca.setAtivo(false);
				this.dentistaAgendaDAO.atualizar(dentistaAgendaBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public DentistaAgendaDTO buscarPorId(Long idDentistaAgenda) {
		return this.dentistaAgendaConversorDTO.converterEntidadeEmDTO(
				this.dentistaAgendaDAO.buscar(idDentistaAgenda)
				);
	}
	
	@Override
	public List<DentistaAgendaDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorData(dataI, dataF, idEstabelecimento)
				);
	}
	
	@Override
	public List<DentistaAgendaDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorDentista(idDentista, idEstabelecimento)
				);
	}
	
	@Override
	public List<DentistaAgendaDTO> buscarPorDentistaEData(Long idDentista, Date data, Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorDentistaEData(idDentista, data, idEstabelecimento)
				);
	}
	
	@Override
	public List<DentistaAgendaDTO> buscarPorDentistaDataEHorario(Long idDentista, Date data, Date horarioI, Date horarioF, Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorDentistaDataEHorario(idDentista, data, horarioI, horarioF, idEstabelecimento)
				);
	}

	@Override
	public List<DentistaAgendaDTO> buscarAtivos(Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorCondicao(true, idEstabelecimento)
				);
	}

	@Override
	public List<DentistaAgendaDTO> buscarInativos(Long idEstabelecimento) {
		return this.dentistaAgendaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaAgendaDAO.buscarPorCondicao(false, idEstabelecimento)
				);
	}

	@Override
	public DentistaAgendaDTO getEntidadeFromList(List<DentistaAgendaDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
