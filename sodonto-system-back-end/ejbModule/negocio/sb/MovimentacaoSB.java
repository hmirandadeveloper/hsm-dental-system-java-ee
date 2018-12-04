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
import negocio.fachada.local.IMovimentacaoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.CaixaDAO;
import persistencia.dao.MovimentacaoDAO;
import dto.MovimentacaoDTO;
import dto.conversor.conversores.CaixaConversorDTO;
import dto.conversor.conversores.MovimentacaoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.MovimentacaoAtributoValidador;
import entidade.Caixa;
import entidade.Movimentacao;

@Stateless
@Remote(IMovimentacaoFachada.class)
public class MovimentacaoSB implements IMovimentacaoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private MovimentacaoDAO movimentacaoDAO;
	@EJB
	private CaixaDAO caixaDAO;
	
	private MovimentacaoConversorDTO movimentacaoConversorDTO;
	private CaixaConversorDTO caixaConversorDTO;
	private MovimentacaoAtributoValidador movimentacaoAtributoValidador;
	
	public MovimentacaoSB()
	{
		this.movimentacaoConversorDTO = ConversorDTOFactory.getMovimentacaoConversorDTO();
		this.caixaConversorDTO = ConversorDTOFactory.getCaixaConversorDTO();
		this.movimentacaoAtributoValidador = AtributoValidadorFactory.getMovimentacaoAtributoValidador();
	}
	
	@Override
	public void salvar(MovimentacaoDTO movimentacaoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.movimentacaoAtributoValidador.validarAtributosEmEntidade(movimentacaoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Movimentacao> movimentacaosBusca = this.movimentacaoDAO.buscarPorRefMovimentacaoCaixaEData(
					movimentacaoDTO.getRefMovimentacao(), movimentacaoDTO.getCaixa().getIdCaixa(), new Date());
			Movimentacao movimentacaoBusca = movimentacaosBusca.size() > 0 && movimentacaosBusca.size() < 2 ? 
					movimentacaosBusca.get(0) : null;

			if(movimentacaoBusca != null)
			{
				if(!movimentacaoBusca.getAtivo())
				{
					movimentacaoDTO.setIdMovimentacao(movimentacaoBusca.getIdMovimentacao());
					movimentacaoDTO.setAtivo(true);
					System.out.println("[SODONTO SYSTEM][SB] Iniciando persistência, acessando Camada DAO...");
					this.movimentacaoDAO.atualizar(this.movimentacaoConversorDTO.converterDTOEmEntidade(movimentacaoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				movimentacaoDTO.setAtivo(true);
				this.movimentacaoDAO.atualizar(this.movimentacaoConversorDTO.converterDTOEmEntidade(movimentacaoDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public MovimentacaoDTO alterar(MovimentacaoDTO movimentacaoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		MovimentacaoDTO movimentacaoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.movimentacaoAtributoValidador.validarAtributosEmEntidade(movimentacaoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Movimentacao> movimentacaosBusca = this.movimentacaoDAO.buscarPorRefMovimentacaoCaixaEData(
					movimentacaoDTO.getRefMovimentacao(), movimentacaoDTO.getCaixa().getIdCaixa(), new Date());
			Movimentacao movimentacaoBusca = movimentacaosBusca.size() > 0 && movimentacaosBusca.size() < 2 ? 
					movimentacaosBusca.get(0) : null;
			
			if(movimentacaoBusca != null)
			{
				movimentacaoBusca = this.movimentacaoConversorDTO.converterDTOEmEntidade(movimentacaoDTO);
				this.movimentacaoDAO.atualizar(movimentacaoBusca);
				
				movimentacaoRetornoDTO = movimentacaoDTO;
			}
			else if(movimentacaosBusca.size() > 1)
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
		
		return movimentacaoRetornoDTO;
	}

	@Override
	public void inativar(MovimentacaoDTO movimentacaoDTO, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Movimentacao movimentacaoBusca = null;
		Caixa caixaAtualizacao = this.caixaConversorDTO.converterDTOEmEntidade(movimentacaoDTO.getCaixa());
		
		if(perfilUsuario == EPerfilUsuario.A || 
				perfilUsuario == EPerfilUsuario.G)
		{
			movimentacaoBusca = this.movimentacaoDAO.buscar(movimentacaoDTO.getIdMovimentacao());
			if(movimentacaoBusca != null)
			{
				try
				{
					this.caixaDAO.atualizar(caixaAtualizacao);
					this.movimentacaoDAO.remover(movimentacaoBusca);
				}
				catch(Exception e)
				{
					movimentacaoBusca.setAtivo(false);
					this.movimentacaoDAO.atualizar(movimentacaoBusca);
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
	}

	@Override
	public MovimentacaoDTO buscarPorId(Long idMovimentacao) {
		return this.movimentacaoConversorDTO.converterEntidadeEmDTO(
				this.movimentacaoDAO.buscar(idMovimentacao)
				);
	}
	
	@Override
	public List<MovimentacaoDTO> buscarPorRefMovimentacao(String refMovimentacao) {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorRefMovimentacao(refMovimentacao)
				);
	}
	
	@Override
	public List<MovimentacaoDTO> buscarPorRefMovimentacaoCaixaEData(String refMovimentacao, Long idCaixa, Date data) {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorRefMovimentacaoCaixaEData(refMovimentacao, idCaixa, data)
				);
	}
	
	@Override
	public List<MovimentacaoDTO> buscarPorTipo(String tipo) {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorTipo(tipo)
				);
	}
	
	@Override
	public List<MovimentacaoDTO> buscarPorCaixa(Long idCaixa) {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorCaixa(idCaixa)
				);
	}

	@Override
	public List<MovimentacaoDTO> buscarAtivos() {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<MovimentacaoDTO> buscarInativos() {
		return this.movimentacaoConversorDTO.converterListEntidadeEmListDTO(
				this.movimentacaoDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public MovimentacaoDTO getEntidadeFromList(List<MovimentacaoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
