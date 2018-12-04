package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.PlanoPacienteDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.PlanoPaciente;


public class PlanoPacienteConversorDTO implements IConversorDTO<PlanoPacienteDTO, PlanoPaciente>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private PacienteConversorDTO pacienteConversorDTO;
	private PlanoConversorDTO planoConversorDTO;
	
	public PlanoPacienteConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.pacienteConversorDTO = ConversorDTOFactory.getPacienteConversorDTO();
		this.planoConversorDTO = ConversorDTOFactory.getPlanoConversorDTO();
	}
	
	@Override
	public PlanoPaciente converterDTOEmEntidade(PlanoPacienteDTO entidadeDTO) {

		PlanoPaciente entidade = new PlanoPaciente();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdPlanoPaciente() != null)
			{
				entidade.setIdPlanoPaciente(entidadeDTO.getIdPlanoPaciente());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setPaciente(pacienteConversorDTO.converterDTOEmEntidade(entidadeDTO.getPaciente()));
			entidade.setPlano(planoConversorDTO.converterDTOEmEntidade(entidadeDTO.getPlano()));
			entidade.setDataAssinatura(entidadeDTO.getDataAssinatura());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public PlanoPacienteDTO converterEntidadeEmDTO(PlanoPaciente entidade) {
		PlanoPacienteDTO entidadeDTO = new PlanoPacienteDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdPlanoPaciente() != null)
			{
				entidadeDTO.setIdPlanoPaciente(entidade.getIdPlanoPaciente());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setPaciente(pacienteConversorDTO.converterEntidadeEmDTO(entidade.getPaciente()));
			entidadeDTO.setPlano(planoConversorDTO.converterEntidadeEmDTO(entidade.getPlano()));
			entidadeDTO.setDataAssinatura(entidade.getDataAssinatura());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<PlanoPacienteDTO> converterSetEntidadeEmListDTO(Set<PlanoPaciente> entidades) {

		List<PlanoPacienteDTO> entidadesDTO = new ArrayList<PlanoPacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(PlanoPaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<PlanoPacienteDTO> converterListEntidadeEmListDTO(
			List<PlanoPaciente> entidades) {
		List<PlanoPacienteDTO> entidadesDTO = new ArrayList<PlanoPacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(PlanoPaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
