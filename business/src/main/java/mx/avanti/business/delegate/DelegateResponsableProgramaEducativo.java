package mx.avanti.business.delegate;

import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.ResponsableProgramaEducativo;
import mx.avanti.siract.integration.ServiceLocator;

import java.util.List;

/**
 * @author Kevin Arizaga
 */
public class DelegateResponsableProgramaEducativo {

    public void iniciarTransaccion() {
        ServiceLocator.getInstanceResponsableEducativo().iniciarTransaccion();
    }

    public void finalizarTransaccion() {
        ServiceLocator.getInstanceResponsableEducativo().finalizarTransaccion();
    }

    public void save(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceLocator.getInstanceResponsableEducativo().save(responsableProgramaEducativo);
    }

    public void update(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceLocator.getInstanceResponsableEducativo().update(responsableProgramaEducativo);
    }

    public void delete(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceLocator.getInstanceResponsableEducativo().delete(responsableProgramaEducativo);
    }

    public void deleteVersion(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceLocator.getInstanceResponsableEducativo().deleteVersion(responsableProgramaEducativo);
    }

    public List<ResponsableProgramaEducativo> findAll() {
        return ServiceLocator.getInstanceResponsableEducativo().findAll();
    }

    public List<ResponsableProgramaEducativo> findByProfesor(Profesor profesor) {
        return ServiceLocator.getInstanceResponsableEducativo().findByProfesorID(profesor.getPROid());
    }

    public List<ResponsableProgramaEducativo> findByProgramaEducativo(Programaeducativo programaeducativo) {
        return ServiceLocator.getInstanceResponsableEducativo().findByProgramaEducativo(programaeducativo);
    }

    public List<ResponsableProgramaEducativo> findByCicloEscolar(Cicloescolar cicloescolar) {
        return ServiceLocator.getInstanceResponsableEducativo().findRPEbyCicloEscolarID(cicloescolar);
    }

    public ResponsableProgramaEducativo findByID(Integer responsableProgramaEducativoID) {
        return ServiceLocator.getInstanceResponsableEducativo().find(responsableProgramaEducativoID);
    }

    public ResponsableProgramaEducativo findByProfesorProgramaEducativoAndCicloescolar(Profesor profesor, Programaeducativo programaeducativo, Cicloescolar cicloescolar) {
        return ServiceLocator.getInstanceResponsableEducativo().findRpeByData(profesor, programaeducativo, cicloescolar);
    }

    public List<ResponsableProgramaEducativo> findAllRpeActual() {
        return ServiceLocator.getInstanceResponsableEducativo().findAllRpeActual();
    }

    public void secureSave(ResponsableProgramaEducativo responsableProgramaEducativo) {
        ServiceLocator.getInstanceResponsableEducativo().secureSave(responsableProgramaEducativo);
    }

    public ResponsableProgramaEducativo findRPEbyProgramaEducativoAndCicloescolar(Cicloescolar cicloescolar, Programaeducativo programaeducativo) {
        return ServiceLocator.getInstanceResponsableEducativo().findRPEbyCicloAndProgramaEducativoID(cicloescolar.getCESid(), programaeducativo.getPEDid());
    }

    public List<ResponsableProgramaEducativo> findRpeByProfesorAndProgramaEducativo(Profesor profesor, Programaeducativo programaeducativo) {
        return ServiceLocator.getInstanceResponsableEducativo().findRpeByProfesorAndProgramaEducativo(profesor, programaeducativo);
    }

    public ResponsableProgramaEducativo findRpeRecentVersion(Integer prOid, Integer peDid) {
        return ServiceLocator.getInstanceResponsableEducativo().findRpeRecentVersion(prOid, peDid);
    }

    public ResponsableProgramaEducativo findActualRpeByProgramaEducativo(Programaeducativo programaEducativo) {
        return ServiceLocator.getInstanceResponsableEducativo().findActualRpeByProgramaEducativo(programaEducativo);
    }
}
