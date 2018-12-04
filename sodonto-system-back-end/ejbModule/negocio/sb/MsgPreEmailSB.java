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
import negocio.fachada.local.IMsgPreEmailFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.MsgPreEmailDAO;
import dto.MsgPreEmailDTO;
import dto.conversor.conversores.MsgPreEmailConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.MsgPreEmailAtributoValidador;
import entidade.MsgPreEmail;

@Stateless
@Remote(IMsgPreEmailFachada.class)
public class MsgPreEmailSB implements IMsgPreEmailFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private MsgPreEmailDAO msgPreEmailDAO;
	
	private MsgPreEmailConversorDTO msgPreEmailConversorDTO;
	private MsgPreEmailAtributoValidador msgPreEmailAtributoValidador;
	
	public MsgPreEmailSB()
	{
		this.msgPreEmailConversorDTO = ConversorDTOFactory.getMsgPreEmailConversorDTO();
		this.msgPreEmailAtributoValidador = AtributoValidadorFactory.getMsgPreEmailAtributoValidador();
	}
	
	@Override
	public void salvar(MsgPreEmailDTO msgPreEmailDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.msgPreEmailAtributoValidador.validarAtributosEmEntidade(msgPreEmailDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MsgPreEmail> msgPreEmailsBusca = this.msgPreEmailDAO.buscarPorDescricao(
					msgPreEmailDTO.getDescicao());
			MsgPreEmail msgPreEmailBusca = msgPreEmailsBusca.size() > 0 && msgPreEmailsBusca.size() < 2 ? 
					msgPreEmailsBusca.get(0) : null;

			if(msgPreEmailBusca != null)
			{
				if(!msgPreEmailBusca.isAtivo())
				{
					msgPreEmailDTO.setIdMsgPreEmail(msgPreEmailBusca.getIdMsgPreEmail());
					msgPreEmailDTO.setAtivo(true);
					this.msgPreEmailDAO.atualizar(this.msgPreEmailConversorDTO.converterDTOEmEntidade(msgPreEmailDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				msgPreEmailDTO.setAtivo(true);
				this.msgPreEmailDAO.salvar(this.msgPreEmailConversorDTO.converterDTOEmEntidade(msgPreEmailDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public MsgPreEmailDTO alterar(MsgPreEmailDTO msgPreEmailDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		MsgPreEmailDTO msgPreEmailRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.msgPreEmailAtributoValidador.validarAtributosEmEntidade(msgPreEmailDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<MsgPreEmail> msgPreEmailsBusca = this.msgPreEmailDAO.buscarPorDescricao(
					msgPreEmailDTO.getDescicao());
			MsgPreEmail msgPreEmailBusca = msgPreEmailsBusca.size() > 0 && msgPreEmailsBusca.size() < 2 ? 
					msgPreEmailsBusca.get(0) : null;
			
			if(msgPreEmailBusca != null || ( msgPreEmailDTO.getIdMsgPreEmail() != null 
					&& msgPreEmailBusca == null))
			{
				msgPreEmailBusca = this.msgPreEmailConversorDTO.converterDTOEmEntidade(msgPreEmailDTO);
				this.msgPreEmailDAO.atualizar(msgPreEmailBusca);
				
				msgPreEmailRetornoDTO = msgPreEmailDTO;
			}
			else if(msgPreEmailsBusca.size() > 1)
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
		
		return msgPreEmailRetornoDTO;
	}

	@Override
	public void inativar(Long idMsgPreEmail, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		MsgPreEmail msgPreEmailBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			msgPreEmailBusca = this.msgPreEmailDAO.buscar(idMsgPreEmail);
			if(msgPreEmailBusca != null)
			{
				try
				{
					this.msgPreEmailDAO.remover(msgPreEmailBusca);
				}
				catch(Exception e)
				{
					msgPreEmailBusca.setAtivo(false);
					this.msgPreEmailDAO.atualizar(msgPreEmailBusca);
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
			msgPreEmailBusca = this.msgPreEmailDAO.buscar(idMsgPreEmail);
			if(msgPreEmailBusca != null)
			{
				msgPreEmailBusca.setAtivo(false);
				this.msgPreEmailDAO.atualizar(msgPreEmailBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public MsgPreEmailDTO buscarPorId(Long idMsgPreEmail) {
		return this.msgPreEmailConversorDTO.converterEntidadeEmDTO(
				this.msgPreEmailDAO.buscar(idMsgPreEmail)
				);
	}
	
	@Override
	public List<MsgPreEmailDTO> buscarPorDescricao(String descricao) {
		return this.msgPreEmailConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreEmailDAO.buscarPorDescricao(descricao)
				);
	}

	@Override
	public List<MsgPreEmailDTO> buscarAtivos() {
		return this.msgPreEmailConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreEmailDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<MsgPreEmailDTO> buscarInativos() {
		return this.msgPreEmailConversorDTO.converterListEntidadeEmListDTO(
				this.msgPreEmailDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public MsgPreEmailDTO getEntidadeFromList(List<MsgPreEmailDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
