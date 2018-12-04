package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.AtendimentoDTO;

public interface IAtendimentoFachada {
	public abstract void salvar(AtendimentoDTO atendimentoDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException;
	public abstract AtendimentoDTO alterar(AtendimentoDTO atendimentoDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idAtendimento, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract AtendimentoDTO buscarPorId(Long idAtendimento);
	public abstract List<AtendimentoDTO> buscarPorAgendamento(Long idAgendamento);
	public abstract List<AtendimentoDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento);
	public abstract List<AtendimentoDTO> buscarPorPaciente(Long idPaciente, Long idEstabelecimento);
	public abstract List<AtendimentoDTO> buscarPorSituacao(String situacao, Long idEstabelecimento);
	public abstract List<AtendimentoDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento);
	public abstract List<AtendimentoDTO> buscarAtivos(Long idEstabelecimento);
	public abstract List<AtendimentoDTO> buscarInativos(Long idEstabelecimento);
	public abstract AtendimentoDTO getEntidadeFromList(List<AtendimentoDTO> lista);
}
