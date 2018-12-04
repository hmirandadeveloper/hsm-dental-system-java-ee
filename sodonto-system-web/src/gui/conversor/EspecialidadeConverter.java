package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.EspecialidadeDTO;

public class EspecialidadeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		EspecialidadeDTO especialidadeDTO = null; 
		if(value != null && !value.equals(""))
		{
			especialidadeDTO = (EspecialidadeDTO)component.getAttributes().get(value);
		}
		return especialidadeDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof EspecialidadeDTO)
		{
			EspecialidadeDTO especialidadeDTO = (EspecialidadeDTO)object;
			component.getAttributes().put(especialidadeDTO.getNomeEspecialidade(), especialidadeDTO);
			return especialidadeDTO.getNomeEspecialidade();
		}
		return "";
	}

}
