package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EUf;
import dto.EnderecoDTO;
import dto.conversor.template.IConversorDTO;
import entidade.Endereco;


public class EnderecoConversorDTO implements IConversorDTO<EnderecoDTO, Endereco>{

	@Override
	public Endereco converterDTOEmEntidade(EnderecoDTO entidadeDTO) {

		Endereco entidade = new Endereco();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdEndereco() != null)
			{
				entidade.setIdEndereco(entidadeDTO.getIdEndereco());
			}
			entidade.setLogradouro(entidadeDTO.getLogradouro());
			entidade.setNumero(entidadeDTO.getNumero());
			entidade.setComplemento(entidadeDTO.getComplemento());
			entidade.setBairro(entidadeDTO.getBairro());
			entidade.setCidade(entidadeDTO.getCidade());
			entidade.setUf(entidadeDTO.getUf().name());
			entidade.setCep(entidadeDTO.getCep());
		}
		
		return entidade;
	}

	@Override
	public EnderecoDTO converterEntidadeEmDTO(Endereco entidade) {
		EnderecoDTO entidadeDTO = new EnderecoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdEndereco() != null)
			{
				entidadeDTO.setIdEndereco(entidade.getIdEndereco());
			}
			entidadeDTO.setLogradouro(entidade.getLogradouro());
			entidadeDTO.setNumero(entidade.getNumero());
			entidadeDTO.setComplemento(entidade.getComplemento());
			entidadeDTO.setBairro(entidade.getBairro());
			entidadeDTO.setCidade(entidade.getCidade());
			entidadeDTO.setUf(EUf.valueOf(entidade.getUf()));
			entidadeDTO.setCep(entidade.getCep());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<EnderecoDTO> converterSetEntidadeEmListDTO(Set<Endereco> entidades) {

		List<EnderecoDTO> entidadesDTO = new ArrayList<EnderecoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Endereco entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<EnderecoDTO> converterListEntidadeEmListDTO(
			List<Endereco> entidades) {
		List<EnderecoDTO> entidadesDTO = new ArrayList<EnderecoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Endereco entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
