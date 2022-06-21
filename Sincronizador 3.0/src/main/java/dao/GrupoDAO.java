/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import entidades.Grupo;

import java.util.List;

/**
 * @author ODOSMO
 */
public class GrupoDAO extends AbstractDAO<Integer, Grupo> {

    /**
     * Metodo para obtener Grupos por Unidad de Aprendizaje
     *
     * @param UAClave Clave de la Unidad de Aprendizaje
     * @param PEId    Id del Programa Educativo
     * @param CE      Ciclo escolar
     * @return Lista de Grupos encontrados
     */
    public List<Grupo> consultaGruposporUA(int UAClave, String PEId, String CE) {
        List<Grupo> resultado = null;

        resultado = this.executeQueryHql("select distinct a from Grupo a join a.unidadaprendizajeImparteProfesors b join a.planestudio pe join pe.programaeducativo proe where b.unidadaprendizaje.uapclave=" + UAClave + " and b.cicloescolar.cesid=" + CE + " and proe.pedid=" + PEId);
        return resultado;
    }

    public Grupo getGrupoByGpoNumero(int gponumero) {
        Grupo resultado = null;
        resultado = this.executeQueryUnique("Select * from Grupo where GPOnumero = '" + gponumero + "' ");
        return resultado;
    }

}
