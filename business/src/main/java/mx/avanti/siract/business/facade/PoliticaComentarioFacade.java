/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import mx.avanti.business.delegate.DelegatePoliticaComentario;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;

/**
 *
 * @author eduardocardona
 */
public class PoliticaComentarioFacade {
    
    private final DelegatePoliticaComentario delegatePoliticaComentario;


    public PoliticaComentarioFacade() {
        delegatePoliticaComentario = new DelegatePoliticaComentario();
    }
    
    
    public boolean agregarComentarioPolitica(Politicaevaluacioncomentario comentario){
        return delegatePoliticaComentario.agregarComentarioPolitica(comentario);
    }
    
}
