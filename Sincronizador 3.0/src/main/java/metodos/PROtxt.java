package metodos;

import UI.GUI;
import clases.HibernateUtil;
import dao.*;
import entidades.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author José Sánchez
 */

public class PROtxt {

    List<ProfesorPerteneceProgramaeducativo> profesorPElist;
    private ProfesorDAO profesorDAO;
    private UsuarioDAO usuarioDAO;
    private UnidadacademicaDAO unidadAcademicaDAO;
    private ProfesorPertenecePEDAO profesorPertenecePEDAO;
    private ProgramaeducativoDAO programaeducativoDAO;
    private CicloEscolarDAO cicloEscolarDAO;
    private RolDAO rolDAO;
    private SimpleDateFormat sdf;
    private DateFormat dateFormat;
    private Cicloescolar ciclo;
    private ProfesorPerteneceProgramaeducativo perteneceProgramaeducativo;
    private ProfesorPerteneceProgramaeducativoPK pk;
    private Programaeducativo programaeducativo;
    private Unidadacademica unidadacademica;
    private Usuario usuario;
    private Profesor profesor;
    private String cicloArchivo;
    private String agregados;
    private String cicloE;
    private StringBuilder elementos;
    private String[] uap;
    private String usuarioID = "";
    private BufferedReader br;
    private int numeroEmpleado;
    private List<Unidadacademica> unidadAcademicaList;
    private List<Rol> listaRol;
    private boolean ocurrioUnError;
    private boolean esNecesarioGurdar;
    private ProfesorPerteneceProgramaeducativo profesorPertenecePeTemp;
    private Integer totalAñadidosPrueba = 0;
    private Integer totalErroresPrueba = 0;
    private StringBuilder profesoresAgregados;


    public PROtxt() {
        profesorDAO = new ProfesorDAO();
        usuarioDAO = new UsuarioDAO();
        unidadAcademicaDAO = new UnidadacademicaDAO();
        profesorPertenecePEDAO = new ProfesorPertenecePEDAO();
        programaeducativoDAO = new ProgramaeducativoDAO();
        cicloEscolarDAO = new CicloEscolarDAO();
        rolDAO = new RolDAO();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        ciclo = new Cicloescolar();
        uap = new String[8];
        br = null;
        numeroEmpleado = 0;
        boolean ocurrioUnError = false;
        boolean esNecesarioGurdar = false;
    }

    public static void main(String[] args) {
        new PROtxt().sincronizarProfesores();
    }
    /*
                                                           INDICE DE ARCHIVO .TXT

                   2221--ALANIS--DAVILA--MARCO JULIO--AADM480724CAA--marco.julio.alanis.davila@uabc.edu.mx--140--14010
                   [0]    [1]     [2]        [3]          [4]                      [5]                      [6]   [7]

               uap[0] = "2221"                                     - Número de Empleado del Profesor
               uap[1] = "ALANIS"                                   - Apellido Paterno de Profesor
               uap[2] = "DAVILA"                                   - Apellido Materno de Profesor
               uap[3] = "MARCO JULIO"                              - Nombre Profesor
               uap[4] = "AADM480724CAA"                            - RFC Profesor
               uap[5] = "marco.julio.alanis.davila@uabc.edu.mx"    - Correo institucional de Profesor
               uap[6] = "140"                                      - Unidad Académica del Profesor
               uap[7] = "14010"                                    - Programa Educativo del Profesor
    */

