package metodos;

import UI.GUI;
import dao.CampusDAO;
import dao.UnidadacademicaDAO;
import entidades.Unidadacademica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * @author José Sánchez
 */

public class UACtxt {

    String ruta;
    String agregados;
    StringBuilder elementos = new StringBuilder();
    int contador = 0;

    public void unidadAcademica() {
        
        //Variable "uap" recibirá la línea del archivo .txt
        String[] uap = new String[4];
        
        /*
                            INDICE DE ARCHIVO TXT

                  140--FACULTAD DE INGENIERIA--1--MEXICALI
                  [0]          [1]            [2]   [3]

                 uap[0] = "140"                     - Clave de Unidad Académica 
                 uap[1] = "FACULTAD DE INGENIERIA"  - Nombre de Unidad Académica
                 uap[2] = "1"                       - ID Campus
                 uap[3] = "MEXICALI"                - Nombre del Campus
        
        */
        
        //DECLARACION DE ENTIDADES
        Unidadacademica unidadacademica = new Unidadacademica();
        
        //DECLARACION DE DAOS
        UnidadacademicaDAO unidadAcademicaDAO = new UnidadacademicaDAO();
        CampusDAO campusDAO = new CampusDAO();
        
        
        BufferedReader br = null;
        
        int campus_id = 0;
        
        elementos.append("\n\n         < UNIDADES ACADÉMICAS > \n\n");
        
        try {
            
            String currentLine;            
            recibir(ruta);
            System.out.println("IMPRIMO RUTA: " + ruta);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
            
            while ((currentLine = br.readLine()) != null) {
                
                uap = currentLine.split("--");

                try {
                    
                    campus_id = campusDAO.getCampusByID(Integer.parseInt(uap[2])).getCAMid(); 
                    unidadacademica = unidadAcademicaDAO.getUnidadAcademicaByClaveAndCAMid(uap[0],campus_id);     
                    
                    System.out.println("UNIDAD ACADÉMICA ENCONTRADA: " + unidadacademica.getUACnombre());
                                        
                } catch (NullPointerException npe) {
                    
                    System.out.println("APENAS SE VA A CREAR");
                    unidadacademica = new Unidadacademica();
                    unidadacademica.setUACclave(Integer.parseInt(uap[0]));
                    unidadacademica.setUACnombre(uap[1]);
                    unidadacademica.setCampusCAMid(campusDAO.getCampusByID(Integer.parseInt(uap[2])));
                    unidadacademica.setUACtipo("1");    // Columna de tipo de Unidad Académica no se relaciona con ninguna otra clase, se queda en 1

                    unidadAcademicaDAO.save(unidadacademica);
                    
                    elementos.append("\n Unidad Académica: " + uap[1] + "    Clave: " + uap[0] );
                                        
                    System.out.println("UNIDAD ACADÉMICA AGREGADA: " + unidadacademica.getUACnombre());
                }
                                
                unidadacademica = new Unidadacademica();
                uap = new String[4];
                
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        agregados = elementos.toString();
        GUI.recibirNuevos(agregados);
        
    }

    public void recibir(String path) {
        ruta = path;
    }
}
