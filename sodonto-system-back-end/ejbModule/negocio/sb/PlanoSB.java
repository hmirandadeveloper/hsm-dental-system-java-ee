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
import negocio.fachada.local.IPlanoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.PlanoDAO;
import dto.PlanoDTO;
import dto.conversor.conversores.PlanoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.PlanoAtributoValidador;
import entidade.Plano;

@Stateless
@Remote(IPlanoFachada.class)
public class PlanoSB implements IPlanoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private PlanoDAO planoDAO;
	
	private PlanoConversorDTO planoConversorDTO;
	private PlanoAtributoValidador planoAtributoValidador;
	
	public PlanoSB()
	{
		this.planoConversorDTO = ConversorDTOFactory.getPlanoConversorDTO();
		this.planoAtributoValidador = AtributoValidadorFactory.getPlanoAtributoValidador();
	}
	
	@Override
	public void salvar(PlanoDTO planoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.planoAtributoValidador.validarAtributosEmEntidade(planoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Plano> planosBusca = this.planoDAO.buscarPorNome(
					planoDTO.getNomePlano());
			Plano planoBusca = planosBusca.size() > 0 && planosBusca.size() < 2 ? 
					planosBusca.get(0) : null;
			if(planoBusca != null)
			{
				if(!planoBusca.isAtivo())
				{
					planoDTO.setIdPlano(planoBusca.getIdPlano());
					planoDTO.setAtivo(true);
					this.planoDAO.atualizar(this.planoConversorDTO.converterDTOEmEntidade(planoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				System.out.println("04");
				planoDTO.setAtivo(true);
				Plano p = this.planoConversorDTO.converterDTOEmEntidade(planoDTO);
				this.planoDAO.salvar(p);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public PlanoDTO alterar(PlanoDTO planoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		PlanoDTO planoRetornoDTO = null;
		System.out.println("1");
		GerenciadorAtributo gerenciadorAtributos = this.planoAtributoValidador.validarAtributosEmEntidade(planoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			System.out.println("2");
			List<Plano> planosBusca = this.planoDAO.buscarPorNome(
					planoDTO.getNomePlano());
			Plano planoBusca = planosBusca.size() > 0 && planosBusca.size() < 2 ? 
					planosBusca.get(0) : null;
			
			if(planoBusca != null || ( planoDTO.getIdPlano() != null 
					&& planoBusca == null))
			{
				planoBusca = this.planoConversorDTO.converterDTOEmEntidade(planoDTO);
				this.planoDAO.atualizar(planoBusca);
				
				planoRetornoDTO = planoDTO;
			}
			else if(planosBusca.size() > 1)
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
		
		return planoRetornoDTO;
	}

	@Override
	public void inativar(Long idPlano, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Plano planoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			planoBusca = this.planoDAO.buscar(idPlano);
			if(planoBusca != null)
			{
				planoBusca.setAtivo(false);
				this.planoDAO.atualizar(planoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		else if (perfilUsuario == EPerfilUsuario.G)
		{
			planoBusca = this.planoDAO.buscar(idPlano);
			if(planoBusca != null)
			{
				planoBusca.setAtivo(false);
				this.planoDAO.atualizar(planoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public PlanoDTO buscarPorId(Long idPlano) {
		return this.planoConversorDTO.converterEntidadeEmDTO(
				this.planoDAO.buscar(idPlano)
				);
	}
	
	@Override
	public List<PlanoDTO> buscarVigentes(Long idEstabelecimento) {
		return this.planoConversorDTO.converterListEntidadeEmListDTO(
				this.planoDAO.buscarVigentes(idEstabelecimento)
				);
	}
	
	@Override
	public List<PlanoDTO> buscarPorNome(String nome) {
		return this.planoConversorDTO.converterListEntidadeEmListDTO(
				this.planoDAO.buscarPorNome(nome)
				);
	}
	
	@Override
	public List<PlanoDTO> buscarPorValidade(Date dataI, Date dataF) {
		return this.planoConversorDTO.converterListEntidadeEmListDTO(
				this.planoDAO.buscarPorValidade(dataI, dataF)
				);
	}

	@Override
	public List<PlanoDTO> buscarAtivos() {
		return this.planoConversorDTO.converterListEntidadeEmListDTO(
				this.planoDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<PlanoDTO> buscarInativos() {
		return this.planoConversorDTO.converterListEntidadeEmListDTO(
				this.planoDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public PlanoDTO getEntidadeFromList(List<PlanoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
