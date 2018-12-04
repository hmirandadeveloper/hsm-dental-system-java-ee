package dto.validador.validadores;

import java.util.List;

import negocio.util.GerenciadorAtributo;
import dto.FuncionarioDTO;
import dto.validador.factory.AtributoValidadorFactory;
import dto.validador.template.IAtributoValidadorEntidade;

public class FuncionarioAtributoValidador implements IAtributoValidadorEntidade<FuncionarioDTO>{

	private EnderecoAtributoValidador enderecoAtributoValidador;
	private UsuarioAtributoValidador usuarioAtributoValidador;
	
	public FuncionarioAtributoValidador()
	{
		this.enderecoAtributoValidador = AtributoValidadorFactory.getEnderecoAtributoValidador();
		this.usuarioAtributoValidador = AtributoValidadorFactory.getUsuarioAtributoValidador();
	}
	
	@Override
	public GerenciadorAtributo validarAtributosEmEntidade(FuncionarioDTO entidadeDTO) {
		
		GerenciadorAtributo gerenciadorAtributos = new GerenciadorAtributo();
		
		if(entidadeDTO == null)
		{
			gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nulo");
		}
		else
		{
			if(entidadeDTO.getEstabelecimentoDTO() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Estabelecimento");
			}
			if(!this.enderecoAtributoValidador.validarAtributosEmEntidade(entidadeDTO.getEndereco()).isAtributosValidados())
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Informações referentes ao Endereço");
			}
			if(!this.usuarioAtributoValidador.validarAtributosEmEntidade(entidadeDTO.getUsuarioPerfil()).isAtributosValidados())
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Informações referentes ao Usuário do Funcionário");
			}
			if(entidadeDTO.getUsuario() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Usuário Cadastrante");
			}
			if(entidadeDTO.getCargo() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Cargo");
			}
			if(entidadeDTO.getNome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Nome");
			}
			if(entidadeDTO.getSobrenome().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Sobrenome");
			}
			if(entidadeDTO.getCpf().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("CPF");
			}
			if(entidadeDTO.getRg().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("RG");
			}
			if(entidadeDTO.getRgOrgao().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Órgão do RG");
			}
			if(entidadeDTO.getRgUf() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("UF do RG");
			}
			if(entidadeDTO.getPisNit().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("PIS/NIT");
			}
			if(entidadeDTO.getTelResidencial().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Telefone");
			}
			if(entidadeDTO.getCel01().equals("") || 
					entidadeDTO.getOperadoraCel01().getIdOperadora() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Celular");
			}
			if(entidadeDTO.getEmail().equals(""))
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("E-mail");
			}
			if(entidadeDTO.getDataContratacao() == null)
			{
				gerenciadorAtributos.getAtibutosNaoPreenchidos().add("Data de Contratação");
			}
		}
		
		return gerenciadorAtributos;
	}

	@Override
	public boolean validarAtributosEmListaDeEntidades(
			List<FuncionarioDTO> entidadesDTO) {
		boolean validacaoAtributo = true;
		if(entidadesDTO == null)
		{
			validacaoAtributo = false;
		}
		else
		{
			for(FuncionarioDTO dto : entidadesDTO)
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
