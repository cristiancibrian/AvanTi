/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedProperty;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Rol;
import mx.avanti.siract.entity.Unidadacademica;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.helper.CatalogoCTBeanHelper;
import mx.avanti.siract.helper.UnidadAprendizajeBeanHelper;
import mx.avanti.siract.ui.LoginBean;
import org.opensaml.saml2.core.impl.GetCompleteBuilder;


/**
 *
 * @author Usagi
 */
public class NewClass {
    @ManagedProperty(value = "#{loginBean}")
    static private LoginBean loginBean;
    static CatalogoCTBeanHelper ct = new CatalogoCTBeanHelper();
    
    public static void main(String[] args) {
        List<Coordinadorareaadministrativa> caa = ServiceFacadeLocator.getInstanceUsuarioFacade().buscarUsuarioPorId(195).getProfesorList().get(0).getCoordinadorareaadministrativaList();
        if(caa==null)
            System.out.println("F");
        else{
            if(caa.isEmpty())
                System.out.println("mini f");
            else{
                System.out.println(caa.get(0).getAreaAdministrativaAADid().getAADnombre());
            }
                
        }
            //System.out.println(caa.getPROnombre());
        
        /*
        CatalogoCTBeanHelper catalogoCTBeanHelper = new CatalogoCTBeanHelper();
        List<Unidadaprendizaje> unidadesAprendizaje = catalogoCTBeanHelper.getUnidadByUnidadAcademica(195);
        
        
         
        List<Unidadaprendizaje> ap = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadByPECAA("26",195);
        System.out.println("ap: "+ap.size());
        
        
        for(Unidadaprendizaje uap : unidadesAprendizaje){
            if(ap.contains(uap)){
                System.out.println(uap.getUAPnombre());
                
            }   
        }
        
        System.out.println("---------------");
        if(areas.isEmpty())
            System.out.println("efecita");
        else{
            System.out.println(areas.size());
            for(Areaconocimiento area : areas)
                System.out.println(area.getACOnombre());
        }
        /*
        System.out.println("Saiz: "+ap.size());
        for(Unidadaprendizaje uap : ap){
            System.out.println(uap.getUAPnombre());
        }
        
        /*
        */
    }
    
    public static Areaconocimiento getAREA(Unidadaprendizaje upa, Programaeducativo pe){
        List<Areaconocimiento> ara = ct.getAreaPorUAP(upa.getUAPid());
        //List<Programaeducativo> prorgamas = ct.getPEDeAreaConocimiento(upa.getUAPid().toString());
        for(Areaconocimiento araara : ara){
            //System.out.print(araara.getPlanEstudioPESid()+" --- ");
            //System.out.println(araara.getACOnombre());
            if(Objects.equals(pe.getPEDid(), araara.getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid())){
                return araara;
            }
        }
        //Areaconocimiento araara=ct.areaConocimientodeUnidadAprandizajeEnProgramaEducativo(upa.getUAPid().toString(), pe.getPEDid().toString());
        
        //System.out.println("R: "+upa.getUAPnombre()+" - "+upa.getUAPclave()+" - "+pe.getPEDnombre()+" - "+pe.getPEDclave()+" - "+araara.getACOnombre());
        return null;
    }
}
