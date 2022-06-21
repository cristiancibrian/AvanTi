/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author ferna
 */
public class DelegateAlumnoPerteneceGrupo {

    public List<AlumnoPerteneceGrupo> findgrupos(int id){
        return ServiceLocator.getInstanceAlumnoPerteneceGrupoDAO().findalumnoporgrupos(id);
    }
    
public List<AlumnoPerteneceGrupo> findAlumnosGrupo(int ce,int uip,int gpo){
        List<AlumnoPerteneceGrupo> result = new ArrayList();
        result = ServiceLocator.getInstanceAlumnoPerteneceGrupoDAO().executeProcedure("CALL ObtenerAlumnosdegGrupo (\'" + ce + "\', \'" + uip + "\', \'" +gpo+ "\')");
        if(isNull(result)){
            System.out.println("<Error en la consulta por usuario de profesor>");
            return null;
        }else if(result.isEmpty()){
            return result;
        }else{
            return result;
        }
    }

}