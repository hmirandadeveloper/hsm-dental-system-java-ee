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
import negocio.fachada.local.IEspecialidadeFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.EspecialidadeDAO;
import dto.EspecialidadeDTO;
import dto.conversor.conversores.EspecialidadeConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.EspecialidadeAtributoValidador;
import entidade.Especialidade;

@Stateless
@Remote(IEspecialidadeFachada.class)
public class EspecialidadeSB implements IEspecialidadeFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private EspecialidadeDAO especialidadeDAO;
	
	private EspecialidadeConversorDTO especialidadeConversorDTO;
	private EspecialidadeAtributoValidador especialidadeAtributoValidador;
	
	public EspecialidadeSB()
	{
		this.especialidadeConversorDTO = ConversorDTOFactory.getEspecialidadeConversorDTO();
		this.especialidadeAtributoValidador = AtributoValidadorFactory.getEspecialidadeAtributoValidador();
	}
	
	@Override
	public void salvar(EspecialidadeDTO especialidadeDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.especialidadeAtributoValidador.validarAtributosEmEntidade(especialidadeDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Especialidade> especialidadesBusca = this.especialidadeDAO.buscarPorNome(
					especialidadeDTO.getNomeEspecialidade());
			Especialidade especialidadeBusca = especialidadesBusca.size() > 0 && especialidadesBusca.size() < 2 ? 
					especialidadesBusca.get(0) : null;

			if(especialidadeBusca != null)
			{
				if(!especialidadeBusca.isAtivo())
				{
					especialidadeDTO.setIdEspecialidade(especialidadeBusca.getIdEspecialidade());
					especialidadeDTO.setAtivo(true);
					this.especialidadeDAO.atualizar(this.especialidadeConversorDTO.converterDTOEmEntidade(especialidadeDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				especialidadeDTO.setAtivo(true);
				this.especialidadeDAO.salvar(this.especialidadeConversorDTO.converterDTOEmEntidade(especialidadeDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public EspecialidadeDTO alterar(EspecialidadeDTO especialidadeDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		EspecialidadeDTO especialidadeRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.especialidadeAtributoValidador.validarAtributosEmEntidade(especialidadeDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Especialidade> especialidadesBusca = this.especialidadeDAO.buscarPorNome(
					especialidadeDTO.getNomeEspecialidade());
			Especialidade especialidadeBusca = especialidadesBusca.size() > 0 && especialidadesBusca.size() < 2 ? 
					especialidadesBusca.get(0) : null;
			
			if(especialidadeBusca != null || ( especialidadeDTO.getIdEspecialidade() != null 
					&& especialidadeBusca == null))
			{
				especialidadeBusca = this.especialidadeConversorDTO.converterDTOEmEntidade(especialidadeDTO);
				this.especialidadeDAO.atualizar(especialidadeBusca);
				
				especialidadeRetornoDTO = especialidadeDTO;
			}
			else if(especialidadesBusca.size() > 1)
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
		
		return especialidadeRetornoDTO;
	}

	@Override
	public void inativar(Long idEspecialidade, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Especialidade especialidadeBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			especialidadeBusca = this.especialidadeDAO.buscar(idEspecialidade);
			if(especialidadeBusca != null)
			{
				try
				{
					this.especialidadeDAO.remover(especialidadeBusca);
				}
				catch(Exception e)
				{
					especialidadeBusca.setAtivo(false);
					this.especialidadeDAO.atualizar(especialidadeBusca);
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
			especialidadeBusca = this.especialidadeDAO.buscar(idEspecialidade);
			if(especialidadeBusca != null)
			{
				especialidadeBusca.setAtivo(false);
				this.especialidadeDAO.atualizar(especialidadeBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public EspecialidadeDTO buscarPorId(Long idEspecialidade) {
		return this.especialidadeConversorDTO.converterEntidadeEmDTO(
				this.especialidadeDAO.buscar(idEspecialidade)
				);
	}
	
	@Override
	public List<EspecialidadeDTO> buscarPorNome(String nome) {
		return this.especialidadeConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<EspecialidadeDTO> buscarAtivos() {
		return this.especialidadeConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<EspecialidadeDTO> buscarInativos() {
		return this.especialidadeConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public EspecialidadeDTO getEntidadeFromList(List<EspecialidadeDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
