package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.PlanoMensalidadeDTO;

public interface IPlanoMensalidadeFachada {
	public abstract void salvar(PlanoMensalidadeDTO planoMensalidadeDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract PlanoMensalidadeDTO alterar(PlanoMensalidadeDTO planoMensalidadeDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idPlanoMensalidade, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract PlanoMensalidadeDTO buscarPorId(Long idPlanoMensalidade);
	public abstract List<PlanoMensalidadeDTO> buscarPorPlano(Long idPlano);
	public abstract List<PlanoMensalidadeDTO> buscarPorPlanoEMes(Long idPlano, int mes);
	public abstract List<PlanoMensalidadeDTO> buscarAtivos();
	public abstract List<PlanoMensalidadeDTO> buscarInativos();
	public abstract PlanoMensalidadeDTO getEntidadeFromList(List<PlanoMensalidadeDTO> lista);
}
