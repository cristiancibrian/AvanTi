package mx.avanti.siract.ui;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.avanti.siract.entity.Alumno;

import mx.avanti.siract.entity.Cicloescolar;

import mx.avanti.siract.helper.UsuarioBeanHelper;
import mx.avanti.siract.helper.PoliticaEvaluacionBeanHelper;
import mx.avanti.siract.helper.MensajeBeanHelper;

import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.entity.Alerta;

import org.opensaml.xml.security.Criteria;

import org.primefaces.PrimeFaces;

/**
 *
 * @author Eduardo Cardona
 */
@ManagedBean
@ViewScoped
public class AceptarPoliticaBeanUI implements Serializable {

    @ManagedProperty(value = "#{loginBean}")

    private LoginBean loginBean;
    //TODO: change helper
    private PoliticaEvaluacionBeanHelper politicaevaluacionBeanHelper = null;
    private MensajeBeanHelper mensajeBeanHelper = null;

    private UsuarioBeanHelper usuarioBeanHelper = null;
    String fechaActual = "DD/MM/AAAA";
    private Profesor profesor;
    private String nombreCompleto;
    private String nombreProfesorCorreo;
    private String destinatario;
    private String nombreRPECorreo;
    private String destinatario2;
    private String destinatarioAlumno;

    private String nombreAlumno;

    private String unidadAprendizajeCorreo;
    private String grupoCorreo;
    int bandera;

    private String cicloEscolar;
    private int cicloEscolarId;
    private List<Politicaevaluacion> politicasPendientes,fitroPoliticas;
    private int uipIdSeleccionado;
    Politicaevaluacion politicaSeleccionada;
    private String textoNombreUsuario = "";
    private boolean esAlumno = false;
    private Politicaevaluacioncomentario comentarioPolitica;
    private Alumno alumno;
    private int politicasPendientesCount = 0;

    private List<Mensaje> message;

    private boolean disableBotones = true;
    private boolean disableSelect = true;

    private final int IDCATALOGOACEPTARPOLITICA = 20;

    public int getIDCATALOGOACEPTARPOLITICA() {
        return IDCATALOGOACEPTARPOLITICA;
    }

    // Constructor
    public AceptarPoliticaBeanUI() {
        init();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActual = sdf.format(date);
        Cicloescolar cicloEscolarActual = politicaevaluacionBeanHelper.cicloEscolarActual();
        cicloEscolarId = cicloEscolarActual.getCESid();
        cicloEscolar = cicloEscolarActual.getCEScicloEscolar();
        comentarioPolitica = new Politicaevaluacioncomentario();
        // TODO: Logica para Maestro o Alumno

    }

    public void init() {

        politicaevaluacionBeanHelper = new PoliticaEvaluacionBeanHelper();
        mensajeBeanHelper = new MensajeBeanHelper();
    }

