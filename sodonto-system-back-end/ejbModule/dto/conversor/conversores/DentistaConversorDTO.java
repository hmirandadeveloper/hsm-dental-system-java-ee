package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EUf;
import dto.DentistaDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Dentista;


public class DentistaConversorDTO implements IConversorDTO<DentistaDTO, Dentista>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public DentistaConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Dentista converterDTOEmEntidade(DentistaDTO entidadeDTO) {

		Dentista entidade = new Dentista();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdDentista() != null)
			{
				entidade.setIdDentista(entidadeDTO.getIdDentista());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			entidade.setNome(entidadeDTO.getNome());
			entidade.setSobrenome(entidadeDTO.getSobrenome());
			entidade.setCpf(entidadeDTO.getCpf());
			entidade.setCroUf(entidadeDTO.getCroUf().name());
			entidade.setCro(entidadeDTO.getCro());
			entidade.setPisNit(entidadeDTO.getPisNit());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public DentistaDTO converterEntidadeEmDTO(Dentista entidade) {
		DentistaDTO entidadeDTO = new DentistaDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{
			if(entidade.getIdDentista() != null)
			{
				entidadeDTO.setIdDentista(entidade.getIdDentista());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setNome(entidade.getNome());
			entidadeDTO.setSobrenome(entidade.getSobrenome());
			entidadeDTO.setCpf(entidade.getCpf());
			entidadeDTO.setCroUf(EUf.valueOf(entidade.getCroUf()));
			entidadeDTO.setCro(entidade.getCro());
			entidadeDTO.setPisNit(entidade.getPisNit());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<DentistaDTO> converterSetEntidadeEmListDTO(Set<Dentista> entidades) {

		List<DentistaDTO> entidadesDTO = new ArrayList<DentistaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Dentista entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<DentistaDTO> converterListEntidadeEmListDTO(
			List<Dentista> entidades) {
		List<DentistaDTO> entidadesDTO = new ArrayList<DentistaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Dentista entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
