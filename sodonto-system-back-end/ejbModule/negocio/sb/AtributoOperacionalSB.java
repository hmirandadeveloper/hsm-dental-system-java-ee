package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IAtributoOperacionalFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.AtributoOperacionalDAO;
import dto.AtributoOperacionalDTO;
import dto.conversor.conversores.AtributoOperacionalConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.AtributoOperacionalAtributoValidador;
import entidade.operacional.AtributoOperacional;

@Singleton
@Remote(IAtributoOperacionalFachada.class)
public class AtributoOperacionalSB implements IAtributoOperacionalFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private AtributoOperacionalDAO atributoOperacionalDAO;
	
	private AtributoOperacionalConversorDTO atributoOperacionalConversorDTO;
	private AtributoOperacionalAtributoValidador atributoOperacionalAtributoValidador;
	
	public AtributoOperacionalSB()
	{
		this.atributoOperacionalConversorDTO = ConversorDTOFactory.getAtributoOperacionalConversorDTO();
		this.atributoOperacionalAtributoValidador = AtributoValidadorFactory.getAtributoOperacionalAtributoValidador();
	}
	
	@Override
	public void salvar(AtributoOperacionalDTO atributoOperacionalDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.atributoOperacionalAtributoValidador.validarAtributosEmEntidade(atributoOperacionalDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<AtributoOperacional> atributoOperacionalsBusca = this.atributoOperacionalDAO.buscarPorEstado(
					atributoOperacionalDTO.isAtivo());
			AtributoOperacional atributoOperacionalBusca = atributoOperacionalsBusca.size() > 0 && atributoOperacionalsBusca.size() < 2 ? 
					atributoOperacionalsBusca.get(0) : null;

			if(atributoOperacionalBusca != null)
			{
				if(!atributoOperacionalBusca.isAtivo())
				{
					atributoOperacionalDTO.setIdAtributoOperacional(atributoOperacionalBusca.getIdAtributoOperacional());
					atributoOperacionalDTO.setAtivo(true);
					this.atributoOperacionalDAO.atualizar(this.atributoOperacionalConversorDTO.converterDTOEmEntidade(atributoOperacionalDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				atributoOperacionalDTO.setAtivo(true);
				this.atributoOperacionalDAO.salvar(this.atributoOperacionalConversorDTO.converterDTOEmEntidade(atributoOperacionalDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public AtributoOperacionalDTO alterar(AtributoOperacionalDTO atributoOperacionalDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		AtributoOperacionalDTO atributoOperacionalRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.atributoOperacionalAtributoValidador.validarAtributosEmEntidade(atributoOperacionalDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<AtributoOperacional> atributoOperacionalsBusca = this.atributoOperacionalDAO.buscarPorEstado(
					atributoOperacionalDTO.isAtivo());
			AtributoOperacional atributoOperacionalBusca = atributoOperacionalsBusca.size() > 0 && atributoOperacionalsBusca.size() < 2 ? 
					atributoOperacionalsBusca.get(0) : null;
			
			if(atributoOperacionalBusca != null || ( atributoOperacionalDTO.getIdAtributoOperacional() != null 
					&& atributoOperacionalBusca == null))
			{
				atributoOperacionalBusca = this.atributoOperacionalConversorDTO.converterDTOEmEntidade(atributoOperacionalDTO);
				this.atributoOperacionalDAO.atualizar(atributoOperacionalBusca);
				
				atributoOperacionalRetornoDTO = atributoOperacionalDTO;
			}
			else if(atributoOperacionalsBusca.size() > 1)
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
		
		return atributoOperacionalRetornoDTO;
	}

	@Override
	public void inativar(Long idAtributoOperacional, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		AtributoOperacional atributoOperacionalBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			atributoOperacionalBusca = this.atributoOperacionalDAO.buscar(idAtributoOperacional);
			if(atributoOperacionalBusca != null)
			{
				try
				{
					this.atributoOperacionalDAO.remover(atributoOperacionalBusca);
				}
				catch(Exception e)
				{
					atributoOperacionalBusca.setAtivo(false);
					this.atributoOperacionalDAO.atualizar(atributoOperacionalBusca);
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
			atributoOperacionalBusca = this.atributoOperacionalDAO.buscar(idAtributoOperacional);
			if(atributoOperacionalBusca != null)
			{
				atributoOperacionalBusca.setAtivo(false);
				this.atributoOperacionalDAO.atualizar(atributoOperacionalBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public AtributoOperacionalDTO buscarPorId(Long idAtributoOperacional) {
		return this.atributoOperacionalConversorDTO.converterEntidadeEmDTO(
				this.atributoOperacionalDAO.buscar(idAtributoOperacional)
				);
	}
	
	@Override
	public List<AtributoOperacionalDTO> buscarPorEstado(boolean estado) {
		return this.atributoOperacionalConversorDTO.converterListEntidadeEmListDTO(
				this.atributoOperacionalDAO.buscarPorEstado(estado)
				);
	}

	@Override
	public AtributoOperacionalDTO buscarAtributoSelecionado() {
		System.out.println("*** ### *** | 02");
		return this.atributoOperacionalConversorDTO.converterEntidadeEmDTO(
				this.atributoOperacionalDAO.buscarAtributoSelecionado());
	}

}
