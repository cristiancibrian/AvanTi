/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author eduardocardona
 */
public class DelegatePoliticaComentario {
    
     
 
    
    
    public boolean agregarComentarioPolitica(Politicaevaluacioncomentario comentario){
        int alumnoId = 0;
        int ProfesorId = 0;
        List<Politicaevaluacioncomentario> result =  new ArrayList<Politicaevaluacioncomentario>();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        if(comentario.getAlumnoALId() != null){
            alumnoId = comentario.getAlumnoALId().getALId();
        }
        if(comentario.getProfesorProId()!= null){
            ProfesorId = comentario.getProfesorProId().getPROid();
        }
     
        try{
            // opcion 3 es para consultar por alumno de politica
            result = ServiceLocator
                    .getInstancePoliticaComentarioDAO()
                    .ExecuteStoreProcedure(
                     "CALL agregarComentarioPolitica("
                    + "\'" + ProfesorId+ "\',"
                    + "\'" + alumnoId + "\',"
                    + "\'" + comentario.getPoliticaPOEId().getPOEId() + "\',"
                    + "\'" + timestamp + "\',"
                    + " \'" +  comentario.getPECComentario()+ "\')");
            
        }catch(Exception e){
            System.out.println(e);
            return true;
        }
        
        return result != null;
     
    }
    
    
}
