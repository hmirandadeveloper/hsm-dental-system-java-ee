package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.MsgPreEmailDTO;

public interface IMsgPreEmailFachada {
	public abstract void salvar(MsgPreEmailDTO msgPreEmailDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract MsgPreEmailDTO alterar(MsgPreEmailDTO msgPreEmailDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idMsgPreEmail, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract MsgPreEmailDTO buscarPorId(Long idMsgPreEmail);
	public abstract List<MsgPreEmailDTO> buscarPorDescricao(String descricao);
	public abstract List<MsgPreEmailDTO> buscarAtivos();
	public abstract List<MsgPreEmailDTO> buscarInativos();
	public abstract MsgPreEmailDTO getEntidadeFromList(List<MsgPreEmailDTO> lista);
}
