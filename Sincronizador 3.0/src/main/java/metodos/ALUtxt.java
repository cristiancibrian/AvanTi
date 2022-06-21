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

public class ALUtxt {

    private AlumnoDAO alumnoDAO;
    private UsuarioDAO usuarioDAO;
    private CicloEscolarDAO cicloEscolarDAO;
    private RolDAO rolDAO;
    private UnidadAprendizajeImparteProfesorDAO uipDAO;
    private UnidadAprendizajeDAO  uaDAO;
    private Unidadaprendizaje ua;
    private SimpleDateFormat sdf;
    private DateFormat dateFormat;
    private Cicloescolar ciclo;
    private String cicloE;
    private String cicloArchivo;
    private StringBuilder elementos;
    private BufferedReader br;
    private Usuario usuario;
    private Alumno alumno;
    private AlumnoPerteneceGrupo apg;
    private AlumnoPerteneceGrupoDAO apgDAO;
    private UnidadaprendizajeImparteProfesor uip;
    private String[] uap;
    private int numeroMatricula;
    private boolean ocurrioUnError;
    private boolean esNecesarioGurdar;
    private String usuarioID = "";
    private List<Rol> listaRol;
    private Integer totalAñadidosPrueba = 0;
    private Integer totalErroresPrueba = 0;
    private StringBuilder alumnosAgregados;

    public ALUtxt() {
        alumnoDAO = new AlumnoDAO();
        usuarioDAO = new UsuarioDAO();
        cicloEscolarDAO = new CicloEscolarDAO();
        apgDAO = new AlumnoPerteneceGrupoDAO();
        uipDAO = new UnidadAprendizajeImparteProfesorDAO();
        uaDAO = new UnidadAprendizajeDAO();
        apg = new AlumnoPerteneceGrupo();
        rolDAO = new RolDAO();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        br = null;
        uap = new String[6];
        numeroMatricula = 0;
        boolean ocurrioUnError = false;
        boolean esNecesarioGurdar = false;
    }

    public static void main(String[] args) {
        new ALUtxt().sincronizarAlumnos();
    }

