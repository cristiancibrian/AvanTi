/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.List;
import mx.avanti.business.delegate.DelegateAlumnoPerteneceGrupo;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;

/**
 *
 * @author ferna
 */
public class AlumnoPerteneceGrupoFacade {
    
    private final DelegateAlumnoPerteneceGrupo delegatealumnopertenecegrupo;
    
    public AlumnoPerteneceGrupoFacade(){
        delegatealumnopertenecegrupo = new DelegateAlumnoPerteneceGrupo();
    }
    
    
     public List<AlumnoPerteneceGrupo> getListaalumnosgrupo(int id) {
        return delegatealumnopertenecegrupo.findgrupos(id);
    }
    
}
