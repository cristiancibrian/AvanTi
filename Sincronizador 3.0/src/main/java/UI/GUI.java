package UI;

import metodos.*;
import principal.Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Misael & José Shz
 */
public class GUI {

    static String elementos, elementosN;
    static JTextArea agregadosTA;
    static String ciclo;

    JFrame frame;
    JButton archivos, sincronizar, archivosRecomendado, archivosPersonalizado, regresarArchivosRecomendado, regresarArchivosPersonalizado, regresarMenuPrincipal;
    JPanel pnlMenuPrincipal, pnlMenuArchivos, pnlArchivosRecomendado, pnlArchivosPersonalizado, pnlSincronizar;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //Elementos del menú de Descarga Personalizada
    JRadioButton rdCampus, rdCarreras, rdCarreras2, rdPlan, rdProfesor, rdUAIP, rdUAIP2, rdUnidad, rdAlumno, rdAPG;
    JLabel lblCampus, lblCarreras, lblCarreras2, lblPlan, lblProfesor, lblUAIP, lblUAIP2, lblUnidad, lblAlumno, lblAPG;
    JButton asDescargar, asRegresar;
    //Elementos del panel Sincronizar
    JRadioButton rbUnidadAprendizaje, rbPrograma, rbAsignaciones, rbPlan, rbGrupo, rbUAIP, rbProfesor, rbUnidad, rbAlumno;
    JLabel lbUnidadAprendizaje, lbPrograma, lbAsignaciones, lbPlan, lbGrupo, lbUAIP, lbProfesor, lbUnidad, lbAlumno;
    JButton sincAceptar;
    //Elementos del panel de Agregados
    JPanel pnlAgregados;
    JButton agregAceptar;
    JScrollPane scroll;

