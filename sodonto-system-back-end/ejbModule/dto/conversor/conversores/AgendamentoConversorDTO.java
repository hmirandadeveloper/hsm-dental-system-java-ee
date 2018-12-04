package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.ESituacaoAgendamento;
import dto.AgendamentoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Agendamento;


public class AgendamentoConversorDTO implements IConversorDTO<AgendamentoDTO, Agendamento>{
	
	private PacienteConversorDTO pacienteConversorDTO;
	private DentistaAgendaConversorDTO dentistaAgendaConversorDTO;
	private UsuarioConversorDTO usuarioConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public AgendamentoConversorDTO()
	{
		this.pacienteConversorDTO = ConversorDTOFactory.getPacienteConversorDTO();
		this.dentistaAgendaConversorDTO = ConversorDTOFactory.getDentistaAgendaConversorDTO();
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Agendamento converterDTOEmEntidade(AgendamentoDTO entidadeDTO) {

		Agendamento entidade = new Agendamento();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdAgendamento() != null)
			{
				entidade.setIdAgendamento(entidadeDTO.getIdAgendamento());
			}
			entidade.setPaciente(pacienteConversorDTO.converterDTOEmEntidade(entidadeDTO.getPaciente()));
			entidade.setDentistaAgenda(dentistaAgendaConversorDTO.converterDTOEmEntidade(entidadeDTO.getDentistaAgenda()));
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setUsuarioCriacao(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioCriacaoDTO()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			entidade.setDataCriacao(entidadeDTO.getDataCriacao());
			entidade.setSituacao(entidadeDTO.getSituacao().name());
			entidade.setRemarcacao(entidadeDTO.isRemarcacao());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public AgendamentoDTO converterEntidadeEmDTO(Agendamento entidade) {
		AgendamentoDTO entidadeDTO = new AgendamentoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdAgendamento() != null)
			{
				entidadeDTO.setIdAgendamento(entidade.getIdAgendamento());
			}
			entidadeDTO.setPaciente(pacienteConversorDTO.converterEntidadeEmDTO(entidade.getPaciente()));
			entidadeDTO.setDentistaAgenda(dentistaAgendaConversorDTO.converterEntidadeEmDTO(entidade.getDentistaAgenda()));
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setUsuarioCriacaoDTO(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuarioCriacao()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setDataCriacao(entidade.getDataCriacao());
			entidadeDTO.setSituacao(ESituacaoAgendamento.valueOf(entidade.getSituacao()));
			entidadeDTO.setRemarcacao(entidade.isRemarcacao());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<AgendamentoDTO> converterSetEntidadeEmListDTO(Set<Agendamento> entidades) {

		List<AgendamentoDTO> entidadesDTO = new ArrayList<AgendamentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Agendamento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<AgendamentoDTO> converterListEntidadeEmListDTO(
			List<Agendamento> entidades) {
		List<AgendamentoDTO> entidadesDTO = new ArrayList<AgendamentoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Agendamento entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
