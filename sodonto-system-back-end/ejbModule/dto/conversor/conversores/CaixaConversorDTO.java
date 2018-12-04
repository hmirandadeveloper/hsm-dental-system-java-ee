package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.CaixaDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Caixa;


public class CaixaConversorDTO implements IConversorDTO<CaixaDTO, Caixa>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private EstabelecimentoConversorDTO estabelecimentoConversorDTO;
	
	public CaixaConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.estabelecimentoConversorDTO = ConversorDTOFactory.getEstabelecimentoConversorDTO();
	}
	
	@Override
	public Caixa converterDTOEmEntidade(CaixaDTO entidadeDTO) {
		
		System.out.println("[SODONTO SYSTEM][SB][CV-CAIXA] Iniciando conversão...");
		
		Caixa entidade = new Caixa();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			if(entidadeDTO.getIdCaixa() != null)
			{
				entidade.setIdCaixa(entidadeDTO.getIdCaixa());
			}
			System.out.println("1");
			entidade.setUsuarioAbertura(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioAbertura()));
			System.out.println("2");
			entidade.setUsuarioFechamento(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuarioFechamento()));
			System.out.println("3");
			entidade.setEstabelecimento(estabelecimentoConversorDTO.converterDTOEmEntidade(entidadeDTO.getEstabelecimentoDTO()));
			System.out.println("4");
			entidade.setDataCaixa(entidadeDTO.getDataCaixa());
			System.out.println("5");
			entidade.setNumeroOrdem(entidadeDTO.getNumeroOrdem());
			System.out.println("6");
			entidade.setValorTotal(entidadeDTO.getValorTotal());
			System.out.println("7");
			entidade.setObs(entidadeDTO.getObs());
			System.out.println("8");
			entidade.setAtivo(entidadeDTO.isAtivo());
		}
		System.out.println("[SODONTO SYSTEM][SB][CV-CAIXA] Conversão concluída!");
		return entidade;
	}

	@Override
	public CaixaDTO converterEntidadeEmDTO(Caixa entidade) {
		CaixaDTO entidadeDTO = new CaixaDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdCaixa() != null)
			{
				entidadeDTO.setIdCaixa(entidade.getIdCaixa());
			}
			entidadeDTO.setUsuarioAbertura(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuarioAbertura()));
			entidadeDTO.setUsuarioFechamento(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuarioFechamento()));
			entidadeDTO.setEstabelecimentoDTO(estabelecimentoConversorDTO.converterEntidadeEmDTO(entidade.getEstabelecimento()));
			entidadeDTO.setDataCaixa(entidade.getDataCaixa());
			entidadeDTO.setNumeroOrdem(entidade.getNumeroOrdem());
			entidadeDTO.setValorTotal(entidade.getValorTotal());
			entidadeDTO.setObs(entidade.getObs());
			entidadeDTO.setAtivo(entidade.isAtivo());
		}
		
		return entidadeDTO;
	}

	@Override
	public List<CaixaDTO> converterSetEntidadeEmListDTO(Set<Caixa> entidades) {

		List<CaixaDTO> entidadesDTO = new ArrayList<CaixaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Caixa entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<CaixaDTO> converterListEntidadeEmListDTO(
			List<Caixa> entidades) {
		List<CaixaDTO> entidadesDTO = new ArrayList<CaixaDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Caixa entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