    @PostConstruct
    public void postConstructor() {

        if (loginBean == null && loginBean.getLogueado() != null) {
            System.out.println("No hay loginbean");
        } else {
            try {
                if (loginBean.getLogueado().getProfesorList().size() > 0) {
                    profesor = loginBean.getLogueado().getProfesorList().get(0);
                    nombreCompleto = profesor.getPROnombre() + " " + profesor.getPROapellidoPaterno() + " " + profesor.getPROapellidoMaterno();
                    textoNombreUsuario = "Nombre de profesor:";
                }
                if (loginBean.getLogueado().getAlumnoList().size() > 0) {
                    alumno = loginBean.getLogueado().getAlumnoList().get(0);
                    nombreCompleto = alumno.getALNombre() + " " + alumno.getALApellidoPater() + " " + alumno.getALApellidoMaterno();
                    textoNombreUsuario = "Nombre de alumno:";
                    esAlumno = true;
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario sin profesor", "Este usuario no tiene ligado ningun profesor"));

            }
            if (esAlumno) {
                fitroPoliticas = politicaevaluacionBeanHelper.politicasAlumno(alumno.getALId());
                politicasPendientes = politicaevaluacionBeanHelper.politicasAlumno(alumno.getALId());
            } else {
                fitroPoliticas = politicaevaluacionBeanHelper
                        .getPoliticaPorResponsable(profesor.getPROid());
                politicasPendientes = politicaevaluacionBeanHelper
                        .getPoliticaPorResponsable(profesor.getPROid());
            }
            
            if(esAlumno){
            for(Politicaevaluacion politica: fitroPoliticas){
                if(politica.getEstadoPolEva()!=1){
                    politicasPendientes.remove(politica);
                }
            }
            }else{
                for(Politicaevaluacion politica: fitroPoliticas){
                if(politica.getEstadoPolEva()!=2){
                    politicasPendientes.remove(politica);
                }
            }
            }
                
            politicasPendientesCount = politicasPendientes.size();
            disableSelect = false;
            if (politicasPendientesCount < 1) {
                disableSelect = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin políticas pendientes", "Ninguna política por aceptar."));

            }

        }

    }

    public void onSelectPolitica() {
        disableBotones = false;
        if (uipIdSeleccionado == 0) {
            politicaSeleccionada = new Politicaevaluacion();
            disableBotones = true;
        }
        comentarioPolitica = new Politicaevaluacioncomentario();
        PrimeFaces.current().ajax().update("formId:representante");
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");

    }

    public void rechazar() {
        boolean respuesta = false;
        boolean res = false;
        if (esAlumno) {
            politicaevaluacionBeanHelper.ActualizarRechazoAlumno(politicaSeleccionada);
            comentarioPolitica.setAlumnoALId(alumno);
            bandera = 3;
            enviarCorreo();
        } else {
            politicaevaluacionBeanHelper.ActualizarRechazoResponsable(politicaSeleccionada);
            comentarioPolitica.setProfesorProId(profesor);
            bandera = 5;
            enviarCorreo();
        }
        comentarioPolitica.setPoliticaPOEId(politicaSeleccionada);

        respuesta = politicaevaluacionBeanHelper.agregarComentarioPolitica(comentarioPolitica);
        if (respuesta) {

            afterGuardadoExitoso("rechazada");

        }

        PrimeFaces.current().ajax().update("formId:pendientesCount");
    }

    public void enviarCorreo() {

        //Lleno la lista con los mensajes de la base de datos
        message = mensajeBeanHelper.getMensajes();
        int i;
        //profesor y alumno
        for (i = 0; i <= politicasPendientes.size(); i++) {
            System.out.println("Entro al for");
            if (uipIdSeleccionado == politicasPendientes.get(i).getUIPId().getUIPid()) {
                //si funiciona
                destinatario = politicasPendientes.get(i).getUIPId().getProfesorPROid().getUsuarioUSUid().getUSUusuario() + "@uabc.edu.mx";

                nombreAlumno = politicasPendientes.get(i).getAlumnoALId().getALNombre() + " " + politicasPendientes.get(i).getAlumnoALId().getALApellidoPater() + " " + politicasPendientes.get(i).getAlumnoALId().getALApellidoMaterno();
                destinatarioAlumno = politicasPendientes.get(i).getAlumnoALId().getALCorreo();
                nombreProfesorCorreo = politicasPendientes.get(i).getUIPId().getProfesorPROid().getPROnombre() + " " + politicasPendientes.get(i).getUIPId().getProfesorPROid().getPROapellidoPaterno() + " " + politicasPendientes.get(i).getUIPId().getProfesorPROid().getPROapellidoMaterno();
                unidadAprendizajeCorreo = politicasPendientes.get(i).getUIPId().getUnidadAprendizajeUAPid().getUAPnombre();
                grupoCorreo = String.valueOf(politicasPendientes.get(i).getUIPId().getGrupoGPOid().getGPOnumero());
                String ng = String.valueOf(grupoCorreo.charAt(5)) + String.valueOf(grupoCorreo.charAt(6)) + String.valueOf(grupoCorreo.charAt(7));
                grupoCorreo = ng;
                break;
            }

        }
        //RPE
        for (i = 0; i <= politicasPendientes.size(); i++) {
            if (uipIdSeleccionado == politicasPendientes.get(i).getUIPId().getUIPid()) {
                //si funiciona
                destinatario2 = politicasPendientes.get(i).getResponsablePROId().getUsuarioUSUid().getUSUusuario() + "@uabc.edu.mx";
                nombreRPECorreo = politicasPendientes.get(i).getResponsablePROId().getPROnombre() + " " + politicasPendientes.get(i).getResponsablePROId().getPROapellidoPaterno() + " " + politicasPendientes.get(i).getResponsablePROId().getPROapellidoMaterno();
                unidadAprendizajeCorreo = politicasPendientes.get(i).getUIPId().getUnidadAprendizajeUAPid().getUAPnombre();
                grupoCorreo = String.valueOf(politicasPendientes.get(i).getUIPId().getGrupoGPOid().getGPOnumero());
                String ng = String.valueOf(grupoCorreo.charAt(5)) + String.valueOf(grupoCorreo.charAt(6)) + String.valueOf(grupoCorreo.charAt(7));
                grupoCorreo = ng;
                break;
            }

        }

        System.out.println("Entro al metodo");
        String correoEnvia = " siract.fim@uabc.edu.mx";
        String contrasena = "3aMKAPJt&g";
        String TituloAlerta = "Política de evaluación aceptada";
        String TituloAlerta2 = "Política de evaluación rechazada";

        switch (bandera) {

            case 2:
                String MENSAJE_HTML_acepto_alumno = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
                        + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
                        + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
                        + "                <tr>\n"
                        + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
                        + "                        \n" + TituloAlerta
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>\n"
                        + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
                        + "                                Facultad de Ingeniería\n"
                        + "                        </p>\n"
                        + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
                        + "                        <br>\n"
                        + "                        <b>Estimado(a): nombreProfesor"
                        + "                        <br>\n"
                        + "</p>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "
                        + "                     MENSAJE  \n"
                        + "</p>"
                        + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
                        + "</div>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
                        + "                        al Administrador del Sistema siract.fim@uabc.edu.mx.<br>"
                        //                + "                        Asimismo, si requiere conocer los pasos a seguir para realizar el reporte de avance consultar el\n"
                        //                + "                        <a href=\"https://www.youtube.com/watch?v=PogX3JBdISI\" target=\"_blank\">TUTORIAL SIRACT</a>. \n"
                        + "                        </div>\n"
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
                        + "                ©SIRACT\n"
                        + "            </p>\n"
                        + "        </div>";

                Properties propiedad = new Properties();
                Properties propiedad2 = new Properties();
                //profesor
                propiedad.put("mail.smtp.host", "smtp.gmail.com");
                propiedad.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad.setProperty("mail.smtp.port", "587");
                propiedad.setProperty("mail.smtp.auth", "true");
                propiedad.setProperty("mail.smtp.starttls.enable", "true");
                propiedad.setProperty("mail.smtp.user", correoEnvia);
                //rpe
                propiedad2.put("mail.smtp.host", "smtp.gmail.com");
                propiedad2.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad2.setProperty("mail.smtp.port", "587");
                propiedad2.setProperty("mail.smtp.auth", "true");
                propiedad2.setProperty("mail.smtp.starttls.enable", "true");
                propiedad2.setProperty("mail.smtp.user", correoEnvia);

                //PROFESOR
                Session sesion;
                sesion = Session.getDefaultInstance(propiedad);
                //RPE
                Session sesion2;
                sesion2 = Session.getDefaultInstance(propiedad2);
                //Profesor
                MimeMessage mail = new MimeMessage(sesion);
                MimeMultipart mp = new MimeMultipart("related");
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                //RPE
                MimeMessage mail2 = new MimeMessage(sesion2);
                MimeMultipart mp2 = new MimeMultipart("related");
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();

                try {
                    mail.setFrom(new InternetAddress(correoEnvia));
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                    mail.setSubject("Políticas de Evaluación");
                    //En la siguiente sentencia cambio la parte del texto que dice nombreProfesor por la variable nombreProfesorCorreo que tiene el nombre correspondiente dependiendo de la clase
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace("nombreProfesor", nombreProfesorCorreo);
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace("MENSAJE", message.get(4).getMENmensaje());
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace("[unidadAprendizajeCorreo]", "<b>" + unidadAprendizajeCorreo + "</b>");
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace("[grupoCorreo]", "<b>" + grupoCorreo + "</b>");
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace("[NOMBRE ALUMNO]", "<b>" + nombreAlumno + "</b>");

                    messageBodyPart.setText(MENSAJE_HTML_acepto_alumno, "UTF-8", "html");
                    mp.addBodyPart(messageBodyPart);
                    mail.setContent(mp);

                    Transport transporte = sesion.getTransport("smtp");
                    transporte.connect(correoEnvia, contrasena);
                    transporte.sendMessage(mail, mail.getAllRecipients());
                    transporte.close();
                    System.out.println("Correo enviado a Profesor");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado a Profesor");

                //RPE
                try {
                    mail2.setFrom(new InternetAddress(correoEnvia));
                    mail2.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario2));
                    mail2.setSubject("Políticas de Evaluación");
                    MENSAJE_HTML_acepto_alumno = MENSAJE_HTML_acepto_alumno.replace(nombreProfesorCorreo, nombreRPECorreo);
                    messageBodyPart2.setText(MENSAJE_HTML_acepto_alumno, "UTF-8", "html");
                    mp2.addBodyPart(messageBodyPart2);
                    mail2.setContent(mp2);

                    Transport transporte2 = sesion2.getTransport("smtp");
                    transporte2.connect(correoEnvia, contrasena);
                    transporte2.sendMessage(mail2, mail2.getAllRecipients());
                    transporte2.close();
                    System.out.println("Correo enviado a RPE");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado a RPE");
                break;

            case 3:
                String MENSAJE_HTML_rechazo_alumno = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
                        + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
                        + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
                        + "                <tr>\n"
                        + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
                        + "                        \n" + TituloAlerta2
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>\n"
                        + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
                        + "                                Facultad de Ingeniería\n"
                        + "                        </p>\n"
                        + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
                        + "                        <br>\n"
                        + "                        <b>Estimado(a): nombreProfesor"
                        + "                        <br>\n"
                        + "</p>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "
                        + "                       Mensajee\n"
                        + "</p>"
                        + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
                        + "</div>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
                        + "                        al Administrador del Sistema siract.fim@uabc.edu.mx.<br>"
                        //                + "                        Asimismo, si requiere conocer los pasos a seguir para realizar el reporte de avance consultar el\n"
                        //                + "                        <a href=\"https://www.youtube.com/watch?v=PogX3JBdISI\" target=\"_blank\">TUTORIAL SIRACT</a>. \n"
                        + "                        </div>\n"
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
                        + "                ©SIRACT\n"
                        + "            </p>\n"
                        + "        </div>";

                Properties propiedad3 = new Properties();
                propiedad3.put("mail.smtp.host", "smtp.gmail.com");
                propiedad3.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad3.setProperty("mail.smtp.port", "587");
                propiedad3.setProperty("mail.smtp.auth", "true");
                propiedad3.setProperty("mail.smtp.starttls.enable", "true");
                propiedad3.setProperty("mail.smtp.user", correoEnvia);

                Session sesion3;
                sesion3 = Session.getDefaultInstance(propiedad3);

                MimeMessage mail3 = new MimeMessage(sesion3);
                MimeMultipart mp3 = new MimeMultipart("related");
                MimeBodyPart messageBodyPart3 = new MimeBodyPart();

                try {
                    mail3.setFrom(new InternetAddress(correoEnvia));
                    mail3.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                    mail3.setSubject("Políticas de Evaluación");
                    //En la siguiente sentencia cambio la parte del texto que dice nombreProfesor por la variable nombreProfesorCorreo que tiene el nombre correspondiente dependiendo de la clase
                    MENSAJE_HTML_rechazo_alumno = MENSAJE_HTML_rechazo_alumno.replace("nombreProfesor", nombreProfesorCorreo);
                    MENSAJE_HTML_rechazo_alumno = MENSAJE_HTML_rechazo_alumno.replace("Mensajee", message.get(6).getMENmensaje());
                    MENSAJE_HTML_rechazo_alumno = MENSAJE_HTML_rechazo_alumno.replace("[unidadAprendizajeCorreo]", "<b>" + unidadAprendizajeCorreo + "</b>");
                    MENSAJE_HTML_rechazo_alumno = MENSAJE_HTML_rechazo_alumno.replace("[grupoCorreo]", "<b>" + grupoCorreo + "</b>");
                    MENSAJE_HTML_rechazo_alumno = MENSAJE_HTML_rechazo_alumno.replace("[NOMBRE DEL ALUMNO]", "<b>" + nombreAlumno + "</b>");

                    messageBodyPart3.setText(MENSAJE_HTML_rechazo_alumno, "UTF-8", "html");
                    mp3.addBodyPart(messageBodyPart3);
                    mail3.setContent(mp3);

                    Transport transporte3 = sesion3.getTransport("smtp");
                    transporte3.connect(correoEnvia, contrasena);
                    transporte3.sendMessage(mail3, mail3.getAllRecipients());
                    transporte3.close();
                    System.out.println("Correo enviado");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado");
                break;

            case 4:
                String Mensaje = "hola";
                String MENSAJE_HTML_acepto_RPE = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
                        + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
                        + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
                        + "                <tr>\n"
                        + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
                        + "                        \n" + TituloAlerta
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>\n"
                        + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
                        + "                                Facultad de Ingeniería\n"
                        + "                        </p>\n"
                        + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
                        + "                        <br>\n"
                        + "                        <b>Estimado(a): nombreProfesor"
                        + "                        <br>\n"
                        + "</p>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "
                        + "                      MensajE"
                        + "</p>"
                        + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
                        + "</div>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
                        + "                        al Administrador del Sistema siract.fim@uabc.edu.mx.<br>"
                        //                + "                        Asimismo, si requiere conocer los pasos a seguir para realizar el reporte de avance consultar el\n"
                        //                + "                        <a href=\"https://www.youtube.com/watch?v=PogX3JBdISI\" target=\"_blank\">TUTORIAL SIRACT</a>. \n"
                        + "                        </div>\n"
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
                        + "                ©SIRACT\n"
                        + "            </p>\n"
                        + "        </div>";

                Properties propiedad4 = new Properties();
                propiedad4.put("mail.smtp.host", "smtp.gmail.com");
                propiedad4.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad4.setProperty("mail.smtp.port", "587");
                propiedad4.setProperty("mail.smtp.auth", "true");
                propiedad4.setProperty("mail.smtp.starttls.enable", "true");
                propiedad4.setProperty("mail.smtp.user", correoEnvia);

                Session sesion4;
                sesion4 = Session.getDefaultInstance(propiedad4);

                MimeMessage mail4 = new MimeMessage(sesion4);
                MimeMultipart mp4 = new MimeMultipart("related");
                MimeBodyPart messageBodyPart4 = new MimeBodyPart();

                try {
                    mail4.setFrom(new InternetAddress(correoEnvia));
                    mail4.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                    mail4.setSubject("Políticas de Evaluación");
                    //En la siguiente sentencia cambio la parte del texto que dice nombreProfesor por la variable nombreProfesorCorreo que tiene el nombre correspondiente dependiendo de la clase
                    MENSAJE_HTML_acepto_RPE = MENSAJE_HTML_acepto_RPE.replace("nombreProfesor", nombreProfesorCorreo);
                    MENSAJE_HTML_acepto_RPE = MENSAJE_HTML_acepto_RPE.replace("MensajE", message.get(5).getMENmensaje());
                    MENSAJE_HTML_acepto_RPE = MENSAJE_HTML_acepto_RPE.replace("[unidadAprendizaje]", "<b>" + unidadAprendizajeCorreo + "</b>");
                    MENSAJE_HTML_acepto_RPE = MENSAJE_HTML_acepto_RPE.replace("[Grupo]", "<b>" + grupoCorreo + "</b>");

                    messageBodyPart4.setText(MENSAJE_HTML_acepto_RPE, "UTF-8", "html");
                    mp4.addBodyPart(messageBodyPart4);
                    mail4.setContent(mp4);

                    Transport transporte4 = sesion4.getTransport("smtp");
                    transporte4.connect(correoEnvia, contrasena);
                    transporte4.sendMessage(mail4, mail4.getAllRecipients());
                    transporte4.close();
                    System.out.println("Correo enviado");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado");

                //ALUMNO
                Properties propiedad_4 = new Properties();
                propiedad_4.put("mail.smtp.host", "smtp.gmail.com");
                propiedad_4.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad_4.setProperty("mail.smtp.port", "587");
                propiedad_4.setProperty("mail.smtp.auth", "true");
                propiedad_4.setProperty("mail.smtp.starttls.enable", "true");
                propiedad_4.setProperty("mail.smtp.user", correoEnvia);

                Session sesion_4;
                sesion_4 = Session.getDefaultInstance(propiedad_4);

                MimeMessage mail_4 = new MimeMessage(sesion_4);
                MimeMultipart mp_4 = new MimeMultipart("related");
                MimeBodyPart messageBodyPart_4 = new MimeBodyPart();

                try {
                    mail_4.setFrom(new InternetAddress(correoEnvia));
                    mail_4.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatarioAlumno));
                    mail_4.setSubject("Políticas de Evaluación");
                    MENSAJE_HTML_acepto_RPE = MENSAJE_HTML_acepto_RPE.replace(nombreProfesorCorreo, nombreAlumno);
                    messageBodyPart_4.setText(MENSAJE_HTML_acepto_RPE, "UTF-8", "html");
                    mp_4.addBodyPart(messageBodyPart_4);
                    mail_4.setContent(mp_4);

                    Transport transporte_4 = sesion_4.getTransport("smtp");
                    transporte_4.connect(correoEnvia, contrasena);
                    transporte_4.sendMessage(mail_4, mail_4.getAllRecipients());
                    transporte_4.close();
                    System.out.println("Correo enviado");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado");
                break;

            case 5:
                String MENSAJE_HTML_rechazo_RPE = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
                        + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
                        + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
                        + "                <tr>\n"
                        + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
                        + "                        \n" + TituloAlerta2
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "                <tr>\n"
                        + "                    <td>\n"
                        + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
                        + "                                Facultad de Ingeniería\n"
                        + "                        </p>\n"
                        + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
                        + "                        <br>\n"
                        + "                        <b>Estimado(a): nombreProfesor"
                        + "                        <br>\n"
                        + "</p>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "
                        + "                       Mensajee\n"
                        + "</p>"
                        + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
                        + "</div>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
                        + "                        al Administrador del Sistema siract.fim@uabc.edu.mx.<br>"
                        //                + "                        Asimismo, si requiere conocer los pasos a seguir para realizar el reporte de avance consultar el\n"
                        //                + "                        <a href=\"https://www.youtube.com/watch?v=PogX3JBdISI\" target=\"_blank\">TUTORIAL SIRACT</a>. \n"
                        + "                        </div>\n"
                        + "                    </td>\n"
                        + "                </tr>\n"
                        + "            </table>\n"
                        + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
                        + "                ©SIRACT\n"
                        + "            </p>\n"
                        + "        </div>";

                Properties propiedad5 = new Properties();
                propiedad5.put("mail.smtp.host", "smtp.gmail.com");
                propiedad5.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad5.setProperty("mail.smtp.port", "587");
                propiedad5.setProperty("mail.smtp.auth", "true");
                propiedad5.setProperty("mail.smtp.starttls.enable", "true");
                propiedad5.setProperty("mail.smtp.user", correoEnvia);

                Session sesion5;
                sesion5 = Session.getDefaultInstance(propiedad5);

                MimeMessage mail5 = new MimeMessage(sesion5);
                MimeMultipart mp5 = new MimeMultipart("related");
                MimeBodyPart messageBodyPart5 = new MimeBodyPart();

                try {
                    mail5.setFrom(new InternetAddress(correoEnvia));
                    mail5.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                    mail5.setSubject("Políticas de Evaluación");
                    //En la siguiente sentencia cambio la parte del texto que dice nombreProfesor por la variable nombreProfesorCorreo que tiene el nombre correspondiente dependiendo de la clase
                    MENSAJE_HTML_rechazo_RPE = MENSAJE_HTML_rechazo_RPE.replace("nombreProfesor", nombreProfesorCorreo);
                    MENSAJE_HTML_rechazo_RPE = MENSAJE_HTML_rechazo_RPE.replace("Mensajee", message.get(7).getMENmensaje());
                    MENSAJE_HTML_rechazo_RPE = MENSAJE_HTML_rechazo_RPE.replace("[unidadAprendizajeCorreo]", "<b>" + unidadAprendizajeCorreo + "</b>");
                    MENSAJE_HTML_rechazo_RPE = MENSAJE_HTML_rechazo_RPE.replace("[grupoCorreo]", "<b>" + grupoCorreo + "</b>");
                    messageBodyPart5.setText(MENSAJE_HTML_rechazo_RPE, "UTF-8", "html");
                    mp5.addBodyPart(messageBodyPart5);
                    mail5.setContent(mp5);

                    Transport transporte5 = sesion5.getTransport("smtp");
                    transporte5.connect(correoEnvia, contrasena);
                    transporte5.sendMessage(mail5, mail5.getAllRecipients());
                    transporte5.close();
                    System.out.println("Correo enviado");
                } catch (AddressException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(RACTBeanUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Correo enviado");
                break;

        }

    }

    public void Aceptacion() {
        boolean respuesta = false;

        //TODO: Validar si es alumno;
        if (esAlumno) {
            respuesta = politicaevaluacionBeanHelper.AceptacionAlumno(politicaSeleccionada);
            bandera = 2;
            enviarCorreo();
        } else {
            respuesta = politicaevaluacionBeanHelper.AceptacionRPE(politicaSeleccionada);
            bandera = 4;
            enviarCorreo();
        }
        if (respuesta) {
            afterGuardadoExitoso("guardada");
        }
        PrimeFaces.current().ajax().update("formId:pendientesCount");

    }

    private void afterGuardadoExitoso(String estado) {
        FacesContext context = FacesContext.getCurrentInstance();
        disableBotones = true;
        politicasPendientes.remove(politicaSeleccionada);
        politicaSeleccionada = new Politicaevaluacion();
        politicasPendientesCount = politicasPendientes.size();
        if (politicasPendientesCount < 1) {
            disableSelect = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sin políticas pendientes", "Ninguna política por aceptar."));

        }
        PrimeFaces.current().ajax().update("formId:tablaCriterios");
        PrimeFaces.current().ajax().update("formId:textCom");
        PrimeFaces.current().ajax().update("formId:criterioExentarPO");
        PrimeFaces.current().ajax().update("formId:UnidadesAprendisaje");
        PrimeFaces.current().ajax().update("formId:pendientesCoun");
        PrimeFaces.current().ajax().update("formId:ge");
        PrimeFaces.current().ajax().update("formId:gs");
        PrimeFaces.current().ajax().update("formId:representante");

        String message = "política " + estado + " exitosamente, " + politicasPendientes.size() + " política(s) pendiente(s).";

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado exitoso", message));
    }

    private void afterError() {
        FacesContext context = FacesContext.getCurrentInstance();
        String message = "Hubo un error al guardar la política, por favor intente de nuevo.";

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", message));
    }

    //-----------SETTERS & GETTER ---------------//
    public String getFechaActual() {
        return fechaActual;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCicloEscolar() {
        return cicloEscolar;
    }

    public List<Politicaevaluacion> getPoliticasPendientes() {
        return politicasPendientes;
    }

    public int getUipIdSeleccionado() {
        return uipIdSeleccionado;
    }

    public void setUipIdSeleccionado(int uipIdSeleccionado) {
        this.uipIdSeleccionado = uipIdSeleccionado;
        for (Politicaevaluacion p : politicasPendientes) {
            if (p.getUIPId().getUIPid() == uipIdSeleccionado) {
                politicaSeleccionada = p;
                break;
            }
        }
    }

    public Politicaevaluacion getPoliticaSeleccionada() {
        return politicaSeleccionada;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getTextoNombreUsuario() {
        return textoNombreUsuario;
    }

    public Politicaevaluacioncomentario getComentarioPolitica() {
        return comentarioPolitica;
    }

    public void setComentarioPolitica(Politicaevaluacioncomentario comentarioPolitica) {
        this.comentarioPolitica = comentarioPolitica;
    }

    public int getPoliticasPendientesCount() {
        return politicasPendientesCount;
    }

    public boolean isDisableBotones() {
        return disableBotones;
    }

    public boolean isDisableSelect() {
        return disableSelect;
    }

}
