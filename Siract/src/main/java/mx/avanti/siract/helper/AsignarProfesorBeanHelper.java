/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
/**
 *
 * @author Samanta Rdgz
 * @author Noe_Lopez
 */
public class AsignarProfesorBeanHelper implements Serializable {
    //private AsignarProfesorDelegate asignarProfesorDeleagate;
    
    private Profesor selecProfesor;
    private Areaconocimiento areac;
    private Profesor[] profesoresSeleccionados;
    private Areaconocimiento[] areasSeleccionados;
    //CRITERIA
    private Planestudio planestudiocriteria;
    private Areaconocimiento areaconocimientocriteria;
    private Programaeducativo programaeducativocriteria;
    private Profesor profesorcriteria;

    public AsignarProfesorBeanHelper() {
        try {
            //this.asignarProfesorDeleagate = new AsignarProfesorDelegate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        selecProfesor = new Profesor();
        areac = new Areaconocimiento();
        
        //CRITERIA
        planestudiocriteria = new Planestudio();
        planestudiocriteria.setPESid(0);
        areaconocimientocriteria = new Areaconocimiento();
        areaconocimientocriteria.setACOid(0);
        programaeducativocriteria = new Programaeducativo();
        programaeducativocriteria.setPEDid(0);
        profesorcriteria = new Profesor();
        profesorcriteria.setPROid(0);
    }
    /*public AsignarProfesorDelegate getAsignarProfesorDeleagate() {
        return asignarProfesorDeleagate;
    }*/
    public Profesor getSelecProfesor() {
        return selecProfesor;
    }
    public void setSelecProfesor(Profesor selecProfesor) {
        this.selecProfesor = selecProfesor;
    }
    public Areaconocimiento getAreac() {
        return areac;
    }
    public void setAreac(Areaconocimiento areac) {
        this.areac = areac;
    }
    public Profesor[] getProfesoresSeleccionados() {
        return profesoresSeleccionados;
    }
    public void setProfesoresSeleccionados(Profesor[] profesoresSeleccionados) {
        this.profesoresSeleccionados = profesoresSeleccionados;
    }
    public Areaconocimiento[] getAreasSeleccionados() {
        return areasSeleccionados;
    }
    public void setAreasSeleccionados(Areaconocimiento[] areasSeleccionados) {
        this.areasSeleccionados = areasSeleccionados;
    }
    //CRITERIA
    public Planestudio getPlanestudiocriteria() {
        return planestudiocriteria;
    }
    public void setPlanestudiocriteria(Planestudio planestudiocriteria) {
        this.planestudiocriteria = planestudiocriteria;
    }
    public Areaconocimiento getAreaconocimientocriteria() {
        return areaconocimientocriteria;
    }
    public void setAreaconocimientocriteria(Areaconocimiento areaconocimientocriteria) {
        this.areaconocimientocriteria = areaconocimientocriteria;
    }
    public Programaeducativo getProgramaeducativocriteria() {
        return programaeducativocriteria;
    }
    public void setProgramaeducativocriteria(Programaeducativo programaeducativocriteria) {
        this.programaeducativocriteria = programaeducativocriteria;
    }
    public Profesor getProfesorcriteria() {
        return profesorcriteria;
    }
    public void setProfesorcriteria(Profesor profesorcriteria) {
        this.profesorcriteria = profesorcriteria;
    }
    /**
     * ASIGNAR PROFESOR
     * @return 
     */
    public Profesor[] asignar() {
        Profesor prof[] = getProfesoresSeleccionados();
        Areaconocimiento areacon[] = getAreasSeleccionados();
        for (int x = 0; x < prof.length; x++) {
            System.out.println("profesor: " + prof[x].getPROnombre());
        }
        for (int x = 0; x < prof.length; x++) {
            try {
                //asignarProfesorDeleagate.agregarProfesor(prof[x]);
                ServiceFacadeLocator.getInstanceAsignarProfesorFacade().agregarProfesor(prof[x]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prof;
    }
    /**
     * Obtener planes por programa
     * @return 
     */
    public List<Planestudio> getPlanesByPrograma() {
        if (programaeducativocriteria.getPEDid()!= 0) {
            planestudiocriteria.setPESid(0);
            areaconocimientocriteria.setACOid(0);
            //return getAsignarProfesorDeleagate().getPlanesByPrograma(programaeducativocriteria.getPedid());
             return programaeducativocriteria.getPlanestudioList();
            //return ServiceFacadeLocator.getInstanceAsignarProfesorFacade().getPlanesByPrograma(programaeducativocriteria.getPEDid());
        } else {
            planestudiocriteria.setPESid(0);
            return new ArrayList<Planestudio>();
        }
    }    
    /**
     * Obtener profesor 
     * @return 
     */
    public List<Profesor> getProfesor(){
        if(programaeducativocriteria.getPEDid()!= 0){
            profesorcriteria.setPROid(0);
            //return getAsignarProfesorDeleagate().getProfesor(programaeducativocriteria.getPedid());
            return ServiceFacadeLocator.getInstanceAsignarProfesorFacade().getProfesor(programaeducativocriteria.getPEDid());
        } else {
            profesorcriteria.setPROid(0);
            return new ArrayList<Profesor>();
        }
    }
}