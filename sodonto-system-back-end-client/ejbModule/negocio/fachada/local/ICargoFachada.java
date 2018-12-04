package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.CargoDTO;

public interface ICargoFachada {
	public abstract void salvar(CargoDTO cargoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract CargoDTO alterar(CargoDTO cargoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idCargo, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract CargoDTO buscarPorId(Long idCargo);
	public abstract List<CargoDTO> buscarPorNome(String nome);
	public abstract List<CargoDTO> buscarAtivos();
	public abstract List<CargoDTO> buscarInativos();
	public abstract CargoDTO getEntidadeFromList(List<CargoDTO> lista);
}