    public GUI() {

        // SE CREA EL FRAME DONDE ESTARAN LOS COMPONENTES
        frame = new JFrame("Sincronizador SIRACT");
        frame.setSize(600, 550);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //CODIGO PARA OBTENER EL CICLO ESCOLAR
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(sdf.format(new Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            // LA VARIABLE CICLO SE ASIGNA DEPENDIENDO DEL MES, SE DETERMINA EL
            // CICLO AUTOMATICAMENTE Y SE GUARDA EN UNA VARIABLE
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                ciclo = anio + "_1";
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                ciclo = anio + "_2";
            }

        } catch (Exception ex) {
            System.out.println("Error Ciclo Escolar");
        }

        //elementos del panel que es el Menú Principal
        pnlMenuPrincipal = new JPanel();
        pnlMenuPrincipal.setLayout(null);
        pnlMenuPrincipal.setBounds(10, 10, 400, 400);
        //pnlMenuPrincipal.setBackground(Color.BLACK);
        frame.add(pnlMenuPrincipal);

        //Botón para entrar al menú de obtener archivos
        archivos = new JButton("Obtener archivos");
        archivos.setBounds(10, 180, 200, 20);
        pnlMenuPrincipal.add(archivos);

        //Botón para acceder a la sincronización de la base de datos
        sincronizar = new JButton("Sincronizar Base de Datos");
        sincronizar.setBounds(10, 210, 200, 20);
        pnlMenuPrincipal.add(sincronizar);

        //Panel que contiene el menú donde se selecciona que tipo de descarga de archivos se quiere
        pnlMenuArchivos = new JPanel();
        pnlMenuArchivos.setLayout(null);
        pnlMenuArchivos.setBounds(10, 10, 400, 400);
        //pnlMenuArchivos.setBackground(Color.red);
        frame.add(pnlMenuArchivos);
        pnlMenuArchivos.setVisible(false);

        //Botón que bajará todos los archivos de la base de datos
        archivosRecomendado = new JButton("Bajar Todos los Archivos");
        archivosRecomendado.setBounds(10, 10, 200, 20);
        pnlMenuArchivos.add(archivosRecomendado);

        //Botón que abre el menú para que escoger los archivos que se desean bajar
        archivosPersonalizado = new JButton("Seleccionar Archivos a Bajar");
        archivosPersonalizado.setBounds(10, 40, 200, 20);
        pnlMenuArchivos.add(archivosPersonalizado);

        //Botón para regresar al menú principal desde el Menú de bajar archivos
        regresarMenuPrincipal = new JButton("Regresar");
        regresarMenuPrincipal.setBounds(10, 70, 200, 20);
        pnlMenuArchivos.add(regresarMenuPrincipal);

        //Panel que contiene los elementos de Archivos Recomendados
        pnlArchivosRecomendado = new JPanel();
        pnlArchivosRecomendado.setLayout(null);
        pnlArchivosRecomendado.setBounds(10, 10, 400, 400);
        pnlArchivosRecomendado.setBackground(Color.blue);
        frame.add(pnlArchivosRecomendado);
        pnlArchivosRecomendado.setVisible(false);

        //Panel que contiene el menú para seleccionar archivos
        pnlArchivosPersonalizado = new JPanel();
        pnlArchivosPersonalizado.setLayout(null);
        pnlArchivosPersonalizado.setBounds(10, 10, 400, 400);
        //pnlArchivosPersonalizado.setBackground(Color.black);
        frame.add(pnlArchivosPersonalizado);
        pnlArchivosPersonalizado.setVisible(false);

        //Panel que contiene los elementos de pnlSincronizar
        pnlSincronizar = new JPanel();
        pnlSincronizar.setLayout(null);
        pnlSincronizar.setBounds(10, 10, 400, 400);
        frame.add(pnlSincronizar);
        pnlSincronizar.setVisible(false);

        //RadioButtons y Lables para el panel de Sincronizar
        rbUnidad = new JRadioButton();
        rbUnidad.setBounds(160, 110, 20, 20);
        pnlSincronizar.add(rbUnidad);
        rbUnidad.setEnabled(false);

        lbUnidad = new JLabel("Unidad Academica");
        lbUnidad.setBounds(190, 110, 200, 20);
        pnlSincronizar.add(lbUnidad);

        rbPrograma = new JRadioButton();
        rbPrograma.setBounds(160, 140, 20, 20);
        pnlSincronizar.add(rbPrograma);
        rbPrograma.setEnabled(false);

        lbPrograma = new JLabel("Programa Educativo");
        lbPrograma.setBounds(190, 140, 200, 20);
        pnlSincronizar.add(lbPrograma);

        rbPlan = new JRadioButton();
        rbPlan.setBounds(160, 170, 20, 20);
        pnlSincronizar.add(rbPlan);
        rbPlan.setEnabled(false);

        lbPlan = new JLabel("Plan de Estudio");
        lbPlan.setBounds(190, 170, 200, 20);
        pnlSincronizar.add(lbPlan);

        rbGrupo = new JRadioButton();
        rbGrupo.setBounds(160, 200, 20, 20);
        pnlSincronizar.add(rbGrupo);
        rbGrupo.setEnabled(false);

        lbGrupo = new JLabel("Grupos");
        lbGrupo.setBounds(190, 200, 200, 20);
        pnlSincronizar.add(lbGrupo);

        rbProfesor = new JRadioButton();
        rbProfesor.setBounds(160, 230, 20, 20);
        pnlSincronizar.add(rbProfesor);
        rbProfesor.setEnabled(false);

        lbProfesor = new JLabel("Profesor");
        lbProfesor.setBounds(190, 230, 200, 20);
        pnlSincronizar.add(lbProfesor);

        rbUnidadAprendizaje = new JRadioButton();
        rbUnidadAprendizaje.setBounds(160, 260, 20, 20);
        pnlSincronizar.add(rbUnidadAprendizaje);
        rbUnidadAprendizaje.setEnabled(false);

        lbUnidadAprendizaje = new JLabel("Unidades de Aprendizaje");
        lbUnidadAprendizaje.setBounds(190, 260, 200, 20);
        pnlSincronizar.add(lbUnidadAprendizaje);
       
        rbAsignaciones = new JRadioButton();
        rbAsignaciones.setBounds(160, 290, 20, 20);
        pnlSincronizar.add(rbAsignaciones);
        rbAsignaciones.setEnabled(false);

        lbAsignaciones = new JLabel("Asignaciones");
        lbAsignaciones.setBounds(190, 290, 200, 20);
        pnlSincronizar.add(lbAsignaciones);
        
        rbAlumno = new JRadioButton();
        rbAlumno.setBounds(160, 320, 20, 20);
        pnlSincronizar.add(rbAlumno);
        rbAlumno.setEnabled(false);
        
        lbAlumno = new JLabel("Alumno");
        lbAlumno.setBounds(190, 320, 200, 20);
        pnlSincronizar.add(lbAlumno);

        sincAceptar = new JButton("Aceptar");
        sincAceptar.setBounds(300, 370, 100, 25);
        pnlSincronizar.add(sincAceptar);

        //Radio buttons y labels para la descarga personalizada de archivos
        rdCampus = new JRadioButton();
        rdCampus.setBounds(10, 10, 20, 20);
        pnlArchivosPersonalizado.add(rdCampus);

        lblCampus = new JLabel("campus" + ciclo);
        lblCampus.setBounds(40, 10, 200, 20);
        pnlArchivosPersonalizado.add(lblCampus);

        rdCarreras = new JRadioButton();
        rdCarreras.setBounds(10, 40, 20, 20);
        pnlArchivosPersonalizado.add(rdCarreras);

        lblCarreras = new JLabel("carreras" + ciclo);
        lblCarreras.setBounds(40, 40, 200, 20);
        pnlArchivosPersonalizado.add(lblCarreras);

        rdCarreras2 = new JRadioButton();
        rdCarreras2.setBounds(10, 70, 20, 20);
        pnlArchivosPersonalizado.add(rdCarreras2);

        lblCarreras2 = new JLabel("carreras2" + ciclo);
        lblCarreras2.setBounds(40, 70, 200, 20);
        pnlArchivosPersonalizado.add(lblCarreras2);

        rdPlan = new JRadioButton();
        rdPlan.setBounds(10, 100, 20, 20);
        pnlArchivosPersonalizado.add(rdPlan);

        lblPlan = new JLabel("plan" + ciclo);
        lblPlan.setBounds(40, 100, 200, 20);
        pnlArchivosPersonalizado.add(lblPlan);

        rdProfesor = new JRadioButton();
        rdProfesor.setBounds(10, 130, 20, 20);
        pnlArchivosPersonalizado.add(rdProfesor);

        lblProfesor = new JLabel("profesor" + ciclo);
        lblProfesor.setBounds(40, 130, 200, 20);
        pnlArchivosPersonalizado.add(lblProfesor);

        rdUAIP = new JRadioButton();
        rdUAIP.setBounds(10, 160, 20, 20);
        pnlArchivosPersonalizado.add(rdUAIP);

        lblUAIP = new JLabel("uaip" + ciclo);
        lblUAIP.setBounds(40, 160, 200, 20);
        pnlArchivosPersonalizado.add(lblUAIP);

        rdUAIP2 = new JRadioButton();
        rdUAIP2.setBounds(10, 190, 20, 20);
        pnlArchivosPersonalizado.add(rdUAIP2);

        lblUAIP2 = new JLabel("uaip2" + ciclo);
        lblUAIP2.setBounds(40, 190, 200, 20);
        pnlArchivosPersonalizado.add(lblUAIP2);

        rdUnidad = new JRadioButton();
        rdUnidad.setBounds(10, 220, 20, 20);
        pnlArchivosPersonalizado.add(rdUnidad);

        lblUnidad = new JLabel("unidad" + ciclo);
        lblUnidad.setBounds(40, 220, 200, 20);
        pnlArchivosPersonalizado.add(lblUnidad);
        
        rdAlumno = new JRadioButton();
        rdAlumno.setBounds(10, 250, 20, 20);
        pnlArchivosPersonalizado.add(rdAlumno);
        
        lblAlumno = new JLabel("Alumno" + ciclo);
        lblAlumno.setBounds(40, 250, 200, 20);
        pnlArchivosPersonalizado.add(lblAlumno);
        
        rdAPG = new JRadioButton();
        rdAPG.setBounds(10, 280, 20, 20);
        pnlArchivosPersonalizado.add(rdAPG);
        
        lblAPG = new JLabel("APG" + ciclo);
        lblAPG.setBounds(40, 280, 200, 20);
        pnlArchivosPersonalizado.add(lblAPG);

        asDescargar = new JButton("Descargar");
        asDescargar.setBounds(200, 80, 100, 20);
        pnlArchivosPersonalizado.add(asDescargar);

        asRegresar = new JButton("Regresar");
        asRegresar.setBounds(200, 110, 100, 20);
        pnlArchivosPersonalizado.add(asRegresar);

        //PANEL AGREGADOS
        //Elementos dentro del panel que mostrará lo que se agregó a la base de datos
        pnlAgregados = new JPanel();
        pnlAgregados.setLayout(null);
        pnlAgregados.setBounds(10, 10, 600, 600);
        frame.add(pnlAgregados);
        pnlAgregados.setVisible(false);

        agregadosTA = new JTextArea();
        agregadosTA.setEditable(false);

        scroll = new JScrollPane(agregadosTA);
        scroll.setBounds(9, 10, 550, 410);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlAgregados.add(scroll);

        agregAceptar = new JButton("Aceptar");
        agregAceptar.setBounds(445, 450, 100, 25);
        pnlAgregados.add(agregAceptar);

        revision();
        acciones();

        frame.setVisible(true);
    }

