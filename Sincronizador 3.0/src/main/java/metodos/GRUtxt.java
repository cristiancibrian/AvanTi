package metodos;

import UI.GUI;
import dao.GrupoDAO;
import dao.PlanestudioDAO;
import dao.ProgramaeducativoDAO;
import entidades.Grupo;
import entidades.Planestudio;
import entidades.Programaeducativo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * @author José Sánchez
 */
public class GRUtxt {

    static String ruta;
    static String agregados;
    static StringBuilder elementos = new StringBuilder();

    public static void main(String[] args) {
        
        //Varoable "uap" que recibirá la línea del archivo .txt
        String[] uap = new String[12];
        
        /*
                        INDICE DE ARCHIVO .TXT
        
                140--14013--20201--33537--286--0--46--1--Clase--28391--2020--2
                [0]   [1]   [2]     [3]   [4] [5] [6][7]  [8]    [9]   [10] [11]
        
            uap[0] = "140"        - Unidad Académica de Facultad de Ingeniería
            uap[1] = "14013"      - Clave de Programa Educativo
            uap[2] = "20201"      - Plan de estudio
            uap[3] = "33537"      - Clave de Unidad de Aprendizaje
            uap[4] = "286"        - Segunda Parte del número de Grupo
            uap[5] = "0"          - 
            uap[6] = "46"         - Créditos Optativos
            uap[7] = "1"          - Créditos Obligatorios (parte 2)
            uap[8] = "Clase"      - Tipo de ---
            uap[9] = "28391"      - Número de Empleado de Profesor
           uap[10] = "2020"       - Plan de Estudio
           uap[11] = "2"          - Etapa Plan de Estudio
        
        */
        
        // DECLARACIÓN DE LAS ENTIDADES
        Grupo grupo = new Grupo();
        Programaeducativo programaeducativo = new Programaeducativo();
        Planestudio planestudio = new Planestudio();

        // DECLARACION DE LOS DAOS
        GrupoDAO grupoDAO = new GrupoDAO();
        PlanestudioDAO planEstudioDAO = new PlanestudioDAO();
        ProgramaeducativoDAO programaeducativoDAO = new ProgramaeducativoDAO();        

        BufferedReader br = null;
        
        elementos.append("\n\n         < GRUPOS > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("La para GRUtxt es: " + ruta);
            br = new BufferedReader(new FileReader(ruta));
            
            while ((currentLine = br.readLine()) != null) {
                
                
                uap = currentLine.split("--");
                
                try{
                    
                   grupo = grupoDAO.getGrupoByGpoNumero(Integer.parseInt(uap[1] + uap[4]));
                    
                   System.out.println("GRUPO ENCONTRADO");
                   
                   programaeducativo = programaeducativoDAO.getProgramaEducativoByClave(uap[1]);
                   
                    try{
                       
                       //SE CONSIGUE EL PLAN DE ESTUDIO Y SE REALIZA UNA VALIDACIÓN PARA VERIFICAR QUE SEAN IGUALES
                       planestudio = planEstudioDAO.getPlanByPEDidAndPESVigencia(uap[2].substring(0, 4)+ "-" + uap[2].substring(4), programaeducativo.getPEDid());
                       
                        if(!planestudio.getPESid().equals(grupo.getPlanEstudioPESid().getPESid())){
                            grupo.setPlanEstudioPESid(planestudio);
                            grupoDAO.saveOrUpdate(grupo);
                            System.out.println("GRUPO ACTUALIZADO - " + grupo.getGPOnumero());
                        }else{
                            System.out.println("GRUPO YA EXISTENTE");
                        }
                       
                    }catch(NullPointerException np3p){
                        System.out.println("<-- PLAN DE ESTUDIO NO ENCONTRADO, NO SE ACTUALIZÓ -->");
                        System.out.println("GRUPO NO REGISTRADO: " + grupo.getGPOnumero() + "  CLAVE PROFESOR: " + uap[9]);
                    }
                   
                        
                }catch(NullPointerException np3){
                    
                    // CASO PARA CUANDO EL GRUPO EXISTE NO EXISTE
                    // SE LE ASIGNA EL NUMERO DE GRUPO
                    grupo = new Grupo();
                    grupo.setGPOnumero(Integer.parseInt(uap[1] + "" + uap[4]));
                    
                    programaeducativo = programaeducativoDAO.getProgramaEducativoByClave(uap[1]);
                    grupo.setPlanEstudioPESid(new PlanestudioDAO().getPlanByPEDidAndPESVigencia(uap[2].substring(0, 4)+ "-" + uap[2].substring(4), programaeducativo.getPEDid()));
                                        

                    try {
                        // SE GUARDA EL GRUPO
                        System.out.println("GRUPO NUEVO: " + grupo.getGPOnumero() + "  " + grupo.getPlanEstudioPESid().getPESid());
                        grupoDAO.save(grupo);
                        
                        elementos.append("\n No.Grupo: " + uap[1] + "" + uap[4] );

                    } catch (Exception ex) {
                        System.out.println("ERROR AL REGISTRAR GRUPO");
                        grupo = new Grupo();
                    }                   
                    
                }  
                
                planestudio = new Planestudio();
                uap = new String[8];
                grupo = new Grupo();
            }
            
        } catch (Exception e) {
            System.out.println("YA ESTABA");
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
    
    /* Se realiza un método de búsqueda por vigencia del plan de estudio donde 
        se toman como parámetros la lista de todos los planes de estudios actuales
        y el plan de estudios actual donde sequiera col.
    */
    
    
   
    public static void recibir(String path) {
        ruta = path;
    }
}
