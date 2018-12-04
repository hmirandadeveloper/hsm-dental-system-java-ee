package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.LogOperacionalDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.LogOperacional;


public class LogOperacionalConversorDTO implements IConversorDTO<LogOperacionalDTO, LogOperacional>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public LogOperacionalConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public LogOperacional converterDTOEmEntidade(LogOperacionalDTO entidadeDTO) {

		LogOperacional entidade = new LogOperacional();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdLogOperacional() != null)
			{
				entidade.setIdLogOperacional(entidadeDTO.getIdLogOperacional());
			}
			entidade.setUsuarioLog(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioLogDTO()));
			entidade.setTituloOperacao(entidadeDTO.getTituloOperacao());
			entidade.setDetalhesOperacao(entidadeDTO.getDetalhesOperacao());
			entidade.setDataLog(entidadeDTO.getDataLog());
		}
		
		return entidade;
	}

	@Override
	public LogOperacionalDTO converterEntidadeEmDTO(LogOperacional entidade) {
		LogOperacionalDTO entidadeDTO = new LogOperacionalDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdLogOperacional() != null)
			{
				entidadeDTO.setIdLogOperacional(entidade.getIdLogOperacional());
			}
			entidadeDTO.setUsuarioLogDTO(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuarioLog()));
			entidadeDTO.setTituloOperacao(entidade.getTituloOperacao());
			entidadeDTO.setDetalhesOperacao(entidade.getDetalhesOperacao());
			entidadeDTO.setDataLog(entidade.getDataLog());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<LogOperacionalDTO> converterSetEntidadeEmListDTO(Set<LogOperacional> entidades) {

		List<LogOperacionalDTO> entidadesDTO = new ArrayList<LogOperacionalDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(LogOperacional entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<LogOperacionalDTO> converterListEntidadeEmListDTO(
			List<LogOperacional> entidades) {
		List<LogOperacionalDTO> entidadesDTO = new ArrayList<LogOperacionalDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(LogOperacional entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