    public static String recibirCiclo() {
        return ciclo;
    }

    public static void recibir(String elements) {
        elementos = elements;
        if (elementos == null) {
            System.out.println("NO SE AGREGO NADA");
        }
    }

    public static void recibirNuevos(String elementsNew) {
        elementosN = elementsNew;
        if (elementosN == null) {
            System.out.println("NO HAY AGREGADOS");
        } else {
            agregadosTA.append(elementosN);
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    public void acciones() {
        //Botón que apaga los JPanels para acceder al menú para seleccionar la forma de descarga de archivos
        archivos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlMenuPrincipal.setVisible(false);
                pnlMenuArchivos.setVisible(true);
            }
        });
        //Botón que apaga los JPanels para bajar todos los archivos
        archivosRecomendado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LO PRESIONE");
                Principal p = new Principal();
                for (int x = 0; x < 8; x++) {
                    System.out.println("X ES: " + x);
                    p.ejecutarPA(x);
                }

            }
        });
        /**
         * Botón que apaga los JPanels para acceder al menú de Archivos
         * Personalizado
         */
        archivosPersonalizado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlMenuArchivos.setVisible(false);
                pnlArchivosPersonalizado.setVisible(true);
            }
        });
        /**
         * Botón que apaga los JPanels para regresar al menú principal
         */
        regresarMenuPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlMenuArchivos.setVisible(false);
                pnlMenuPrincipal.setVisible(true);
            }
        });
        /**
         * Al activar este botón revisará cuales son los radio buttons que estan
         * activados, y dependiendo de cuales estén activados descargara el
         * archivo que se solicitó utilizando la clase Principal del proyecto
         * ProcedimientosAlmacenados
         */
        asDescargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Principal p = new Principal();
                if (rdCarreras.isSelected() == true) {
                    p.ejecutarPA(0);
                }
                if (rdCampus.isSelected() == true) {
                    p.ejecutarPA(1);
                }
                if (rdUnidad.isSelected() == true) {
                    p.ejecutarPA(2);
                }
                if (rdCarreras2.isSelected() == true) {
                    p.ejecutarPA(3);
                }
                if (rdPlan.isSelected() == true) {
                    p.ejecutarPA(4);
                }
                if (rdProfesor.isSelected() == true) {
                    p.ejecutarPA(5);
                }
                if (rdUAIP.isSelected() == true) {
                    p.ejecutarPA(6);
                }
                if (rdUAIP2.isSelected() == true) {
                    p.ejecutarPA(7);
                }
                if (rdAlumno.isSelected() == true) {
                    p.ejecutarPA(8);
                }
                if (rdAPG.isSelected() == true) {
                    p.ejecutarPA(9);
                }
            }
        });

        asRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlArchivosPersonalizado.setVisible(false);
                pnlMenuArchivos.setVisible(true);
                revision();
            }
        });

        sincronizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlMenuPrincipal.setVisible(false);
                pnlSincronizar.setVisible(true);
                String path = System.getProperty("user.dir");
                agregadosTA.removeAll();
                /**
                 * Aquí se instanacian las clases del paquete metodos en del
                 * proyecto SincronizadorSiract para que se corran en el orden
                 * correcto de ejecución. Cada clase tiene un método llamado
                 * recibir que recibe la ruta donde se ecuentra el archivo que
                 * tiene que leer. En este caso se usa el método getProperty
                 * para determinar la ruta donde se guardo el proyecto que es
                 * donde se guardarán los archivos y se concatena el nombre del
                 * archivo para que lo encuentre.
                 */

