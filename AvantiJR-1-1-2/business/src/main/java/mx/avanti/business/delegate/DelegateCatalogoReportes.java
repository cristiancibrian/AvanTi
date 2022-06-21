/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.List;
import mx.avanti.siract.entity.Catalogoreportes;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Paul Miranda
 */
public class DelegateCatalogoReportes {
    /***
     *  antes se llamaba saveCatalogoReportes
     * Espera un objeto tipo Catalogo reportes del delegate, el objeto debe 
     * contenter una variable tipo usuario, un string con el nombre del reporte
     * y otro string que contenga un query de sql, si el catalogo existe lo va a
     * modificar, si no existe lo va a crear
     * @param catalagoreportes 
     */
    public void setCatalogoReportes(Catalogoreportes catalagoreportes){
        Catalogoreportes resultado = null;
        if(catalagoreportes.getCTRid()!= null){
            ServiceLocator.getInstanceCatalogoreportes().find(catalagoreportes.getCTRid());

        }
        if(resultado != null){
            ServiceLocator.getInstanceCatalogoreportes().saveOrUpdate(catalagoreportes);

        } else{
            ServiceLocator.getInstanceCatalogoreportes().save(catalagoreportes);
        }
    }
    /***
     * antes se llamaba findallCatalogoReportes
     * Busca todos los catalogos que se encuentran en la base de datos y los 
     * guarda en una lista de tipo Catalogoreportes
     * @return Devuelve una lista de objeto tipo Catalogoreportes.
     */
    public List<Catalogoreportes> getCatalaogoReportes(){
        return ServiceLocator.getInstanceCatalogoreportes().findAll();
    }
    
    //ENVIADO POR GENERADOR DE REPORTES
    
    /***
     * Espera un objeto tipo Catalogo reportes del delegate, el objeto debe 
     * contenter una variable tipo usuario, un string con el nombre del reporte
     * y otro string que contenga un query de sql, si el catalogo existe lo va a
     * modificar, si no existe lo va a crear
     * @param catalogoreporte 
     */
   public void agregarCatalogoReportes(Catalogoreportes catalogoreporte){
        Catalogoreportes resultado = null;
        if(catalogoreporte.getCTRid()!= null){ //debe normalmente ser null en vez de cero, al mapear hibernate int por integer

        }
        if(resultado != null) {
            ServiceLocator.getInstanceCatalogoreportes().updateCatalogoreportes(catalogoreporte);

        }else{
            ServiceLocator.getInstanceCatalogoreportes().addCatalogoReportes(catalogoreporte);

        }
    }
    
   /***
    * Busca todos los catalogos de la base de datos y los guarda en una lista
    * de tipo Catalogoreportes
    * @return Devuelve una lista de objeto tipo Catalogoreportes.
    */
    public List<Catalogoreportes> consultaCatalogoreportes(){
        return ServiceLocator.getInstanceCatalogoreportes().findAll();
    }
    
    /***
     * Espera una variable de tipo entero que contenga el id del catalogo, el id
     * tiene que existir
     * @param ctrid 
     */
    public void eliminarCatalogoreporte(int ctrid){
        ServiceLocator.getInstanceCatalogoreportes().deleteCatalogoreportes(ctrid);
    }
    
    /***
     * Buscar un catalogo por su id, el id tiene que existir
     * @param ctrid
     * @return Devuelve un objeto tipo Catalogoreportes.
     */
    public Catalogoreportes consultaCatalogoreporte(int ctrid){
        return ServiceLocator.getInstanceCatalogoreportes().findByCatalogoreportesId(ctrid);
    }
}

