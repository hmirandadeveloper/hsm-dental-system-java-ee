package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.CargoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Cargo;


public class CargoConversorDTO implements IConversorDTO<CargoDTO, Cargo>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public CargoConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public Cargo converterDTOEmEntidade(CargoDTO entidadeDTO) {

		Cargo entidade = new Cargo();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdCargo() != null)
			{
				entidade.setIdCargo(entidadeDTO.getIdCargo());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setNomeCargo(entidadeDTO.getNomeCargo());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public CargoDTO converterEntidadeEmDTO(Cargo entidade) {
		CargoDTO entidadeDTO = new CargoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdCargo() != null)
			{
				entidadeDTO.setIdCargo(entidade.getIdCargo());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setNomeCargo(entidade.getNomeCargo());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<CargoDTO> converterSetEntidadeEmListDTO(Set<Cargo> entidades) {

		List<CargoDTO> entidadesDTO = new ArrayList<CargoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Cargo entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<CargoDTO> converterListEntidadeEmListDTO(
			List<Cargo> entidades) {
		List<CargoDTO> entidadesDTO = new ArrayList<CargoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Cargo entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
