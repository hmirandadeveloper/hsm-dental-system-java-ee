package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.EstabelecimentoDTO;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.template.IAtributoValidadorEntidade;

public class EstabelecimentoAtributoValidador implements IAtributoValidadorEntidade<EstabelecimentoDTO>{

	private EnderecoAtributoValidador enderecoAtributoValidador;
	
	public EstabelecimentoAtributoValidador()
	{
		this.enderecoAtributoValidador = AtributoValidadorFactory.getEnderecoAtributoValidador();
	}
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(EstabelecimentoDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(!this.enderecoAtributoValidador.validarAtributosEmEntidade(entidadeDTO.getEnderecoDTO()).isAtributosValidados())
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Endereço");
			}
			if(entidadeDTO.getUsuarioDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getCnpj().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CNPJ");
			}
			if(entidadeDTO.getRazaoSocial().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Razão Social");
			}
			if(entidadeDTO.getNomeFantasia().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome Fantasia");
			}
			if(entidadeDTO.getTelefone().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Telefone");
			}
			if(entidadeDTO.getEmail().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("E-mail");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<EstabelecimentoDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(EstabelecimentoDTO dto : entidadesDTO)
			{
				if(!validarAtributosEmEntidade(dto).isAtributosValidados())
				{
					validacaoAtributo = false;
					break;
				}
			}
		}
		
		return validacaoAtributo;
	}

}
