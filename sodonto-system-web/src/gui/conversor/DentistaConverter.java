package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.DentistaDTO;

public class DentistaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		DentistaDTO dentistaDTO = null; 
		if(value != null && !value.equals(""))
		{
			dentistaDTO = (DentistaDTO)component.getAttributes().get(value);
		}
		return dentistaDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof DentistaDTO)
		{
			DentistaDTO dentistaDTO = (DentistaDTO)object;
			component.getAttributes().put(dentistaDTO.getCroFormatado(), dentistaDTO);
			return dentistaDTO.getCroFormatado();
		}
		return "";
	}

}
