package br.com.caelum.fj91.rh.util.jsf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("LocalDateConverter")
public class LocalDateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		
		return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		
		LocalDate localDate = (LocalDate) value;
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}
