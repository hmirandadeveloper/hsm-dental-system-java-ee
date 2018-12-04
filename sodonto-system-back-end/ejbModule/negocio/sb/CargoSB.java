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
import negocio.fachada.local.ICargoFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.CargoDAO;
import dto.CargoDTO;
import dto.conversor.conversores.CargoConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.CargoAtributoValidador;
import entidade.Cargo;

@Stateless
@Remote(ICargoFachada.class)
public class CargoSB implements ICargoFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private CargoDAO cargoDAO;
	
	private CargoConversorDTO cargoConversorDTO;
	private CargoAtributoValidador cargoAtributoValidador;
	
	public CargoSB()
	{
		this.cargoConversorDTO = ConversorDTOFactory.getCargoConversorDTO();
		this.cargoAtributoValidador = AtributoValidadorFactory.getCargoAtributoValidador();
	}
	
	@Override
	public void salvar(CargoDTO cargoDTO) throws EntidadeCadastradaException,
	AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.cargoAtributoValidador.validarAtributosEmEntidade(cargoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Cargo> cargosBusca = this.cargoDAO.buscarPorNome(
					cargoDTO.getNomeCargo());
			Cargo cargoBusca = cargosBusca.size() > 0 && cargosBusca.size() < 2 ? 
					cargosBusca.get(0) : null;

			if(cargoBusca != null)
			{
				if(!cargoBusca.isAtivo())
				{
					cargoDTO.setIdCargo(cargoBusca.getIdCargo());
					cargoDTO.setAtivo(true);
					this.cargoDAO.atualizar(this.cargoConversorDTO.converterDTOEmEntidade(cargoDTO));
				}
				else
				{
					throw new EntidadeCadastradaException(MensagemErro.MSG_ERRO_INF_DUPLICADA);
				}
			}
			else
			{
				cargoDTO.setAtivo(true);
				this.cargoDAO.salvar(this.cargoConversorDTO.converterDTOEmEntidade(cargoDTO));
			}
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}
	
	@Override
	public CargoDTO alterar(CargoDTO cargoDTO)
			throws EntidadeInexistenteException, AtributoIncompletoException,
			EntidadeCadastradaException {
		CargoDTO cargoRetornoDTO = null;
		GerenciadorAtributo gerenciadorAtributos = this.cargoAtributoValidador.validarAtributosEmEntidade(cargoDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			List<Cargo> cargosBusca = this.cargoDAO.buscarPorNome(
					cargoDTO.getNomeCargo());
			Cargo cargoBusca = cargosBusca.size() > 0 && cargosBusca.size() < 2 ? 
					cargosBusca.get(0) : null;
			
			if(cargoBusca != null || ( cargoDTO.getIdCargo() != null 
					&& cargoBusca == null))
			{
				cargoBusca = this.cargoConversorDTO.converterDTOEmEntidade(cargoDTO);
				this.cargoDAO.atualizar(cargoBusca);
				
				cargoRetornoDTO = cargoDTO;
			}
			else if(cargosBusca.size() > 1)
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
		
		return cargoRetornoDTO;
	}

	@Override
	public void inativar(Long idCargo, EPerfilUsuario perfilUsuario)
			throws EntidadeInexistenteException {
		Cargo cargoBusca = null;
		
		if(perfilUsuario == EPerfilUsuario.A)
		{
			cargoBusca = this.cargoDAO.buscar(idCargo);
			if(cargoBusca != null)
			{
				try
				{
					this.cargoDAO.remover(cargoBusca);
				}
				catch(Exception e)
				{
					cargoBusca.setAtivo(false);
					this.cargoDAO.atualizar(cargoBusca);
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
			cargoBusca = this.cargoDAO.buscar(idCargo);
			if(cargoBusca != null)
			{
				cargoBusca.setAtivo(false);
				this.cargoDAO.atualizar(cargoBusca);
			}
			else
			{
				throw new EntidadeInexistenteException(MensagemErro.MSG_ERRO_ENT_INEXISTENTE);
			}
		}
		
	}

	@Override
	public CargoDTO buscarPorId(Long idCargo) {
		return this.cargoConversorDTO.converterEntidadeEmDTO(
				this.cargoDAO.buscar(idCargo)
				);
	}
	
	@Override
	public List<CargoDTO> buscarPorNome(String nome) {
		return this.cargoConversorDTO.converterListEntidadeEmListDTO(
				this.cargoDAO.buscarPorNome(nome)
				);
	}

	@Override
	public List<CargoDTO> buscarAtivos() {
		return this.cargoConversorDTO.converterListEntidadeEmListDTO(
				this.cargoDAO.buscarPorCondicao(true)
				);
	}

	@Override
	public List<CargoDTO> buscarInativos() {
		return this.cargoConversorDTO.converterListEntidadeEmListDTO(
				this.cargoDAO.buscarPorCondicao(false)
				);
	}

	@Override
	public CargoDTO getEntidadeFromList(List<CargoDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
