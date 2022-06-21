/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.util;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.ui.FiltrosBeanUI;


@FacesConverter(value = "mx.avanti.siract.util.UnidadAcademicaConverter")
public class UnidadAcademicaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String beerId) {
//        //reporte
//        return unidadAcademica.consultaUnidadAcademica(Integer.parseInt(beerId));
        return new Unidadacademica(Integer.parseInt(beerId));
        
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object unidadAcademica) {
//        return Long.toString(((Unidadacademica)unidadAcademica).getUACid());
            return "32";
    }

}