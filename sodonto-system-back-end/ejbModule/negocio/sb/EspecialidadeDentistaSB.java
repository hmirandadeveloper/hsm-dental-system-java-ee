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
import negocio.fachada.local.IEspecialidadeDentistaFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.EspecialidadeDentistaDAO;
import dto.EspecialidadeDentistaDTO;
import dto.conversor.conversores.EspecialidadeDentistaConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.EspecialidadeDentistaAtributoValidador;
import entidade.EspecialidadeDentista;

@Stateless
@Remote(IEspecialidadeDentistaFachada.class)
public class EspecialidadeDentistaSB implements IEspecialidadeDentistaFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private EspecialidadeDentistaDAO especialidadeDentistaDAO;
	
	private EspecialidadeDentistaConversorDTO especialidadeDentistaConversorDTO;
	private EspecialidadeDentistaAtributoValidador especialidadeDentistaAtributoValidador;
	
	public EspecialidadeDentistaSB()
	{
		this.especialidadeDentistaConversorDTO = ConversorDTOFactory.getEspecialidadeDentistaConversorDTO();
		this.especialidadeDentistaAtributoValidador = AtributoValidadorFactory.getEspecialidadeDentistaAtributoValidador();
	}
	
	@Override
	public void salvar(EspecialidadeDentistaDTO especialidadeDentistaDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.especialidadeDentistaAtributoValidador.validarAtributosEmEntidade(especialidadeDentistaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<EspecialidadeDentista> especialidadeDentistasBusca = this.especialidadeDentistaDAO.buscarPorEspecialidadeEDentista(
					especialidadeDentistaDTO.getEspecialidade().getIdEspecialidade(), 
					especialidadeDentistaDTO.getDentista().getIdDentista());
			EspecialidadeDentista especialidadeDentistaBusca = especialidadeDentistasBusca.size() > 0 && especialidadeDentistasBusca.size() < 2 ? 
					especialidadeDentistasBusca.get(0) : null;

			if(especialidadeDentistaBusca != null)
			{
				if(!especialidadeDentistaBusca.isAtivo())
				{
					especialidadeDentistaDTO.setIdEspecialidadeDentista(especialidadeDentistaBusca.getIdEspecialidadeDentista());
					especialidadeDentistaDTO.setAtivo(true);
					this.especialidadeDentistaDAO.atualizar(this.especialidadeDentistaConversorDTO.converterDTOEmEntidade(especialidadeDentistaDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				especialidadeDentistaDTO.setAtivo(true);
				this.especialidadeDentistaDAO.salvar(this.especialidadeDentistaConversorDTO.converterDTOEmEntidade(especialidadeDentistaDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public EspecialidadeDentistaDTO alterar(EspecialidadeDentistaDTO especialidadeDentistaDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		EspecialidadeDentistaDTO especialidadeDentistaRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.especialidadeDentistaAtributoValidador.validarAtributosEmEntidade(especialidadeDentistaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<EspecialidadeDentista> especialidadeDentistasBusca = this.especialidadeDentistaDAO.buscarPorEspecialidadeEDentista(
					especialidadeDentistaDTO.getEspecialidade().getIdEspecialidade(), 
					especialidadeDentistaDTO.getDentista().getIdDentista());
			EspecialidadeDentista especialidadeDentistaBusca = especialidadeDentistasBusca.size() > 0 && especialidadeDentistasBusca.size() < 2 ? 
					especialidadeDentistasBusca.get(0) : null;
			
			if(especialidadeDentistaBusca != null)
			{
				especialidadeDentistaBusca = this.especialidadeDentistaConversorDTO.converterDTOEmEntidade(especialidadeDentistaDTO);
				this.especialidadeDentistaDAO.atualizar(especialidadeDentistaBusca);
				
				especialidadeDentistaRetornoDTO = especialidadeDentistaDTO;
			}
			else if(especialidadeDentistasBusca.size() > 1)
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
		
		return especialidadeDentistaRetornoDTO;
	}

	@Override
	public void inativar(Long idEspecialidadeDentista, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		EspecialidadeDentista especialidadeDentistaBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A || perfilUsuario == EPerfilUsuario.G)
		{
			especialidadeDentistaBusca = this.especialidadeDentistaDAO.buscar(idEspecialidadeDentista);
			if(especialidadeDentistaBusca != null)
			{
				try
				{
					this.especialidadeDentistaDAO.remover(especialidadeDentistaBusca);
				}
				catch(Exception e)
				{
					especialidadeDentistaBusca.setAtivo(false);
					this.especialidadeDentistaDAO.atualizar(especialidadeDentistaBusca);
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
			especialidadeDentistaBusca = this.especialidadeDentistaDAO.buscar(idEspecialidadeDentista);
			if(especialidadeDentistaBusca != null)
			{
				especialidadeDentistaBusca.setAtivo(false);
				this.especialidadeDentistaDAO.atualizar(especialidadeDentistaBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public EspecialidadeDentistaDTO buscarPorId(Long idEspecialidadeDentista) {
		return this.especialidadeDentistaConversorDTO.converterEntidadeEmDTO(
				this.especialidadeDentistaDAO.buscar(idEspecialidadeDentista)
				);
	}
	
	@Override
	public List<EspecialidadeDentistaDTO> buscarPorEspecialidade(Long idEspecialidade) {
		return this.especialidadeDentistaConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDentistaDAO.buscarPorEspecialidade(idEspecialidade)
				);
	}
	
	@Override
	public List<EspecialidadeDentistaDTO> buscarPorDentista(Long idDentista) {
		return this.especialidadeDentistaConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDentistaDAO.buscarPorDentista(idDentista)
				);
	}
	
	@Override
	public List<EspecialidadeDentistaDTO> buscarPorEspecialidadeEDentista(Long idEspecialidade, Long idDentista) {
		return this.especialidadeDentistaConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDentistaDAO.buscarPorEspecialidadeEDentista(idEspecialidade, idDentista)
				);
	}

	@Override
	public List<EspecialidadeDentistaDTO> buscarAtivos() {
		return this.especialidadeDentistaConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDentistaDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<EspecialidadeDentistaDTO> buscarInativos() {
		return this.especialidadeDentistaConversorDTO.converterListEntidadeEmListDTO(
				this.especialidadeDentistaDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public EspecialidadeDentistaDTO getEntidadeFromList(List<EspecialidadeDentistaDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
