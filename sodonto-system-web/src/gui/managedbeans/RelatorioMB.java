package gui.managedbeans;

import gui.managedbeans.atributo.ConstantesSodontoSystem;
import gui.managedbeans.generics.ManagedBeanGenericBasic;
import gui.util.Conexao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import negocio.constante.mensagens.MensagemErro;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import dto.EstabelecimentoDTO;
import dto.FuncionarioDTO;
import dto.factory.DTOFactory;

@ManagedBean
@ViewScoped
public class RelatorioMB extends ManagedBeanGenericBasic {
	
	public static final String MBName = "|RELATÓRIO-MB|";
	
	private EstabelecimentoDTO estabelecimentoSelecionadoDTO;
	private Date dataInicial;
	private Date dataFinal;
	private FacesContext facesContext;
	private ServletContext scontext;
	
	public RelatorioMB()
	{
		this.facesContext = FacesContext.getCurrentInstance();
		this.facesContext.responseComplete();
		this.scontext = (ServletContext) this.facesContext
				.getExternalContext().getContext();
	}
	
	public FuncionarioDTO getFuncionarioLogado()
	{
		return super.getFuncionarioLogado();
	}

	private void gerarRelatorio(String arquivoJasper, Map<String, Object> parametros)
	{
		if(!getKeyValidation().isActive())
		{
			errorKey();
			return;
		}
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.responseComplete();
		ServletContext scontext = (ServletContext) facesContext
				.getExternalContext().getContext();

		Connection con = null;
		try {
			con = Conexao.getConexao();
		} catch (SQLException e1) {
			
			reportarErroAoAnalista(MBName + " GERAR RELATÓRIO", e1);
			
			e1.printStackTrace();
			
		}
		byte[] pdf = null;
		try {
			pdf = JasperRunManager
					.runReportToPdf(
							scontext.getRealPath("/WEB-INF/relatorios/" + arquivoJasper + ".jasper"),
							parametros, con);
		} catch (JRException e) {
			
			reportarErroAoAnalista(MBName + " GERAR RELATÓRIO", e);
			
			e.printStackTrace();
		}
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		/* Retorna um fluxo para enviar dados ao navegador */
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			
			reportarErroAoAnalista(MBName + " GERAR RELATÓRIO", e);
			
			e.printStackTrace();
		}
		/* Mostra o arquivo no navegador */
		try {
			out.write(pdf);
		} catch (IOException e) {
			
			reportarErroAoAnalista(MBName + " GERAR RELATÓRIO", e);
			
			e.printStackTrace();
		}
	}
	
	public void gerarRelatorioAgendamentoCancelado()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null && getDataFinal() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("DATA_F", getDataFinal());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_agendamento_cancelado", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioAtendimentoDentista()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null && getDataFinal() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("DATA_F", getDataFinal());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_atendimento_dentista", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioAtendimentoFinalizado()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null && getDataFinal() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("DATA_F", getDataFinal());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_atendimento_finalizado", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioMarcacaoVolta()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_marcacao_volta", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioPacienteAgendado()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			
			gerarRelatorio("rl_paciente_agendado", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioPacienteFaltoso()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_paciente_faltoso", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioPlanoPaciente()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null && getDataFinal() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("DATA_F", getDataFinal());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_plano_paciente", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public void gerarRelatorioPacienteNaoReagendado()
	{
		if(getEstabelecimentoSelecionadoDTO().getIdEstabelecimento() != null && 
				getDataInicial() != null)
		{
			Map<String, Object> parametros = new HashMap<String, Object>();  
			parametros.put("GESTOR_NOME", getFuncionarioLogado().getNomeCompleto());	
			parametros.put("AMBIENTE_SISTEMA", ConstantesSodontoSystem.SISTEMA_AMBIENTE);	
			parametros.put("MODULO_SISTEMA", "RELATÓRIOS");	
			parametros.put("VERSAO_SISTEMA", ConstantesSodontoSystem.SISTEMA_VERSAO);
			parametros.put("DATA_I", getDataInicial());
			parametros.put("ID_ESTABELECIMENTO", getEstabelecimentoSelecionadoDTO().getIdEstabelecimento());
			parametros.put("IMG", this.scontext.getRealPath("/WEB-INF/relatorios/img/logo.jpg"));
			
			gerarRelatorio("rl_paciente_nao_reagendado", parametros);
		}
		else
		{
			enviarMenssagemErro(MensagemErro.MSG_ERRO_ENT_ATRIBUTO);
		}
	}
	
	public EstabelecimentoDTO getEstabelecimentoSelecionadoDTO() {
		if(this.estabelecimentoSelecionadoDTO == null)
		{
			this.estabelecimentoSelecionadoDTO = DTOFactory.getEstabelecimentoDTO();
		}
		
		return estabelecimentoSelecionadoDTO;
	}

	public void setEstabelecimentoSelecionadoDTO(
			EstabelecimentoDTO estabelecimentoSelecionadoDTO) {
		this.estabelecimentoSelecionadoDTO = estabelecimentoSelecionadoDTO;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
}
