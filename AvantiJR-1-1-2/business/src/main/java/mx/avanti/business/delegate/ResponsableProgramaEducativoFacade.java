/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import mx.avanti.business.delegate.DelegateResponsableProgramaEducativo;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.ResponsableProgramaEducativo;

import java.util.List;

/**
 * @author Kevin Arizaga
 */
public class ResponsableProgramaEducativoFacade {
    private final DelegateResponsableProgramaEducativo delegateResponsableProgramaEducativo;

    public ResponsableProgramaEducativoFacade() {
        delegateResponsableProgramaEducativo = new DelegateResponsableProgramaEducativo();
    }

    public void agregarRPE(ResponsableProgramaEducativo responsableProgramaEducativo) {
        delegateResponsableProgramaEducativo.save(responsableProgramaEducativo);
    }

    public void modificarRPE(ResponsableProgramaEducativo responsableProgramaEducativo) {
        delegateResponsableProgramaEducativo.update(responsableProgramaEducativo);
    }

    public void eliminarRPE(ResponsableProgramaEducativo responsableProgramaEducativo) {
        delegateResponsableProgramaEducativo.delete(responsableProgramaEducativo);
    }

    public void eliminarRpeVersion(ResponsableProgramaEducativo responsableProgramaEducativo) {
        delegateResponsableProgramaEducativo.deleteVersion(responsableProgramaEducativo);
    }

    public List<ResponsableProgramaEducativo> getListaResponsableProgramaEducativo() {
        return delegateResponsableProgramaEducativo.findAll();
    }

    public List<ResponsableProgramaEducativo> findByPE(Programaeducativo programaeducativo) {
        return delegateResponsableProgramaEducativo.findByProgramaEducativo(programaeducativo);
    }

    public List<ResponsableProgramaEducativo> findByProfesor(Profesor profesor) {
        return delegateResponsableProgramaEducativo.findByProfesor(profesor);
    }

    public ResponsableProgramaEducativo findRPEById(Integer responsableProgramaEducativoID) {
        return delegateResponsableProgramaEducativo.findByID(responsableProgramaEducativoID);
    }

    public ResponsableProgramaEducativo findRPEbyProfesorProgramaEducativoAndCicloescolar(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        return delegateResponsableProgramaEducativo.findByProfesorProgramaEducativoAndCicloescolar(profesor, programaeducativo, cicloescolar);
    }

    public List<ResponsableProgramaEducativo> findRpeByCicloEscolar(Cicloescolar cicloescolar) {
        return delegateResponsableProgramaEducativo.findByCicloEscolar(cicloescolar);
    }

    public void iniciarTransaccion() {
        delegateResponsableProgramaEducativo.iniciarTransaccion();
    }

    public void finalizarTransaccion() {
        delegateResponsableProgramaEducativo.finalizarTransaccion();
    }

    public void secureSave(ResponsableProgramaEducativo responsableProgramaEducativo) {
        delegateResponsableProgramaEducativo.secureSave(responsableProgramaEducativo);
    }

    public ResponsableProgramaEducativo findRPEbyProgramaEducativoAndCicloescolar(Cicloescolar ciclo, Programaeducativo programaEducativo) {
        return delegateResponsableProgramaEducativo.findRPEbyProgramaEducativoAndCicloescolar(ciclo, programaEducativo);
    }

    public List<ResponsableProgramaEducativo> findRpeByProfesorAndProgramaEducativo(Profesor profesor, Programaeducativo programaeducativo) {
        return delegateResponsableProgramaEducativo.findRpeByProfesorAndProgramaEducativo(profesor, programaeducativo);
    }

    public ResponsableProgramaEducativo findRpeRecentVersion(Integer prOid, Integer peDid) {
        return delegateResponsableProgramaEducativo.findRpeRecentVersion(prOid, peDid);
    }

    public ResponsableProgramaEducativo findActualRpeByProgramaEducativo(Programaeducativo programaEducativo) {
        return delegateResponsableProgramaEducativo.findActualRpeByProgramaEducativo(programaEducativo);
    }
}