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
import negocio.fachada.local.IOperadoraFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.OperadoraDAO;
import dto.OperadoraDTO;
import dto.conversor.conversores.OperadoraConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.OperadoraAtributoValidador;
import entidade.Operadora;

@Stateless
@Remote(IOperadoraFachada.class)
public class OperadoraSB implements IOperadoraFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private OperadoraDAO operadoraDAO;
	
	private OperadoraConversorDTO operadoraConversorDTO;
	private OperadoraAtributoValidador operadoraAtributoValidador;
	
	public OperadoraSB()
	{
		this.operadoraConversorDTO = ConversorDTOFactory.getOperadoraConversorDTO();
		this.operadoraAtributoValidador = AtributoValidadorFactory.getOperadoraAtributoValidador();
	}
	
	@Override
	public void salvar(OperadoraDTO operadoraDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.operadoraAtributoValidador.validarAtributosEmEntidade(operadoraDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Operadora> operadorasBusca = this.operadoraDAO.buscarPorNome(
					operadoraDTO.getNomeOperadora());
			Operadora operadoraBusca = operadorasBusca.size() > 0 && operadorasBusca.size() < 2 ? 
					operadorasBusca.get(0) : null;

			if(operadoraBusca != null || ( operadoraDTO.getIdOperadora() != null 
					&& operadoraBusca == null))
			{
				if(!operadoraBusca.isAtivo())
				{
					operadoraDTO.setIdOperadora(operadoraBusca.getIdOperadora());
					operadoraDTO.setAtivo(true);
					this.operadoraDAO.atualizar(this.operadoraConversorDTO.converterDTOEmEntidade(operadoraDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				operadoraDTO.setAtivo(true);
				this.operadoraDAO.salvar(this.operadoraConversorDTO.converterDTOEmEntidade(operadoraDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public OperadoraDTO alterar(OperadoraDTO operadoraDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		OperadoraDTO operadoraRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.operadoraAtributoValidador.validarAtributosEmEntidade(operadoraDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Operadora> operadorasBusca = this.operadoraDAO.buscarPorNome(
					operadoraDTO.getNomeOperadora());
			Operadora operadoraBusca = operadorasBusca.size() > 0 && operadorasBusca.size() < 2 ? 
					operadorasBusca.get(0) : null;
			
			if(operadoraBusca != null)
			{
				operadoraBusca = this.operadoraConversorDTO.converterDTOEmEntidade(operadoraDTO);
				this.operadoraDAO.atualizar(operadoraBusca);
				
				operadoraRetornoDTO = operadoraDTO;
			}
			else if(operadorasBusca.size() > 1)
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
		
		return operadoraRetornoDTO;
	}

	@Override
	public void inativar(Long idOperadora, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Operadora operadoraBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			operadoraBusca = this.operadoraDAO.buscar(idOperadora);
			if(operadoraBusca != null)
			{
				try
				{
					this.operadoraDAO.remover(operadoraBusca);
				}
				catch(Exception e)
				{
					operadoraBusca.setAtivo(false);
					this.operadoraDAO.atualizar(operadoraBusca);
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
			operadoraBusca = this.operadoraDAO.buscar(idOperadora);
			if(operadoraBusca != null)
			{
				operadoraBusca.setAtivo(false);
				this.operadoraDAO.atualizar(operadoraBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public OperadoraDTO buscarPorId(Long idOperadora) {
		return this.operadoraConversorDTO.converterEntidadeEmDTO(
				this.operadoraDAO.buscar(idOperadora)
				);
	}
	
	@Override
	public List<OperadoraDTO> buscarPorNome(String nome) {
		return this.operadoraConversorDTO.converterListEntidadeEmListDTO(
				this.operadoraDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<OperadoraDTO> buscarAtivos() {
		return this.operadoraConversorDTO.converterListEntidadeEmListDTO(
				this.operadoraDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<OperadoraDTO> buscarInativos() {
		return this.operadoraConversorDTO.converterListEntidadeEmListDTO(
				this.operadoraDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public OperadoraDTO getEntidadeFromList(List<OperadoraDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
