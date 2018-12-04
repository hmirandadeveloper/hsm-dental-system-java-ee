package dto.conversor.template;

import java.util.List;
import java.util.Set;

public interface IConversorDTO<EntidadeDTO, Entidade> {
	
	public abstract Entidade converterDTOEmEntidade(EntidadeDTO entidadeDTO);
	public abstract EntidadeDTO converterEntidadeEmDTO(Entidade entidade);
	public abstract List<EntidadeDTO> converterSetEntidadeEmListDTO(Set<Entidade> entidades);
	public abstract List<EntidadeDTO> converterListEntidadeEmListDTO(List<Entidade> entidades);
}
