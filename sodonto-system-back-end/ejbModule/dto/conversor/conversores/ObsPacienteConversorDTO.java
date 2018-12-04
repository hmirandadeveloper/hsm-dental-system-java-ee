package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.ETipoObsPaciente;

import dto.ObsPacienteDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.ObsPaciente;


public class ObsPacienteConversorDTO implements IConversorDTO<ObsPacienteDTO, ObsPaciente>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private PacienteConversorDTO pacienteConversorDTO;
	
	public ObsPacienteConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.pacienteConversorDTO = ConversorDTOFactory.getPacienteConversorDTO();
	}
	
	@Override
	public ObsPaciente converterDTOEmEntidade(ObsPacienteDTO entidadeDTO) {

		ObsPaciente entidade = new ObsPaciente();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdObsPaciente() != null)
			{
				entidade.setIdObsPaciente(entidadeDTO.getIdObsPaciente());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioDTO()));
			entidade.setPaciente(pacienteConversorDTO.converterDTOEmEntidade(entidadeDTO.getPacienteDTO()));
			entidade.setData(entidadeDTO.getData());
			entidade.setObs(entidadeDTO.getObs());
			entidade.setTipo(entidadeDTO.getTipo().name());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public ObsPacienteDTO converterEntidadeEmDTO(ObsPaciente entidade) {
		ObsPacienteDTO entidadeDTO = new ObsPacienteDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdObsPaciente() != null)
			{
				entidadeDTO.setIdObsPaciente(entidade.getIdObsPaciente());
			}
			entidadeDTO.setUsuarioDTO(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setPacienteDTO(pacienteConversorDTO.converterEntidadeEmDTO(entidade.getPaciente()));
			entidadeDTO.setData(entidade.getData());
			entidadeDTO.setObs(entidade.getObs());
			entidadeDTO.setTipo(ETipoObsPaciente.valueOf(entidade.getTipo()));
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<ObsPacienteDTO> converterSetEntidadeEmListDTO(Set<ObsPaciente> entidades) {

		List<ObsPacienteDTO> entidadesDTO = new ArrayList<ObsPacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(ObsPaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<ObsPacienteDTO> converterListEntidadeEmListDTO(
			List<ObsPaciente> entidades) {
		List<ObsPacienteDTO> entidadesDTO = new ArrayList<ObsPacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(ObsPaciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
