package negocio.sb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.constante.enums.EPerfilUsuario;
import negocio.constante.mensagens.MensagemErro;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.CpfInvalidoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import negocio.fachada.local.IDentistaFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.DentistaDAO;
import dto.DentistaDTO;
import dto.conversor.conversores.DentistaConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.DentistaAtributoValidador;
import dto.validador.validadores.util.ValidadorAtributosEspeciais;
import entidade.Dentista;

@Stateless
@Remote(IDentistaFachada.class)
public class DentistaSB implements IDentistaFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private DentistaDAO dentistaDAO;
	
	private DentistaConversorDTO dentistaConversorDTO;
	private DentistaAtributoValidador dentistaAtributoValidador;
	
	public DentistaSB()
	{
		this.dentistaConversorDTO = ConversorDTOFactory.getDentistaConversorDTO();
		this.dentistaAtributoValidador = AtributoValidadorFactory.getDentistaAtributoValidador();
	}
	
	@Override
	public void salvar(DentistaDTO dentistaDTO) throws EntidadeCadastradaException,
			AtributoIncompletoException, CpfInvalidoException {
		GerenciadorAtributo gerenciadorAtributos = this.dentistaAtributoValidador.validarAtributosEmEntidade(dentistaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			if(ValidadorAtributosEspeciais.validarCpf(dentistaDTO.getCpf()))
			{
				List<Dentista> dentistasBusca = this.dentistaDAO.buscarPorCpfOuCro(
						dentistaDTO.getCpf(), dentistaDTO.getCro());
				Dentista dentistaBusca = dentistasBusca.size() > 0 && dentistasBusca.size() < 2 ? 
						dentistasBusca.get(0) : null;
				
				if(dentistaBusca != null)
				{
					if(!dentistaBusca.isAtivo())
					{
						dentistaDTO.setIdDentista(dentistaBusca.getIdDentista());
						dentistaDTO.setAtivo(true);
						dentistaDTO.getUsuario().setAtivo(true);
						this.dentistaDAO.atualizar(this.dentistaConversorDTO.converterDTOEmEntidade(dentistaDTO));
					}
					else
					{
						throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
					}
				}
				else
				{
					dentistaDTO.setAtivo(true);
					dentistaDTO.getUsuario().setAtivo(true);
					this.dentistaDAO.salvar(this.dentistaConversorDTO.converterDTOEmEntidade(dentistaDTO));
				}
			}
			else
			{
				throw new CpfInvalidoException(MensagemErro.MSG_ERRO_CPF_INVALIDO);
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}
		
	}
	
	@Override
	public DentistaDTO alterar(DentistaDTO dentistaDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		DentistaDTO dentistaRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.dentistaAtributoValidador.validarAtributosEmEntidade(dentistaDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Dentista> dentistasBusca = this.dentistaDAO.buscarPorCpfOuCro(
					dentistaDTO.getCpf(), dentistaDTO.getCro());
			Dentista dentistaBusca = dentistasBusca.size() > 0 && dentistasBusca.size() < 2 ? 
					dentistasBusca.get(0) : null;
			
			if(dentistaBusca != null)
			{
				dentistaBusca = this.dentistaConversorDTO.converterDTOEmEntidade(dentistaDTO);
				this.dentistaDAO.atualizar(dentistaBusca);
				
				dentistaRetornoDTO = dentistaDTO;
			}
			else if(dentistasBusca.size() > 1)
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
		
		return dentistaRetornoDTO;
	}

	@Override
	public void inativar(Long idDentista, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Dentista dentistaBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			dentistaBusca = this.dentistaDAO.buscar(idDentista);
			if(dentistaBusca != null)
			{
				try
				{
					this.dentistaDAO.remover(dentistaBusca);
				}
				catch(Exception e)
				{
					dentistaBusca.setAtivo(false);
					this.dentistaDAO.atualizar(dentistaBusca);
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
			dentistaBusca = this.dentistaDAO.buscar(idDentista);
			if(dentistaBusca != null)
			{
				dentistaBusca.setAtivo(false);
				this.dentistaDAO.atualizar(dentistaBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public DentistaDTO buscarPorId(Long idDentista) {
		return this.dentistaConversorDTO.converterEntidadeEmDTO(
				this.dentistaDAO.buscar(idDentista)
				);
	}

	@Override
	public List<DentistaDTO> buscarPorCpf(String cpf) {
		return this.dentistaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaDAO.buscarPorCpfOuCro(cpf, "")
				);
	}

	@Override
	public List<DentistaDTO> buscarPorCro(String cro) {
		return this.dentistaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaDAO.buscarPorCpfOuCro("", cro)
				);
	}
	
	@Override
	public List<DentistaDTO> buscarPorNome(String nome) {
		return this.dentistaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<DentistaDTO> buscarAtivos() {
		return this.dentistaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<DentistaDTO> buscarInativos() {
		return this.dentistaConversorDTO.converterListEntidadeEmListDTO(
				this.dentistaDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public DentistaDTO getEntidadeFromList(List<DentistaDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