    /*
                                                           INDICE DE ARCHIVO .TXT

                   Hector Manuel--Quiroz--Vela--1123211--hector.quiroz@uabc.edu.mx--140--14006
                        [0]         [1]    [2]    [3]            [4]                [5]   [6]

               uap[0] = "Hector Manuel"                         - Nombre Alumno
               uap[1] = "Quiroz"                                - Apellido Paterno de Alumno
               uap[2] = "Vela"                                  - Apellido Materno de Alumno
               uap[3] = "01123211"                               - Matricula del Alumno
               uap[4] = "hector.quiroz@uabc.edu.mx"             - Correo institucional del Alumno
               uap[5] = "140"                                   - Unidad academica
               uap[6] = "14006"                                 - Programa educativo
               
     */
    public String sincronizarAlumnos() {
        alumnosAgregados = new StringBuilder();
        cambioCicloEscolar();
        elementos = new StringBuilder();
        alumnosAgregados.append("\n\n         < ALUMNOS > \n\n");

        try {

            String currentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\alumno2022_1.txt")));

            while ((currentLine = br.readLine()) != null) {

                uap = currentLine.split("--");
                numeroMatricula = Integer.parseInt(uap[3]);
                usuarioID = uap[4].split("@")[0];

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
        return alumnosAgregados.toString();
    }

    public void restablecerValores() {
        alumno = new Alumno();
        apg = new AlumnoPerteneceGrupo();
        usuario = new Usuario();
        uap = new String[6];
        listaRol = new ArrayList<>();
        ocurrioUnError = false;
        esNecesarioGurdar = false;

    }

    public void verificarDatos() {
        ocurrioUnError = false;
        esNecesarioGurdar = false;
        System.out.println("Verificando si faltan datos del alumno: " + uap[0] + " " + uap[1] + " " + uap[2]);

        // Verificación de alumno (si existe).
        try {
            if (isNull(alumnoDAO.getAlumnoByMatricula(numeroMatricula))) {
                System.out.println("No existe el alumno... Estableciendo uno.");
                crearAlumno();
                alumnoDAO.saveOrUpdate(alumno);
                esNecesarioGurdar = true;
            } else {
                alumno = alumnoDAO.getAlumnoByMatricula(numeroMatricula);
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el alumno.");
            ocurrioUnError = true;
        }

        // Verificación del usuario (si existe).
        try {
            if (isNull(alumnoDAO.getAlumnoByMatricula(numeroMatricula).getUsuarioUSUid())) {
                System.out.println("Sin usuario... Estableciendo uno.");
                crearUsuario();
                usuarioDAO.save(usuario);
                alumno.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                esNecesarioGurdar = true;
            } else if (!usuarioDAO.getUsuarioByUSUusuario(alumnoDAO.getAlumnoByMatricula(numeroMatricula).getUsuarioUSUid().getUSUusuario()).getUSUusuario().equals(usuarioID)) {
                System.out.println("Sin usuario... Estableciendo uno.");
                crearUsuario();
                usuarioDAO.saveOrUpdate(usuario);
                alumno.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                esNecesarioGurdar = true;
                // Verificación de Rol, si el usuario no tiene un rol asignado o es nulo.
            } else if (isNull(alumnoDAO.getAlumnoByMatricula(numeroMatricula).getUsuarioUSUid().getRolList())
                    || alumnoDAO.getAlumnoByMatricula(numeroMatricula).getUsuarioUSUid().getRolList().isEmpty()) {
                System.out.println("Este usuario no tiene rol... Estableciendo uno.");
                usuario = alumnoDAO.getAlumnoByMatricula(numeroMatricula).getUsuarioUSUid();
                establecerRol();
                usuarioDAO.saveOrUpdate(usuario);
                alumno.setUsuarioUSUid(usuarioDAO.getUsuarioByUSUusuario(usuarioID));
                alumnoDAO.saveOrUpdate(alumno);
                esNecesarioGurdar = true;
                //Obtener solamente su usuario
            } else {
                usuario = new Usuario();
                usuario = alumno.getUsuarioUSUid();
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer un usuario.");
            ocurrioUnError = true;
        }

//        //Verificacion si el alumno pertenece a grupo
//        try {
//            if (isNull(apgDAO.getAlumnoById(alumno.getALId()))) {
//                System.out.println("Alumno no pertenece a grupo... Estableciendo uno.");
//                establecerAlumnoPerteneceGrupo();
//                apgDAO.save(apg);
//            }
//        } catch (Exception e) {
//            System.err.println("No se pudo establecer el alumno al grupo");
//            ocurrioUnError = true;
//        }

        // Si no ocurre un error y es necesario guardar o actualizar la información del profesor, entra la condicion del if.
        if (!ocurrioUnError && esNecesarioGurdar) {
            alumnoDAO.saveOrUpdate(alumno);
            System.out.println("AÑADIDO: " + alumno.getALNombre());
            alumnosAgregados.append("\n Alumno: ").append(uap[0] + " " + uap[1] + " " + uap[2]).append("  Matricula: ").append(uap[3]);
            totalAñadidosPrueba++;

            // Si ocurrio algún error se descarta el guardado y se indica cuál alumno tuvo ese error.
        } else if (ocurrioUnError) {
            totalErroresPrueba++;
            System.err.println("No se pudo añadir a: " + uap[0] + " " + uap[1] + " " + uap[2]);
            restablecerValores();
        }
    }

    public void crearAlumno() {
        alumno = new Alumno();
        alumno.setALNombre(uap[0]);
        alumno.setALApellidoPater(uap[1]);
        alumno.setALApellidoMaterno(uap[2]);
        alumno.setALMatricula(uap[3]);
        alumno.setALCorreo(uap[4]);
    }

    private void crearUsuario() {
        establecerUsuario();
        establecerRol();
    }

    private void establecerRol() {
        Rol rol = (Rol) rolDAO.find(8);
        listaRol = new ArrayList<>();
        listaRol.add(rol);
        usuario.setRolList(listaRol);
    }

    private void establecerUsuario() {
        usuario = new Usuario();
        if (usuarioID.equalsIgnoreCase("SIN CORREO")) {
            usuario.setUSUusuario("SC" + alumno.getALNombre().trim() + "" + alumno.getALApellidoPater().trim());
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

    private void establecerAlumnoPerteneceGrupo() {
        ua = new Unidadaprendizaje();
        ua = uaDAO.getUnidadAprendizajeByClave(Integer.parseInt(uap[5]));
        System.out.println("HOLAAA" + ua.getUAPid());
        uip = new UnidadaprendizajeImparteProfesor();
        uip = uipDAO.getUnidadImparteProfesorByUAPidAndGPOidAndUIPSubgrupo(ua.getUAPid(), Integer.parseInt(uap[6]), Integer.parseInt(uap[7]), ciclo.getCESid()); //ciclo.getCESid()
        apg = new AlumnoPerteneceGrupo();
        apg.setAlumnoALId(alumno);
        apg.setUIPId(uip.getUIPid());
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
