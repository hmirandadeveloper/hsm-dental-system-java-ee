package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.exception.AtributoIncompletoException;
import dao.util.Filtro;
import dto.LogOperacionalDTO;

public interface ILogOperacionalFachada {
	public abstract void salvar(LogOperacionalDTO logOperacionalDTO) 
			throws AtributoIncompletoException;
	public abstract LogOperacionalDTO buscarPorId(Long idCargo);
	public abstract List<LogOperacionalDTO> buscarPorFuncionarioNome(String nome, Filtro filtro);
	public abstract int buscarTotalPorFuncionarioNome(String nome);
	public abstract List<LogOperacionalDTO> buscarPorData(Date data, Filtro filtro);
	public abstract int buscarTotalPorData(Date data);
	public abstract LogOperacionalDTO getEntidadeFromList(List<LogOperacionalDTO> lista);
}