//                Main.main(new String[]{path + "\\unidad" + ciclo + ".txt"});
//                rbUnidad.setSelected(true);
//                recibir(elementos);
//                
//                
//////////              SINCRONIZACION DE PROGRAMA EDUCATIVO

//                PEDtxt.recibir(path + "\\carreras" + ciclo + ".txt");
//                PEDtxt.main(new String[]{path + "\\carreras" + ciclo + ".txt"});
//                rbPrograma.setSelected(true);
//                recibir(elementos);

//////////              SINCRONIZACION DE PLAN DE ESTUDIOS

//                PEStxt.recibir(path + "\\plan" + ciclo + ".txt");
//                PEStxt.main(new String[]{path});
//                rbPlan.setSelected(true);
//                recibir(elementos);

//////////              SINCRONIZACION DE GRUPOS

//                GRUtxt.recibir(path + "\\uaip2" + ciclo + ".txt");
//                GRUtxt.main(new String[]{path});
//                rbGrupo.setSelected(true);
//                recibir(elementos);
                

//////////              SINCORNIZACION DE PROFESOR Y USUARIO

//                rbProfesor.setSelected(true);
//                recibirNuevos(new PROtxt().sincronizarProfesores());

////////////            SINCRONIZACION DE UNIDAD DE APRENDIZAJE

