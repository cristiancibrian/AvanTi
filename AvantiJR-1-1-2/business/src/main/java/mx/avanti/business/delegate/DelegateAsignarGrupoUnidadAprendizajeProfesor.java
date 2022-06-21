/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Adriana
 */
public class DelegateAsignarGrupoUnidadAprendizajeProfesor {

    private List<Grupo> listaGrupo;
    private List<Unidadaprendizaje> listaUnidadAprendizaje;
    private List<Profesor> listaProfesor;
    private List<Planestudio> listaPlanEstudio;
    private List<Areaconocimiento> listaAreaConocimiento;
    private List<Programaeducativo> listaProgramaEducativo;
    private List<UnidadaprendizajeImparteProfesor> listaimparteProfesors;

    public DelegateAsignarGrupoUnidadAprendizajeProfesor() {
        listaGrupo = new ArrayList<Grupo>();
        listaProfesor = new ArrayList<Profesor>();
        listaUnidadAprendizaje = new ArrayList<Unidadaprendizaje>();
        listaimparteProfesors = new ArrayList<UnidadaprendizajeImparteProfesor>();
    }

    /**
     * Metodo para obtener laas asignaciones por ciclo escolar y programa
     * educativo
     *
     * @param idCiclo id Ciclo escolar
     * @param idPE id Programa educativo
     * @return Lista de Unidades de Aprendizaje Imparte Profesor encontrados
     */
    public List<UnidadaprendizajeImparteProfesor> getAsignacionPorCesPE(int idCiclo, int idPE) {
        
        List<UnidadaprendizajeImparteProfesor> uaip = new ArrayList<>();
        List<UnidadaprendizajeImparteProfesor> uaipCicloEscolar = ServiceLocator.getInstanceCicloEscolarDAO().find(idCiclo).getUnidadaprendizajeImparteProfesorList();
       
        for(UnidadaprendizajeImparteProfesor ua : uaipCicloEscolar){
            if(ua.getGrupoGPOid().getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid()==idPE){
                uaip.add(ua);
            }
        }
        
        return uaip;
    }

    /**
     * Metodo para obtener las asignaciones por Ciclo Escolar y Plan de Estudios
     *
     * @param idCiclo Id Ciclo Escolar
     * @param idPlan Id Plan de Estudios
     * @return Lista de asignaciones encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> getAsignacionPorCesPlan(int idCiclo, int idPlan) {
        List<UnidadaprendizajeImparteProfesor> uaip = new ArrayList<>();
        List<UnidadaprendizajeImparteProfesor> uaipCiclo = ServiceLocator.getInstanceCicloEscolarDAO().find(idCiclo).getUnidadaprendizajeImparteProfesorList();
        
        for(UnidadaprendizajeImparteProfesor ua : uaipCiclo){
            if(ua.getGrupoGPOid().getPlanEstudioPESid().getPESid()==idPlan){
                uaip.add(ua);
            }
        }
        return uaip;        
    }

}
