package metodos;

import UI.GUI;
import dao.CicloEscolarDAO;
import dao.UnidadAprendizajeDAO;
import dao.UnidadAprendizajeTieneContenidotematicoDAO;
import entidades.Cicloescolar;
import entidades.Unidadaprendizaje;
import entidades.UnidadaprendizajeTieneContenidotematico;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author José Sánchez
 */

public class UATCTtxt {

    static String ruta;
    static String agregados;
    static String cicloE;
    static StringBuilder elementos = new StringBuilder();
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        
        //Variable "uap" recibirá la línea del archivo .txt
        
        String[] uap = new String[13];
        
        /*
        
                                        INDICE DE ARCHIVO TXT
        
            140--14001--20092--11956--INGENIERIA SISMICA--2970--2020--2--3--TERMINAL--5--Clase--2
            [0]   [1]    [2]    [3]            [4]         [5]  [6]  [7][8]   [9]    [10] [11] [12]
        
            uap[0] = "140"                    - Unidad Académica de Facultad de Ingeniería
            uap[1] = "14001"                  - Clave de Programa Educativo
            uap[2] = "20092"                  - Plan de Estudio
            uap[3] = "11956"                  - Clave de Unidad de Aprendizaje
            uap[4] = "INGENIERIA SISMICA"     - Nombre de Unidad de Aprendizaje
            uap[5] = "2970"                   - Número de Empleado de Profesor
            uap[6] = "2020"                   - Parte 1 Ciclo Escolar
            uap[7] = "2"                      - Parte 2 Ciclo Escolar
            uap[8] = "3"                      - Clave de la Etapa
            uap[9] = "TERMINAL"               - Tipo de Etapa de Formación de la Unidad de Aprendizaje
           uap[10] = "5"                      - Total de Créditos de la Unidad de Aprendizaje
           uap[11] = "Clase"                  - Nombre del campo al que se le agregarán horas (UAPhorasClase)
           uap[12] = "2"                      - Total de horas que se agregarán al campo especificado en uap[11]
        
        */
        
        
        //Creación de Instancias 

        
        UnidadAprendizajeTieneContenidotematicoDAO unidadTieneContenidoDAO = new UnidadAprendizajeTieneContenidotematicoDAO();
        UnidadaprendizajeTieneContenidotematico unidadTieneContenido = new UnidadaprendizajeTieneContenidotematico();
        
        UnidadAprendizajeDAO unidadAprendizajeDAO = new UnidadAprendizajeDAO();
        CicloEscolarDAO cicloEscolarDAO = new CicloEscolarDAO();
        Unidadaprendizaje unidadAprendizaje = new Unidadaprendizaje();
        
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

        int contador = 1;
        BufferedReader br = null;
        
        elementos.append("\n\n        < UNIDAD APRENDIZAJE TIENE CONTENIDO TEMÁTICO > \n\n");
        
