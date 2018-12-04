package gui.managedbeans;

import gui.managedbeans.generics.ManagedBeanGenericBasic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import negocio.fachada.local.ILogOperacionalFachada;
import negocio.util.DataUtil;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import dao.util.Filtro;
import dto.LogOperacionalDTO;

@ManagedBean
@ViewScoped
public class LogOperacionalMB extends ManagedBeanGenericBasic{
	
	public static final String MBName = "|CARGO-MB|";
	
	@EJB
	private ILogOperacionalFachada logOperacionalSB;
	
	//Filtros de Busca
	private String nomeFuncionarioFiltro;
	private Date dataFiltro;
	private int filtroFoco;
	
	//Lazy Data Models
	private LazyDataModel<LogOperacionalDTO> modelPorFuncionario;
	private LazyDataModel<LogOperacionalDTO> modelPorData;
	
	//Data Table
	private DataTable dataTable;
	
	public LogOperacionalMB()
	{
		this.modelPorFuncionario = 
				new LazyDataModel<LogOperacionalDTO>() 
		
		{
			private static final long serialVersionUID = 1L;
					
			@Override
			public List<LogOperacionalDTO> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				
				Filtro filtro = new Filtro();
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				
				setRowCount(logOperacionalSB.buscarTotalPorFuncionarioNome(nomeFuncionarioFiltro));
				
				return logOperacionalSB.buscarPorFuncionarioNome(nomeFuncionarioFiltro, filtro);
			}		
		};
		
		this.modelPorData = 
				new LazyDataModel<LogOperacionalDTO>() 
		
		{
			private static final long serialVersionUID = 1L;
					
			@Override
			public List<LogOperacionalDTO> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, String> filters) {
				
				Filtro filtro = new Filtro();
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				
				setRowCount(logOperacionalSB.buscarTotalPorData(dataFiltro));
				
				return logOperacionalSB.buscarPorData(dataFiltro, filtro);
			}		
		};
	}
	
	public void limparMB()
	{
		this.nomeFuncionarioFiltro = null;
		this.dataFiltro = null;
	}
	
	public void limparNomeFuncionarioFiltro()
	{
		this.nomeFuncionarioFiltro = null;
	}
	
	public void limparDataFiltro()
	{
		this.dataFiltro = null;
	}
	
	public void selecionarFiltro()
	{
		if(this.filtroFoco == 0)
		{
			limparNomeFuncionarioFiltro();
		}
		else if(this.filtroFoco == 1)
		{
			limparDataFiltro();
		}
	}
	
	public int getCurrentPage()
	{	
		return this.dataTable != null ? this.dataTable.getPage() : 0;
	}
	
	public LazyDataModel<LogOperacionalDTO> getFiltrados()
	{

		if(!getNomeFuncionarioFiltro().equals(""))
		{
			setNomeFuncionarioFiltro(!getNomeFuncionarioFiltro().equals("") ? "%" + this.nomeFuncionarioFiltro + "%" : "");
			return buscarPorFuncionarioNome();
		}
		else if (getDataFiltro() != null)
		{

			return buscarPorData();
		}
		else
		{
			return null;
		}
	}
	
	public LazyDataModel<LogOperacionalDTO> buscarPorFuncionarioNome()
	{
		return this.modelPorFuncionario;
	}
	
	public LazyDataModel<LogOperacionalDTO> buscarPorData()
	{
		return this.modelPorData;
	}

	public String getNomeFuncionarioFiltro() {
		if(nomeFuncionarioFiltro == null)
		{
			this.nomeFuncionarioFiltro = "";
		}
		return nomeFuncionarioFiltro;
	}

	public void setNomeFuncionarioFiltro(String nomeFuncionarioFiltro) {
		this.nomeFuncionarioFiltro = nomeFuncionarioFiltro;
	}

	public Date getDataFiltro() {
		if(dataFiltro == null && getNomeFuncionarioFiltro().equals(""))
		{
			dataFiltro = DataUtil.getDataAtual();
		}
		
		return dataFiltro;
	}

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}

	public int getFiltroFoco() {
		return filtroFoco;
	}

	public void setFiltroFoco(int filtroFoco) {
		this.filtroFoco = filtroFoco;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
}
