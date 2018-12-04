package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EEstadoCivil;
import negocio.constante.enums.ESituacaoPaciente;
import negocio.constante.enums.EUf;
import dto.PacienteDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Paciente;


public class PacienteConversorDTO implements IConversorDTO<PacienteDTO, Paciente>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EnderecoConversorDTO enderecoConversorDTO;
	private OperadoraConversorDTO operadoraConversorDTO;
	private DentistaConversorDTO dentistaConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public PacienteConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.enderecoConversorDTO = ConversorDTOFactory.getEnderecoConversorDTO();
		this.operadoraConversorDTO = ConversorDTOFactory.getOperadoraConversorDTO();
		this.dentistaConversorDTO = ConversorDTOFactory.getDentistaConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Paciente converterDTOEmEntidade(PacienteDTO entidadeDTO) {
		Paciente entidade = new Paciente();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdPaciente() != null)
			{
				entidade.setIdPaciente(entidadeDTO.getIdPaciente());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setEndereco(enderecoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEndereco()));
			entidade.setOperadoraCel01(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel01()));
			entidade.setOperadoraCel02(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel02()));
			entidade.setOperadoraCel03(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel03()));
			entidade.setDentista(dentistaConversorDTO.converterDTOEmEntidade(entidadeDTO.getDentistaDTO()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			if(entidadeDTO.getPaciente() != null)
			{
				if(entidadeDTO.getPaciente().getIdPaciente() != null && !entidadeDTO.isContratante())
				{
					entidade.setPaciente(this.converterDTOEmEntidade(entidadeDTO.getPaciente()));
				}
			}
			entidade.setNome(entidadeDTO.getNome());
			entidade.setSobrenome(entidadeDTO.getSobrenome());
			entidade.setCpf(entidadeDTO.getCpf());
			if(entidadeDTO.getRg() != null && entidadeDTO.getRgOrgao() != null && entidadeDTO.getRgUf() != null)
			{
				entidade.setRg(entidadeDTO.getRg());
				entidade.setRgOrgao(entidadeDTO.getRgOrgao());
				entidade.setRgUf(entidadeDTO.getRgUf().name());
			}
			entidade.setSexo(entidadeDTO.getSexo());
			entidade.setEstadoCivil(entidadeDTO.getEstadoCivil().ordinal());
			entidade.setContratante(entidadeDTO.isContratante());
			entidade.setTelResidencial(entidadeDTO.getTelResidencial());
			entidade.setCel01(entidadeDTO.getCel01());
			entidade.setCel02(entidadeDTO.getCel02());
			entidade.setCel03(entidadeDTO.getCel03());
			entidade.setEmail(entidadeDTO.getEmail());
			entidade.setDataNascimento(entidadeDTO.getDataNascimento());
			entidade.setDataCadastro(entidadeDTO.getDataCadastro());
			entidade.setSituacao(entidadeDTO.getSituacaoPaciente().name());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		System.out.println("[SODONTO SYSTEM][SB][CV-PACIENTE] Conversão de DTO em Entidade Concluída!");
		return entidade;
	}

	@Override
	public PacienteDTO converterEntidadeEmDTO(Paciente entidade) {
		PacienteDTO entidadeDTO = new PacienteDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{
			if(entidade.getIdPaciente() != null)
			{
				entidadeDTO.setIdPaciente(entidade.getIdPaciente());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setEndereco(enderecoConversorDTO.converterEntidadeEmDTO(entidade.getEndereco()));
			entidadeDTO.setOperadoraCel01(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel01()));
			entidadeDTO.setOperadoraCel02(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel02()));
			entidadeDTO.setOperadoraCel03(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel03()));
			entidadeDTO.setDentistaDTO(dentistaConversorDTO.converterEntidadeEmDTO(entidade.getDentista()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			if(entidade.getPaciente() != null)
			{
				if(entidade.getPaciente().getIdPaciente() != null && !entidade.isContratante())
				{
					entidadeDTO.setPaciente(this.converterEntidadeEmDTO(entidade.getPaciente()));
				}
			}
			entidadeDTO.setNome(entidade.getNome());
			entidadeDTO.setSobrenome(entidade.getSobrenome());
			entidadeDTO.setCpf(entidade.getCpf());
			if(entidade.getRg() != null && entidade.getRgOrgao() != null && entidade.getRgUf() != null)
			{
				entidadeDTO.setRg(entidade.getRg());
				entidadeDTO.setRgOrgao(entidade.getRgOrgao());
				entidadeDTO.setRgUf(EUf.valueOf(entidade.getRgUf()));
			}
			
			entidadeDTO.setSexo(entidade.getSexo());
			entidadeDTO.setEstadoCivil(EEstadoCivil.values()[entidade.getEstadoCivil()]);
			entidadeDTO.setContratante(entidade.isContratante());
			entidadeDTO.setTelResidencial(entidade.getTelResidencial());
			entidadeDTO.setCel01(entidade.getCel01());
			entidadeDTO.setCel02(entidade.getCel02());
			entidadeDTO.setCel03(entidade.getCel03());
			entidadeDTO.setEmail(entidade.getEmail());
			entidadeDTO.setDataNascimento(entidade.getDataNascimento());
			entidadeDTO.setDataCadastro(entidade.getDataCadastro());
			entidadeDTO.setSituacaoPaciente(ESituacaoPaciente.valueOf(entidade.getSituacao()));
			entidadeDTO.setDataMov(entidade.getDataMov());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<PacienteDTO> converterSetEntidadeEmListDTO(Set<Paciente> entidades) {

		List<PacienteDTO> entidadesDTO = new ArrayList<PacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Paciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<PacienteDTO> converterListEntidadeEmListDTO(
			List<Paciente> entidades) {
		List<PacienteDTO> entidadesDTO = new ArrayList<PacienteDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Paciente entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
