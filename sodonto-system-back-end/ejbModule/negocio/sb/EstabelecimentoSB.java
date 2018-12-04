package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CnpjInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IEstabelecimentoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.EstabelecimentoDAO;
import dto.EstabelecimentoDTO;
import dto.conversor.conversores.EstabelecimentoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.EstabelecimentoAtributoValidador;
import dto.validador.validadores.util.ValidadorAtributosEspeciais;
import entidade.Estabelecimento;

@Stateless
@Remote(IEstabelecimentoFachada.class)
public class EstabelecimentoSB implements IEstabelecimentoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private EstabelecimentoDAO estabelecimentoDAO;
	
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	private EstabelecimentoAtributoValidador estabelecimentoAtributoValidador;
	
	public EstabelecimentoSB()
	{
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
		this.estabelecimentoAtributoValidador = AtributoValidadorFactory.getEstabelecimentoAtributoValidador();
	}
	
	@Override
	public void salvar(EstabelecimentoDTO estabelecimentoDTO) throws EntidadeCadastradaException,
			AtributoIncompletoException, CnpjInvalidoException {
		GerenciadorAtributo gerenciadorAtributos = this.estabelecimentoAtributoValidador.validarAtributosEmEntidade(estabelecimentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			if(ValidadorAtributosEspeciais.validarCnpj(estabelecimentoDTO.getCnpj()))
			{
				List<Estabelecimento> estabelecimentosBusca = this.estabelecimentoDAO.buscarPorCnpj(
						estabelecimentoDTO.getCnpj());
				Estabelecimento estabelecimentoBusca = estabelecimentosBusca.size() > 0 && estabelecimentosBusca.size() < 2 ? 
						estabelecimentosBusca.get(0) : null;
				
				if(estabelecimentoBusca != null)
				{
					if(!estabelecimentoBusca.isAtivo())
					{
						estabelecimentoDTO.setIdEstabelecimento(estabelecimentoBusca.getIdEstabelecimento());
						estabelecimentoDTO.setAtivo(true);
						estabelecimentoDTO.getUsuarioDTO().setAtivo(true);
						this.estabelecimentoDAO.atualizar(this.estabelecimentoConversorDTO.converterDTOEmEntidade(estabelecimentoDTO));
					}
					else
					{
						throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
					}
				}
				else
				{
					estabelecimentoDTO.setAtivo(true);
					estabelecimentoDTO.getUsuarioDTO().setAtivo(true);
					this.estabelecimentoDAO.salvar(this.estabelecimentoConversorDTO.converterDTOEmEntidade(estabelecimentoDTO));
				}
			}
			else
			{
				throw new CnpjInvalidoException(MensagemErro.MSG_ERRO_CNPJ_INVALIDO);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
	}
	
	@Override
	public EstabelecimentoDTO alterar(EstabelecimentoDTO estabelecimentoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		EstabelecimentoDTO estabelecimentoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.estabelecimentoAtributoValidador.validarAtributosEmEntidade(estabelecimentoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Estabelecimento> estabelecimentosBusca = this.estabelecimentoDAO.buscarPorCnpj(
					estabelecimentoDTO.getCnpj());
			Estabelecimento estabelecimentoBusca = estabelecimentosBusca.size() > 0 && estabelecimentosBusca.size() < 2 ? 
					estabelecimentosBusca.get(0) : null;
			
			if(estabelecimentoBusca != null)
			{
				estabelecimentoBusca = this.estabelecimentoConversorDTO.converterDTOEmEntidade(estabelecimentoDTO);
				this.estabelecimentoDAO.atualizar(estabelecimentoBusca);
				
				estabelecimentoRetornoDTO = estabelecimentoDTO;
			}
			else if(estabelecimentosBusca.size() > 1)
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
		
		return estabelecimentoRetornoDTO;
	}

	@Override
	public void inativar(Long idEstabelecimento, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Estabelecimento estabelecimentoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			estabelecimentoBusca = this.estabelecimentoDAO.buscar(idEstabelecimento);
			if(estabelecimentoBusca != null)
			{
				try
				{
					this.estabelecimentoDAO.remover(estabelecimentoBusca);
				}
				catch(Exception e)
				{
					estabelecimentoBusca.setAtivo(false);
					this.estabelecimentoDAO.atualizar(estabelecimentoBusca);
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
			estabelecimentoBusca = this.estabelecimentoDAO.buscar(idEstabelecimento);
			if(estabelecimentoBusca != null)
			{
				estabelecimentoBusca.setAtivo(false);
				this.estabelecimentoDAO.atualizar(estabelecimentoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public EstabelecimentoDTO buscarPorId(Long idEstabelecimento) {
		return this.estabelecimentoConversorDTO.converterEntidadeEmDTO(
				this.estabelecimentoDAO.buscar(idEstabelecimento)
				);
	}

	@Override
	public List<EstabelecimentoDTO> buscarPorCnpj(String cnpj) {
		return this.estabelecimentoConversorDTO.converterListEntidadeEmListDTO(
				this.estabelecimentoDAO.buscarPorCnpj(cnpj)
				);
	}
	
	@Override
	public List<EstabelecimentoDTO> buscarPorNome(String nome) {
		return this.estabelecimentoConversorDTO.converterListEntidadeEmListDTO(
				this.estabelecimentoDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<EstabelecimentoDTO> buscarAtivos() {
		return this.estabelecimentoConversorDTO.converterListEntidadeEmListDTO(
				this.estabelecimentoDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<EstabelecimentoDTO> buscarInativos() {
		return this.estabelecimentoConversorDTO.converterListEntidadeEmListDTO(
				this.estabelecimentoDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public EstabelecimentoDTO getEntidadeFromList(List<EstabelecimentoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