//                rbUnidadAprendizaje.setSelected(true);
//                recibirNuevos(new UAPtxt().sincronizarUnidadesAP());


//////////              SINCRONIZACION DE LA INSTANCIA DE UNIDAD APRENDIZAJE
////////////////        TIENE CONTENIDO TEMATICO

//                UATCTtxt.recibir(path + "\\uaip" + ciclo + ".txt");
//                UATCTtxt.main(new String[]{path});
//                rbUnidadAprendizaje.setSelected(true);
//                recibir(elementos);

//////////              SINCRONIZACION DE ASIGNACIONES DE PROFESOR, GRUPO Y UNIDAD
//
////                WARNING
////                Este método realiza el registro de todas las
////                líneas existentes en el archivo .txt.
////                Puede tardar más de 30 minutos en terminar el proceso
////                dada la cantidad de registros.

//                UAPIPtxt2.recibir(path + "\\uaip2" + ciclo + ".txt");
//                UAPIPtxt2.main(new String[]{path});
//                rbAsignaciones.setSelected(true);
//                recibir(elementos);


//////////              SINCRONIZADOR DE ALUMNOS

                rbAlumno.setSelected(true);
                recibirNuevos(new ALUtxt().sincronizarAlumnos());
                
//////////              SINCRONIZADOR DE ALUMNO PERTENECE A GRUPO
//
                ALUPGtxt.recibir(path + "\\apg" + ciclo + ".txt");
                ALUPGtxt.main(new String[]{path});
                rbAlumno.setSelected(true);
                recibir(elementos);
                
            }
        });

        sincAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlSincronizar.setVisible(false);
                pnlAgregados.setVisible(true);
                System.out.println("ACEPTE SINCRONIZAR");
            }
        });

        agregAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlAgregados.setVisible(false);
                pnlMenuPrincipal.setVisible(true);
                agregadosTA.setText("");
            }
        });
    }

    public void revision() {
        /**
         * Cuando se bajan los archivos se guardan en la ruta donde esta
         * guardado el programa. Aquí el programa toma la ruta donde esta
         * guardado el programa y busca que esten todos los archivos. Se usa la
         * función exists() para verificar que éxista, si existe la función
         * lanzará true, si no existe false. En caso de que no exista el botón
         * para sincronizar los archivos será deshabilitado y se mostrará cuales
         * son los archivos faltantes.
         */
        String rutaMadre = System.getProperty("user.dir");
        System.out.println(rutaMadre + "\\campus" + ciclo + "\\");
        File rutaCampus, rutaCarreras, rutaCarreras2, rutaPlan, rutaProfesor, rutaUAIP, rutaUnidad, rutaUAIP2, rutaALU, rutaALUPG;
        rutaCampus = new File(rutaMadre + "\\campus" + ciclo + ".txt");
        rutaCarreras = new File(rutaMadre + "\\carreras" + ciclo + ".txt");
        rutaCarreras2 = new File(rutaMadre + "\\carreras2" + ciclo + ".txt");
        rutaPlan = new File(rutaMadre + "\\plan" + ciclo + ".txt");
        rutaProfesor = new File(rutaMadre + "\\profesor" + ciclo + ".txt");
        rutaUAIP = new File(rutaMadre + "\\uaip" + ciclo + ".txt");
        rutaUnidad = new File(rutaMadre + "\\unidad" + ciclo + ".txt");
        rutaUAIP2 = new File(rutaMadre + "\\uaip2" + ciclo + ".txt");
        rutaALU = new File(rutaMadre + "\\alumno" + ciclo + ".txt");
        rutaALUPG = new File(rutaMadre + "\\apg" + ciclo + ".txt");
        
        

        /**
         * Si los archivos existen se podrá continuar con la sincronización. En
         * caso de faltar uno se deshabilitará el botón para acceder a la
         * sincronización
         */
        if (rutaCampus.exists() && rutaCarreras.exists() && rutaCarreras2.exists() && rutaPlan.exists() && rutaProfesor.exists()
                && rutaUAIP.exists() && rutaUnidad.exists() && rutaUAIP2.exists() && rutaALU.exists() && rutaALUPG.exists()) {
            System.out.println("TODOS EXISTEN");
        } else {
            sincronizar.setEnabled(false);

            if (!rutaCampus.exists()) {
                System.out.println("FALTA CAMPUS");
            }
            if (!rutaCarreras.exists()) {
                System.out.println("FALTA CARRERAS");
            }
            if (!rutaCarreras2.exists()) {
                System.out.println("FALTA CARRERAS2");
            }
            if (!rutaPlan.exists()) {
                System.out.println("FALTA PLAN");
            }
            if (!rutaProfesor.exists()) {
                System.out.println("FALTA PROFESOR");
            }
            if (!rutaUAIP.exists()) {
                System.out.println("FALTA UAIP");
            }
            if (!rutaUnidad.exists()) {
                System.out.println("FALTA UNIDAD");
            }
            if (!rutaUAIP2.exists()) {
                System.out.println("FALTA UAIP2");
            }
            if(!rutaALU.exists()){
                System.out.println("FALTA ALUMNO");
            }
            if(!rutaALUPG.exists()){
                System.out.println("FALTA APG");
            }
        }
    }
}
