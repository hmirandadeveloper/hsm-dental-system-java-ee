package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.ESituacaoAtendimento;
import dto.AtendimentoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Atendimento;


public class AtendimentoConversorDTO implements IConversorDTO<AtendimentoDTO, Atendimento>{
	
	private AgendamentoConversorDTO agendamentoConversorDTO;
	private UsuarioConversorDTO usuarioConversorDTO;
	
	public AtendimentoConversorDTO()
	{
		this.agendamentoConversorDTO = ConversorDTOFactory.getAgendamentoConversorDTO();
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
	}
	
	@Override
	public Atendimento converterDTOEmEntidade(AtendimentoDTO entidadeDTO) {

		Atendimento entidade = new Atendimento();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdAtendimento() != null)
			{
				entidade.setIdAtendimento(entidadeDTO.getIdAtendimento());
			}
			entidade.setAgendamento(agendamentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getAgendamento()));
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setSituacao(entidadeDTO.getSituacao().name());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public AtendimentoDTO converterEntidadeEmDTO(Atendimento entidade) {
		AtendimentoDTO entidadeDTO = new AtendimentoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdAtendimento() != null)
			{
				entidadeDTO.setIdAtendimento(entidade.getIdAtendimento());
			}
			entidadeDTO.setAgendamento(agendamentoConversorDTO.converterEntidadeEmDTO(entidade.getAgendamento()));
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setSituacao(ESituacaoAtendimento.valueOf(entidade.getSituacao()));
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		return entidadeDTO;
	}

	@Override
	public List<AtendimentoDTO> converterSetEntidadeEmListDTO(Set<Atendimento> entidades) {

		List<AtendimentoDTO> entidadesDTO = new ArrayList<AtendimentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Atendimento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<AtendimentoDTO> converterListEntidadeEmListDTO(
			List<Atendimento> entidades) {
		List<AtendimentoDTO> entidadesDTO = new ArrayList<AtendimentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Atendimento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
