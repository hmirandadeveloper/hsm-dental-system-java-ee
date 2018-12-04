package negocio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import negocio.constante.enums.EMeses;

public abstract class DataUtil {
	
	public static Date setDataInicioDia(Date data)
	{
		Date dataInicioDia = null;
		
		if(data != null)
		{
			dataInicioDia = getDataCompletaPorString(
					getDataFormatada(data) 
					+ " 00:00:00");
		}
		
		return dataInicioDia;
	}
	
	public static Date setDataFinalDia(Date data)
	{
		Date dataFinalDia = null;
		
		if(data != null)
		{
			dataFinalDia = getDataCompletaPorString(
					getDataFormatada(data) 
					+ " 23:59:59");
		}
		
		return dataFinalDia;
	}
	
	public static String getHoraDaData(Date data)
	{
		String hora = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
			
			hora = simpleDateFormat.format(data);
		}
		
		return hora;
	}
	
	public static String getDiaDaData(Date data)
	{
		String dia = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
			
			dia = simpleDateFormat.format(data);
		}
		
		return dia;
	}
	
	public static int getNumDiaDaData(Date data)
	{
		String dia = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
			
			dia = simpleDateFormat.format(data);
		}
		
		return Integer.valueOf(dia);
	}
	
	public static String getMesDaData(Date data)
	{
		String mes = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
			
			mes = simpleDateFormat.format(data);
		}
		
		return mes;
	}
	
	public static int getNumMesDaData(Date data)
	{
		String mes = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
			
			mes = simpleDateFormat.format(data);
		}
		
		return Integer.valueOf(mes);
	}
	
	public static String getMesExtensoDaData(Date data)
	{
		String mes = "VAZIO";
		
		if(data != null)
		{
			GregorianCalendar dataCal = new GregorianCalendar(); 
			dataCal.setTime(data);
			
			mes = EMeses.values()[dataCal.get(Calendar.MONTH)].name();
		}
		
		return mes;
	}
	
	public static String getAnoDaData(Date data)
	{
		String ano = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
			
			ano = simpleDateFormat.format(data);
		}
		
		return ano;
	}
	
	public static int getNumAnoDaData(Date data)
	{
		String ano = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
			
			ano = simpleDateFormat.format(data);
		}
		
		return Integer.valueOf(ano);
	}
	
	public static String getDataFormatadaExtenso(Date data)
	{
		String dataFormatada = "VAZIO";
		if(data != null)
		{
			
			dataFormatada = getDiaDaData(data) + " DE " +
					getMesExtensoDaData(data) + " DE " + getAnoDaData(data);
		}
		
		return dataFormatada;
	}
	
	public static String getDataFormatada(Date data)
	{
		String dataFormatada = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			dataFormatada = simpleDateFormat.format(data);
		}
		
		return dataFormatada;
	}
	
	public static String getDataEHoraFormatada(Date data)
	{
		String dataFormatada = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			
			dataFormatada = simpleDateFormat.format(data);
		}
		
		return dataFormatada;
	}
	
	public static String getHoraPorData(Date data)
	{
		String dataFormatada = "VAZIO";
		
		if(data != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
			
			dataFormatada = simpleDateFormat.format(data);
		}
		
		return dataFormatada;
	}
	
	public static Date getDataPorString(String dataString)
	{
		Date data = null;
		if(dataString != null)
		{
			if(dataString.length() == 10)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					data = dateFormat.parse(dataString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	public static Date getDataCompletaPorString(String dataString)
	{
		Date data = null;
		if(dataString != null)
		{
			if(dataString.length() == 19)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				try {
					data = dateFormat.parse(dataString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	public static boolean isSuperiorDataAtual(Date data)
	{
		if(new Date().getTime() <= (data.getTime() + 65000000))
		{
			return true;
		}
		return false;
	}
	
	public static int getDiferencaDiasPelaDataAtual(Date data)
	{
		Long diferencaData = (new Date().getTime() - data.getTime());
		Long totalDias = (((((diferencaData/1000)/60)/60)/24));
		return Integer.parseInt(totalDias.toString());
	}
	
	public static int getDiferencaHorarioEmHoras(Date horarioI, Date horarioF)
	{
		Long diferencaData = (horarioF.getTime() - horarioI.getTime());
		Long totalDias = ((((diferencaData/1000)/60)/60));
		return Integer.parseInt(totalDias.toString());
	}
	
	public static int getDiferencaHorarioEmMinutos(Date horarioI, Date horarioF)
	{
		Long diferencaData = (horarioF.getTime() - horarioI.getTime());
		Long totalDias = ((diferencaData/1000)/60);
		return Integer.parseInt(totalDias.toString());
	}
	
	public static int getDiferencaHorarioEmSegundos(Date horarioI, Date horarioF)
	{
		Long diferencaData = (horarioF.getTime() - horarioI.getTime());
		Long totalDias = diferencaData/1000;
		return Integer.parseInt(totalDias.toString());
	}
	
	public static Date setDataToFinalDia(Date data)
	{
		String dataCompleta = getDataFormatada(data);
		dataCompleta += " 23:59:00";
		
		return getDataCompletaPorString(dataCompleta);
	}
	
	public static boolean igualDataAtual(Date data)
	{
		
		boolean resultado = false;
		String dataAtual = DataUtil.getDataFormatada(new Date());
		String novaData = DataUtil.getDataFormatada(data);
		
		if(novaData.equals(dataAtual))
		{
			resultado = true;
		}
		
		return resultado;
	}
	
	public static boolean igualMesAtual(Date data)
	{
		
		boolean resultado = false;
		int mes = Integer.valueOf(DataUtil.getMesDaData(data));
		int mesAtual = Integer.valueOf(DataUtil.getMesDaData(new Date()));
		int ano = Integer.valueOf(DataUtil.getAnoDaData(data));
		int anoAtual = Integer.valueOf(DataUtil.getAnoDaData(new Date()));
		
		if(mes == mesAtual && ano == anoAtual)
		{
			resultado = true;
		}
		else
		{
			resultado = false;
		}
		
		return resultado;
	}
	
	public static boolean maiorQueMesAtual(Date data)
	{
		
		boolean resultado = false;
		int mes = Integer.valueOf(DataUtil.getMesDaData(data));
		int mesAtual = Integer.valueOf(DataUtil.getMesDaData(new Date()));
		int ano = Integer.valueOf(DataUtil.getAnoDaData(data));
		int anoAtual = Integer.valueOf(DataUtil.getAnoDaData(new Date()));
		
		if(mes < mesAtual && ano > anoAtual)
		{
			resultado = true;
		}
		else if(mes > mesAtual && ano == anoAtual)
		{
			resultado = true;
		}
		else
		{
			resultado = false;
		}
		
		return resultado;
	}
	
	public static boolean menorQueMesAtual(Date data)
	{
		boolean resultado = false;
		int mes = Integer.valueOf(DataUtil.getMesDaData(data));
		int mesAtual = Integer.valueOf(DataUtil.getMesDaData(new Date()));
		int ano = Integer.valueOf(DataUtil.getAnoDaData(data));
		int anoAtual = Integer.valueOf(DataUtil.getAnoDaData(new Date()));
		
		if(mes < mesAtual && ano <= anoAtual)
		{
			resultado = true;
		}
		else
		{
			resultado = false;
		}
		
		return resultado;
	}
	
	public static Date getDataAtual()
	{
		return new Date();
	}
	
	public static int compararData(Date dataA, Date dataB)
	{
		int anoDataA = Integer.valueOf(getAnoDaData(dataA));
		int anoDataB = Integer.valueOf(getAnoDaData(dataB));
		int mesDataA = Integer.valueOf(getMesDaData(dataA));
		int mesDataB = Integer.valueOf(getMesDaData(dataB));
		int diaDataA = Integer.valueOf(getDiaDaData(dataA));
		int diaDataB = Integer.valueOf(getDiaDaData(dataB));
		
		if(anoDataA < anoDataB)
		{
			return -1;
		}
		
		if(anoDataA > anoDataB)
		{
			return 1;
		}
		
		if(anoDataA == anoDataB)
		{
			if(mesDataA < mesDataB)
			{
				return -1;
			}
			
			if(mesDataA > mesDataB)
			{
				return 1;
			}	
			
			if(mesDataA == mesDataB)
			{
				if(diaDataA < diaDataB)
				{
					return -1;
				}
				
				if(diaDataA > diaDataB)
				{
					return 1;
				}
				
				if(diaDataA == diaDataB)
				{
					return 0;
				}
			}
		}
		
		return 0;
	}
}
