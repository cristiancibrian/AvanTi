/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateGrupo;
import mx.avanti.siract.entity.Grupo;

/**
 *
 * @author ODOSMO
 */
public class GrupoFacade {

    private final DelegateGrupo delegateGrupo;

    public GrupoFacade() {
        delegateGrupo = new DelegateGrupo();
    }

    /**
     * Metodo para buscar un Grupo por Id
     *
     * @param id Id del Grupo
     * @return Grupo encontrado
     */
    
   
    public Grupo buscarGrupo(int id){
  
         return delegateGrupo.getGrupoById(id);
    }
    
    /**
     * Metodo para consultar Grupos
     *
     * @return Lista de todos los Grupos
     */
    public List<Grupo> consultarListaGrupo() {
        return delegateGrupo.getGrupos();
    }
    

    /**
     * Metodo para agregar un Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
    public void agregarGrupo(Grupo grupo) {
        delegateGrupo.saveGrupo(grupo);
    }

    /**
     * Metodo para modificar un Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
    public void modificarGrupo(Grupo grupo) {
        delegateGrupo.updateGrupo(grupo);
    }

    /**
     * Metodo para elimnar un Grupo
     *
     * @param grupo Objeto de tipo Grupo
     */
    public void eliminarGrupo(Grupo grupo) {
        delegateGrupo.deleteGrupo(grupo);
    }

    /**
     * Metodo para obtener Grupos por Unidad de Aprendizaje
     *
     * @param UAClave Clave de la Unidad de Aprendizaje
     * @param PEId Id del Programa Educativo
     * @param CE Ciclo Escolar
     * @return Lista de Grupos encontrados
     */
    public List<Grupo> getGruposPorUA(int UAClave, String PEId, String CE) {
        return delegateGrupo.getGruposByUA(UAClave, PEId, CE);
    }

}
