package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.PlanoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Plano;


public class PlanoConversorDTO implements IConversorDTO<PlanoDTO, Plano>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public PlanoConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Plano converterDTOEmEntidade(PlanoDTO entidadeDTO) {
		Plano entidade = new Plano();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdPlano() != null)
			{
				entidade.setIdPlano(entidadeDTO.getIdPlano());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			entidade.setNomePlano(entidadeDTO.getNomePlano());
			entidade.setDescricaoPlano(entidadeDTO.getDescricaoPlano());
			entidade.setTotalMeses(entidadeDTO.getTotalMeses());
			entidade.setValorTotal(entidadeDTO.getValorTotal());
			entidade.setValidade(entidadeDTO.getValidade());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public PlanoDTO converterEntidadeEmDTO(Plano entidade) {
		PlanoDTO entidadeDTO = new PlanoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdPlano() != null)
			{
				entidadeDTO.setIdPlano(entidade.getIdPlano());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setNomePlano(entidade.getNomePlano());
			entidadeDTO.setDescricaoPlano(entidade.getDescricaoPlano());
			entidadeDTO.setTotalMeses(entidade.getTotalMeses());
			entidadeDTO.setValorTotal(entidade.getValorTotal());
			entidadeDTO.setValidade(entidade.getValidade());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<PlanoDTO> converterSetEntidadeEmListDTO(Set<Plano> entidades) {

		List<PlanoDTO> entidadesDTO = new ArrayList<PlanoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Plano entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<PlanoDTO> converterListEntidadeEmListDTO(
			List<Plano> entidades) {
		List<PlanoDTO> entidadesDTO = new ArrayList<PlanoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Plano entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
