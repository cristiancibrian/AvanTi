/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Coordinadorareaadministrativa;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Paul Miranda
 */
public class DelegateCoordinadorAreaAdministrativa {

    public DelegateCoordinadorAreaAdministrativa() {
    }

    /**
     * *
     * Este método guarda el registro que se envia como parametro en la base de
     * datos
     *
     * @param coordinador Recibe un registro de Coordinadorareaadministrativa
     * sin id
     */
    public boolean save(Coordinadorareaadministrativa coordinador) {
        Coordinadorareaadministrativa auxCoor;
        boolean bandera = true;
        ServiceLocator.getInstanceCoordinadorAreaAdministrativa().iniciarTransaccion();

        auxCoor = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedureCoordinadorAreaAdministrativas("CALL asignarCoordinadorArea"
                + "(\'" + coordinador.getProfesorPROid().getPROid() + "\' , \'" + coordinador.getAreaAdministrativaAADid().getAADid() + "\')").get(0);

        if (auxCoor != null) {
            for (int x = 0; x < coordinador.getUnidadaprendizajeList().size(); x++) {
                bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL agregarUnidadAprendizajeAreaAdministrativa" + "(\'" + auxCoor.getCOAid() + "\' , \'" + coordinador.getUnidadaprendizajeList().get(x).getUAPid() + "\')");
                if (!bandera) {
                    break;
                }
            }
        } else {
            bandera = false;
        }

        return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().terminarTransaccion(bandera);
    }

    /**
     * *
     * Método para ver todos los registros que hay en la base de datos
     *
     * @return Lista con todos los registros de coordinador de área
     * administrativa
     */
    public List<Coordinadorareaadministrativa> findAll() {
        return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedureCoordinadorAreaAdministrativas("CALL consultarAsignacionesArea()");
    }

    /**
     * *
     * Método para eliminar un registro especifico de la base de datos
     *
     * @param coordinador Registro completo de coordinador de área
     * administrativa
     */
    public boolean delete(Coordinadorareaadministrativa coordinador) {
        boolean bandera;
        ServiceLocator.getInstanceCoordinadorAreaAdministrativa().iniciarTransaccion();
        //return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().deleteAsignacion(coordinador);
        bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL eliminarUnidadesAreaAdministrativa"
                + "(\'" + coordinador.getCOAid() + "\')");

        bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL eliminarAsignacionArea"
                + "(\'" + coordinador.getCOAid() + "\')");

        return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().terminarTransaccion(bandera);
    }

    /**
     * *
     * Método para modificar un registro de la base de datos
     *
     * @param coorninador Registro completo con una diferencia con el original
     */
    public boolean update(Coordinadorareaadministrativa coordinador) {
        boolean bandera;
        ServiceLocator.getInstanceCoordinadorAreaAdministrativa().iniciarTransaccion();

        bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL eliminarUnidadesAreaAdministrativa"
                + "(\'" + coordinador.getCOAid() + "\')");

        if (bandera) {
            bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL modificarCoordinacionArea"
                    + "(\'" + coordinador.getCOAid() + "\' , \'" + coordinador.getProfesorPROid().getPROid() + "\' , \'" + coordinador.getAreaAdministrativaAADid().getAADid() + "\')");
        }

        if (bandera) {
            for (int x = 0; x < coordinador.getUnidadaprendizajeList().size(); x++) {
                bandera = ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedure("CALL agregarUnidadAprendizajeAreaAdministrativa" + "(\'" + coordinador.getCOAid() + "\' , \'" + coordinador.getUnidadaprendizajeList().get(x).getUAPid() + "\')");
                if (!bandera) {
                    break;
                }
            }
        }

        //return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().updateAsignacion(coordinador);
        return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().terminarTransaccion(bandera);
    }

    /**
     * Método para encontrar un registro especifico de coordinador de área
     * administrativa
     *
     * @param idCoordinador Id del registro que se quiere recuperar
     * @return registro de coordinador de área administrativa
     */
    public Coordinadorareaadministrativa find(int idCoordinador) {
        return ServiceLocator.getInstanceCoordinadorAreaAdministrativa().executeProcedureCoordinadorAreaAdministrativas("CALL buscarCoordinadorArea"
                + "(\'" + idCoordinador + "\')").get(0);
    }
}
