package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.CaixaDTO;

public class CaixaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		CaixaDTO caixaDTO = null; 
		if(value != null && !value.equals(""))
		{
			caixaDTO = (CaixaDTO)component.getAttributes().get(value);
		}
		return caixaDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof CaixaDTO)
		{
			CaixaDTO caixaDTO = (CaixaDTO)object;
			component.getAttributes().put(caixaDTO.getCaixaDados(), caixaDTO);
			return caixaDTO.getCaixaDados();
		}
		return "";
	}

}
