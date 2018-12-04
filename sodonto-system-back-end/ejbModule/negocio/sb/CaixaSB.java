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
import negocio.fachada.local.ICaixaFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.CaixaDAO;
import dto.CaixaDTO;
import dto.conversor.conversores.CaixaConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.CaixaAtributoValidador;
import entidade.Caixa;

@Stateless
@Remote(ICaixaFachada.class)
public class CaixaSB implements ICaixaFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private CaixaDAO caixaDAO;
	
	private CaixaConversorDTO caixaConversorDTO;
	private CaixaAtributoValidador caixaAtributoValidador;
	
	public CaixaSB()
	{
		this.caixaConversorDTO = ConversorDTOFactory.getCaixaConversorDTO();
		this.caixaAtributoValidador = AtributoValidadorFactory.getCaixaAtributoValidador();
	}
	
	@Override
	public void salvar(CaixaDTO caixaDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.caixaAtributoValidador.validarAtributosEmEntidade(caixaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Caixa> caixasBusca = this.caixaDAO.buscarPorDataEOrdem(
					caixaDTO.getDataCaixa(), caixaDTO.getNumeroOrdem());
			Caixa caixaBusca = caixasBusca.size() > 0 && caixasBusca.size() < 2 ? 
					caixasBusca.get(0) : null;

			if(caixaBusca != null)
			{
				if(!caixaBusca.isAtivo())
				{
					caixaDTO.setIdCaixa(caixaBusca.getIdCaixa());
					caixaDTO.setAtivo(true);
					this.caixaDAO.atualizar(this.caixaConversorDTO.converterDTOEmEntidade(caixaDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				caixaDTO.setAtivo(true);
				this.caixaDAO.salvar(this.caixaConversorDTO.converterDTOEmEntidade(caixaDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public CaixaDTO alterar(CaixaDTO caixaDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		CaixaDTO caixaRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.caixaAtributoValidador.validarAtributosEmEntidade(caixaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Caixa> caixasBusca = this.caixaDAO.buscarPorDataEOrdem(
					caixaDTO.getDataCaixa(), caixaDTO.getNumeroOrdem());
			Caixa caixaBusca = caixasBusca.size() > 0 && caixasBusca.size() < 2 ? 
					caixasBusca.get(0) : null;
			
			if(caixaBusca != null || ( caixaDTO.getIdCaixa() != null 
					&& caixaBusca == null))
			{
				caixaBusca = this.caixaConversorDTO.converterDTOEmEntidade(caixaDTO);
				this.caixaDAO.atualizar(caixaBusca);
				
				caixaRetornoDTO = caixaDTO;
			}
			else if(caixasBusca.size() > 1)
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
		
		return caixaRetornoDTO;
	}

	@Override
	public void inativar(Long idCaixa, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Caixa caixaBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			caixaBusca = this.caixaDAO.buscar(idCaixa);
			if(caixaBusca != null)
			{
				try
				{
					this.caixaDAO.remover(caixaBusca);
				}
				catch(Exception e)
				{
					caixaBusca.setAtivo(false);
					this.caixaDAO.atualizar(caixaBusca);
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
			caixaBusca = this.caixaDAO.buscar(idCaixa);
			if(caixaBusca != null)
			{
				caixaBusca.setAtivo(false);
				this.caixaDAO.atualizar(caixaBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public CaixaDTO buscarPorId(Long idCaixa) {
		return this.caixaConversorDTO.converterEntidadeEmDTO(
				this.caixaDAO.buscar(idCaixa)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarPorUsuarioA(Long idUsuarioA) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorUsuarioA(idUsuarioA)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarPorUsuarioAEmData(Long idUsuarioA, Date data) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorUsuarioAEmData(idUsuarioA, data)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarPorUsuarioF(Long idUsuarioF) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorUsuarioF(idUsuarioF)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarPorData(Date dataI, Date dataF) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorData(dataI, dataF)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarCaixasEmAbertoPorDataEUsuario(Date dataI, Date dataF, Long idUsuario) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarCaixasEmAbertoPorDataEUsuario(dataI, dataF, idUsuario)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarCaixasEmAbertoPorUsuario(Long idUsuario) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarCaixasEmAbertoPorUsuario(idUsuario)
				);
	}
	
	@Override
	public List<CaixaDTO> buscarPorDataEOrdem(Date data, int numeroOrdem) {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorDataEOrdem(data, numeroOrdem)
				);
	}
	
	@Override
	public int buscarPorMaxOrdemData(Date data) {
		return this.caixaDAO.buscarPorMaxOrdemData(data);
	}

	@Override
	public List<CaixaDTO> buscarAtivos() {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<CaixaDTO> buscarInativos() {
		return this.caixaConversorDTO.converterListEntidadeEmListDTO(
				this.caixaDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public CaixaDTO getEntidadeFromList(List<CaixaDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
