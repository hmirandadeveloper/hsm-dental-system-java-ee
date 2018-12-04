package dto.conversor.conversores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dto.MovimentacaoDTO;
import dto.conversor.factory.ConversorDTOFactory;
import dto.conversor.template.IConversorDTO;
import entidade.Movimentacao;


public class MovimentacaoConversorDTO implements IConversorDTO<MovimentacaoDTO, Movimentacao>{
	
	private UsuarioConversorDTO usuarioConversorDTO;
	private CaixaConversorDTO caixaConversorDTO;
	
	public MovimentacaoConversorDTO()
	{
		this.usuarioConversorDTO = ConversorDTOFactory.getUsuarioConversorDTO();
		this.caixaConversorDTO = ConversorDTOFactory.getCaixaConversorDTO();
	}
	
	@Override
	public Movimentacao converterDTOEmEntidade(MovimentacaoDTO entidadeDTO) {
		System.out.println("1");
		Movimentacao entidade = new Movimentacao();
		if(entidadeDTO == null)
		{
			entidade = null;
		}
		else
		{
			System.out.println("2");
			if(entidadeDTO.getIdMovimentacao() != null)
			{
				entidade.setIdMovimentacao(entidadeDTO.getIdMovimentacao());
			}
			System.out.println("3");
			entidade.setUsuario(usuarioConversorDTO.converterDTOEmEntidade(entidadeDTO.getUsuario()));
			System.out.println("4");
			entidade.setCaixa(caixaConversorDTO.converterDTOEmEntidade(entidadeDTO.getCaixa()));
			System.out.println("5");
			entidade.setTipo(entidadeDTO.getTipo());
			System.out.println("6");
			entidade.setValor(entidadeDTO.getValor());
			System.out.println("7");
			entidade.setRefMovimentacao(entidadeDTO.getRefMovimentacao());
			System.out.println("8");
			entidade.setObs(entidadeDTO.getObs());
			System.out.println("9");
			entidade.setAtivo(entidadeDTO.getAtivo());
		}
		System.out.println("[SODONTO SYSTEM][SB][MOVIMENTACAO] Conversão em Entidade Concluída!");
		return entidade;
	}

	@Override
	public MovimentacaoDTO converterEntidadeEmDTO(Movimentacao entidade) {
		MovimentacaoDTO entidadeDTO = new MovimentacaoDTO();
		if(entidade == null)
		{
			entidadeDTO = null;
		}
		else
		{

			if(entidade.getIdMovimentacao() != null)
			{
				entidadeDTO.setIdMovimentacao(entidade.getIdMovimentacao());
			}
			entidadeDTO.setUsuario(usuarioConversorDTO.converterEntidadeEmDTO(entidade.getUsuario()));
			entidadeDTO.setCaixa(caixaConversorDTO.converterEntidadeEmDTO(entidade.getCaixa()));
			entidadeDTO.setTipo(entidade.getTipo());
			entidadeDTO.setValor(entidade.getValor());
			entidadeDTO.setRefMovimentacao(entidade.getRefMovimentacao());
			entidadeDTO.setObs(entidade.getObs());
			entidadeDTO.setAtivo(entidade.getAtivo());
		}
		
		System.out.println("[SODONTO SYSTEM][SB][MOVIMENTACAO] Conversão em DTO Concluída!");
		return entidadeDTO;
	}

	@Override
	public List<MovimentacaoDTO> converterSetEntidadeEmListDTO(Set<Movimentacao> entidades) {

		List<MovimentacaoDTO> entidadesDTO = new ArrayList<MovimentacaoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Movimentacao entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

	@Override
	public List<MovimentacaoDTO> converterListEntidadeEmListDTO(
			List<Movimentacao> entidades) {
		List<MovimentacaoDTO> entidadesDTO = new ArrayList<MovimentacaoDTO>();
		if(entidades == null)
		{
			entidadesDTO = null;
		}
		else
		{
			for(Movimentacao entidade : entidades)
			{
				entidadesDTO.add(converterEntidadeEmDTO(entidade));
			}
		}
		return entidadesDTO;
	}

}
