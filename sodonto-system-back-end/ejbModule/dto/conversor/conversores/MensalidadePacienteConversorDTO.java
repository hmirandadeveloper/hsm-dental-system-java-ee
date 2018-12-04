package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.ESituacaoMensalidadePaciente;

import dto.MensalidadePacienteDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.MensalidadePaciente;


public class MensalidadePacienteConversorDTO implements IConversorDTO<MensalidadePacienteDTO, MensalidadePaciente>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private PlanoPacienteConversorDTO planoPacienteConversorDTO;
	private PlanoMensalidadeConversorDTO planoMensalidadeConversorDTO;
	
	public MensalidadePacienteConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.planoPacienteConversorDTO = ConversorDTOFactory.getPlanoPacienteConversorDTO();
		this.planoMensalidadeConversorDTO = ConversorDTOFactory.getPlanoMensalidadeConversorDTO();
	}
	
	@Override
	public MensalidadePaciente converterDTOEmEntidade(MensalidadePacienteDTO entidadeDTO) {

		MensalidadePaciente entidade = new MensalidadePaciente();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdMensalidadePaciente() != null)
			{
				entidade.setIdMensalidadePaciente(entidadeDTO.getIdMensalidadePaciente());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setPlanoPaciente(planoPacienteConversorDTO.converterDTOEmEntidade(entidadeDTO.getPlanoPaciente()));
			entidade.setPlanoMensalidade(planoMensalidadeConversorDTO.converterDTOEmEntidade(entidadeDTO.getPlanoMensalidade()));
			entidade.setValorPago(entidadeDTO.getValorPago());
			entidade.setSituacao(entidadeDTO.getSituacao().name());
			entidade.setDataMensalidade(entidadeDTO.getDataMensalidade());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public MensalidadePacienteDTO converterEntidadeEmDTO(MensalidadePaciente entidade) {
		MensalidadePacienteDTO entidadeDTO = new MensalidadePacienteDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdMensalidadePaciente() != null)
			{
				entidadeDTO.setIdMensalidadePaciente(entidade.getIdMensalidadePaciente());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setPlanoPaciente(planoPacienteConversorDTO.converterEntidadeEmDTO(entidade.getPlanoPaciente()));
			entidadeDTO.setPlanoMensalidade(planoMensalidadeConversorDTO.converterEntidadeEmDTO(entidade.getPlanoMensalidade()));
			entidadeDTO.setValorPago(entidade.getValorPago());
			entidadeDTO.setSituacao(ESituacaoMensalidadePaciente.valueOf(entidade.getSituacao()));
			entidadeDTO.setDataMensalidade(entidade.getDataMensalidade());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<MensalidadePacienteDTO> converterSetEntidadeEmListDTO(Set<MensalidadePaciente> entidades) {

		List<MensalidadePacienteDTO> entidadesDTO = new ArrayList<MensalidadePacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MensalidadePaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<MensalidadePacienteDTO> converterListEntidadeEmListDTO(
			List<MensalidadePaciente> entidades) {
		List<MensalidadePacienteDTO> entidadesDTO = new ArrayList<MensalidadePacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(MensalidadePaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
