
import java.util.List;

import mx.avanti.siract.dao.ProfesorDAO;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Profesor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kitto
 */
public class TestingDAO {
    public static void main(String[] args) {
      
      
        
        ProfesorDAO profe = new ProfesorDAO();
        
        List<Profesor> profes;
        profes=profe.findAll();
        
        for(Profesor p:profes){
            System.out.println(p.getPROapellidoPaterno());
        }
     
    }
}
