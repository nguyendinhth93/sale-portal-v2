package com.tp.common.ultima;

import org.primefaces.component.api.Widget;
import org.primefaces.component.menu.AbstractMenu;
import org.primefaces.model.menu.MenuModel;

import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

public class Ultima extends AbstractMenu implements Widget {
    private static final String DEFAULT_RENDERER = "org.primefaces.component.UltimaRenderer";

    public Ultima() {
        this.setRendererType("org.primefaces.component.UltimaRenderer");
    }

    public String getFamily() {
        return "org.primefaces.component";
    }

    public String getWidgetVar() {
        return (String) this.getStateHelper().eval(PropertyKeys.widgetVar, (Object) null);
    }

    public void setWidgetVar(String _widgetVar) {
        this.getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public MenuModel getModel() {
        return (MenuModel) this.getStateHelper().eval(PropertyKeys.model, (Object) null);
    }

    public void setModel(MenuModel _model) {
        this.getStateHelper().put(PropertyKeys.model, _model);
    }

    public String getStyle() {
        return (String) this.getStateHelper().eval(PropertyKeys.style, (Object) null);
    }

    public void setStyle(String _style) {
        this.getStateHelper().put(PropertyKeys.style, _style);
    }

    public String getStyleClass() {
        return (String) this.getStateHelper().eval(PropertyKeys.styleClass, (Object) null);
    }

    public void setStyleClass(String _styleClass) {
        this.getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }

    public String resolveWidgetVar() {
        FacesContext context = this.getFacesContext();
        String userWidgetVar = (String) this.getAttributes().get("widgetVar");
        return userWidgetVar != null
                ? userWidgetVar
                : "widget_"
                + this.getClientId(context)
                .replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
    }

    protected static enum PropertyKeys {
        widgetVar,
        model,
        style,
        styleClass;

        String toString;

        private PropertyKeys(String toString) {
            this.toString = toString;
        }

        private PropertyKeys() {
        }

        public String toString() {
            return this.toString != null ? this.toString : super.toString();
        }
    }
}
