package dto.validador.template;

import java.util.List;

import negocio.util.GerenciadorAtributo;

public interface IAtributoValidadorEntidade<EntidadeDTO> {
	public abstract GerenciadorAtributo validarAtributosEmEntidade(EntidadeDTO entidadeDTO);
	public abstract boolean validarAtributosEmListaDeEntidades(List<EntidadeDTO> entidadesDTO);
}
