package com.tp.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by hopnv on 24/07/2017.
 */
@FacesConverter("upperCaseConverter")
public class UpperCaseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        return (submittedValue != null) ? submittedValue.toUpperCase() : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        return (modelValue != null) ? modelValue.toString() : "";
    }

}
