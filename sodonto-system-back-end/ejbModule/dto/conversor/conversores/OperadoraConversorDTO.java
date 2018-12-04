package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.OperadoraDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Operadora;


public class OperadoraConversorDTO implements IConversorDTO<OperadoraDTO, Operadora>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public OperadoraConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public Operadora converterDTOEmEntidade(OperadoraDTO entidadeDTO) {

		Operadora entidade = new Operadora();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdOperadora() != null)
			{
				entidade.setIdOperadora(entidadeDTO.getIdOperadora());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setNomeOperadora(entidadeDTO.getNomeOperadora());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public OperadoraDTO converterEntidadeEmDTO(Operadora entidade) {
		OperadoraDTO entidadeDTO = new OperadoraDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdOperadora() != null)
			{
				entidadeDTO.setIdOperadora(entidade.getIdOperadora());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setNomeOperadora(entidade.getNomeOperadora());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<OperadoraDTO> converterSetEntidadeEmListDTO(Set<Operadora> entidades) {

		List<OperadoraDTO> entidadesDTO = new ArrayList<OperadoraDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Operadora entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<OperadoraDTO> converterListEntidadeEmListDTO(
			List<Operadora> entidades) {
		List<OperadoraDTO> entidadesDTO = new ArrayList<OperadoraDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Operadora entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
