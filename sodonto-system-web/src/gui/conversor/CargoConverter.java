package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.CargoDTO;

public class CargoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		CargoDTO cargoDTO = null; 
		if(value != null && !value.equals(""))
		{
			cargoDTO = (CargoDTO)component.getAttributes().get(value);
		}
		return cargoDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof CargoDTO)
		{
			CargoDTO cargoDTO = (CargoDTO)object;
			component.getAttributes().put(cargoDTO.getNomeCargo(), cargoDTO);
			return cargoDTO.getNomeCargo();
		}
		return "";
	}

}
