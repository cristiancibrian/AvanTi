package metodos;

import UI.GUI;
import dao.ProgramaeducativoDAO;
import dao.UnidadacademicaDAO;
import entidades.Programaeducativo;
import entidades.Unidadacademica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author José Sánchez
 */
public class PEDtxt {

    static String ruta;
    static String agregados;
    static StringBuilder elementos = new StringBuilder();

    public static void main(String[] args) {
        
        //Variable "uap" recibirá la línea del archivo .txt
        
        String[] uap = new String[4];
        
        /*
                            INDICE DE ARCHIVO TXT

                         14001--INGENIERO CIVIL--140
                          [0]         [1]        [2]

                 uap[0] = "14001"            - Clave de Programa Educativo
                 uap[1] = "INGENIERO CIVIL"  - Nombre de Programa Educativo
                 uap[2] = "140"              - Unidad Académica de Facultad de Ingeniería
        
        */
        
        //DECLARACION DE ENTIDADES
        Programaeducativo programaeducativo = new Programaeducativo();
        Unidadacademica unidadacademica = new Unidadacademica(); 
        
        //DECLARACION DE DAOS
        ProgramaeducativoDAO programaEducativoDAO = new ProgramaeducativoDAO();
        UnidadacademicaDAO unidadacademicaDAO = new UnidadacademicaDAO();
        
        BufferedReader br = null;
        
        elementos.append("\n\n        < PROGRAMAS EDUCATIVOS > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("La ruta es " + ruta);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
            
            while ((currentLine = br.readLine()) != null) {
                
                uap = currentLine.split("--");
                
                try{
                    //SE REALIZA UNA BÚSQUEDA DEL PROGRAMA EDUCATIVO
                    programaeducativo = programaEducativoDAO.getProgramaEducativoByClave(uap[0]);                    
                    System.out.println("PROGRAMA EDUCATIVO ENCONTRADO");
                    
                    try{
                        //SE REALIZA UNA BÚSQUEDA PDE LA UNIDAD ACADÉMICA PARA VERIFICAR SI EXISTE
                        unidadacademica = unidadacademicaDAO.getUnidadAcademicaByClave(uap[2]);
                        
                        /*
                            SE VERIFICA QUE EL ID DE UNIDAD ACADÉMICA COINCIDA CON EL QUE ESTÁ ASOCIADO
                            DE LO CONTRARIO SE ACTUALIZA CON EL QUE SE ESTABLECE EN EL ARCHIVO .TXT
                        */
                        if(!unidadacademica.getUACid().equals(programaeducativo.getUnidadAcademicaUACid().getUACid())){
                            programaeducativo.setUnidadAcademicaUACid(unidadacademica);
                            programaEducativoDAO.saveOrUpdate(programaeducativo);
                            System.out.println("PROGRAMA EDUCATIVO ACTUALIZADO - " + programaeducativo.getPEDnombre());
                        }else{
                            System.out.println("NO SE ACTUALIZÓ EL PROGRAMA EDUCATIVO - " + programaeducativo.getPEDnombre());
                            
                        }                           
                        
                    }catch(NullPointerException np3un){
                        System.out.println("<-- UNIDAD ACADÉMICA NO ENCONTRADA, NO SE ACTUALIZÓ -->");
                        System.out.println("PROGRAMA EDUCATIVO NO REGISTRADO: " + programaeducativo.getPEDnombre());
                    }
                    
                }catch(NullPointerException np3){
                    
                    System.out.println("NO SE ENCONTRÓ, SE AGREGARÁ");
                    
                    // SE LE ASIGNAN LOS VALORES AL OBJETO PROGRAMA EDUCATIVO
                    programaeducativo = new Programaeducativo();                    
                    
                    programaeducativo.setPEDclave(Integer.parseInt(uap[0]));
                    programaeducativo.setPEDnombre(uap[1]);
                    programaeducativo.setUnidadAcademicaUACid(new UnidadacademicaDAO().getUnidadAcademicaByClave(uap[2]));
                    
                    programaEducativoDAO.save(programaeducativo);
                    
                    elementos.append("\n Programa educativo: " + uap[1] + "    CLAVE: " + uap[0] );
                    
                    System.out.println("PROGRAMA EDUCATIVO AGREGADO, NOMBRE: " + programaeducativo.getPEDnombre());
                        
                }
                
                programaeducativo = new Programaeducativo();
                uap = new String[4];
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    public static void recibir(String path) {
        ruta = path;
    }
}
