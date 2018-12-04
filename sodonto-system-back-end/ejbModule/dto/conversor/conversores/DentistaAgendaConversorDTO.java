package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.DentistaAgendaDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.DentistaAgenda;


public class DentistaAgendaConversorDTO implements IConversorDTO<DentistaAgendaDTO, DentistaAgenda>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private DentistaConversorDTO dentistaConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public DentistaAgendaConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.dentistaConversorDTO = ConversorDTOFactory.getDentistaConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public DentistaAgenda converterDTOEmEntidade(DentistaAgendaDTO entidadeDTO) {

		DentistaAgenda entidade = new DentistaAgenda();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdDentistaAgenda() != null)
			{
				entidade.setIdDentistaAgenda(entidadeDTO.getIdDentistaAgenda());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setDentista(dentistaConversorDTO.converterDTOEmEntidade(entidadeDTO.getDentista()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			entidade.setDataAgenda(entidadeDTO.getDataAgenda());
			entidade.setHorarioI(entidadeDTO.getHorarioI());
			entidade.setHorarioF(entidadeDTO.getHorarioF());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public DentistaAgendaDTO converterEntidadeEmDTO(DentistaAgenda entidade) {
		DentistaAgendaDTO entidadeDTO = new DentistaAgendaDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{
			if(entidade.getIdDentistaAgenda() != null)
			{
				entidadeDTO.setIdDentistaAgenda(entidade.getIdDentistaAgenda());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setDentista(dentistaConversorDTO.converterEntidadeEmDTO(entidade.getDentista()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setDataAgenda(entidade.getDataAgenda());
			entidadeDTO.setHorarioI(entidade.getHorarioI());
			entidadeDTO.setHorarioF(entidade.getHorarioF());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<DentistaAgendaDTO> converterSetEntidadeEmListDTO(Set<DentistaAgenda> entidades) {

		List<DentistaAgendaDTO> entidadesDTO = new ArrayList<DentistaAgendaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(DentistaAgenda entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<DentistaAgendaDTO> converterListEntidadeEmListDTO(
			List<DentistaAgenda> entidades) {
		List<DentistaAgendaDTO> entidadesDTO = new ArrayList<DentistaAgendaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(DentistaAgenda entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
