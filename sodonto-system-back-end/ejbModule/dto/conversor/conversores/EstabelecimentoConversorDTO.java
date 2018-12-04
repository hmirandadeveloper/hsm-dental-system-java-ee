package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.EstabelecimentoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Estabelecimento;


public class EstabelecimentoConversorDTO implements IConversorDTO<EstabelecimentoDTO, Estabelecimento>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EnderecoConversorDTO enderecoConversorDTO;
	
	public EstabelecimentoConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.enderecoConversorDTO = ConversorDTOFactory.getEnderecoConversorDTO();
	}
	
	@Override
	public Estabelecimento converterDTOEmEntidade(EstabelecimentoDTO entidadeDTO) {

		Estabelecimento entidade = new Estabelecimento();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdEstabelecimento() != null)
			{
				entidade.setIdEstabelecimento(entidadeDTO.getIdEstabelecimento());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioDTO()));
			entidade.setEndereco(enderecoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEnderecoDTO()));
			entidade.setCnpj(entidadeDTO.getCnpj());
			entidade.setRazaoSocial(entidadeDTO.getRazaoSocial());
			entidade.setNomeFantasia(entidadeDTO.getNomeFantasia());
			entidade.setTelefone(entidadeDTO.getTelefone());
			entidade.setFax(entidadeDTO.getFax());
			entidade.setEmail(entidadeDTO.getEmail());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public EstabelecimentoDTO converterEntidadeEmDTO(Estabelecimento entidade) {
		EstabelecimentoDTO entidadeDTO = new EstabelecimentoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{
			if(entidade.getIdEstabelecimento() != null)
			{
				entidadeDTO.setIdEstabelecimento(entidade.getIdEstabelecimento());
			}
			entidadeDTO.setUsuarioDTO(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setEnderecoDTO(enderecoConversorDTO.converterEntidadeEmDTO(entidade.getEndereco()));
			entidadeDTO.setCnpj(entidade.getCnpj());
			entidadeDTO.setRazaoSocial(entidade.getRazaoSocial());
			entidadeDTO.setNomeFantasia(entidade.getNomeFantasia());
			entidadeDTO.setTelefone(entidade.getTelefone());
			entidadeDTO.setFax(entidade.getFax());
			entidadeDTO.setEmail(entidade.getEmail());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<EstabelecimentoDTO> converterSetEntidadeEmListDTO(Set<Estabelecimento> entidades) {

		List<EstabelecimentoDTO> entidadesDTO = new ArrayList<EstabelecimentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Estabelecimento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<EstabelecimentoDTO> converterListEntidadeEmListDTO(
			List<Estabelecimento> entidades) {
		List<EstabelecimentoDTO> entidadesDTO = new ArrayList<EstabelecimentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Estabelecimento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