    public String sincronizarProfesores() {
        profesoresAgregados = new StringBuilder();
        // EL CICLO ESCOLAR SE DEBE CAMBIAR DEPENDIENDO EL CICLO ACTUAL
        cambioCicloEscolar();
        elementos = new StringBuilder();
        profesoresAgregados.append("\n\n         < PROFESORES > \n\n");

        try {

            String currentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\profesor" + cicloArchivo + ".txt")));

            while ((currentLine = br.readLine()) != null) {

                uap = currentLine.split("--");
                numeroEmpleado = Integer.parseInt(uap[0]);
                usuarioID = uap[5].split("@")[0];

                verificarDatos();
                restablecerValores();
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
        System.err.println("Total añadidos: " + totalAñadidosPrueba + "\nTotal errores: " + totalErroresPrueba);
        return profesoresAgregados.toString();
    }

    private void restablecerValores() {
        profesor = new Profesor();
        usuario = new Usuario();
        unidadacademica = new Unidadacademica();
        unidadAcademicaList = new ArrayList<>();
        programaeducativo = new Programaeducativo();
        perteneceProgramaeducativo = new ProfesorPerteneceProgramaeducativo();
        pk = new ProfesorPerteneceProgramaeducativoPK();
        profesorPElist = profesor.getProfesorPerteneceProgramaeducativoList();
        profesorPertenecePeTemp = new ProfesorPerteneceProgramaeducativo();
        listaRol = new ArrayList<>();
        uap = new String[8];
        ocurrioUnError = false;
        esNecesarioGurdar = false;
    }

    /**
     * Este método verifica todos los datos del profesor a añadir, si existe como Usuario, si tiene un Rol, si existe como Profesor, si cuenta
     * con Unidad Academica, con ProgramaEducativo. En caso de no tener alguno de estos datos se le crea uno y se asigna, caso contrario se ignora y no se añade.
     */
    private void verificarDatos() {
        ocurrioUnError = false;
        esNecesarioGurdar = false;
        System.out.println("Verificando si faltan datos del profesor: " + uap[3] + " " + uap[1] + " " + uap[2]);
        // Verificación de profesor (si existe).
        try {
            if (isNull(profesorDAO.getProfesorByNumEmpleado(numeroEmpleado))) {
                System.out.println("No existe el profesor... Estableciendo uno.");
                crearProfesor();
                profesorDAO.saveOrUpdate(profesor);
                esNecesarioGurdar = true;
            } else {
                profesor = profesorDAO.getProfesorByNumEmpleado(numeroEmpleado);
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el profesor.");
            ocurrioUnError = true;
        }
        // Verificación del usuario (si existe).
        try {
            if (isNull(profesorDAO.getProfesorByNumEmpleado(numeroEmpleado).getUsuarioUSUid())) {
                System.out.println("Sin usuario... Estableciendo uno.");
                crearUsuario();
                usuarioDAO.save(usuario);
                profesor.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                esNecesarioGurdar = true;
            } else if (!usuarioDAO.getUsuarioByUSUusuario(profesorDAO.getProfesorByNumEmpleado(numeroEmpleado).getUsuarioUSUid().getUSUusuario()).getUSUusuario().equals(usuarioID)) {
                System.out.println("Sin usuario... Estableciendo uno.");
                crearUsuario();
                usuarioDAO.saveOrUpdate(usuario);
                profesor.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                esNecesarioGurdar = true;
                // Verificación de Rol, si el usuario no tiene un rol asignado o es nulo.
            } else if (isNull(profesorDAO.getProfesorByNumEmpleado(numeroEmpleado).getUsuarioUSUid().getRolList())
                    || profesorDAO.getProfesorByNumEmpleado(numeroEmpleado).getUsuarioUSUid().getRolList().isEmpty()) {
                System.out.println("Este usuario no tiene rol... Estableciendo uno.");
                usuario = profesorDAO.getProfesorByNumEmpleado(numeroEmpleado).getUsuarioUSUid();
                establecerRol();
                usuarioDAO.saveOrUpdate(usuario);
                profesor.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                esNecesarioGurdar = true;
                //Obtener solamente su usuario
            } else {
                usuario = new Usuario();
                usuario = profesor.getUsuarioUSUid();
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer un usuario.");
            ocurrioUnError = true;
        }

        // Verificación de la unidad académica (si existe).
        try {
            if (isNull(profesor.getUnidadacademicaList())) {
                System.out.println("Sin unidad academica... Estableciendo uno.");
                establecerUnidadAcademica();
                profesor.setUnidadacademicaList(unidadAcademicaList);
                esNecesarioGurdar = true;
            } else if (profesor.getUnidadacademicaList().isEmpty() || unidadAcademicaDAO.getUnidadAcademicaByClave(uap[6]).getUACclave() != profesor.getUnidadacademicaList().get(0).getUACclave()) {
                System.out.println("Sin unidad academica... Estableciendo uno.");
                establecerUnidadAcademica();
                profesor.setUnidadacademicaList(unidadAcademicaList);
                esNecesarioGurdar = true;
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer una unidad acedemica.");
            ocurrioUnError = true;
        }

        // Verificación profesor pertenece a programa educativo
        try {

            //Verifica si su lista de "profesorPerteneceProgramaEducativoList" es nula.
            //TODO Falta verificar si es necesario la segunda validación de este if
            if (isNull(profesor.getProfesorPerteneceProgramaeducativoList()) || isNull(profesor.getProgramaeducativoList())) {
                System.out.println("Profesor no pertenece a programa educativo... Estableciendo uno.");
                establecerProfesorPerteneceProgramaeducativo();
                establecerProfesorPerteneceProgramaeducativoPK();
                profesorPElist = new ArrayList<>();
                profesorPElist.add(perteneceProgramaeducativo);
                profesor.setProfesorPerteneceProgramaeducativoList(profesorPElist);
                profesorPertenecePEDAO.saveOrUpdate(perteneceProgramaeducativo);
                esNecesarioGurdar = true;
            }

            // Verifica si el Profesor ya pertenece a ese programa educativo, si no pertenece entra a la condificon.
            if (!validarProfesorPerteneceProgramaEducativo()) {
                System.out.println("Profesor no pertenece a programa educativo... Estableciendo uno.");
                establecerProfesorPerteneceProgramaeducativo();
                establecerProfesorPerteneceProgramaeducativoPK();
                profesorPElist.add(perteneceProgramaeducativo);
                profesor.setProfesorPerteneceProgramaeducativoList(profesorPElist);
                profesorPertenecePEDAO.saveOrUpdate(perteneceProgramaeducativo);
                esNecesarioGurdar = true;
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer un profesor pertenece a programa educativo.");
            ocurrioUnError = true;
        }

        // Si no ocurre un error y es necesario guardar o actualizar la información del profesor, entra la condicion del if.
        if (!ocurrioUnError && esNecesarioGurdar) {
            profesorDAO.saveOrUpdate(profesor);
            System.out.println("AÑADIDO: " + profesor.getPROnombre());
            profesoresAgregados.append("\n Profesores: ").append(uap[3] + " " + uap[1] + " " + uap[2]).append("  No. Empleado: ").append(uap[0]);
            totalAñadidosPrueba++;

            // Si ocurrio algún error se descarta el guardado y se indica cuál profesor tuvo ese error.
        } else if (ocurrioUnError) {
            totalErroresPrueba++;
            System.err.println("No se pudo añadir a: " + uap[3] + " " + uap[1] + " " + uap[2]);
            restablecerValores();
        }
    }

    private void crearProfesor() {
        profesor = new Profesor();
        profesor.setPROnumeroEmpleado(Integer.parseInt(uap[0]));
        profesor.setPROapellidoPaterno(uap[1]);
        profesor.setPROapellidoMaterno(uap[2]);
        profesor.setPROnombre(uap[3]);
        profesor.setPROrfc(uap[4]);
    }

    /**
     * Método que verifica si el profesor está asociado al programa educativo correspondiente.
     *
     * @return true, si se comprueba que el programa educativo esta asociado ene l ciclo escolar correspondiente.
     */
    private boolean validarProfesorPerteneceProgramaEducativo() {
        programaeducativo = programaeducativoDAO.getProgramaEducativoByClave(uap[7]);
        profesorPElist = profesor.getProfesorPerteneceProgramaeducativoList();
        profesorPertenecePeTemp = new ProfesorPerteneceProgramaeducativo();
        for (ProfesorPerteneceProgramaeducativo ppe :
                profesor.getProfesorPerteneceProgramaeducativoList()) {
            if (programaeducativo.getPEDid() == ppe.getProgramaeducativo().getPEDid()
                    && ppe.getCicloescolar().equals(ciclo)) {
                perteneceProgramaeducativo = profesorPertenecePEDAO.getProfesorPertenecePEDAOByPROidAndPEDidAndCESid(profesor.getPROid(), programaeducativo.getPEDid(), ppe.getCicloescolar().getCESid());
                return true;
            }
        }
        return false;
    }


    private void establecerProfesorPerteneceProgramaeducativoPK() {
        pk = new ProfesorPerteneceProgramaeducativoPK();
        pk.setCicloEscolarCESid(ciclo.getCESid());
        pk.setProfesorPROid(profesor.getPROid());
        pk.setProgramaEducativoPEDid(programaeducativo.getPEDid());
        perteneceProgramaeducativo.setProfesorPerteneceProgramaeducativoPK(pk);
    }

    private void establecerProfesorPerteneceProgramaeducativo() {
        programaeducativo = new Programaeducativo();
        perteneceProgramaeducativo = new ProfesorPerteneceProgramaeducativo();
        programaeducativo = programaeducativoDAO.getProgramaEducativoByClave((uap[7]));
        perteneceProgramaeducativo.setCicloescolar(ciclo);
        perteneceProgramaeducativo.setProfesor(profesor);
        perteneceProgramaeducativo.setProgramaeducativo(programaeducativo);
    }

    private void establecerUnidadAcademica() {
        unidadacademica = new Unidadacademica();
        unidadAcademicaList = new ArrayList<>();
        unidadacademica = unidadAcademicaDAO.getUnidadAcademicaByClave(uap[6]);
        unidadAcademicaList.add(unidadacademica);
    }


    private void crearUsuario() {
        establecerUsuario();
        establecerRol();
    }

    private void establecerRol() {
        Rol rol = (Rol) rolDAO.find(7);
        listaRol = new ArrayList<>();
        listaRol.add(rol);
        usuario.setRolList(listaRol);
    }

    private void establecerUsuario() {
        usuario = new Usuario();
        if (usuarioID.equalsIgnoreCase("SIN CORREO")) {
            usuario.setUSUusuario("SC" + profesor.getPROnombre().trim() + "" + profesor.getPROapellidoPaterno().trim());
            usuario.setUSUcontrasenia("vacante");
        } else {
            usuario.setUSUusuario(usuarioID);
            usuario.setUSUcontrasenia(usuarioID);
        }
    }

    private void establecerUsuario(String usuarioID) {
        usuario = new Usuario();
        usuario.setUSUusuario(usuarioID);
        usuario.setUSUcontrasenia(usuarioID);
    }

    private void cambioCicloEscolar() {
        try {
            Date date = sdf.parse(sdf.format(new Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            // LA VARIABLE CICLO SE ASIGNA DEPENDIENDO DEL MES, SE DETERMINA EL
            // CICLO AUTOMATICAMENTE Y SE GUARDA EN UNA VARIABLE
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                cicloE = anio + "-1";
                cicloArchivo = anio + "_1";
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                cicloE = anio + "-2";
                cicloArchivo = anio + "_2";
            }
        } catch (Exception ex) {
            System.out.println("Error Ciclo Escolar");
        }
        ciclo = cicloEscolarDAO.getCESCicloEscolar(cicloE);
    }
}
