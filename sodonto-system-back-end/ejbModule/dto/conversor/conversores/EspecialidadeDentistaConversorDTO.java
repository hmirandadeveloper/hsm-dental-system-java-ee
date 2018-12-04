package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.EspecialidadeDentistaDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.EspecialidadeDentista;


public class EspecialidadeDentistaConversorDTO implements IConversorDTO<EspecialidadeDentistaDTO, EspecialidadeDentista>{
	
	private DentistaConversorDTO dentistaConversorDTO;
	private EspecialidadeConversorDTO especialidadeConversorDTO;
	
	public EspecialidadeDentistaConversorDTO()
	{
		this.dentistaConversorDTO = ConversorDTOFactory.getDentistaConversorDTO();
		this.especialidadeConversorDTO = ConversorDTOFactory.getEspecialidadeConversorDTO();
	}
	
	@Override
	public EspecialidadeDentista converterDTOEmEntidade(EspecialidadeDentistaDTO entidadeDTO) {

		EspecialidadeDentista entidade = new EspecialidadeDentista();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdEspecialidadeDentista() != null)
			{
				entidade.setIdEspecialidadeDentista(entidadeDTO.getIdEspecialidadeDentista());
			}
			entidade.setDentista(dentistaConversorDTO.converterDTOEmEntidade(entidadeDTO.getDentista()));
			entidade.setEspecialidade(especialidadeConversorDTO.converterDTOEmEntidade(entidadeDTO.getEspecialidade()));
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public EspecialidadeDentistaDTO converterEntidadeEmDTO(EspecialidadeDentista entidade) {
		EspecialidadeDentistaDTO entidadeDTO = new EspecialidadeDentistaDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdEspecialidadeDentista() != null)
			{
				entidadeDTO.setIdEspecialidadeDentista(entidade.getIdEspecialidadeDentista());
			}
			entidadeDTO.setDentista(dentistaConversorDTO.converterEntidadeEmDTO(entidade.getDentista()));
			entidadeDTO.setEspecialidade(especialidadeConversorDTO.converterEntidadeEmDTO(entidade.getEspecialidade()));
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<EspecialidadeDentistaDTO> converterSetEntidadeEmListDTO(Set<EspecialidadeDentista> entidades) {

		List<EspecialidadeDentistaDTO> entidadesDTO = new ArrayList<EspecialidadeDentistaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(EspecialidadeDentista entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<EspecialidadeDentistaDTO> converterListEntidadeEmListDTO(
			List<EspecialidadeDentista> entidades) {
		List<EspecialidadeDentistaDTO> entidadesDTO = new ArrayList<EspecialidadeDentistaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(EspecialidadeDentista entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
