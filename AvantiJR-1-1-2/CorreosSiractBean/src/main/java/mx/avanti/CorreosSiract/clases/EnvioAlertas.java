package mx.avanti.CorreosSiract.clases;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Configuracion;
import mx.avanti.siract.entity.Mensaje;
import mx.avanti.siract.entity.Profesor;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import mx.avanti.CorreosSiract.persistencia.BaseDAO;
import mx.avanti.CorreosSiract.persistencia.Criterios;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.integration.ServiceLocator;


public class EnvioAlertas implements Serializable {

    BaseDAO baseDAO = new BaseDAO();
    Criterios criterios = new Criterios();
    Calendarioreporte calendarioReporte = new Calendarioreporte();
    Cicloescolar cicloescolar;
    SimpleDateFormat sdfDDMMMMYYYYEs = new SimpleDateFormat("dd/MMMM/yyyy", new Locale("es"));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int numeroReporte = 0;
    private int tipoDeMensaje = 99;
    //<editor-fold defaultstate="collapsed" desc="HTML del correo reportes">
    private String MENSAJE_HTML = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
            + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
            + "            <table style=\"margin: 0 auto; width: 480px; margin-top: 20px; border-radius: 10px; border: 4px green solid;\">\n"
            + "                <tr>\n"
            + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
            + "                        TituloAlerta\n"
            + "                    </td>\n"
            + "                </tr>\n"
            + "                <tr>\n"
            + "                    <td>\n"
            + "                        <p style=\"text-align: center; color:black; font-family: arial; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
            + "                                Facultad de Ingeniería\n"
            + "                        </p>\n"
            + "                        <p style=\"font-family: arial; color:black;  font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
            + "                        <br>\n"
            + "                        <b>Profesor: nombreProfesor</b><br><br>\n"
            +"</p>"
            + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "

            + "                        Mensaje"
            + " <br><br>"
            + "   <b> NOTA: En caso de haber enviado su reporte bajo la opción (Guardar y enviar) le pedimos por favor omitir este mensaje, mismo que ha sido enviado masivamente debido a limitaciones derivadas por la contingencia. </b> <br><br> "
            +"</p>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
                        + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n "

            + "                        Por el momento el sistema sólo estará disponible para reportar unidades de aprendizaje de los programas educativos de Tronco Común, Electrónica, Mecatrónica y Sistemas Computacionales.<br><br>"
            + "                        Sin más por el momento, un cordial saludo.<br><br>\n"
            +"</p>"
            + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br><br>"
           +"</div>"
                        + "                <div style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"

            + "                        Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
            + "                        al Administrador del Sistema siract.fim@uabc.edu.mx\n"
            + "                        </div>\n"
            + "                    </td>\n"
            + "                </tr>\n"
            + "            </table>\n"
            + "            <p style=\"text-align: center; font-family: arial; color:black; font-size: 12px;\">\n"
            + "                ©SIRACT\n"
            + "            </p>\n"
            + "        </div>";
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HTML del correo alerta general">
    String MENSAJE_GENERAL = "<div style=\"width: 700px; background: white; border: 3px solid green; border-radius: 10px;\">\n"
            + "            <img src=\"http://ingenieria.mxl.uabc.mx/images/logonew.png\" width=\"480\" style=\"margin-left: 110px; margin-top: 10px;\" alt=\"\">\n"
            + "            <table style=\"margin: 0 auto; width: 600px; margin-top: 20px;   color: black; border-radius: 10px; border: 4px green solid;\">\n"
            + "                <tr>\n"
            + "                    <td style=\"text-align: center; color: white; font-family: arial; background-color: green; border-radius: 5px;\">\n"
            + "                        TituloAlerta\n"
            + "                    </td>\n"
            + "                </tr>\n"
            + "                <tr>\n"
            + "                    <td style=\"border: 1px solid #C0C0C0; color: black; padding: 5px;\">\n"
            + "                        <p style=\"text-align: center; font-family: arial; color: black; font-size: 16px;\">Universidad Autónoma de Baja California<br>\n"
            + "                            Facultad de Ingeniería\n"
            + "                        </p>\n"
            + "                        <p style=\"font-family: arial;  font-size: 16px; color: black; margin-left: 5px;\">\n"
            + "                            <br>\n"
            + "                            <b>Profesor: nombreProfesor</b><br><br>\n"
            + "                        </p>\n"
            + "                        <p style=\"font-family: arial;  font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"

