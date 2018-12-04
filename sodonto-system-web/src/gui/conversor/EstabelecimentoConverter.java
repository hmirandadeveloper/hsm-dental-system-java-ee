package gui.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import dto.EstabelecimentoDTO;

public class EstabelecimentoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		EstabelecimentoDTO estabelecimentoDTO = null; 
		if(value != null && !value.equals(""))
		{
			estabelecimentoDTO = (EstabelecimentoDTO)component.getAttributes().get(value);
		}
		return estabelecimentoDTO;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object object) {
		if(object != null && object instanceof EstabelecimentoDTO)
		{
			EstabelecimentoDTO estabelecimentoDTO = (EstabelecimentoDTO)object;
			component.getAttributes().put(estabelecimentoDTO.getCnpj(), estabelecimentoDTO);
			return estabelecimentoDTO.getCnpj();
		}
		return "";
	}

}
