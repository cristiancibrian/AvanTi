package metodos;

import dao.*;
import entidades.Areaconocimiento;
import entidades.Cicloescolar;
import entidades.Planestudio;
import entidades.Programaeducativo;
import entidades.Unidadaprendizaje;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author José Sánchez
 */
public class UAPtxt {

    CicloEscolarDAO cicloEscolarDAO;
    ProgramaeducativoDAO programaeducativoDAO;
    PlanestudioDAO planestudioDAO;
    AreaConocimientoDAO areaconocimientoDAO;
    UnidadAprendizajeDAO unidadAprendizajeDAO;

    Cicloescolar ciclo;
    Programaeducativo programaeducativo;
    Planestudio planestudio;
    Areaconocimiento areaconocimiento;
    Unidadaprendizaje unidadAprendizaje;

    private String cicloE, cicloArchivo;
    private String[] uap;
    private final StringBuilder unidadesAgregadas;
    private final SimpleDateFormat formatoFecha;
    private final HashMap<Integer, Unidadaprendizaje> unidadesAP;

    public UAPtxt() {
        unidadAprendizajeDAO = new UnidadAprendizajeDAO();
        cicloEscolarDAO = new CicloEscolarDAO();
        programaeducativoDAO = new ProgramaeducativoDAO();
        planestudioDAO = new PlanestudioDAO();
        areaconocimientoDAO = new AreaConocimientoDAO();

        unidadesAP = new HashMap<>();
        unidadesAgregadas = new StringBuilder();
        formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    }

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
    public String sincronizarUnidadesAP() {
        //Variable "uap" recibirá la línea del archivo .txt;
        obtenerUnidadesAP();
        establecerCicloActual();
        ciclo = cicloEscolarDAO.getCESCicloEscolar(cicloE);
        boolean unidadAgregada;

        BufferedReader br = null;
        unidadesAgregadas.append("\n\n         < UNIDADES DE APRENDIZAJE > \n\n");

        try {
            String currentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\uaip" + cicloArchivo + ".txt")));

            while ((currentLine = br.readLine()) != null) {
                uap = currentLine.split("--");
                unidadAprendizaje = unidadesAP.get(Integer.parseInt(uap[3]));

                if (unidadAprendizaje != null) {
                    unidadAgregada = true;
                    establecerHorasUnidadAP();
                } else { // CASO PARA CUANDO LA UNIDAD DE APRENDIZAJE NO EXISTE
                    unidadAgregada = false;
                    crearObjetoUnidadAP();
                }

                asociarAreasConocimiento();
                unidadesAP.put(unidadAprendizaje.getUAPclave(), unidadAprendizaje);

                if (!unidadAgregada) {
                    unidadAprendizajeDAO.save(unidadAprendizaje);
                } else {
                    unidadAprendizajeDAO.update(unidadAprendizaje);
                }

                unidadesAgregadas.append("\n Unidad de Aprendizaje: ").append(uap[4]).append("  Clave: ").append(uap[3]);
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println(ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
            }
        }

       // eliminarUnidadesAntiguas();
        return unidadesAgregadas.toString();
    }

    public void asociarAreasConocimiento() {
        if (unidadAprendizaje.getAreaconocimientoList().isEmpty()) {
            programaeducativo = programaeducativoDAO.getProgramaEducativoByClave(uap[1]);
            planestudio = planestudioDAO.getPlanByPEDidAndPESVigencia(uap[2].substring(0, 4) + "-" + uap[2].substring(4), programaeducativo.getPEDid());
            areaconocimiento = areaconocimientoDAO.findByACOnombreAndPESid("SIN ASIGNAR", planestudio.getPESid());

            if (areaconocimiento != null) {
                List<Areaconocimiento> listaAreaCon = new ArrayList<>();
                listaAreaCon.add(areaconocimiento);
                unidadAprendizaje.setAreaconocimientoList(listaAreaCon);

                System.out.println("SE ASIGNÓ AREA CONOCIMIENTO ID: " + areaconocimiento.getACOid());
            }
        }
    }

    public void crearObjetoUnidadAP() {
        unidadAprendizaje = new Unidadaprendizaje();
        unidadAprendizaje.setUAPclave(Integer.parseInt(uap[3]));
        unidadAprendizaje.setUAPnombre(uap[4].toUpperCase());
        unidadAprendizaje.setUAPcreditos(Integer.parseInt(uap[10]));

        // AGREGAR CICLO ESCOLAR
        unidadAprendizaje.setCicloEscolarCESid(ciclo);

        // INICIALIZAR HORAS EN 0
        unidadAprendizaje.setUAPhorasCampo(0);
        unidadAprendizaje.setUAPhorasClase(0);
        unidadAprendizaje.setUAPhorasClinica(0);
        unidadAprendizaje.setUAPhorasExtraClase(0);
        unidadAprendizaje.setUAPhorasLaboratorio(0);
        unidadAprendizaje.setUAPhorasTaller(0);
        unidadAprendizaje.setAreaconocimientoList(new ArrayList<>());
        // DAR VALOR DEPENDIENDO AL ARCHIVO

        if (uap[9].equals("BASICA")) {
            unidadAprendizaje.setUAPetapaFormacion("BASICA");
            unidadAprendizaje.setUAPtipoCaracter("OBLIGATORIA");
        }
        if (uap[9].equals("TERMINAL")) {
            unidadAprendizaje.setUAPetapaFormacion("TERMINAL");
            unidadAprendizaje.setUAPtipoCaracter("OBLIGATORIA");
        }
        if (uap[9].equals("DISCIPLINARIA")) {
            unidadAprendizaje.setUAPetapaFormacion("DISCIPLINARIA");
            unidadAprendizaje.setUAPtipoCaracter("OBLIGATORIA");
        }
        if (uap[9].equals("OPTATIVAS ETAPA BASICA")) {
            unidadAprendizaje.setUAPetapaFormacion("BASICA");
            unidadAprendizaje.setUAPtipoCaracter("OPTATIVA");
        }
        if (uap[9].equals("OPTATIVAS ETAPA DISCIPLINARIA")) {
            unidadAprendizaje.setUAPetapaFormacion("DISCIPLINARIA");
            unidadAprendizaje.setUAPtipoCaracter("OPTATIVA");
        }
        if (uap[9].equals("OPTATIVAS ETAPA TERMINAL")) {
            unidadAprendizaje.setUAPetapaFormacion("TERMINAL");
            unidadAprendizaje.setUAPtipoCaracter("OPTATIVA");
        }
        if (uap[9].equals("OPTATIVAS LIBRES")) {
            unidadAprendizaje.setUAPetapaFormacion("BASICA");
            unidadAprendizaje.setUAPtipoCaracter("OPTATIVA");
        }

        establecerHorasUnidadAP();
    }

    public void establecerHorasUnidadAP() {
        // DEPENDIENDO DE LA LINEA SE ACTUALIZAN LAS HORAS
        // SE DISTINGUE SI ES CLASE, LABORATORIO O TALLER
        switch (uap[11]) {
            case "Clase":
                unidadAprendizaje.setUAPhorasClase(Integer.parseInt(uap[12]));
                break;
            case "Laboratorio":
                unidadAprendizaje.setUAPhorasLaboratorio(Integer.parseInt(uap[12]));
                break;
            case "Taller":
                unidadAprendizaje.setUAPhorasTaller(Integer.parseInt(uap[12]));
                break;
            default:
                unidadAprendizaje.setUAPhorasCampo(Integer.parseInt(uap[12]));
        }
    }

    public void obtenerUnidadesAP() {
        List<Unidadaprendizaje> listaUnidadAP = unidadAprendizajeDAO.findAll();

        for (Unidadaprendizaje unidadAP : listaUnidadAP) {
            unidadesAP.put(unidadAP.getUAPclave(), unidadAP);
        }
    }

    public void eliminarUnidadesAntiguas() {
        for (Unidadaprendizaje unidadAP : unidadesAP.values()) {
            if (unidadAP.getAreaconocimientoList().isEmpty()) {
                unidadAprendizajeDAO.delete(unidadAP);
            }
        }
    }

    public void establecerCicloActual() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = formatoFecha.parse(formatoFecha.format(new Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);

            // LA VARIABLE CICLO SE ASIGNA DEPENDIENDO DEL MES, SE DETERMINA EL
            // CICLO AUTOMATICAMENTE Y SE GUARDA EN UNA VARIABLE
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                cicloE = anio + "-1";
                cicloArchivo = anio + "_1";
            } else if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                cicloE = anio + "-2";
                cicloArchivo = anio + "_2";
            }
        } catch (NumberFormatException | ParseException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        new UAPtxt().sincronizarUnidadesAP();
    }
}