            + "                            mensaje<br>\n"
            +"</p>"
            + "                        <p style=\"font-family: arial;  font-size: 16px; color: black; margin-left: 5px;\">\n"

            + "                <center>\n"
            + "                    <table style=\"border:1px solid #C0C0C0; color: black;   border-collapse: collapse; padding: 5px; width:400px;\">\n"
   //         + "                        <caption style=\"font-family: arial; font-size: 16px; margin-left: 5px;\">A continuación se señalan las fechas de corte y límite para reportar el avance respectivo.</caption>\n"
            + "                        <thead>\n"
            + "                            <tr>\n"
            + "                                <th style=\" background: #F0F0F0; border:1px solid #C0C0C0;   padding: 5px;\">Numero de Reporte</th>\n"
            + "                                <th style=\" background: #F0F0F0; border:1px solid #C0C0C0;   padding: 5px;\">Fecha Corte</th>\n"
            + "                                <th style=\" background: #F0F0F0; border:1px solid #C0C0C0;   padding: 5px;\">Fecha Limite</th>\n"
            + "                            </tr>\n"
            + "                        </thead>\n"
            + "                        <tbody>\n"
            + "                            <tr>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">1ro</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">1rocorte</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">1rolimite</td>\n"
            + "                            </tr>\n"
            + "                            <tr>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">2do</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">2docorte</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">2dolimite</td>\n"
            + "                            </tr>\n"
            + "                            <tr>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">3ro</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">3rocorte</td>\n"
            + "                                <td style=\"border: 1px solid #C0C0C0; padding: 5px;\">3rolimite</td>\n"
            + "                            </tr>\n"
            + "                        </tbody>\n"
            + "                    </table>\n"
                        +"</p>"

            + "                </center>\n"
            + "                <br>                \n"
            + "                        <p style=\"font-family: arial; color: black; font-size: 16px; margin-left: 5px; text-align: justify;\">\n"
            + "                            segundotexto<br>\n"
//            + "                <center style=\"font-family: arial; font-size: 16px; margin-left: 5px;\">\n"
//            + "                    Sin más por el momento, un cordial saludo.\n"
//            + "                </center>                \n"
            + "                        <center><a href=\"http://siract.mxl.uabc.mx:8080\" target=\"_blank\">Enlace a SIRACT</a></center><br>"
                        + "                        </p>\n"

            + "                <p style=\"font-family: arial; font-size: 16px; color: black; margin-left: 5px; text-align: justify;\">\n"
            + "                Para cualquier comentario o aclaración sobre el sistema favor de enviar un correo\n"
            + "                al Administrador del Sistema siract.fim@uabc.edu.mx\n"
            + "                </p>\n"
            + "                </td>\n"
            + "                </tr>\n"
            + "            </table>\n"
            + "            <p style=\"text-align: center; font-family: arial; color: black; font-size: 12px;\">\n"
            + "                ©SIRACT\n"
            + "            </p>\n"
            + "        </div>";

    //</editor-fold>
    
