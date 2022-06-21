package metodos;

import dao.*;
import entidades.*;

import UI.GUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author José Sánchez
 */

public class UAPIPtxt2 {

    static String ruta;
    static String agregados;
    static String cicloE;
    static StringBuilder elementos = new StringBuilder();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        
        //Varoable "uap" que recibirá la línea del archivo .txt
        String[] uap = new String[12];
        
        /*
                                    INDICE DE ARCHIVO .TXT
        
                140--14013--20201--33537--286--0--46--1--Clase--28391--2020--2
                [0]   [1]   [2]     [3]   [4] [5][6] [7]  [8]    [9]   [10] [11]
        
                    uap[0] = "140"        - Unidad Académica de Facultad de Ingeniería
                    uap[1] = "14013"      - Clave de Programa Educativo
                    uap[2] = "20201"      - Ciclo Escolar 
                    uap[3] = "33537"      - Clave de Unidad de Aprendizaje
                    uap[4] = "286"        - Segunda Parte del número de Grupo
                    uap[5] = "0"          - 
                    uap[6] = "46"         - Créditos Optativos
                    uap[7] = "1"          - Créditos Obligatorios (parte 2)
                    uap[8] = "Clase"      - Tipo de ---
                    uap[9] = "28391"      - Número de Empleado de Profesor
                   uap[10] = "2020"       - Plan de Estudio
        */

        // DECLARACION DE DAOS
        CicloEscolarDAO cicloEscolarDAO = new CicloEscolarDAO();
        UnidadAprendizajeDAO unidadAprendizajeDAO = new UnidadAprendizajeDAO();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        GrupoDAO grupoDAO = new GrupoDAO();
        UnidadAprendizajeImparteProfesorDAO unidadAprendizajeImparteProfesorDAO = new UnidadAprendizajeImparteProfesorDAO();

        // SE CREAN LOS OBJETOS
        UnidadaprendizajeImparteProfesor uaip = new UnidadaprendizajeImparteProfesor();
        Grupo grupo = new Grupo();
        Unidadaprendizaje unidadaprendizaje = new Unidadaprendizaje();
        Profesor profesor = new Profesor();
        Cicloescolar cicloescolar = new Cicloescolar();
        
        try {            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(sdf.format(new Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            // LA VARIABLE CICLO SE ASIGNA DEPENDIENDO DEL MES, SE DETERMINA EL 
            // CICLO AUTOMATICAMENTE Y SE GUARDA EN UNA VARIABLE
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                cicloE =  anio + "-1";
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                cicloE = anio + "-2";
            }
        }catch (Exception ex) {
            System.out.println("Error Ciclo Escolar");
        }
        
        Cicloescolar ciclo = cicloEscolarDAO.getCESCicloEscolar(cicloE);
        System.out.println(ciclo.getCESid());
        
        List<UnidadaprendizajeImparteProfesor> listUAP = new ArrayList<UnidadaprendizajeImparteProfesor>();
        
        BufferedReader br = null;
        
        elementos.append("\n\n         < UNIDAD DE APRENDIZAJE IMPARTE PROFESOR > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("La ruta de UAPIPtxt2 es: " + ruta);
            br = new BufferedReader(new FileReader(ruta));
            
            
            while ((currentLine = br.readLine()) != null) {
                
                uap = currentLine.split("--");
                
                try{
                    
                       
                    
                        String GPOnumero = uap[1] + uap[4];
                        
                        profesor = profesorDAO.getProfesorByNumEmpleado(Integer.parseInt(uap[9]));
                        grupo = grupoDAO.getGrupoByGpoNumero(Integer.parseInt(GPOnumero));
                        unidadaprendizaje = unidadAprendizajeDAO.getUnidadAprendizajeByClave(Integer.parseInt(uap[3]));
                        cicloescolar = cicloEscolarDAO.getCESCicloEscolar(uap[10] + "-" + uap[11]);
                        
                        
                        System.out.println("REGISTRO DE PROFESOR: " + profesor.getPROnombre());
                    
                            uaip = new UnidadaprendizajeImparteProfesor();

                            // SE LE ASIGNAN LOS DATOS DEL PROFESOR Y LA UNIDAD
                            uaip.setProfesorPROid(profesor);
                            uaip.setUnidadAprendizajeUAPid(unidadaprendizaje);
                            uaip.setGrupoGPOid(grupo);
                            

                            // SE LE ASIGNA EL TIPO DE ASIGNACION DEPENDIENDO EL 
                            // ARCHIVO
                            if (uap[8].equals("Clase")) {    uaip.setUIPtipoSubgrupo("C");      }
                            if (uap[8].contains("Laboratorio")) {    uaip.setUIPtipoSubgrupo("L");  }
                            if (uap[8].contains("Taller")) {  uaip.setUIPtipoSubgrupo("T");    }
                            if (uap[8].contains("Practicas de Campo")) {    uaip.setUIPtipoSubgrupo("P");     }
                            if (uap[8].contains("Clínicas")) {    uaip.setUIPtipoSubgrupo("CL");    }


                            // SE LE ASIGNA EL SUBGRUPO Y EL CICLO ESCOLAR
                            uaip.setUIPsubgrupo(uap[5]);

                            cicloescolar = cicloEscolarDAO.getCESCicloEscolar(cicloE);
                            uaip.setCicloEscolarCESid(cicloescolar);

                                try{

                                    unidadAprendizajeImparteProfesorDAO.saveOrUpdate(uaip);
                                    listUAP.add(uaip);
                                    profesor.setUnidadaprendizajeImparteProfesorList(listUAP);                                
                                    profesorDAO.saveOrUpdate(profesor);
                                    
                                    elementos.append("\nProfesor: " + profesor.getPROnombre() + "    ID Unidad de Aprendizaje: " + unidadaprendizaje.getUAPid() + "    No.Grupo: " + grupo.getGPOid() );

                                }catch(Exception e){
                                        System.out.println("HUBO UN PROBLEMA AL REGISTRAR PROFESOR");
                                }
                                
                                
                }catch(Exception upimp){
                    System.out.println("HUBO UN PROBLEMA");
                }
                
                    profesor = new Profesor();
                    grupo = new Grupo();
                    unidadaprendizaje = new Unidadaprendizaje();
                    uaip = new UnidadaprendizajeImparteProfesor();
                
                    uap = new String[12];
            }
            
        }  catch (Exception e) {
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

    public static void recibir(String path) {
        ruta = path;
    }
}