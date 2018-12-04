package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EProvedorSMS;
import dto.AtributoOperacionalDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.operacional.AtributoOperacional;


public class AtributoOperacionalConversorDTO implements IConversorDTO<AtributoOperacionalDTO, AtributoOperacional>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public AtributoOperacionalConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public AtributoOperacional converterDTOEmEntidade(AtributoOperacionalDTO entidadeDTO) {

		AtributoOperacional entidade = new AtributoOperacional();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdAtributoOperacional() != null)
			{
				entidade.setIdAtributoOperacional(entidadeDTO.getIdAtributoOperacional());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioDTO()));
			entidade.setTitulo(entidadeDTO.getTitulo());
			entidade.setSelecionado(entidadeDTO.isSelecionado());
			entidade.setDuracaoAtendimento(entidadeDTO.getDuracaoAtendimento());
			entidade.setTempoValidacaoTelefone(entidadeDTO.getTempoValidacaoTelefone());
			entidade.setNumeroLinhaTabela(entidadeDTO.getNumeroLinhaTabela());
			entidade.setLimitQuery(entidadeDTO.getLimitQuery());
			entidade.setProvedorSMS(entidadeDTO.getProvedorSMS().ordinal());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public AtributoOperacionalDTO converterEntidadeEmDTO(AtributoOperacional entidade) {
		AtributoOperacionalDTO entidadeDTO = new AtributoOperacionalDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdAtributoOperacional() != null)
			{
				entidadeDTO.setIdAtributoOperacional(entidade.getIdAtributoOperacional());
			}
			entidadeDTO.setUsuarioDTO(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setTitulo(entidade.getTitulo());
			entidadeDTO.setSelecionado(entidade.isSelecionado());
			entidadeDTO.setDuracaoAtendimento(entidade.getDuracaoAtendimento());
			entidadeDTO.setTempoValidacaoTelefone(entidade.getTempoValidacaoTelefone());
			entidadeDTO.setNumeroLinhaTabela(entidade.getNumeroLinhaTabela());
			entidadeDTO.setLimitQuery(entidade.getLimitQuery());
			entidadeDTO.setProvedorSMS(EProvedorSMS.values()[entidade.getProvedorSMS()]);
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<AtributoOperacionalDTO> converterSetEntidadeEmListDTO(Set<AtributoOperacional> entidades) {

		List<AtributoOperacionalDTO> entidadesDTO = new ArrayList<AtributoOperacionalDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(AtributoOperacional entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<AtributoOperacionalDTO> converterListEntidadeEmListDTO(
			List<AtributoOperacional> entidades) {
		List<AtributoOperacionalDTO> entidadesDTO = new ArrayList<AtributoOperacionalDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(AtributoOperacional entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
