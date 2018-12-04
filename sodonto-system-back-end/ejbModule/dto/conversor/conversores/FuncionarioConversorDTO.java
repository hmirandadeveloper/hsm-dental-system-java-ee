package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import negocio.constante.enums.EUf;
import dto.FuncionarioDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Funcionario;


public class FuncionarioConversorDTO implements IConversorDTO<FuncionarioDTO, Funcionario>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EnderecoConversorDTO enderecoConversorDTO;
	private CargoConversorDTO cargoConversorDTO;
	private OperadoraConversorDTO operadoraConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public FuncionarioConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.enderecoConversorDTO = ConversorDTOFactory.getEnderecoConversorDTO();
		this.cargoConversorDTO = ConversorDTOFactory.getCargoConversorDTO();
		this.operadoraConversorDTO = ConversorDTOFactory.getOperadoraConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Funcionario converterDTOEmEntidade(FuncionarioDTO entidadeDTO) {

		Funcionario entidade = new Funcionario();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdFuncionario() != null)
			{
				entidade.setIdFuncionario(entidadeDTO.getIdFuncionario());
			}
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			entidade.setUsuarioPerfil(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioPerfil()));
			entidade.setEndereco(enderecoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEndereco()));
			entidade.setCargo(cargoConversorDTO.converterDTOEmEntidade(entidadeDTO.getCargo()));
			entidade.setOperadoraCel01(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel01()));
			entidade.setOperadoraCel02(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel02()));
			entidade.setOperadoraCel03(operadoraConversorDTO.converterDTOEmEntidade(entidadeDTO.getOperadoraCel03()));
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			entidade.setNome(entidadeDTO.getNome());
			entidade.setSobrenome(entidadeDTO.getSobrenome());
			entidade.setCpf(entidadeDTO.getCpf());
			entidade.setRg(entidadeDTO.getRg());
			entidade.setRgOrgao(entidadeDTO.getRgOrgao());
			entidade.setRgUf(entidadeDTO.getRgUf().name());
			entidade.setPisNit(entidadeDTO.getPisNit());
			entidade.setTelResidencial(entidadeDTO.getTelResidencial());
			entidade.setCel01(entidadeDTO.getCel01());
			entidade.setCel02(entidadeDTO.getCel02());
			entidade.setCel03(entidadeDTO.getCel03());
			entidade.setEmail(entidadeDTO.getEmail());
			entidade.setDataContratacao(entidadeDTO.getDataContratacao());
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		
		return entidade;
	}

	@Override
	public FuncionarioDTO converterEntidadeEmDTO(Funcionario entidade) {
		FuncionarioDTO entidadeDTO = new FuncionarioDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{
			if(entidade.getIdFuncionario() != null)
			{
				entidadeDTO.setIdFuncionario(entidade.getIdFuncionario());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setUsuarioPerfil(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuarioPerfil()));
			entidadeDTO.setEndereco(enderecoConversorDTO.converterEntidadeEmDTO(entidade.getEndereco()));
			entidadeDTO.setCargo(cargoConversorDTO.converterEntidadeEmDTO(entidade.getCargo()));
			entidadeDTO.setOperadoraCel01(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel01()));
			entidadeDTO.setOperadoraCel02(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel02()));
			entidadeDTO.setOperadoraCel03(operadoraConversorDTO.converterEntidadeEmDTO(entidade.getOperadoraCel03()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setNome(entidade.getNome());
			entidadeDTO.setSobrenome(entidade.getSobrenome());
			entidadeDTO.setCpf(entidade.getCpf());
			entidadeDTO.setRg(entidade.getRg());
			entidadeDTO.setRgOrgao(entidade.getRgOrgao());
			entidadeDTO.setRgUf(EUf.valueOf(entidade.getRgUf()));
			entidadeDTO.setPisNit(entidade.getPisNit());
			entidadeDTO.setTelResidencial(entidade.getTelResidencial());
			entidadeDTO.setCel01(entidade.getCel01());
			entidadeDTO.setCel02(entidade.getCel02());
			entidadeDTO.setCel03(entidade.getCel03());
			entidadeDTO.setEmail(entidade.getEmail());
			entidadeDTO.setDataContratacao(entidade.getDataContratacao());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<FuncionarioDTO> converterSetEntidadeEmListDTO(Set<Funcionario> entidades) {

		List<FuncionarioDTO> entidadesDTO = new ArrayList<FuncionarioDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Funcionario entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<FuncionarioDTO> converterListEntidadeEmListDTO(
			List<Funcionario> entidades) {
		List<FuncionarioDTO> entidadesDTO = new ArrayList<FuncionarioDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Funcionario entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