        try {
            
            String currentLine;
            recibir(ruta);
            System.out.println("LA RUTA ES DE UAPtxt ES: " + ruta);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
            
            while ((currentLine = br.readLine()) != null) {
               
                uap = currentLine.split("--");                
                System.out.println("LINEA: " + currentLine);
                
                try {                    
                    // SE BUSCA LA UNIDAD DE APRENDIZAJE POR CLAVE Y LA 
                    // INSTANCIA DE CONTENIDO TEMATICO CON AL MISMA CLAVE
                    
                    unidadAprendizaje = unidadAprendizajeDAO.getUnidadAprendizajeByClave(Integer.parseInt(uap[3]));
                    
                    try{
                    
                        unidadTieneContenido = unidadTieneContenidoDAO.getUnidadAprendizajeContenidoTematicoByClave(unidadAprendizaje.getUAPid());
                        System.out.println("ID UNIDAD TIENE CONTENIDO: " + unidadTieneContenido.getCTid());
                        
                    }catch(NullPointerException npe1){
                        contador ++;
                        
                        System.out.println("SE CREARA UNA NUEVA INSTANCIA DE CONTENIDO TEMATICO");
                    
                        // SE OBTIENE LA UNIDAD LA UNIDAD DE APRENDIZAJE POR CLAVE
                        unidadAprendizaje = new Unidadaprendizaje();
                        unidadAprendizaje = unidadAprendizajeDAO.getUnidadAprendizajeByClave(Integer.parseInt(uap[3]));


                        //SE CREA UN NUEVO OBJETO DE TIPO UNIDAD TIENE CONTENIDO TEMÁTICO Y
                        //POSTERIORMENTE SE DECLARAN SUS ATRIBUTOS

                        unidadTieneContenido = new UnidadaprendizajeTieneContenidotematico();                    
                        // SE LE ASIGNA VALOR A LOS ATRIBUTOS DE LA TABLA
                        // UNIDAD_APRENDIZAJE_TIENE_CONTENIDOTEMATICO
                        // EL ID DE CONTENIDO ES UN ID COMPUESTO, EN ESTE CASO SE
                        // ASIGNA EL MISMO DE LA UNIDAD DE APRENDIZAJE

                        unidadTieneContenido.setCTid(unidadAprendizaje.getUAPid());
                        unidadTieneContenido.setUnidadAprendizajeUAPid(unidadAprendizaje);

                        // SE LE ASIGNA EL CICLO ESCOLAR ACTUAL DEFINIDO ARRIBA
                        unidadTieneContenido.setCicloEscolarCESid(ciclo);


                        // SE ASIGNA LA VERSION 1
                        unidadTieneContenido.setVersion(1);

                        // VALIDACIÓN DE HORAS EN UNIDAD DE APRENDIZAJE, SI ES DIFERENTE A 0
                        // SE ESTABLECE COMO "TRUE" DE LO CONTRARIO SE ESTABLECE COMO "FALSE"

                        try {

                            //HORAS CLASE
                            if (unidadAprendizaje.getUAPhorasClase() != 0) {
                                unidadTieneContenido.setUAPhorasClaseCompletas(Boolean.TRUE);
                            }   else  { unidadTieneContenido.setUAPhorasClaseCompletas(Boolean.FALSE); }

                            //HORAS TALLER
                            if (unidadAprendizaje.getUAPhorasTaller() != 0) {
                                unidadTieneContenido.setUAPhorasTallerCompletas(Boolean.TRUE);
                            }   else  { unidadTieneContenido.setUAPhorasTallerCompletas(Boolean.FALSE); }

                            //HORAS LABORATORIO
                            if (unidadAprendizaje.getUAPhorasLaboratorio() != 0) {
                                unidadTieneContenido.setUAPhorasLaboratorioCompletas(Boolean.TRUE);
                            }   else  { unidadTieneContenido.setUAPhorasLaboratorioCompletas(Boolean.FALSE); }

                            //HORAS CAMPO
                            if (unidadAprendizaje.getUAPhorasCampo() != 0) {
                                unidadTieneContenido.setUAPhorasCampoCompletas(Boolean.TRUE);
                            }   else { unidadTieneContenido.setUAPhorasCampoCompletas(Boolean.FALSE); }

                        } catch (NullPointerException npe3) {
                            System.out.println("SE ENCONTRÓ UN NULL EN LAS HORAS - CASO 1");
                        }
                        
                        
                        // SE GUARDA LA UNIDAD DE APRENDIZAJE
                        // SE GUARDA EL CONTENIDO TEMATICO 
                        unidadTieneContenidoDAO.saveOrUpdate(unidadTieneContenido);
                        
                        elementos.append("\n Unidad de Aprendizaje: " + uap[4] + "    Clave: " + uap[3]);
                        
                        
                        
                        System.out.println("SE GUARDO EXITOSAMENTE, UAPID: " + unidadTieneContenido.getUnidadAprendizajeUAPid());
                    
                    }
                    
                    
                } catch (NullPointerException npe) {
                    System.out.println("UNIDAD DE APRENDIZAJE NO ENCONTRADA");
                }
                
                unidadTieneContenido = new UnidadaprendizajeTieneContenidotematico();
                unidadAprendizaje = new Unidadaprendizaje();
                uap = new String[13];
            }
                System.out.println("CONTADOR: " + contador);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("YA ESTABA");
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
