package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dto.PlanoMensalidadeDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Plano;
import entidade.PlanoMensalidade;


public class PlanoMensalidadeConversorDTO implements IConversorDTO<PlanoMensalidadeDTO, PlanoMensalidade>{
	
	private PlanoConversorDTO planoConversorDTO;
	
	public PlanoMensalidadeConversorDTO()
	{
		this.planoConversorDTO = ConversorDTOFactory.getPlanoConversorDTO();
	}
	
	@Override
	public PlanoMensalidade converterDTOEmEntidade(PlanoMensalidadeDTO entidadeDTO) {

		PlanoMensalidade entidade = new PlanoMensalidade();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdPlanoMensalidade() != null)
			{
				entidade.setIdPlanoMensalidade(entidadeDTO.getIdPlanoMensalidade());
			}
			entidade.setPlano(planoConversorDTO.converterDTOEmEntidade(entidadeDTO.getPlano()));
			entidade.setMes(entidadeDTO.getMes());
			entidade.setValorMes(entidadeDTO.getValorMes());
			entidade.setValorReajustado(entidadeDTO.getValorReajustado());
			entidade.setObs(entidadeDTO.getObs());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public PlanoMensalidadeDTO converterEntidadeEmDTO(PlanoMensalidade entidade) {
		PlanoMensalidadeDTO entidadeDTO = new PlanoMensalidadeDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdPlanoMensalidade() != null)
			{
				entidadeDTO.setIdPlanoMensalidade(entidade.getIdPlanoMensalidade());
			}
			entidadeDTO.setPlano(planoConversorDTO.converterEntidadeEmDTO(entidade.getPlano()));
			entidadeDTO.setMes(entidade.getMes());
			entidadeDTO.setValorMes(entidade.getValorMes());
			entidadeDTO.setValorReajustado(entidade.getValorReajustado());
			entidadeDTO.setObs(entidade.getObs());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<PlanoMensalidadeDTO> converterSetEntidadeEmListDTO(Set<PlanoMensalidade> entidades) {

		List<PlanoMensalidadeDTO> entidadesDTO = new ArrayList<PlanoMensalidadeDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(PlanoMensalidade entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<PlanoMensalidadeDTO> converterListEntidadeEmListDTO(
			List<PlanoMensalidade> entidades) {
		List<PlanoMensalidadeDTO> entidadesDTO = new ArrayList<PlanoMensalidadeDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(PlanoMensalidade entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}
	
	public Set<PlanoMensalidade> converterListDTOEmSetEntidade(
			List<PlanoMensalidadeDTO> entidadesDTO, Plano plano) {
		Set<PlanoMensalidade> entidades = new HashSet<PlanoMensalidade>();
		if(entidadesDTO == null)
		{
			entidades = null;
		}
		else
		{
			for(PlanoMensalidadeDTO entidadeDTO : entidadesDTO)
			{
				entidades.add(converterDTOEmEntidade(entidadeDTO));
			}
		}
		return entidades;
	}

}
