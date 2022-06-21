/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import java.util.List;
import mx.avanti.business.delegate.DelegateCatalogoReportes;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Catalogoreportes;

/**
 *
 * @author Paul Miranda
 */
public class CatalogoReportesFacade {
    private final DelegateCatalogoReportes delegateCatalogoReportes;
    private List<Catalogoreportes> catalogoreportes;
    private Catalogoreportes catalogoreporte;
    private Catalogoreportes selectedCatalogoreporte = null;
    int selectedid;
    
    public CatalogoReportesFacade(){
        delegateCatalogoReportes = new DelegateCatalogoReportes();
        selectedCatalogoreporte = new Catalogoreportes();
        catalogoreportes = new ArrayList<Catalogoreportes>();   
    }
    
    /***
     * Busca todos los catalogos de la base de datos y los guarda en una lista de
     * tipo Catalogoreportes
     * @return Devuelve una lista de objeto tipo Catalogoreportes.
     */
    public List<Catalogoreportes> getCatalagoReportes() {
        return delegateCatalogoReportes.consultaCatalogoreportes();
    }

    public void setCatalagoReporteslist(List<Catalogoreportes> catalogoreportes) {
        this.catalogoreportes=catalogoreportes;
    }
    
    /***
     * Recibe un objeto de tipo catalogo reportes y lo guarda en la base de datos
     * @param catalogoreportes 
     */
    public void setCatalagoReportes(Catalogoreportes catalogoreportes){
        delegateCatalogoReportes.agregarCatalogoReportes(catalogoreporte);
    }
    
    /***
     * Recibe un objeto de tipo Catalogoreportes y lo guarda en la base de datos
     * @param catalogoreporte 
     */
    public void agregarCatalogoreporte(Catalogoreportes catalogoreporte){
        delegateCatalogoReportes.agregarCatalogoReportes(catalogoreporte);
    }
    
    /***
     * Recibe un objeto de tipo Catalogoreportes y lo elimina de la base de datos
     * @param ctrid 
     */
    public void eliminarCatalogoreporte(int ctrid){
        delegateCatalogoReportes.eliminarCatalogoreporte(ctrid);
    }
    
    /***
     * Busca todos los catalogos y los guarda en una lista de tipo Catalogoreportes
     * @return Devuelve una lista de objeto tipo Catalogoreportes.
     */
    public List<Catalogoreportes> getCatalogoreportes() {
        return delegateCatalogoReportes.consultaCatalogoreportes();
    }
    
    /***
     * Recibe un id de tipo entero y busca un catalogo por ese id, el id tiene que
     * existir
     * @param ctrid
     * @return Devuelve un objeto tipo Catalogoreportes.
     */
    public Catalogoreportes getCatalogoreporte(int ctrid){
        return delegateCatalogoReportes.consultaCatalogoreporte(ctrid);
    }
}

