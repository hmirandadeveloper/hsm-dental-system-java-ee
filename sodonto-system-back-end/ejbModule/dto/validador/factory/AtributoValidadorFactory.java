package dto.validador.factory;

import dto.validador.validadores.AgendamentoAtributoValidador;
import dto.validador.validadores.AtendimentoAtributoValidador;
import dto.validador.validadores.AtributoOperacionalAtributoValidador;
import dto.validador.validadores.CaixaAtributoValidador;
import dto.validador.validadores.CargoAtributoValidador;
import dto.validador.validadores.DentistaAgendaAtributoValidador;
import dto.validador.validadores.DentistaAtributoValidador;
import dto.validador.validadores.EnderecoAtributoValidador;
import dto.validador.validadores.EspecialidadeAtributoValidador;
import dto.validador.validadores.EspecialidadeDentistaAtributoValidador;
import dto.validador.validadores.EstabelecimentoAtributoValidador;
import dto.validador.validadores.FuncionarioAtributoValidador;
import dto.validador.validadores.LogOperacionalAtributoValidador;
import dto.validador.validadores.MensalidadePacienteAtributoValidador;
import dto.validador.validadores.MovimentacaoAtributoValidador;
import dto.validador.validadores.MsgPreEmailAtributoValidador;
import dto.validador.validadores.MsgPreTorpedoAtributoValidador;
import dto.validador.validadores.ObsPacienteAtributoValidador;
import dto.validador.validadores.OperadoraAtributoValidador;
import dto.validador.validadores.PacienteAtributoValidador;
import dto.validador.validadores.PlanoAtributoValidador;
import dto.validador.validadores.PlanoMensalidadeAtributoValidador;
import dto.validador.validadores.PlanoPacienteAtributoValidador;
import dto.validador.validadores.UsuarioAtributoValidador;

public abstract class AtributoValidadorFactory {
	
	public static AgendamentoAtributoValidador getAgendamentoAtributoValidador()
	{
		return new AgendamentoAtributoValidador();
	}
	
	public static AtendimentoAtributoValidador getAtendimentoAtributoValidador()
	{
		return new AtendimentoAtributoValidador();
	}
	
	public static AtributoOperacionalAtributoValidador getAtributoOperacionalAtributoValidador()
	{
		return new AtributoOperacionalAtributoValidador();
	}
	
	public static CaixaAtributoValidador getCaixaAtributoValidador()
	{
		return new CaixaAtributoValidador();
	}
	
	public static CargoAtributoValidador getCargoAtributoValidador()
	{
		return new CargoAtributoValidador();
	}
	
	public static DentistaAgendaAtributoValidador getDentistaAgendaAtributoValidador()
	{
		return new DentistaAgendaAtributoValidador();
	}
	
	public static DentistaAtributoValidador getDentistaAtributoValidador()
	{
		return new DentistaAtributoValidador();
	}
	
	public static EnderecoAtributoValidador getEnderecoAtributoValidador()
	{
		return new EnderecoAtributoValidador();
	}
	
	public static EspecialidadeAtributoValidador getEspecialidadeAtributoValidador()
	{
		return new EspecialidadeAtributoValidador();
	}
	
	public static EspecialidadeDentistaAtributoValidador getEspecialidadeDentistaAtributoValidador()
	{
		return new EspecialidadeDentistaAtributoValidador();
	}
	
	public static EstabelecimentoAtributoValidador getEstabelecimentoAtributoValidador()
	{
		return new EstabelecimentoAtributoValidador();
	}
	
	public static FuncionarioAtributoValidador getFuncionarioAtributoValidador()
	{
		return new FuncionarioAtributoValidador();
	}
	
	public static LogOperacionalAtributoValidador getLogOperacionalAtributoValidador()
	{
		return new LogOperacionalAtributoValidador();
	}
	
	public static MensalidadePacienteAtributoValidador getMensalidadePacienteAtributoValidador()
	{
		return new MensalidadePacienteAtributoValidador();
	}
	
	public static MovimentacaoAtributoValidador getMovimentacaoAtributoValidador()
	{
		return new MovimentacaoAtributoValidador();
	}
	
	public static MsgPreEmailAtributoValidador getMsgPreEmailAtributoValidador()
	{
		return new MsgPreEmailAtributoValidador();
	}
	
	public static MsgPreTorpedoAtributoValidador getMsgPreTorpedoAtributoValidador()
	{
		return new MsgPreTorpedoAtributoValidador();
	}
	
	public static ObsPacienteAtributoValidador getObsPacienteAtributoValidador()
	{
		return new ObsPacienteAtributoValidador();
	}
	
	public static OperadoraAtributoValidador getOperadoraAtributoValidador()
	{
		return new OperadoraAtributoValidador();
	}
	
	public static PacienteAtributoValidador getPacienteAtributoValidador()
	{
		return new PacienteAtributoValidador();
	}
	
	public static PlanoAtributoValidador getPlanoAtributoValidador()
	{
		return new PlanoAtributoValidador();
	}
	
	public static PlanoMensalidadeAtributoValidador getPlanoMensalidadeAtributoValidador()
	{
		return new PlanoMensalidadeAtributoValidador();
	}
	
	public static PlanoPacienteAtributoValidador getPlanoPacienteAtributoValidador()
	{
		return new PlanoPacienteAtributoValidador();
	}
	
	public static UsuarioAtributoValidador getUsuarioAtributoValidador()
	{
		return new UsuarioAtributoValidador();
	}
}
