/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
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

}