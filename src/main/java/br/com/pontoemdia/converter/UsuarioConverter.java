package br.com.pontoemdia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.pontoemdia.model.Usuario;

@FacesConverter(value="usuarioConverter")
public class UsuarioConverter implements Converter<Usuario> {

    @Override
    public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
    	Usuario user = new Usuario();
    	user.setLogin(value);
		return user;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Usuario value) {
    	
    	if(value.toString().isEmpty()) {
    		return "";
    	}
    	
    	Usuario user = (Usuario) value;
		return user.getLogin();
    }
}
