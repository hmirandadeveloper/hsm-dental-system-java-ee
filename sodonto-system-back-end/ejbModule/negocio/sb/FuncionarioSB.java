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
import negocio.fachada.local.IFuncionarioFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.FuncionarioDAO;
import dto.FuncionarioDTO;
import dto.conversor.conversores.FuncionarioConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.FuncionarioAtributoValidador;
import dto.validador.validadores.util.ValidadorAtributosEspeciais;
import entidade.Funcionario;

@Stateless
@Remote(IFuncionarioFachada.class)
public class FuncionarioSB implements IFuncionarioFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private FuncionarioDAO funcionarioDAO;
	
	private FuncionarioConversorDTO funcionarioConversorDTO;
	private FuncionarioAtributoValidador funcionarioAtributoValidador;
	
	public FuncionarioSB()
	{
		this.funcionarioConversorDTO = ConversorDTOFactory.getFuncionarioConversorDTO();
		this.funcionarioAtributoValidador = AtributoValidadorFactory.getFuncionarioAtributoValidador();
	}
	
	@Override
	public void salvar(FuncionarioDTO funcionarioDTO) throws EntidadeCadastradaException,
			AtributoIncompletoException, CpfInvalidoException {
		GerenciadorAtributo gerenciadorAtributos = this.funcionarioAtributoValidador.validarAtributosEmEntidade(funcionarioDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			if(ValidadorAtributosEspeciais.validarCpf(funcionarioDTO.getCpf()))
			{
				List<Funcionario> funcionariosBusca = this.funcionarioDAO.buscarPorCpfOuRg(
						funcionarioDTO.getCpf(), funcionarioDTO.getRg());
				Funcionario funcionarioBusca = funcionariosBusca.size() > 0 && funcionariosBusca.size() < 2 ? 
						funcionariosBusca.get(0) : null;
				
				if(funcionarioBusca != null)
				{
					if(!funcionarioBusca.isAtivo())
					{
						funcionarioDTO.setIdFuncionario(funcionarioBusca.getIdFuncionario());
						funcionarioDTO.setAtivo(true);
						funcionarioDTO.getUsuario().setAtivo(true);
						this.funcionarioDAO.atualizar(this.funcionarioConversorDTO.converterDTOEmEntidade(funcionarioDTO));
					}
					else
					{
						throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
					}
				}
				else
				{
					funcionarioDTO.setAtivo(true);
					funcionarioDTO.getUsuarioPerfil().setAtivo(true);
					this.funcionarioDAO.salvar(this.funcionarioConversorDTO.converterDTOEmEntidade(funcionarioDTO));
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
	public FuncionarioDTO alterar(FuncionarioDTO funcionarioDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		FuncionarioDTO funcionarioRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.funcionarioAtributoValidador.validarAtributosEmEntidade(funcionarioDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Funcionario> funcionariosBusca = this.funcionarioDAO.buscarPorCpfOuRg(
					funcionarioDTO.getCpf(), funcionarioDTO.getRg());
			Funcionario funcionarioBusca = funcionariosBusca.size() > 0 && funcionariosBusca.size() < 2 ? 
					funcionariosBusca.get(0) : null;
			
			if(funcionarioBusca != null)
			{
				funcionarioBusca = this.funcionarioConversorDTO.converterDTOEmEntidade(funcionarioDTO);
				this.funcionarioDAO.atualizar(funcionarioBusca);
				
				funcionarioRetornoDTO = funcionarioDTO;
			}
			else if(funcionariosBusca.size() > 1)
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
		
		return funcionarioRetornoDTO;
	}

	@Override
	public void inativar(Long idFuncionario, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Funcionario funcionarioBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			funcionarioBusca = this.funcionarioDAO.buscar(idFuncionario);
			if(funcionarioBusca != null)
			{
				try
				{
					this.funcionarioDAO.remover(funcionarioBusca);
				}
				catch(Exception e)
				{
					funcionarioBusca.setAtivo(false);
					this.funcionarioDAO.atualizar(funcionarioBusca);
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
			funcionarioBusca = this.funcionarioDAO.buscar(idFuncionario);
			if(funcionarioBusca != null)
			{
				funcionarioBusca.setAtivo(false);
				this.funcionarioDAO.atualizar(funcionarioBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public FuncionarioDTO buscarPorId(Long idFuncionario) {
		return this.funcionarioConversorDTO.converterEntidadeEmDTO(
				this.funcionarioDAO.buscar(idFuncionario)
				);
	}

	@Override
	public List<FuncionarioDTO> buscarPorCpf(String cpf) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorCpfOuRg(cpf, "")
				);
	}

	@Override
	public List<FuncionarioDTO> buscarPorRg(String rg) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorCpfOuRg("", rg)
				);
	}
	
	@Override
	public List<FuncionarioDTO> buscarPorNome(String nome) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<FuncionarioDTO> buscarPorCargo(Long idCargo) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorCargo(idCargo)
				);
	}
	
	@Override
	public List<FuncionarioDTO> buscarPorPerfil(String perfil) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorPerfil(perfil)
				);
	}
	
	@Override
	public List<FuncionarioDTO> buscarPorIdUsuario(Long idUsuario) {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorIdUsuario(idUsuario)
				);
	}

	@Override
	public List<FuncionarioDTO> buscarAtivos() {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<FuncionarioDTO> buscarInativos() {
		return this.funcionarioConversorDTO.converterListEntidadeEmListDTO(
				this.funcionarioDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public FuncionarioDTO getEntidadeFromList(List<FuncionarioDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
