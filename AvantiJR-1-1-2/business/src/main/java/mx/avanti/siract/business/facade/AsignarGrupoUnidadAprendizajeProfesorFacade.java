/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateAsignarGrupoUnidadAprendizajeProfesor;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;

/**
 *
 * @author Adriana
 */
public class AsignarGrupoUnidadAprendizajeProfesorFacade {

    DelegateAsignarGrupoUnidadAprendizajeProfesor delegateAsignarGrupoUnidadAprendizajeProfesor = new DelegateAsignarGrupoUnidadAprendizajeProfesor();

    public AsignarGrupoUnidadAprendizajeProfesorFacade() {

    }

    /**
     * Metodo para obtener las asignaciones por Ciclo Escolar y Programa
     * Educativo
     *
     * @param idCiclo Id del Ciclo Escolar
     * @param idPE Id del Programa Educativo
     * @return Lista de asignaciones encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> getAsignacionPorCesPE(int idCiclo, int idPE) {
        return delegateAsignarGrupoUnidadAprendizajeProfesor.getAsignacionPorCesPE(idCiclo, idPE);
    }

    /**
     * Metodo para obtener las asignaciones por Ciclo escolar y Plan de Estudios
     *
     * @param idCiclo Id del Ciclo Escolar
     * @param idPlan Id del Plan de Estudios
     * @return Lista de asignacones encontradas
     */
    public List<UnidadaprendizajeImparteProfesor> getAsignacionPorCesPlan(int idCiclo, int idPlan) {
        return delegateAsignarGrupoUnidadAprendizajeProfesor.getAsignacionPorCesPlan(idCiclo, idPlan);
    }

}
