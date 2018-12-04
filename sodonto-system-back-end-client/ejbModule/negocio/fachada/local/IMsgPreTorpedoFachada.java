package negocio.fachada.local;

import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.MsgPreTorpedoDTO;

public interface IMsgPreTorpedoFachada {
	public abstract void salvar(MsgPreTorpedoDTO msgPreTorpedoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract MsgPreTorpedoDTO alterar(MsgPreTorpedoDTO msgPreTorpedoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idMsgPreTorpedo, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract MsgPreTorpedoDTO buscarPorId(Long idMsgPreTorpedo);
	public abstract List<MsgPreTorpedoDTO> buscarPorDescricao(String descricao);
	public abstract List<MsgPreTorpedoDTO> buscarAtivos();
	public abstract List<MsgPreTorpedoDTO> buscarInativos();
	public abstract MsgPreTorpedoDTO getEntidadeFromList(List<MsgPreTorpedoDTO> lista);
}
