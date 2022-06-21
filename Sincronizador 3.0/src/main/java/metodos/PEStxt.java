package metodos;

import UI.GUI;
import dao.PlanestudioDAO;
import dao.ProgramaeducativoDAO;
import entidades.Planestudio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author José Sánchez
 */
public class PEStxt {

    static String ruta;
    static String agregados;
    static StringBuilder elementos = new StringBuilder();

    public static void main(String[] args) {
        
        //Variable "uap" recibirá la línea del archivo .txt
        String[] uap = new String[7];
        
        /*
                        INDICE DE ARCHIVO .TXT
        
                20202--14008--2009-05-27--269--69--10--348
                [0]     [1]   [2] [3] [4] [5] [6]  [7] [8]
        
            uap[0] = "20202"        - Vigencia del Plan de Estudio
            uap[1] = "14008"        - Clave de Programa Educativo
            uap[2] = "2009"         - Ciclo Escolar ???
            uap[3] = "05"           - Creditos ???
            uap[4] = "27"           - Nombre de Unidad de Aprendizaje
            uap[5] = "269"          - Créditos Obligatorios (se suma con uap[7])
            uap[6] = "69"           - Créditos Optativos
            uap[7] = "10"           - Créditos Obligatorios (parte 2)
            uap[8] = "348"          - ???
        
        */
        
        int pedid = 0;

        PlanestudioDAO planestudioDAO = new PlanestudioDAO();
        Planestudio planestudio = new Planestudio();
        
        ProgramaeducativoDAO programaeducativoDAO = new ProgramaeducativoDAO();
        
        BufferedReader br = null;
        
        elementos.append("\n\n         < PLANES DE ESTUDIOS > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("La rutade PEStxt es: " + ruta);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
            
            while ((currentLine = br.readLine()) != null) {
                
                uap = currentLine.split("--");
                System.out.println(uap[0].substring(0, 4) + "-" + uap[0].substring(4));

                try {
                    
                    //@param pedid 
                    //recibe el valor del ID del programa educativo el cual se
                    //mandará como parámetro en el método "getPlanByPEDidAndPESVigencia".
                    
                    pedid = programaeducativoDAO.getProgramaEducativoByClave(uap[1]).getPEDid();
                    
                    planestudio = planestudioDAO.getPlanByPEDidAndPESVigencia(uap[0].substring(0, 4) + "-" + uap[0].substring(4), pedid);
                                        
                    //planestudio = planestudioDAO.getPlanByPESVigencia(uap[0].substring(0, 4) + "-" + uap[0].substring(4));
                    System.out.println("PLAN ENCONTRADO: " + planestudio.getPESvigenciaPlan());
                    
                } catch (NullPointerException npe) {
                    
                    /*
                     * Funcionamiento:
                     * Cuando no puede obtener el Plan de Estudio buscandolo por su vigencia
                     * entra a está Excepción y trata de crear un nuevo Plan de estudios
                     * para posteriormente añadirlo a la base de datos.
                     *
                     * Problema:
                     * Tras analizarlo se observo que no asigna el Programa Educativo al Plan de estudios
                     * dejando su valor en NULL, este valor es de caracter obligatorio y no debe
                     * dejarse en NULL por que GRUPOS requiere de su uso posteriormente.
                     * 
                     * Solución:
                     * Se crea un nuevo método en el planestudioDAO donde se realiza una búsqueda más
                     * específica tomando como parámetros el ID del programa educativo y la vigencia
                     * del plan de estudio, de esta forma se trae consigo una sola columna con el registro
                     * encontrado la cual se validará si no contiene ningún valor nulo, de lo contrario se
                     * registrará el nuevo plan de estudio.
                     */
                    
                    System.out.println("PLAN NO ENCONTRADO, SE REGISTRARÁ NUEVO PLAN DE ESTUDIOS");
                    
                    planestudio = new Planestudio();
                    planestudio.setPESvigenciaPlan(uap[0].substring(0, 4) + "-" + uap[0].substring(4));
                    planestudio.setPEScreditosObligatorios(Integer.parseInt(uap[3]) + Integer.parseInt(uap[5]));
                    planestudio.setPEScreditosOptativos(Integer.parseInt(uap[4]));
                    planestudio.setPEStotalCreditos(Integer.parseInt(uap[6]));                    
                    planestudio.setProgramaEducativoPEDid(new ProgramaeducativoDAO().getProgramaEducativoByClave(uap[1]));
                    
                    planestudioDAO.saveOrUpdate(planestudio);
                    
                    elementos.append("\n Plan: " + uap[0].substring(0, 4) + "-" + uap[0].substring(4) + "    ID Programa Educativo: " + pedid );
                                       
                    
                }

                uap = new String[7];
            }
            
        } catch (Exception e) {
            System.out.println("ya estaba");
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
