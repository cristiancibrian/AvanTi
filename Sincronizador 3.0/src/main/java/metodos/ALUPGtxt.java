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


public class ALUPGtxt {
    
    static String ruta;
    static String agregados;
    static String cicloE;
    static StringBuilder elementos = new StringBuilder();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void main(String[] args) {
        
        //Variable "uap" que recibirá la línea del archivo .txt
        String[] uap = new String[9];
        
        /*
                                    INDICE DE ARCHIVO .TXT
        
                140--14006--20092--14782--171--1--Taller--01123211--20211--1
                [0]   [1]   [2]     [3]   [4] [5]  [6]      [7]      [8]  [9]
        
                    uap[0] = "140"          - Unidad Académica de Facultad de Ingeniería
                    uap[1] = "14006"        - Clave de Programa Educativo
                    uap[2] = "20092"        - Plan de Estudio 
                    uap[3] = "14782"        - Clave de Unidad de Aprendizaje
                    uap[4] = "171"          - Grupo
                    uap[5] = "1"            - Subgrupo
                    uap[6] = "Taller"       - Tipo de Clase
                    uap[7] = "01123211"     - Matricula del Alumno
                    uap[8] = "20211"        - Periodo
                    uap[9] = "1"            - Ciclo Escolar
                    
                   
        */

        // DECLARACION DE DAOS
        CicloEscolarDAO cicloEscolarDAO = new CicloEscolarDAO();
        AlumnoPerteneceGrupoDAO apgDAO = new AlumnoPerteneceGrupoDAO();
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        UnidadAprendizajeDAO uaDAO = new UnidadAprendizajeDAO();
        UnidadAprendizajeImparteProfesorDAO uipDAO = new UnidadAprendizajeImparteProfesorDAO();

        // SE CREAN LOS OBJETOS
        AlumnoPerteneceGrupo apg = new AlumnoPerteneceGrupo();
        //Grupo grupo = new Grupo();
        Unidadaprendizaje ua = new Unidadaprendizaje();
        UnidadaprendizajeImparteProfesor uip = new UnidadaprendizajeImparteProfesor();
        Alumno alumno = new Alumno();
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
        
        List<AlumnoPerteneceGrupo> listAPG = new ArrayList<AlumnoPerteneceGrupo>();
        
        BufferedReader br = null;
        
        elementos.append("\n\n         < ALUMNO PERTENECE A GRUPO > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("La ruta de ALUPGtxt es: " + ruta);
            br = new BufferedReader(new FileReader(ruta));
            
            
            while ((currentLine = br.readLine()) != null) {
                
                uap = currentLine.split("--");
                
try{
                        alumno = alumnoDAO.getAlumnoByMatricula(Integer.parseInt(uap[7]));
//                        System.out.println(alumno.getALMatricula());
                        ua = uaDAO.getUnidadAprendizajeByClave(Integer.parseInt(uap[3]));
//                        System.out.println(ua.getUAPid());
//                        System.out.println(Integer.parseInt(uap[4]));
//                        System.out.println(Integer.parseInt(uap[5]));
                        String GPOnumero = uap[1] + uap[4];
                        Grupo grupo = new Grupo();
                        GrupoDAO grupoDAO = new GrupoDAO();
                        grupo = grupoDAO.getGrupoByGpoNumero(Integer.parseInt(GPOnumero));
                        uip = uipDAO.getUnidadImparteProfesorByUAPidAndGPOidAndUIPSubgrupo(ua.getUAPid(), grupo.getGPOid(), Integer.parseInt(uap[5]), ciclo.getCESid());
                        System.out.println(uip.getUIPid());
                        System.out.println("REGISTRO DE AlUMNO: " + alumno.getALNombre());
                            apg = new AlumnoPerteneceGrupo();
                            apg.setAlumnoALId(alumno);
                            System.out.println(apg.getAlumnoALId());
                            apg.setUIPId(uip.getUIPid());
                            System.out.println(apg.getUIPId());
                                try{
                                    apgDAO.saveOrUpdate(apg);
                                    listAPG.add(apg);
                                    alumno.setAlumnoPerteneceGrupoList(listAPG);
                                    alumnoDAO.saveOrUpdate(alumno);
                                    elementos.append("\nAlumno: " + alumno.getALNombre() + "    ID Unidad de Aprendizaje: " + uip.getUnidadAprendizajeUAPid().toString() + "    No.Grupo: " + uip.getGrupoGPOid().toString() );
                                }catch(Exception e){
                                        System.out.println("HUBO UN PROBLEMA AL REGISTRAR ALUMNO");
                                }
                                
                                
                }catch(Exception upimp){
                    System.out.println("HUBO UN PROBLEMA");
                }
                
                    alumno = new Alumno();
                    apg = new AlumnoPerteneceGrupo();
                    ua = new Unidadaprendizaje();
                    uip = new UnidadaprendizajeImparteProfesor();
                    uap = new String[9];
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