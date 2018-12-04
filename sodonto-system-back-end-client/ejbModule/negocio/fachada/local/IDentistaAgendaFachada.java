package negocio.fachada.local;

import java.util.Date;
import java.util.List;

import negocio.constante.enums.EPerfilUsuario;
import negocio.exception.AtributoIncompletoException;
import negocio.exception.DataInvalidaException;
import negocio.exception.EntidadeCadastradaException;
import negocio.exception.EntidadeInexistenteException;
import dto.DentistaAgendaDTO;

public interface IDentistaAgendaFachada {
	public abstract void salvar(DentistaAgendaDTO dentistaAgendaDTO) 
			throws EntidadeCadastradaException, AtributoIncompletoException,
			DataInvalidaException;
	public abstract DentistaAgendaDTO alterar(DentistaAgendaDTO dentistaAgendaDTO) 
			throws EntidadeInexistenteException, AtributoIncompletoException, 
			EntidadeCadastradaException;
	public abstract void inativar(Long idDentistaAgenda, EPerfilUsuario perfilUsuario) 
			throws EntidadeInexistenteException;
	public abstract DentistaAgendaDTO buscarPorId(Long idDentistaAgenda);
	public abstract List<DentistaAgendaDTO> buscarPorData(Date dataI, Date dataF, Long idEstabelecimento);
	public abstract List<DentistaAgendaDTO> buscarPorDentista(Long idDentista, Long idEstabelecimento);
	public abstract List<DentistaAgendaDTO> buscarPorDentistaEData(Long idDentista, Date data, Long idEstabelecimento);
	public abstract List<DentistaAgendaDTO> buscarPorDentistaDataEHorario(Long idDentista, Date data, Date horarioI, Date horarioF, Long idEstabelecimento);
	public abstract List<DentistaAgendaDTO> buscarAtivos(Long idEstabelecimento);
	public abstract List<DentistaAgendaDTO> buscarInativos(Long idEstabelecimento);
	public abstract DentistaAgendaDTO getEntidadeFromList(List<DentistaAgendaDTO> lista);
}
