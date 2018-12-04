package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.EspecialidadeDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Especialidade;


public class EspecialidadeConversorDTO implements IConversorDTO<EspecialidadeDTO, Especialidade>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public EspecialidadeConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public Especialidade converterDTOEmEntidade(EspecialidadeDTO entidadeDTO) {

		Especialidade entidade = new Especialidade();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdEspecialidade() != null)
			{
				entidade.setIdEspecialidade(entidadeDTO.getIdEspecialidade());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setNomeEspecialidade(entidadeDTO.getNomeEspecialidade());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public EspecialidadeDTO converterEntidadeEmDTO(Especialidade entidade) {
		EspecialidadeDTO entidadeDTO = new EspecialidadeDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdEspecialidade() != null)
			{
				entidadeDTO.setIdEspecialidade(entidade.getIdEspecialidade());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setNomeEspecialidade(entidade.getNomeEspecialidade());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<EspecialidadeDTO> converterSetEntidadeEmListDTO(Set<Especialidade> entidades) {

		List<EspecialidadeDTO> entidadesDTO = new ArrayList<EspecialidadeDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Especialidade entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<EspecialidadeDTO> converterListEntidadeEmListDTO(
			List<Especialidade> entidades) {
		List<EspecialidadeDTO> entidadesDTO = new ArrayList<EspecialidadeDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Especialidade entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
