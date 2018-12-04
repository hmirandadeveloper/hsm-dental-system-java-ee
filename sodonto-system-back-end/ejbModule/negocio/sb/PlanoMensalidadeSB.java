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
import negocio.fachada.local.IPlanoMensalidadeFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.PlanoMensalidadeDAO;
import dto.PlanoMensalidadeDTO;
import dto.conversor.conversores.PlanoMensalidadeConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.PlanoMensalidadeAtributoValidador;
import entidade.PlanoMensalidade;

@Stateless
@Remote(IPlanoMensalidadeFachada.class)
public class PlanoMensalidadeSB implements IPlanoMensalidadeFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private PlanoMensalidadeDAO planoMensalidadeDAO;
	
	private PlanoMensalidadeConversorDTO planoMensalidadeConversorDTO;
	private PlanoMensalidadeAtributoValidador planoMensalidadeAtributoValidador;
	
	public PlanoMensalidadeSB()
	{
		this.planoMensalidadeConversorDTO = ConversorDTOFactory.getPlanoMensalidadeConversorDTO();
		this.planoMensalidadeAtributoValidador = AtributoValidadorFactory.getPlanoMensalidadeAtributoValidador();
	}
	
	@Override
	public void salvar(PlanoMensalidadeDTO planoMensalidadeDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		System.out.println("[SODONTO SYSTEM][2.0.00][SB] Salvando Mensalidade do Plano: " + planoMensalidadeDTO.getPlano().getIdPlano());
		GerenciadorAtributo gerenciadorAtributos = this.planoMensalidadeAtributoValidador.validarAtributosEmEntidade(planoMensalidadeDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<PlanoMensalidade> planoMensalidadesBusca = this.planoMensalidadeDAO.buscarPorPlanoEMes(
					planoMensalidadeDTO.getPlano().getIdPlano(), planoMensalidadeDTO.getMes());
			PlanoMensalidade planoMensalidadeBusca = planoMensalidadesBusca.size() > 0 && planoMensalidadesBusca.size() < 2 ? 
					planoMensalidadesBusca.get(0) : null;

			if(planoMensalidadeBusca != null)
			{
				if(!planoMensalidadeBusca.isAtivo())
				{
					planoMensalidadeDTO.setIdPlanoMensalidade(planoMensalidadeBusca.getIdPlanoMensalidade());
					planoMensalidadeDTO.setAtivo(true);
					this.planoMensalidadeDAO.atualizar(this.planoMensalidadeConversorDTO.converterDTOEmEntidade(planoMensalidadeDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				planoMensalidadeDTO.setAtivo(true);
				this.planoMensalidadeDAO.salvar(this.planoMensalidadeConversorDTO.converterDTOEmEntidade(planoMensalidadeDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public PlanoMensalidadeDTO alterar(PlanoMensalidadeDTO planoMensalidadeDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		PlanoMensalidadeDTO planoMensalidadeRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.planoMensalidadeAtributoValidador.validarAtributosEmEntidade(planoMensalidadeDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<PlanoMensalidade> planoMensalidadesBusca = this.planoMensalidadeDAO.buscarPorPlanoEMes(
					planoMensalidadeDTO.getPlano().getIdPlano(), planoMensalidadeDTO.getMes());
			PlanoMensalidade planoMensalidadeBusca = planoMensalidadesBusca.size() > 0 && planoMensalidadesBusca.size() < 2 ? 
					planoMensalidadesBusca.get(0) : null;
			
			if(planoMensalidadeBusca != null)
			{
				planoMensalidadeBusca = this.planoMensalidadeConversorDTO.converterDTOEmEntidade(planoMensalidadeDTO);
				this.planoMensalidadeDAO.atualizar(planoMensalidadeBusca);
				
				planoMensalidadeRetornoDTO = planoMensalidadeDTO;
			}
			else if(planoMensalidadesBusca.size() > 1)
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
		
		return planoMensalidadeRetornoDTO;
	}

	@Override
	public void inativar(Long idPlanoMensalidade, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		PlanoMensalidade planoMensalidadeBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			planoMensalidadeBusca = this.planoMensalidadeDAO.buscar(idPlanoMensalidade);
			if(planoMensalidadeBusca != null)
			{
				planoMensalidadeBusca.setAtivo(false);
				this.planoMensalidadeDAO.atualizar(planoMensalidadeBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else if (perfilUsuario == EPerfilUsuario.G)
		{
			planoMensalidadeBusca = this.planoMensalidadeDAO.buscar(idPlanoMensalidade);
			if(planoMensalidadeBusca != null)
			{
				planoMensalidadeBusca.setAtivo(false);
				this.planoMensalidadeDAO.atualizar(planoMensalidadeBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public PlanoMensalidadeDTO buscarPorId(Long idPlanoMensalidade) {
		return this.planoMensalidadeConversorDTO.converterEntidadeEmDTO(
				this.planoMensalidadeDAO.buscar(idPlanoMensalidade)
				);
	}
	
	@Override
	public List<PlanoMensalidadeDTO> buscarPorPlano(Long idPlano) {
		return this.planoMensalidadeConversorDTO.converterListEntidadeEmListDTO(
				this.planoMensalidadeDAO.buscarPorPlano(idPlano)
				);
	}
	
	@Override
	public List<PlanoMensalidadeDTO> buscarPorPlanoEMes(Long idPlano, int mes) {
		return this.planoMensalidadeConversorDTO.converterListEntidadeEmListDTO(
				this.planoMensalidadeDAO.buscarPorPlanoEMes(idPlano, mes)
				);
	}

	@Override
	public List<PlanoMensalidadeDTO> buscarAtivos() {
		return this.planoMensalidadeConversorDTO.converterListEntidadeEmListDTO(
				this.planoMensalidadeDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<PlanoMensalidadeDTO> buscarInativos() {
		return this.planoMensalidadeConversorDTO.converterListEntidadeEmListDTO(
				this.planoMensalidadeDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public PlanoMensalidadeDTO getEntidadeFromList(List<PlanoMensalidadeDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
