package negocio.sb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import negocio.exception.AtributoIncompletoException;
import negocio.fachada.local.ILogOperacionalFachada;
import negocio.util.GerenciadorAtributo;
import persistencia.dao.LogOperacionalDAO;
import dao.util.Filtro;
import dto.LogOperacionalDTO;
import dto.conversor.conversores.LogOperacionalConversorDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.validadores.LogOperacionalAtributoValidador;

@Stateless
@Remote(ILogOperacionalFachada.class)
public class LogOperacionalSB implements ILogOperacionalFachada, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private LogOperacionalDAO logOperacionalDAO;
	
	private LogOperacionalConversorDTO logOperacionalConversorDTO;
	private LogOperacionalAtributoValidador logOperacionalAtributoValidador;
	
	public LogOperacionalSB()
	{
		this.logOperacionalConversorDTO = ConversorDTOFactory.getLogOperacionalConversorDTO();
		this.logOperacionalAtributoValidador = AtributoValidadorFactory.getLogOperacionalAtributoValidador();
	}
	
	@Override
	public void salvar(LogOperacionalDTO logOperacionalDTO) throws AtributoIncompletoException{
		GerenciadorAtributo gerenciadorAtributos = this.logOperacionalAtributoValidador.validarAtributosEmEntidade(logOperacionalDTO);
		
		if(gerenciadorAtributos.isAtributosValidados())
		{
			this.logOperacionalDAO.salvar(this.logOperacionalConversorDTO.converterDTOEmEntidade(logOperacionalDTO));
		}
		else
		{
			throw new AtributoIncompletoException(gerenciadorAtributos);
		}

	}

	@Override
	public LogOperacionalDTO buscarPorId(Long idLogOperacional) {
		return this.logOperacionalConversorDTO.converterEntidadeEmDTO(
				this.logOperacionalDAO.buscar(idLogOperacional)
				);
	}
	
	@Override
	public List<LogOperacionalDTO> buscarPorFuncionarioNome(String nome, Filtro filtro) {
		return this.logOperacionalConversorDTO.converterListEntidadeEmListDTO(
				this.logOperacionalDAO.buscarPorFuncionarioNome(nome, filtro)
				);
	}
	
	@Override
	public int buscarTotalPorFuncionarioNome(String nome) {
		return this.logOperacionalDAO.buscarTotalPorFuncionarioNome(nome);
	}
	
	@Override
	public List<LogOperacionalDTO> buscarPorData(Date data, Filtro filtro) {
		return this.logOperacionalConversorDTO.converterListEntidadeEmListDTO(
				this.logOperacionalDAO.buscarPorData(data, filtro)
				);
	}
	
	@Override
	public int buscarTotalPorData(Date data) {
		return this.logOperacionalDAO.buscarTotalPorData(data);
	}

	@Override
	public LogOperacionalDTO getEntidadeFromList(List<LogOperacionalDTO> lista) {
		return lista.size() > 0 ? lista.get(0) : null;
	}
}