    public String calendarios() throws ParseException {
        try {
            //--------------------Se obtiene fecha actual, se saca el ciclo----------
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(sdf.format(new Date())); //Date para obtener la fecha actual sin tiempo
            //Date date = new Date(117, 4, 19); //Date para manipular la fecha
            System.out.println("Fecha Actual: " + dateFormat.format(date));
            
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            /**
             * Esta parte del código establece el ciclo escolar dependiendo del mes en el que nos econtramos, si es mayor
             * o igual a 1, osease de enero hasta julio, es ciclo -1, si es de julio en adelante es ciclo -2
             */
            String ciclo = "";
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                ciclo = "" + anio + "-" + 1;
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                ciclo = "" + anio + "-" + 2;
            }
            System.out.println("Ciclo Escolar: " + ciclo);
            
            cicloescolar = ServiceLocator.getInstanceCicloEscolarDAO().getListaOrdenada().get(0);//criterios.cesCriteria(ciclo); //Se obtiene el ciclo escolar
            if (cicloescolar != null) { //Verifica que exista en la BD un ciclo escolar
                System.out.println(cicloescolar.getCESid());
                //Configuracion configuracion = ServiceFacadeLocator.getInstanceConfiguracionFacade().
                Configuracion configuracion = ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarTodasConfiguraciones().get(ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarTodasConfiguraciones().size()-1);//criterios.conCriteria(cicloescolar.getCESid());            
                System.out.println(configuracion.getCicloEscolarCESid() + "////////////////////////////////////////");
                if (configuracion != null) {     
                    /**
                     * Aquí se enviar el correo general, este correo solo se envía una vez al semestre en la fecha de inicio de 
                     * semestre, sirve para que los maestros conozcan las fechas en las que se realizará RACT
                     * Lo que hace es sustituir palabras clave del códigio HTML que se tiene arriba por la información correspondiente
                     * de la base de datos.
                     */
                    if (configuracion.getCONfechaInicioSemestre().equals(date)) {
                        System.out.println("Si es reporte general");
                        //TODO Cambiar criterios por ServiceFacadeLocator
                        Mensaje mensajeGeneral = criterios.menCriteria(1);
                        String menGrlHtml = MENSAJE_GENERAL;
                        
                        List<Calendarioreporte> calendarioreportes = new ArrayList<>();
                        calendarioreportes = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().findAll();
                        //calendarioreportes = criterios.findAllCre(configuracion.getCONid());
                        if (calendarioreportes.size() == 3) {
                            menGrlHtml = menGrlHtml.replace("1rocorte", sdfDDMMMMYYYYEs.format(calendarioreportes.get(0).getCREfechaCorte()));
                            menGrlHtml = menGrlHtml.replace("1rolimite", sdfDDMMMMYYYYEs.format(calendarioreportes.get(0).getCREfechaLimite()));
                            menGrlHtml = menGrlHtml.replace("2docorte", sdfDDMMMMYYYYEs.format(calendarioreportes.get(1).getCREfechaCorte()));
                            menGrlHtml = menGrlHtml.replace("2dolimite", sdfDDMMMMYYYYEs.format(calendarioreportes.get(1).getCREfechaLimite()));
                            menGrlHtml = menGrlHtml.replace("3rocorte", sdfDDMMMMYYYYEs.format(calendarioreportes.get(2).getCREfechaCorte()));
                            menGrlHtml = menGrlHtml.replace("3rolimite", sdfDDMMMMYYYYEs.format(calendarioreportes.get(2).getCREfechaLimite()));
//                            menGrlHtml = menGrlHtml.replace("mensaje", mensajeGeneral.getMenmensaje());
//                            menGrlHtml = menGrlHtml.replace("TituloAlerta", "Alerta General - Ciclo: " + ciclo);
//                            menGrlHtml = menGrlHtml.replace("SIRACTWEB", "<a href=\"www.google.com.mx\" target=\"_blank\">SIRACT</a>");
                            String[] mensaje = mensajeGeneral.getMENmensaje().split("--");
                            //menGrlHtml = menGrlHtml.replace("mensaje", mensajeGeneral.getMenmensaje());
                            menGrlHtml = menGrlHtml.replace("mensaje", mensaje[0]);
                            menGrlHtml = menGrlHtml.replace("segundotexto", mensaje[1]);
                            
                            menGrlHtml = menGrlHtml.replace("TituloAlerta", "Alerta General - Ciclo: " + ciclo);
                            //menGrlHtml = menGrlHtml.replace("TituloAlerta", "Alerta General - Ciclo: 2018-2");
                            menGrlHtml = menGrlHtml.replace("SIRACTWEB", "<a href=\"www.google.com.mx\" target=\"_blank\">SIRACT</a>");
                            return menGrlHtml;
                        } else {
                            System.out.println("No tiene suficientes calendarios");
                            return null;
                        }
                    } else {
                        System.out.println("No es reporte general");
                    }
                    System.out.println("Configuracion: " + configuracion.getCONnumeroSemanas());
                    baseDAO.setTipo(Calendarioreporte.class);
                    List<Calendarioreporte> calendarioreportes = new ArrayList<Calendarioreporte>();
                    List<CalendarioreporteTieneAlerta> ctas = new ArrayList<CalendarioreporteTieneAlerta>();
                    //--
                    List<Date> fechasAleCorte = new ArrayList<Date>();
                    List<Date> fechasAleLimite = new ArrayList<Date>();
                    List<Date> fechasAleAtraso = new ArrayList<Date>();
                    //--
                    //calendarioreportes = criterios.findAllCre(configuracion.getCONid());
                    
                    calendarioreportes = configuracion.getCalendarioreporteList();
                    for(Calendarioreporte x:calendarioreportes){
                        System.out.println(x.getCREfechaCorte() + " - " + x.getCREfechaLimite() + "////////////////////");
                    }
                    
                    if (calendarioreportes.size() == 3) {
                        for (Calendarioreporte c : calendarioreportes) {
                            System.out.println(c.getCREfechaCorte() + " -- " + c.getCREfechaLimite());
                            ctas = c.getCalendarioreporteTieneAlertaList();
                            
                            System.out.println("Milisegundos de ctas:"+ctas.get(0).getCALdias()* 24 * 3600 * 1000);
                            System.out.println("Milisegundos de ctas:"+ctas.get(1).getCALdias()* 24 * 3600 * 1000);
                            System.out.println("Milisegundos de ctas:"+ctas.get(2).getCALdias()* 24 * 3600 * 1000);
                            System.out.println("GetCalDias 0 "+ctas.get(0).getCALdias());
                            System.out.println("GetCalDias 1 "+ctas.get(1).getCALdias());
                            System.out.println("GetCalDias 2 "+ctas.get(2).getCALdias());
                            System.out.println("Resta de las fechas:"+new Date((c.getCREfechaCorte().getTime() - ctas.get(0).getCALdias()* 24 * 3600 * 1000)));
                            System.out.println("Resta de las fechas:"+new Date((c.getCREfechaLimite().getTime() - ctas.get(1).getCALdias()* 24 * 3600 * 1000)));
                            System.out.println("Resta de las fechas:"+new Date((c.getCREfechaLimite().getTime() + ctas.get(2).getCALdias()* 24 * 3600 * 1000)));
                            
                            //Agrega a la lista la fecha para el envío de alerta Corte, el cálculo de esta operacion es en base a milisegundos y se transforma en tipo Date. 
                            //Se resta la fecha corte con los dias que fueron asignados en las alertas.
                            fechasAleCorte.add(new Date((c.getCREfechaCorte().getTime()) - (ctas.get(0).getCALdias() * 24 * 3600 * 1000)));
                            
                            //Agrega a la lista la fecha para el envío de alerta Limite, el cálculo de esta operacion es en base a milisegundos y se transforma en tipo Date. 
                            //Se resta la fecha limite con los dias que fueron asignados en las alertas.
                            fechasAleLimite.add(new Date((c.getCREfechaLimite().getTime()) - (ctas.get(2).getCALdias() * 24 * 3600 * 1000))); //Se le restan o suman dias
                            
                            //Agrega a la lista la fecha para el envío de alerta Atraso, el cálculo de esta operacion es en base a milisegundos y se transforma en tipo Date. 
                            //Se suma la fecha atraso con los dias que fueron asignados en las alertas.
                            fechasAleAtraso.add(new Date((c.getCREfechaLimite().getTime()) + (ctas.get(1).getCALdias() * 24 * 3600 * 1000)));
                        }
                        System.out.println("Numero de calendarios: " + ctas.size());
                        System.out.println("Numero FAC: " + fechasAleCorte.size());
                        numeroReporte = 0;
                        Calendarioreporte calReporte = new Calendarioreporte();
                        for (int i = 0; i < calendarioreportes.size(); i++) {
                            if ((date.before(fechasAleAtraso.get(i)) || date.equals(fechasAleAtraso.get(i))) && (date.after(fechasAleCorte.get(i)) || date.equals(fechasAleCorte.get(i)))) {
                                //if ((date.compareTo(fechasAleAtraso.get(i))>0 || date.compareTo(fechasAleAtraso.get(i))==0) && (date.compareTo(fechasAleCorte.get(i))<0 || date.compareTo(fechasAleCorte.get(i))==0)) {
                                numeroReporte = i + 1;
                                calReporte = calendarioreportes.get(i);
                                calendarioReporte = calReporte;
                            }
                        }
                        System.out.println("Alerta limite fecha:"+fechasAleLimite.get(0));
                        System.out.println("Alerta limite fecha:"+fechasAleLimite.get(1));
                        System.out.println("Alerta limite fecha:"+fechasAleLimite.get(2));
                        System.out.println("Numero de reporte: " + numeroReporte);
                        int numeroAlerta = 0;
                        System.out.println("Date:"+date.toString());
                        Mensaje mensajeP=null;
                        if (numeroReporte != 0) {
                            for (int i = 0; i < fechasAleAtraso.size(); i++) {
                                System.out.print("FECHA: "+fechasAleAtraso.get(i));
                                if (fechasAleCorte.get(i).equals(date)) {
                                    System.out.println("soy alerta corte");
                                    numeroAlerta = 2;
                                }
                                if (fechasAleLimite.get(i).equals(date)) {
                                    System.out.println("soy alerta limite");
                                    mensajeP = ServiceLocator.getInstanceMensajeDAO().find(4);
                                    numeroAlerta = 4;
                                }
                                if (fechasAleAtraso.get(i).equals(date)) {
                                    System.out.println("soy alerta atraso");
                                    mensajeP = ServiceLocator.getInstanceMensajeDAO().find(3);
                                    numeroAlerta = 3;
                                }
                            }
                            if (numeroAlerta != 0) {
                                
                                
                                String mensajeSTR = MENSAJE_HTML.replace("Mensaje", mensajeP.getMENmensaje());
                                switch (numeroAlerta) {
                                    case 2:
                                        mensajeSTR = mensajeSTR.replace("TituloAlerta", "Alerta de Corte");
                                        mensajeSTR = mensajeSTR.replace("fechareporte", "" + sdfDDMMMMYYYYEs.format(calReporte.getCREfechaCorte()) + "");
                                        tipoDeMensaje = 11;
                                        break;
                                    case 3:
                                        mensajeSTR = mensajeSTR.replace("TituloAlerta", "Alerta de Atraso");
                                        tipoDeMensaje = 13;
                                        break;
                                    case 4:
                                        mensajeSTR = mensajeSTR.replace("TituloAlerta", "Alerta de Limite");
                                        mensajeSTR = mensajeSTR.replace("fechareporte", "" + sdfDDMMMMYYYYEs.format(calReporte.getCREfechaLimite()) + "");
                                        tipoDeMensaje = 12;
                                        break;
                                }
                                switch (numeroReporte) {
                                    case 1:
                                        mensajeSTR = mensajeSTR.replace("numeroreporte", "primer");
                                        break;
                                    case 2:
                                        mensajeSTR = mensajeSTR.replace("numeroreporte", "segundo");
                                        break;
                                    case 3:
                                        mensajeSTR = mensajeSTR.replace("numeroreporte", "tercer");
                                        break;
                                }
                                return mensajeSTR;
                            } else {
                                System.out.println("Si entra pero no corresponde a alguna fecha de alerta");
                                return null;
                            }
                        } else {
                            System.out.println("La fecha no entra en ningun calendario");
                            return null;
                        }
                    } else {
                        
                        System.out.println("No Tiene Suficientes Calendarios");
                        return null;
                    }
                } else {
                    System.out.println("No Hay Configuracion");
                    return null;
                }
            } else {
                System.out.println("No Hay Ciclo Escolar");
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Algo Paso :v " + ex.getLocalizedMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public void enviarCorreo() throws ParseException, InterruptedException {
        String mensaj = calendarios();
        String mensajeHtml = mensaj;
        if (mensaj != null) {
            final String username = "siract.fim@uabc.edu.mx"; //emisor correo
            final String password = "3aMKAPJt&g"; //contrasena del correo emisor
            //---Envios de prueba
            String datosPrueba[] = new String[1];
            datosPrueba[0] = "angelicaastorga";

            //---------------- obetener los profesores de lsc y tronco ciclo actual-
            System.out.println("tipo mnensahje  "+tipoDeMensaje);
            List<Profesor> profesoresTCyLSC = new ArrayList<Profesor>();
            /**
             *Se definen a que maestros se enviaran los mensajes de PRE CORTE
             */
            if (tipoDeMensaje == 11 || tipoDeMensaje == 21 || tipoDeMensaje == 31) {//corte
                profesoresTCyLSC = criterios.findProfesorPertenecePEQuery();
            }
            /**
             * Se definen a que maestros se enviaran los mensajes de ATRASO
             */
            else if (tipoDeMensaje == 12 || tipoDeMensaje == 22 || tipoDeMensaje == 32) {
                
                
                profesoresTCyLSC = criterios.findProfesorRACTSiQuery(numeroReporte, sdf.format(calendarioReporte.getCREfechaCorte()) , sdf.format(calendarioReporte.getCREfechaLimite()), cicloescolar.getCESid());
                for(Profesor p : profesoresTCyLSC){
                    System.out.println(p.getPROnombre()+" "+p.getPROapellidoPaterno());
                }
            } 
            /**
             * Se definen a que maestros se enviarán los mensajes de Pre Limite
             */
            else if (tipoDeMensaje == 13 || tipoDeMensaje == 23 || tipoDeMensaje == 33) {
               // message.setSubject("SIRACT - Alerta De Atraso #" + numeroReporte);
               profesoresTCyLSC = criterios.findProfesorRACTSiQuery(numeroReporte, sdf.format(calendarioReporte.getCREfechaCorte()) , sdf.format(calendarioReporte.getCREfechaLimite()), cicloescolar.getCESid());
            }
            /*
            Se definen a que maestros se enviarán los mensajes de ALERTA GENERAL
            */
            else{
                profesoresTCyLSC = criterios.findProfesorPertenecePEQuery();
            }
            System.out.println(profesoresTCyLSC.size() + "EL TAMAÑO PAPA");
            //----------------
            Session session = null;
            try {
                Properties props = System.getProperties(); //Se utilizan para preparar el medio para mandar los correos
                props.put("mail.smtp.host", "smtp.gmail.com"); //Host             
                props.put("mail.smtp.port", "587"); //Puerto
                props.put("mail.smtp.starttls.enable", true);
                props.put("mail.smtp.auth", true);
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

                session = Session.getInstance(props, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com", username, password);
                int cotnador = 0;
                for (Profesor p : profesoresTCyLSC) {
                    if (!p.getUsuarioUSUid().getUSUusuario().contains("SC")){
                        cotnador++;
              
                        MimeBodyPart messageBodyPart = new MimeBodyPart(); //mime y multi permiten agregar contenido html a los correos
                        Multipart multipart = new MimeMultipart();
                        Message message = new MimeMessage(session); //Se crea el mensaje
                        message.setFrom(new InternetAddress(username)); //Se establece el emisor
                        message.setRecipients(Message.RecipientType.TO,
                                
                   //     InternetAddress.parse(p.getUsuarioUSUid().getUSUusuario()+"@uabc.edu.mx")); //aqui va el receptor//
                        
                      InternetAddress.parse("chin.brian@uabc.edu.mx")); //aqui va el receptor, prueba con mi correo
                        
//                        InternetAddress.parse("angelicaastorga@uabc.edu.mx")); //aqui va el receptor, prueba con mi correo
                        if (tipoDeMensaje == 11 || tipoDeMensaje == 21 || tipoDeMensaje == 31) {
                            message.setSubject("SIRACT - Alerta De Corte #" + numeroReporte);
                        } else if (tipoDeMensaje == 12 || tipoDeMensaje == 22 || tipoDeMensaje == 32) {
                            message.setSubject("SIRACT - Alerta De Limite #" + numeroReporte);
                        } else if (tipoDeMensaje == 13 || tipoDeMensaje == 23 || tipoDeMensaje == 33) {
                            message.setSubject("SIRACT - Alerta De Atraso #" + numeroReporte);
                        } else {
                            message.setSubject("SIRACT - Alerta General");
                        }
                        String nombreProfesorCompleto = "Pruebas ";
                        Configuracion config = ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarTodasConfiguraciones().get(ServiceFacadeLocator.getInstanceConfiguracionFacade().BuscarTodasConfiguraciones().size()-1);
                        //Configuracion config = criterios.conCriteria(cicloescolar.getCESid()); 
                        List<Calendarioreporte> calend = new ArrayList<>();
                        //calend = criterios.findAllCre(config.getCONid());
                        calend = config.getCalendarioreporteList();
                        
                        
                        mensaj = mensaj.replace("nombreProfesor", p.getPROnombre() + " " + p.getPROapellidoPaterno() + " " + p.getPROapellidoMaterno());
                        mensaj = mensaj.replace("fechadeinicio",sdfDDMMMMYYYYEs.format(calend.get(numeroReporte-1).getCREfechaCorte()));
                        messageBodyPart.setText(mensaj, "UTF-8", "html"); //se agrega el texto al correo
                        multipart.addBodyPart(messageBodyPart);
                        message.setContent(multipart); //se le agrega el texto al mensaje

                        transport.sendMessage(message, message.getAllRecipients()); //se manda el mensaje
                        System.out.println("Done -- " + p.getUsuarioUSUid().getUSUusuario()); //mensaje de prueba
                        mensaj = mensajeHtml;
                    
                        if (cotnador % 10 == 0) {
                            System.out.println("diez mensajes -- reseteando Transport -- " + cotnador);
                            transport = session.getTransport("smtp");
                            transport.connect("smtp.gmail.com", username, password);
                            Thread.sleep(30000);
                        }
                    }
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ninguna alerta para esta fecha");
        }
    }
}
