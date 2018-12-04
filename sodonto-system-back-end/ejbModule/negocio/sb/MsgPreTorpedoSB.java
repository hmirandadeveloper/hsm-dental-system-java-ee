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
import negocio.fachada.local.IMsgPreTorpedoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.MsgPreTorpedoDAO;
import dto.MsgPreTorpedoDTO;
import dto.conversor.conversores.MsgPreTorpedoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.MsgPreTorpedoAtributoValidador;
import entidade.MsgPreTorpedo;

@Stateless
@Remote(IMsgPreTorpedoFachada.class)
public class MsgPreTorpedoSB implements IMsgPreTorpedoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private MsgPreTorpedoDAO msgPreTorpedoDAO;
	
	private MsgPreTorpedoConversorDTO msgPreTorpedoConversorDTO;
	private MsgPreTorpedoAtributoValidador msgPreTorpedoAtributoValidador;
	
	public MsgPreTorpedoSB()
	{
		this.msgPreTorpedoConversorDTO = ConversorDTOFactory.getMsgPreTorpedoConversorDTO();
		this.msgPreTorpedoAtributoValidador = AtributoValidadorFactory.getMsgPreTorpedoAtributoValidador();
	}
	
	@Override
	public void salvar(MsgPreTorpedoDTO msgPreTorpedoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.msgPreTorpedoAtributoValidador.validarAtributosEmEntidade(msgPreTorpedoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MsgPreTorpedo> msgPreTorpedosBusca = this.msgPreTorpedoDAO.buscarPorDescricao(
					msgPreTorpedoDTO.getDescricao());
			MsgPreTorpedo msgPreTorpedoBusca = msgPreTorpedosBusca.size() > 0 && msgPreTorpedosBusca.size() < 2 ? 
					msgPreTorpedosBusca.get(0) : null;

			if(msgPreTorpedoBusca != null)
			{
				if(!msgPreTorpedoBusca.isAtivo())
				{
					msgPreTorpedoDTO.setIdMsgPreTorpedo(msgPreTorpedoBusca.getIdMsgPreTorpedo());
					msgPreTorpedoDTO.setAtivo(true);
					this.msgPreTorpedoDAO.atualizar(this.msgPreTorpedoConversorDTO.converterDTOEmEntidade(msgPreTorpedoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				msgPreTorpedoDTO.setAtivo(true);
				this.msgPreTorpedoDAO.salvar(this.msgPreTorpedoConversorDTO.converterDTOEmEntidade(msgPreTorpedoDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public MsgPreTorpedoDTO alterar(MsgPreTorpedoDTO msgPreTorpedoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		MsgPreTorpedoDTO msgPreTorpedoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.msgPreTorpedoAtributoValidador.validarAtributosEmEntidade(msgPreTorpedoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MsgPreTorpedo> msgPreTorpedosBusca = this.msgPreTorpedoDAO.buscarPorDescricao(
					msgPreTorpedoDTO.getDescricao());
			MsgPreTorpedo msgPreTorpedoBusca = msgPreTorpedosBusca.size() > 0 && msgPreTorpedosBusca.size() < 2 ? 
					msgPreTorpedosBusca.get(0) : null;
			
			if(msgPreTorpedoBusca != null || ( msgPreTorpedoDTO.getIdMsgPreTorpedo() != null 
					&& msgPreTorpedoBusca == null))
			{
				msgPreTorpedoBusca = this.msgPreTorpedoConversorDTO.converterDTOEmEntidade(msgPreTorpedoDTO);
				this.msgPreTorpedoDAO.atualizar(msgPreTorpedoBusca);
				
				msgPreTorpedoRetornoDTO = msgPreTorpedoDTO;
			}
			else if(msgPreTorpedosBusca.size() > 1)
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
		
		return msgPreTorpedoRetornoDTO;
	}

	@Override
	public void inativar(Long idMsgPreTorpedo, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		MsgPreTorpedo msgPreTorpedoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			msgPreTorpedoBusca = this.msgPreTorpedoDAO.buscar(idMsgPreTorpedo);
			if(msgPreTorpedoBusca != null)
			{
				try
				{
					this.msgPreTorpedoDAO.remover(msgPreTorpedoBusca);
				}
				catch(Exception e)
				{
					msgPreTorpedoBusca.setAtivo(false);
					this.msgPreTorpedoDAO.atualizar(msgPreTorpedoBusca);
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
			msgPreTorpedoBusca = this.msgPreTorpedoDAO.buscar(idMsgPreTorpedo);
			if(msgPreTorpedoBusca != null)
			{
				msgPreTorpedoBusca.setAtivo(false);
				this.msgPreTorpedoDAO.atualizar(msgPreTorpedoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public MsgPreTorpedoDTO buscarPorId(Long idMsgPreTorpedo) {
		return this.msgPreTorpedoConversorDTO.converterEntidadeEmDTO(
				this.msgPreTorpedoDAO.buscar(idMsgPreTorpedo)
				);
	}
	
	@Override
	public List<MsgPreTorpedoDTO> buscarPorDescricao(String descricao) {
		return this.msgPreTorpedoConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreTorpedoDAO.buscarPorDescricao(descricao)
				);
	}

	@Override
	public List<MsgPreTorpedoDTO> buscarAtivos() {
		return this.msgPreTorpedoConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreTorpedoDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<MsgPreTorpedoDTO> buscarInativos() {
		return this.msgPreTorpedoConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreTorpedoDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public MsgPreTorpedoDTO getEntidadeFromList(List<MsgPreTorpedoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
