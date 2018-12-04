package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.OperadoraDTO;

public class OperadoraConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		OperadoraDTO operadoraDTO = null; 
		if(value != null && !value.equals(""))
		{
			operadoraDTO = (OperadoraDTO)component.getAttributes().get(value);
		}
		return operadoraDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof OperadoraDTO)
		{
			OperadoraDTO operadoraDTO = (OperadoraDTO)object;
			component.getAttributes().put(operadoraDTO.getNomeOperadora(), operadoraDTO);
			return operadoraDTO.getNomeOperadora();
		}
		return "";
	}

}
