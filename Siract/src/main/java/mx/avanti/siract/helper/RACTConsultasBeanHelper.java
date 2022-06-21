package mx.avanti.siract.helper;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicascampo;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Reporte;
import mx.avanti.siract.entity.ReportePK;
import mx.avanti.siract.entity.Reporteavancecontenidotematico;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.entity.Usuario;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Mosh
 */
public class RACTConsultasBeanHelper extends RACTBeanHelper implements Serializable {

    //Aqui se accede a los delegate
//    UnidadDelegate unidadDelegate;
//    AreaAdministrativaDelegate areaAdministrativaDelegate;
//    PracticasLaboratorioDelegate practicasLabDelegate;
//    PracticasTallerDelegate practicasTallDelegate;
//    ProfesorDelegate profesorDelegate;
//    UnidadAprendizajeDelegate unidadAprendisajeDelegate;
//    GrupoDelegate grupoDelegate;
    List<Unidadaprendizaje> listaUnidadesAprendisaje;
    List<Unidad> unidades;
    List<Practicalaboratorio> practicasLab;
    List<Practicataller> practicasTall;
    List<Practicascampo> practicasCampo;
    List<Profesor> profesores;
    List<Grupo> grupos;
    List<Programaeducativo> programaesEducativos;
    TreeNode root;
//    private AreaConocimientoDelegate areaConocimientoDelegate;
    private Areaconocimiento areaConocimiento;
    private List<Areaconocimiento> areasConocimiento;
    Profesor profesor;
//    ReporteavancecontenidotematicoDelegate reporteavancecontenidotematicodelegate;
    Reporteavancecontenidotematico auxReporteAvancecontenidotematico;
    Reporte auxReporte;
    String numeroReporte;
    boolean validarTiempo = true;
//    CalendarioreporteDelegate calendarioreporteDelegate;
//    CalendarioreporteTieneAlertaDelegate calendarioreporteTieneAlertaDelegate;
    List<TreeNode> listaSeleccionados;
//    PracticasCampoDelegate practicasCampoDelegate;
    List<NodoMultiClass> listaPDF = new ArrayList();
    List<Profesor> profesorUA;
    List<Areaadministrativa> areasAdministrativas;
    int idProfesorporUASelect;
    NodoMultiClass nodoM = new NodoMultiClass(); //auxiliar que se utiliza para marcar
//    CicloEscolarDelegate cicloescolarDelegate;
    private List<Cicloescolar> ciclosEscolares;
    String reporteSeleccionado = "";
    float porcentajeAvance = 0;
    Date fechaDeReporte = new Date();
    String rolSeleccionado;

    public List<NodoMultiClass> getListaPDF() {
        return listaPDF;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Profesor getProfesorById(int proid) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(proid);
    }

    public List<Programaeducativo> getProgramaesEducativos(String rol) {
        if (profesor != null && profesor.getPROid() != null) {
            programaesEducativos = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getListaUnidadAprendizajeFFWD(null, null, profesor.getPROid().toString());
        }
        return programaesEducativos;
    }

    public List<Programaeducativo> getProgramasEducativos(String cicloEscolar, String rol) {
        programaesEducativos = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getListaProgramaEducativoPorRol(profesor.getPROid().toString(), rol, cicloEscolar, true);
        return programaesEducativos;
    }

    public Profesor getProfesorFromUsuario(Usuario usuario) {
        Profesor profesor = new Profesor();
        if (usuario != null && usuario.getUSUid() != null) {
            System.out.println("EL USUARIO DE ESTE PROFESOR ES" + usuario.getUSUid());
            profesor = usuario.getProfesorList().get(0);
            if (profesor == null) {
                System.out.println("No hay profesor");
            }
        } else {
            return null;
        }
        return profesor;
    }

    public Profesor getProfesorUA(String uaip) {
        int uaipId = Integer.parseInt(uaip);
        UnidadaprendizajeImparteProfesor uip = ServiceFacadeLocator
                .getInstanceUnidadAprendizajeImparteProfesorFacade()
                .findUnidadAprendizajeImparteProfesorByID(uaipId);

        return uip.getProfesorPROid();
    }
    //Delegate programaEducativo
//    ProgramaEducativoDelegate programaEducativoDelegate;
//
//    public String obtenerReporteSiguiente() {
//        String numeroReporte = "0";
//        C//alendarioreporte calendario = calendarioreporteDelegate.siguienteReporte();
//
//        if (calendario == null) {
//            System.out.println("RACTBeanHelper.java:obtenerReporteSiguiente() // NO HAY UN NUMERO DE REPORTE SIGUIENTE");
//        } else {
//            //CalendarioreporteTieneAlerta calendarioReporteAlerta = calendarioreporteTieneAlertaDelegate.getCalendariosFechaActual(calendario.getCreid().toString()).get(0);
//            numeroReporte = calendarioReporteAlerta.getCalnumeroReporte().toString();
//            System.out.println("RACTBeanHelper.java:obtenerReporteSiguiente() // EL NUMERO DE REPORTE ES: " + numeroReporte);
//        }
//        return numeroReporte;
//    }

    public RACTConsultasBeanHelper() {
        init();

    }

    private void init() {
        root = new DefaultTreeNode("raiz", null);

        listaSeleccionados = new ArrayList<TreeNode>();

    }

    public Cicloescolar cicloescolarActual() {

        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
    }

    public List<Programaeducativo> getProgramaesEducativosPorRol(String rol, String ce) {
        if (profesor != null && profesor.getPROid() != null) {

            programaesEducativos = ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getListaProgramaEducativoPorRol(profesor.getPROid().toString(), rol, ce, true);
        }
        return programaesEducativos;
    }

    public List<Unidadaprendizaje> getUnidadesaprendisajePorProgramaEducativo(String programaEducativoSeleccionado, String rol, String profId, String ce) {
        List<Unidadaprendizaje> nuevaLista = null;
        if (profesor != null && profesor.getPROid() != null) {
            nuevaLista = new ArrayList();
            List<Grupo> gruposdeUnidad;
            List<UnidadaprendizajeImparteProfesor> uaips;
            Unidadaprendizaje auxiliar;

            listaUnidadesAprendisaje = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().consultaUnidadesAprendizajePorProgramaEducativo(programaEducativoSeleccionado, ce, rol, profId);
            System.out.println(ce);
            boolean horasCompletas = false;
            for (Unidadaprendizaje unidadaprendizaje : listaUnidadesAprendisaje) {

                gruposdeUnidad = new ArrayList(ServiceFacadeLocator.getInstancGrupoFacade().getGruposPorUA(unidadaprendizaje.getUAPclave(), programaEducativoSeleccionado, ce));

                //ADAPTCION ESPECIAL DEL QUERY
                uaips = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().consultaUIPParaUA(programaEducativoSeleccionado, unidadaprendizaje.getUAPid().toString(), cicloescolarActual().getCEScicloEscolar());
                System.out.print("CIclo qiue envia");

                for (Iterator<UnidadaprendizajeImparteProfesor> it2 = uaips.iterator(); it2.hasNext();) {
                    UnidadaprendizajeImparteProfesor uaip = it2.next();
                    Grupo grupo = null;
                    do {
                        boolean ban = false;
                        Cicloescolar ceAux = cicloescolarActual();

                        grupo = ServiceFacadeLocator.getInstancGrupoFacade().buscarGrupo(uaip.getGrupoGPOid().getGPOid());
                        for (int i = 0; i < gruposdeUnidad.size(); i++) {
                            if (grupo.getGPOid().equals(gruposdeUnidad.get(i).getGPOid())) {
                                ban = true;
                            }
                        }
                        if (grupo != null) {
                            //LOS VALORES QUE SE MUESTRAN EN LA SELECCION
                            String nombre = unidadaprendizaje.getUAPnombre() + " -- " + grupo.getGPOnumero() + " -- " + uaip.getUIPsubgrupo() + " -- " + uaip.getUIPtipoSubgrupo();

                            switch (uaip.getUIPtipoSubgrupo()) {
                                case "C":
                                    horasCompletas = unidadaprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas();
                                    break;
                                case "L":
                                    horasCompletas = unidadaprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasLaboratorioCompletas();
                                    break;
                                case "T":
                                    horasCompletas = unidadaprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasTallerCompletas();
                                    break;
                                case "PC":
                                    horasCompletas = unidadaprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasCampoCompletas();
                                    break;
                            }

                            //CREAR COPIA DE LA UNIDADDEAPRENDISAJE
                            auxiliar = new Unidadaprendizaje(unidadaprendizaje.getUAPid(),
                                    unidadaprendizaje.getUAPclave(), unidadaprendizaje.getUAPnombre(),
                                    unidadaprendizaje.getUAPetapaFormacion(), unidadaprendizaje.getUAPcreditos(),
                                    unidadaprendizaje.getUAPtipoCaracter());

                            auxiliar.setUAPhorasCampo(unidadaprendizaje.getUAPhorasCampo());
                            auxiliar.setUAPhorasClase(unidadaprendizaje.getUAPhorasClase());
                            auxiliar.setUAPhorasLaboratorio(unidadaprendizaje.getUAPhorasLaboratorio());
                            auxiliar.setUAPhorasClinica(unidadaprendizaje.getUAPhorasClinica());
                            auxiliar.setUAPhorasTaller(unidadaprendizaje.getUAPhorasTaller());
                            auxiliar.setUAPhorasExtraClase(unidadaprendizaje.getUAPhorasExtraClase());

                            //SE ASIGNA EL ID DE UNIDAD APRENDIZAJE IMPARTE PROFESOR PARA PODER IDENTIFICAR CADA SET DE PROFESOR/UNIDADAPRENDIZAJE/GRUPO
                            auxiliar.setUAPid(uaip.getUIPid());

                            //revisar que el tipo que de la unidad de aprendizaje tiene las horas copletas
                            if (ban == true) {
                                nuevaLista.add(auxiliar);
                            }
                        }
                        grupo = null;
                    } while (grupo == null);
                }

                //FIN DE CREAR COPIA DE LA UNIDADDEAPRENDISAJE
            }
        }
        for (int x = 0; x < nuevaLista.size(); x++) {
            for (int y = 0; y < nuevaLista.size(); y++) {
                if (nuevaLista.get(x).getUAPnombre().equals(nuevaLista.get(y).getUAPnombre())) {
                    nuevaLista.remove(y);
                }
            }

        }
        return nuevaLista;
    }
    
    
    
    public List<UnidadaprendizajeImparteProfesor> getUNIdadesaprendisajeConGrupos2(String pe, String ce, int proid) {
        return getUNIdadesaprendisajeConGrupos2(pe, ce, proid, 0);
    }
     
      public List<UnidadaprendizajeImparteProfesor> getUNIdadesaprendisajeConGrupos2(String pe, String ce, int proid, int coordinadorProId) {
        List<UnidadaprendizajeImparteProfesor> nuevaLista = null;
        nuevaLista = new ArrayList();
        List<Grupo> gruposdeUnidad;
        List<UnidadaprendizajeImparteProfesor> uaips;
        Unidadaprendizaje auxiliar;
        int peid = Integer.parseInt(pe);
        nuevaLista = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().buscarPorProfesorYProgramaEducativo2(proid, peid, ce, coordinadorProId);
        
        for (UnidadaprendizajeImparteProfesor a : nuevaLista){
            System.out.println(a.getUnidadAprendizajeUAPid());
        }
        return nuevaLista;
       /* for (Object[] row : rawResult) {
            boolean horasCompletas = false;
            Unidadaprendizaje ua = new Unidadaprendizaje();
            ua.setUAPid((int) row[23]);
            ua.setUAPclave((int) row[1]);
            String nombreUAP = (String) row[2];
            ua.setUAPetapaFormacion((String) row[3]);
            ua.setUAPcreditos((int) row[4]);
            ua.setUAPhorasClase((int) row[5]);
            ua.setUAPhorasLaboratorio((int) row[6]);
            ua.setUAPhorasTaller((int) row[7]);
            ua.setUAPhorasClinica((int) row[8]);
            ua.setUAPhorasCampo((int) row[9]);
            ua.setUAPhorasExtraClase((int) row[10]);
            ua.setUAPtipoCaracter((String) row[11]);
            ua.setCicloEscolarCESid(ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar((int) row[12]));
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasClaseCompletas((boolean) row[14]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasLaboratorioCompletas((boolean) row[15]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasTallerCompletas((boolean) row[16]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasCampoCompletas((boolean) row[17]);
            int grupoNumero = (int) row[19];
            String tipoSubgrupo = (String) row[21];
            String uipSubgrupo = (String) row[22];
            ua.setUAPnombre(nombreUAP + " -- " + grupoNumero + " -- " + uipSubgrupo + " -- " + tipoSubgrupo);

            switch (tipoSubgrupo) {
                case "C":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas();
                    break;
                case "L":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasLaboratorioCompletas();
                    break;
                case "T":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasTallerCompletas();
                    break;
                case "PC":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasCampoCompletas();
                    break;
            }
            if (horasCompletas) {
                nuevaLista.add(ua);
            }
        }*/

        //return nuevaLista;
    }

    public List<Unidadaprendizaje> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid) {
        return getUNIdadesaprendisajeConGrupos(pe, ce, proid, 0);
    }

    /**
     * Recibe el programa educativo y ciclo escolar seleccionado para filtrar la
     * lista de unidades de aprendizaje
     *
     * @param pe
     * @param ce
     * @param proid
     * @return
     */
    public List<Unidadaprendizaje> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid, int coordinadorProId) {
        List<Unidadaprendizaje> nuevaLista = null;
        List<Object[]> rawResult;

        nuevaLista = new ArrayList();
        List<Grupo> gruposdeUnidad;
        List<UnidadaprendizajeImparteProfesor> uaips;
        Unidadaprendizaje auxiliar;
        System.out.println("TOMANDO LISTA DE UNIDADES");
        System.out.println("unidadaprendizajeImparteProfesors b join a.areaconocimientos c" + "a.profesor " + "proid " + proid + "c.planestudio.programaeducativo" + "pedid" + pe + "uapnombre" + ce);
        int peid = Integer.parseInt(pe);
        rawResult = ServiceFacadeLocator
                .getInstanceUnidadAprendizajeFacade()
                .buscarPorProfesorYProgramaEducativo(proid, peid, ce, coordinadorProId);

        for (Object[] row : rawResult) {
            boolean horasCompletas = false;
            Unidadaprendizaje ua = new Unidadaprendizaje();
            ua.setUAPid((int) row[23]);
            ua.setUAPclave((int) row[1]);
            String nombreUAP = (String) row[2];
            ua.setUAPetapaFormacion((String) row[3]);
            ua.setUAPcreditos((int) row[4]);
            ua.setUAPhorasClase((int) row[5]);
            ua.setUAPhorasLaboratorio((int) row[6]);
            ua.setUAPhorasTaller((int) row[7]);
            ua.setUAPhorasClinica((int) row[8]);
            ua.setUAPhorasCampo((int) row[9]);
            ua.setUAPhorasExtraClase((int) row[10]);
            ua.setUAPtipoCaracter((String) row[11]);
            ua.setCicloEscolarCESid(ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar((int) row[12]));
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasClaseCompletas((boolean) row[14]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasLaboratorioCompletas((boolean) row[15]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasTallerCompletas((boolean) row[16]);
            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasCampoCompletas((boolean) row[17]);
            int grupoNumero = (int) row[19];
            String tipoSubgrupo = (String) row[21];
            String uipSubgrupo = (String) row[22];
            ua.setUAPnombre(nombreUAP + " -- " + grupoNumero + " -- " + uipSubgrupo + " -- " + tipoSubgrupo);

            switch (tipoSubgrupo) {
                case "C":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas();
                    break;
                case "L":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasLaboratorioCompletas();
                    break;
                case "T":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasTallerCompletas();
                    break;
                case "PC":
                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasCampoCompletas();
                    break;
            }
            if (horasCompletas) {
                nuevaLista.add(ua);
            }
        }

        return nuevaLista;
    }

    public List<Cicloescolar> getAllCiclos() {
        ciclosEscolares = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares();
        return ciclosEscolares;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public List<TreeNode> getListaSeleccionados() {
        return listaSeleccionados;
    }

    public float getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public Date getFechaDeReporte() {
        return fechaDeReporte;
    }

    public boolean validarReporteEnviado(int id_profesor, int id_grupo, String subGrupo, int id_unidadAprendizajeImparteProfesor, String tipo, String ce, int idUnidadSeleccionada) {
        Reporteavancecontenidotematico auxReporteavanceContenidotematico2 = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().buscarPorNumeroYUAIP(id_unidadAprendizajeImparteProfesor, reporteSeleccionado, id_profesor, id_grupo, tipo, ce, idUnidadSeleccionada).get(0);
        if (auxReporteavanceContenidotematico2 == null || !reporteSeleccionado.equals(auxReporteavanceContenidotematico2.getRACnumero()) || !auxReporteavanceContenidotematico2.getRACstatus().equals("Enviado")) {
            return false;

        } else {
            System.out.println("Conflicto al comprobar datos previos al enviar");
            return true;
        }
    }

    public void setIdProfesorporUASelect(int idProfesorporUASelect) {
        this.idProfesorporUASelect = idProfesorporUASelect;
    }

    public void guardarComentario(TreeNode selectedNodes, float porcentajeAvance, int id_profesor, int id_grupo, String subGrupo, String tipo_grupo, int id_unidadAprendizajeImparteProfesor, String ce, int idUnidadSeleccionada) {
        UnidadaprendizajeImparteProfesor auxUnidadAprendizajeImparteProfesor;
        auxReporteAvancecontenidotematico = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().buscarPorNumeroYUAIP(id_unidadAprendizajeImparteProfesor, reporteSeleccionado, id_profesor, id_grupo, tipo_grupo, ce, idUnidadSeleccionada).get(0);

        if (auxReporteAvancecontenidotematico == null) {
            auxUnidadAprendizajeImparteProfesor = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(id_unidadAprendizajeImparteProfesor);
            auxReporteAvancecontenidotematico = new Reporteavancecontenidotematico();
            auxReporteAvancecontenidotematico.setUnidadAprendizajeimparteprofesorUIPid(auxUnidadAprendizajeImparteProfesor);
            auxReporteAvancecontenidotematico.setRACstatus("Parcial");

            if (Integer.parseInt(auxReporteAvancecontenidotematico.getRACnumero()) <= 3) {
                auxReporteAvancecontenidotematico.setRACavanceGlobal(0);
                auxReporteAvancecontenidotematico.setRACfechaElaboracion(new Date());
            }

        }

        Reporteavancecontenidotematico auxReporteA = auxReporteAvancecontenidotematico;
        int racId = auxReporteAvancecontenidotematico.getRACid();
        List<Reporte> listaReportes = new ArrayList();

        if (selectedNodes != null) {
            auxReporte = new Reporte();
            auxReporte.setReportePK(new ReportePK());
            String[] todo = selectedNodes.getData().toString().split("-//-");

            if (auxReporte.getImpartido() == null) {
                System.out.println((((NodoMultiClass) selectedNodes.getData()).isImpartido()));
                auxReporte.setImpartido((((NodoMultiClass) selectedNodes.getData()).isImpartido()));
            }

            auxReporte.setREPobservacion(todo[6]);
            Reporte reporteAuxiliar = new Reporte();

            switch (todo[0]) {
                case "unidad":
                    reporteAuxiliar = ServiceFacadeLocator.getInstanceReporteFacade().buscarPorRACTyNodo(Integer.parseInt(todo[1]), "Unidad", auxReporteA.getRACid());
                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                    break;
                case "temaunidad":
                    reporteAuxiliar = ServiceFacadeLocator.getInstanceReporteFacade().buscarPorRACTyNodo(Integer.parseInt(todo[2]), "Tema", auxReporteA.getRACid());
                    auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                    break;
                case "subtemaunidad":
                    reporteAuxiliar = ServiceFacadeLocator.getInstanceReporteFacade().buscarPorRACTyNodo(Integer.parseInt(todo[3]), "Subtema", auxReporteA.getRACid());
                    auxReporte.getReportePK().setSUTid(Integer.parseInt(todo[3]));
                    auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                    break;
                case "practicalaboratorio":
                    reporteAuxiliar = ServiceFacadeLocator.getInstanceReporteFacade().buscarPorRACTyNodo(Integer.parseInt(todo[1]), "practicaLaboratorio", auxReporteA.getRACid());
                    auxReporte.getReportePK().setPRLid(Integer.parseInt(todo[1]));
                    break;
                case "practicataller":
                    reporteAuxiliar = ServiceFacadeLocator.getInstanceReporteFacade().buscarPorRACTyNodo(Integer.parseInt(todo[1]), "practicaTaller", auxReporteA.getRACid());
                    auxReporte.getReportePK().setPRTid(Integer.parseInt(todo[1]));
                    break;
            }

            if (reporteAuxiliar == null) {
                auxReporte.setImpartido(false);
            } else {
                auxReporte.setImpartido(reporteAuxiliar.getImpartido());
            }

            auxReporte.getReportePK().setREPid(racId);
            auxReporte.setReporteAvanceContenidoTematicoRACid(auxReporteA);
            listaReportes.add(auxReporte);
        }

        auxReporteA.setReporteList(listaReportes);
    }

    @Override
    public TreeNode TraerNodosTree(UnidadaprendizajeImparteProfesor unidadImpartida, Unidadaprendizaje unidadAprendizaje, String numReporte, String cicloEscolarSeleccionado) {
        List<Reporte> reportes;
        Reporteavancecontenidotematico ract = null;
        Reporteavancecontenidotematico ractseleccionado;

        try {
            //Obtiene el ract seleccionado
            for (int i = 0; i <= unidadImpartida.getReporteavancecontenidotematicoList().size() - 1; i++) {
                ractseleccionado = unidadImpartida.getReporteavancecontenidotematicoList().get(i);
                
                if (ractseleccionado.getRACnumero().equalsIgnoreCase(numReporte)) {
                    ract = ractseleccionado;
                }
            }

            this.fechaDeReporte = ract.getRACfechaElaboracion();
            this.porcentajeAvance = ract.getRACavanceGlobal();

            String tipoSubgrupo = unidadImpartida.getUIPtipoSubgrupo();
            reportes = ract.getReporteList();
            Reporte reporte = reportes.get(0);
            int version;

            switch (tipoSubgrupo) {
                case "L":
                    version = ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().busquedaPracticaLaboratorio(reporte.getReportePK().getPRLid()).getContenidoTematicoCTid().getVersion();
                    break;
                case "T":
                    version = ServiceFacadeLocator.getInstancePracticasTallerFacade().busquedaPracticaTaller(reporte.getReportePK().getPRTid()).getContenidoTematicoCTid().getVersion();
                    break;
                default:
                    version = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(reporte.getReportePK().getUNIid()).getContenidoTematicoCTid().getVersion();
            }
            
            this.root = super.TraerNodosTree(unidadImpartida, unidadAprendizaje, numReporte, String.valueOf(version));
        } catch (Exception e) {
            reportes = new ArrayList<>();
        }

        for (TreeNode child1 : this.root.getChildren()) {            
            NodoMultiClass nodo = (NodoMultiClass) child1.getData();
            int id;
            boolean impartido = false;            
            
            switch (unidadImpartida.getUIPtipoSubgrupo()) {
                case "L":
                    id = nodo.getPracticaL().getPRLid();
                    for (Reporte rep : reportes) {
                        boolean observacionAnterior = !rep.getREPobservacion().isEmpty()
                                && numReporte.equals(rep.getReporteAvanceContenidoTematicoRACid().getRACnumero());

                        if (rep.getImpartido() && rep.getReportePK().getPRLid() == id) {
                            impartido = true;
                            break;
                        }

                        //tiene observacion de pero de un ract anterior
                        if (rep.getReportePK().getPRLid() == id && observacionAnterior) {
                            ((NodoMultiClass) child1.getData()).setObservaciones(rep.getREPobservacion());
                        }

                    }
                    ((NodoMultiClass) child1.getData()).setImpartido(impartido);
                    child1.setSelected(impartido);
                    if (!impartido) {
                        ((NodoMultiClass) child1.getData()).setObservaciones("");
                    }
                    break;

                case "T":
                    id = nodo.getPracticaT().getPRTid();
                    for (Reporte rep : reportes) {
                        if (rep.getImpartido() && rep.getReportePK().getPRTid() == id) {
                            impartido = true;
                            break;
                        }

                    }
                    ((NodoMultiClass) child1.getData()).setImpartido(impartido);
                    child1.setSelected(impartido);
                    if (!impartido) {
                        ((NodoMultiClass) child1.getData()).setObservaciones("");
                    }

                    break;

                case "CL":
                    id = nodo.getPracticaC().getPRCid();
                    for (Reporte rep : reportes) {
                        if (rep.getImpartido() && rep.getReportePK().getPRCid() == id) {
                            impartido = true;
                            break;
                        }

                    }
                    ((NodoMultiClass) child1.getData()).setImpartido(impartido);
                    child1.setSelected(impartido);
                    if (!impartido) {
                        ((NodoMultiClass) child1.getData()).setObservaciones("");
                    }
                    break;

                case "C":
                    boolean impartidoSut = false;

                    int totTunImpartidos = 0;

                    //Unidades
                    int uniId = nodo.getUnidad().getUNIid();

                    //Temas
                    for (TreeNode child2 : child1.getChildren()) {
                        int totSutImpartidos = 0;
                        boolean impartidoTun = false;

                        NodoMultiClass nodo2 = (NodoMultiClass) child2.getData();
                        int tunId = ((NodoMultiClass) child2.getData()).getTemaUnidad().getTUNid();

                        for (Reporte rep : reportes) {
                            if (rep.getImpartido() && rep.getReportePK().getTUNid() == tunId) {
                                impartidoTun = true;
                                totTunImpartidos++;
                                break;
                            }
                        }

                        //Subtemas
                        for (TreeNode child3 : child2.getChildren()) {
                            impartidoSut = false;
                            NodoMultiClass nodo3 = (NodoMultiClass) child3.getData();
                            int sutId = ((NodoMultiClass) child3.getData()).getSubTema().getSUTid();
                            for (Reporte rep : reportes) {
                                if (rep.getImpartido() && rep.getReportePK().getSUTid() == sutId) {
                                    impartidoSut = true;
                                    totSutImpartidos++;
                                    break;
                                } else if (!rep.getImpartido() && rep.getReportePK().getSUTid() == sutId) {
                                    impartidoSut = false;
                                }

                            }
                            ((NodoMultiClass) child3.getData()).setImpartido(impartidoSut);
                            ((NodoMultiClass) child3.getData()).setDeReporteAnterior(false);
                            child3.setSelected(impartidoSut);
                            if (!impartidoSut) {
                                ((NodoMultiClass) child3.getData()).setObservaciones("");
                            }
                            impartidoSut = false;
                        }

                        int count = child2.getChildCount();
                        if (count > 0) {
                            impartidoTun = child2.getChildCount() == totSutImpartidos;
                            ((NodoMultiClass) child2.getData()).setImpartido(impartidoTun);
                            child2.setSelected(impartidoTun);
                            if (!impartidoTun) {
                                ((NodoMultiClass) child2.getData()).setObservaciones("");
                            }
                            ((NodoMultiClass) child2.getData()).setParcialSelected(totSutImpartidos > 0 && totSutImpartidos < child2.getChildCount());
                        } else {
                            ((NodoMultiClass) child2.getData()).setImpartido(impartidoTun);
                            child2.setSelected(impartidoTun);
                            if (!impartidoTun) {
                                ((NodoMultiClass) child2.getData()).setObservaciones("");
                            }
                            ((NodoMultiClass) child2.getData()).setParcialSelected(false);
                        }

                        if (!impartidoTun && totTunImpartidos > 1) {
                            totTunImpartidos--;
                        }
                        ((NodoMultiClass) child2.getData()).setDeReporteAnterior(false);

                    }

                    int count = child1.getChildCount();
                    ((NodoMultiClass) child1.getData()).setImpartido(count == totTunImpartidos);
                    child1.setSelected(count == totTunImpartidos);
                    if (count != totTunImpartidos) {
                        ((NodoMultiClass) child1.getData()).setObservaciones("");
                    }
                    ((NodoMultiClass) child1.getData()).setParcialSelected(totTunImpartidos > 0 && totTunImpartidos < count);
            }

            ((NodoMultiClass) child1.getData()).setDeReporteAnterior(false);
        }
        return this.root;
    }

    public void llenarTablaDeReportes(PdfPTable pdfTable, String tipoGrupo, String ce, String numeroReporte, String uip_id, int idUaip) {
        int fontSize = 12;
        Color impartido = new Color(0, 0, 0);
        Color noImpartido = new Color(154, 151, 151);
        String fontBase = FontFactory.TIMES_BOLD;
        String fontTema = FontFactory.TIMES;
        String fontSubtema = FontFactory.TIMES_ITALIC;
        String fontSeleccionado = null;

        Color colorSeleccionado;
        int numUIP = 0;
        try {
            numUIP = Integer.parseInt(uip_id);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        UnidadaprendizajeImparteProfesor uaip;
        uaip = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(idUaip);
        List<Reporte> reportes = ServiceFacadeLocator
                .getInstanceReporteFacade()
                .consultarReportesActualesYAnteriores(uaip.getUIPtipoSubgrupo(), ce, numeroReporte, uaip.getUIPid());
        System.out.println("Reportes actuales y anteriores");
        System.out.println("1: " + uaip.getUIPtipoSubgrupo());
        System.out.println("2: " + ce);
        System.out.println("3: " + numeroReporte);
        System.out.println("4: " + uaip.getUIPid());
        System.out.println("contenido: ");

        int version = 1;
        for (Reporte rep : reportes) {
            System.out.println(rep.getReportePK());
            if (rep.getReportePK().getUNIid() != 0) {
                int versionAux = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(rep.getReportePK().getUNIid()).getContenidoTematicoCTid().getVersion();
                if (versionAux > version) {
                    version = versionAux;
                }
            }
        }

//        List<Reporte> reportes = new ArrayList();
//        for (Reporteavancecontenidotematico ract : uaip.getReporteavancecontenidotematicoList()) {
//            if (Integer.parseInt(ract.getRACnumero()) <= Integer.parseInt(numeroReporte)) {
//
//                for (Reporte reporte : ract.getReporteList()) {
//                    reportes.add(reporte);
//                }
//            }
//        }
//
//        /*Ordenare los TemaUnidades por numero para que se muestren correctamente
//            en el Tree*/
//            Collections.sort(temasUnidad, new Comparator<Temaunidad>() {
//
//                public int compare(Temaunidad tu1, Temaunidad tu2) {
//                    // compare two instance of `Score` and return `int` as result.
//                    return tu1.getTUNnumero().compareTo(tu2.getTUNnumero());
//                }
//            });
        int id = 0;
        List<Integer> unidadesImpartidas = new ArrayList<>();
        List<Integer> temasImpartidos = new ArrayList<>();
        System.out.println("Te encontré esponja");
        List<Unidad> unidadesVA = uaip.getUnidadAprendizajeUAPid().getUnidadaprendizajeTieneContenidotematicoList().get(version - 1).getUnidadList();

        Unidad unidadAux = null;
        Temaunidad temaAux = null;

        List<String> numerosTemas = new ArrayList<>();
        List<String> numerosSubtemas = new ArrayList<>();

        for (int x = 0; x < reportes.size(); x++) {
            Reporte rep = reportes.get(x);
            System.out.print(rep.getReporteAvanceContenidoTematicoRACid().getRACnumero() + " -- ");
            Reporteavancecontenidotematico ract = rep.getReporteAvanceContenidoTematicoRACid();
            boolean fueImpartido = rep.getImpartido();
            colorSeleccionado = (fueImpartido) ? impartido : noImpartido;
            Font font = null;
            boolean mostrar = false;
            switch (uaip.getUIPtipoSubgrupo()) {
                case "C": //Clase
                    int idSubtema = rep.getReportePK().getSUTid();
                    int idTema = rep.getReportePK().getTUNid();
                    int idUnidad = rep.getReportePK().getUNIid();

                    if (idUnidad != 0 && idTema == 0) {

                        Unidad uni = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(idUnidad);
                        unidadAux = uni;

                        if (x - 1 >= 0) {
                            int idUnidadAnterior = reportes.get(x - 1).getReportePK().getUNIid();
                            if (idUnidad != idUnidadAnterior && reportes.get(x - 1).getReportePK().getTUNid() == 0) {
                                Unidad unidadAnterior = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(idUnidadAnterior);
                                if (!Objects.equals(uni.getUNInumero(), unidadAnterior.getUNInumero())) {
                                    mostrar = true;
                                }
                            } else {
                                mostrar = true;
                            }
                        } else {
                            mostrar = true;
                        }

                        if (mostrar) {
                            numerosTemas.clear();
                            for (Temaunidad temaActual : uni.getTemaunidadList()) {
                                numerosTemas.add(temaActual.getTUNnumero());
                            }
                            fontSeleccionado = fontBase;
                            font = FontFactory.getFont(fontSeleccionado, fontSize, colorSeleccionado);
                            pdfTable.addCell(new Phrase(uni.getUNInumero() + ".- " + uni.getUNInombre(), font));
                            pdfTable.addCell(new Phrase(String.format("%.2f", uni.getUNIvalorPorcentaje()), font));
                            //unidadesImpartidas.add(idUnidad);
                        }
                        unidadesImpartidas.add(idUnidad);

                    } else if (idTema != 0 && idSubtema == 0) {
                        //ver si la unidad esta parcialmente impartida
                        if (unidadesImpartidas.indexOf(idUnidad) == -1) {
                            Unidad uni = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(idUnidad);
                            if (unidadAux != null && Objects.equals(uni.getUNInumero(), unidadAux.getUNInumero())) {
                                unidadAux = null;
                            } else {
                                if (x - 1 >= 0) {
                                    int idUnidadAnterior = reportes.get(x - 1).getReportePK().getUNIid();
                                    if (idUnidad != idUnidadAnterior && reportes.get(x - 1).getReportePK().getTUNid() == 0) {
                                        Unidad unidadAnterior = ServiceFacadeLocator.getInstanceUnidadFacade().BuscarUnidad(idUnidadAnterior);
                                        if (!Objects.equals(uni.getUNInumero(), unidadAnterior.getUNInumero())) {
                                            mostrar = true;
                                        }
                                    } else {
                                        mostrar = true;
                                    }
                                } else {
                                    mostrar = true;
                                }
                            }

                            if (mostrar) {
                                numerosTemas.clear();
                                for (Temaunidad temaActual : uni.getTemaunidadList()) {
                                    numerosTemas.add(temaActual.getTUNnumero());
                                }
                                fontSeleccionado = fontBase;
                                font = FontFactory.getFont(fontSeleccionado, fontSize, noImpartido);
                                pdfTable.addCell(new Phrase(uni.getUNInumero() + ".- " + uni.getUNInombre(), font));
                                pdfTable.addCell(new Phrase(String.format("%.2f", uni.getUNIvalorPorcentaje()), font));
                                pdfTable.addCell(new Phrase(""));
                                pdfTable.addCell(new Phrase(""));
                                //unidadesImpartidas.add(idUnidad);
                            }
                            unidadesImpartidas.add(idUnidad);
                        }
                        mostrar = false;

                        Temaunidad tema = ServiceFacadeLocator.getInstancTemaUnidadFacade().findTemauUnidad(idTema + "");
                        temaAux = tema;
                        if (x - 1 >= 0) {
                            int idTemaAnterior = reportes.get(x - 1).getReportePK().getTUNid();
                            if (idTemaAnterior != 0 && idTemaAnterior != reportes.get(x - 1).getReportePK().getSUTid() && idTemaAnterior != idTema) {
                                Temaunidad temaAnterior = ServiceFacadeLocator.getInstancTemaUnidadFacade().findTemauUnidad("" + idTemaAnterior);
                                if (!temaAnterior.getTUNnumero().equals(tema.getTUNnumero())) {
                                    if (numerosTemas.size() > 0) {
                                        if (numerosTemas.contains(tema.getTUNnumero())) {
                                            mostrar = true;
                                        }
                                    } else {
                                        mostrar = true;
                                    }
                                }
                            } else {
                                mostrar = true;
                            }
                        } else {
                            mostrar = true;
                        }

                        if (mostrar) {
                            numerosSubtemas.clear();
                            for (Subtemaunidad subtemaActual : tema.getSubtemaunidadList()) {
                                numerosSubtemas.add(subtemaActual.getSUTnumero());
                            }
                            fontSeleccionado = fontTema;
                            font = FontFactory.getFont(fontSeleccionado, fontSize, colorSeleccionado);
                            pdfTable.addCell(new Phrase("   " + tema.getTUNnumero() + ".- " + tema.getTUNnombre(), font));
                            pdfTable.addCell(new Phrase(String.format("%.2f", tema.getTUNvalorPorcentaje()), font));
                            //temasImpartidos.add(idTema);
                        }
                        temasImpartidos.add(idTema);

                    } else if (idSubtema != 0) {
                        //ver si el tema esta parcialmente impartido
                        if (temasImpartidos.indexOf(idTema) == -1) {

                            Temaunidad tema = ServiceFacadeLocator.getInstancTemaUnidadFacade().findTemauUnidad(idTema + "");
                            if (temaAux != null && tema.getTUNnumero().equals(temaAux.getTUNnumero())) {
                                temaAux = null;
                            } else {
                                if (x - 1 >= 0) {
                                    int idTemaAnterior = reportes.get(x - 1).getReportePK().getTUNid();
                                    if (idTemaAnterior != 0 && idTemaAnterior != reportes.get(x - 1).getReportePK().getSUTid() && idTemaAnterior != idTema) {
                                        Temaunidad temaAnterior = ServiceFacadeLocator.getInstancTemaUnidadFacade().findTemauUnidad("" + idTemaAnterior);
                                        if (!temaAnterior.getTUNnumero().equals(tema.getTUNnumero())) {
                                            if (numerosTemas.size() > 0) {
                                                if (numerosTemas.contains(tema.getTUNnumero())) {
                                                    mostrar = true;
                                                }
                                            } else {
                                                mostrar = true;
                                            }
                                        }
                                    } else {
                                        mostrar = true;
                                    }
                                } else {
                                    mostrar = true;
                                }
                            }
                            if (mostrar) {
                                numerosSubtemas.clear();
                                for (Subtemaunidad subtemaActual : tema.getSubtemaunidadList()) {
                                    numerosSubtemas.add(subtemaActual.getSUTnumero());
                                }
                                fontSeleccionado = fontTema;
                                font = FontFactory.getFont(fontSeleccionado, fontSize, noImpartido);
                                pdfTable.addCell(new Phrase("   " + tema.getTUNnumero() + ".- " + tema.getTUNnombre(), font));
                                pdfTable.addCell(new Phrase(String.format("%.2f", tema.getTUNvalorPorcentaje()), font));
                                pdfTable.addCell(new Phrase(""));
                                pdfTable.addCell(new Phrase(""));
                                //temasImpartidos.add(idTema);
                            }
                            temasImpartidos.add(idTema);
                        }
                        mostrar = false;
                        Subtemaunidad sub = ServiceFacadeLocator.getInstanceSubTemaUnidadFacade().find(idSubtema + "");
                        if (x - 1 >= 0) {
                            int idSubtemaAnterior = reportes.get(x - 1).getReportePK().getSUTid();
                            if (idSubtemaAnterior != 0 && idSubtemaAnterior != idSubtema) {
                                Subtemaunidad subtemaAnterior = ServiceFacadeLocator.getInstanceSubTemaUnidadFacade().find(idSubtemaAnterior + "");
                                if (!subtemaAnterior.getSUTnumero().equals(sub.getSUTnumero())) {
                                    if (numerosSubtemas.size() > 0) {
                                        if (numerosSubtemas.contains(sub.getSUTnumero())) {
                                            mostrar = true;
                                        }
                                    } else {
                                        mostrar = true;
                                    }
                                }
                            } else {
                                mostrar = true;
                            }
                        } else {
                            mostrar = true;
                        }
                        if (mostrar) {
                            fontSeleccionado = fontSubtema;
                            font = FontFactory.getFont(fontSeleccionado, fontSize, colorSeleccionado);
                            pdfTable.addCell(new Phrase("       " + sub.getSUTnumero() + ".- " + sub.getSUTnombre(), font));
                            pdfTable.addCell(new Phrase(String.format("%.2f", sub.getSUTvalorPorcentaje()), font));
                        }
                    }
                    break;
                case "T"://Taller
                    id = rep.getReportePK().getPRTid();

                    fontSeleccionado = fontBase;
                    font = FontFactory.getFont(fontSeleccionado, fontSize, colorSeleccionado);
                    Practicataller taller = ServiceFacadeLocator.getInstancePracticasTallerFacade().busquedaPracticaTaller(id);
                    if (x + 1 < reportes.size()) {
                        Reporte reporteAux = reportes.get(x + 1);
                        Practicataller tallerAux = ServiceFacadeLocator.getInstancePracticasTallerFacade().busquedaPracticaTaller(reporteAux.getReportePK().getPRTid());
                        if (taller.getPRTnumero() != tallerAux.getPRTnumero()) {
                            mostrar = true;
                        }
                    } else {
                        mostrar = true;
                    }
                    if (mostrar) {
                        pdfTable.addCell(new Phrase(taller.getPRTnumero() + ".- " + taller.getPRTnombre(), font));
                        pdfTable.addCell(new Phrase(String.format("%.2f", taller.getPRTvalorPorcentaje()), font));
                    }
                    break;
                case "L"://Laboratorio
                    fontSeleccionado = fontBase;
                    font = FontFactory.getFont(fontSeleccionado, fontSize, colorSeleccionado);
                    id = rep.getReportePK().getPRLid();
                    Practicalaboratorio lab = ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().busquedaPracticaLaboratorio(id);
                    if (x + 1 < reportes.size()) {
                        Reporte reporteAux = reportes.get(x + 1);
                        Practicalaboratorio labAux = ServiceFacadeLocator.getInstancePracticasLaboratorioFacade().busquedaPracticaLaboratorio(reporteAux.getReportePK().getPRLid());
                        if (lab.getPRLnumero() != labAux.getPRLnumero()) {
                            mostrar = true;
                        }
                    } else {
                        mostrar = true;
                    }
                    if (mostrar) {
                        pdfTable.addCell(new Phrase(lab.getPRLnumero() + ".- " + lab.getPRLnombre(), font));
                        pdfTable.addCell(new Phrase(String.format("%.2f", lab.getPRLvalorPorcentaje()), font));
                    }
            }
            if (mostrar) {
                pdfTable.addCell(new Phrase(rep.getREPobservacion(), FontFactory.getFont(fontTema, fontSize)));
                pdfTable.addCell(new Phrase(ract.getRACnumero(), FontFactory.getFont(fontTema, fontSize)));
            }
        }

    }

    public List<Profesor> getProfesoresPorCoordinadoryCE(Integer proid, String cicloEscolarSeleccionado) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorCoordinadoryCE(proid, cicloEscolarSeleccionado);
    }

    public List<Profesor> getProfesoresPorRPEYCE(Integer rpeProId, String ce) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorRPEyCE(rpeProId, ce);
    }

    public List<Profesor> getProfesoresByPEYCE(String peid, String ce) {
        int pe = Integer.parseInt(peid);
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPEyCE(pe, ce);
    }

    /**
     * Metodo para obtener los programas educativos del usuario(profesor)
     * logueado
     *
     * @param usuario usuario en la sesion actual del ract
     * @return lista de programas educativos que pertencen al profesor logueado
     */
    public List<Programaeducativo> programasEducativosProfesor(Profesor profesor, String cicloEscolar) {
        List<Programaeducativo> programaEducativos = new ArrayList();

        for (ProfesorPerteneceProgramaeducativo pp : profesor.getProfesorPerteneceProgramaeducativoList()) {
            if (pp.getCicloescolar().getCESid().toString().equalsIgnoreCase(cicloEscolar)) {
                programaEducativos.add(pp.getProgramaeducativo());
                System.out.println("programa en helper : " + pp.getProgramaeducativo().getPEDnombre());
            }
        }
        //programaEducativos.add(new Programaeducativo());

        return programaEducativos;
    }

    /**
     * Metodo que regresas las unidades de aprendizaje que pertenecen al
     * profesor recibido en el ciclo escolar actual
     *
     * @param profesor
     * @return lista unidadAprendizaje del ciclo escolar actual
     */
    public List<Unidadaprendizaje> UnidadesAprendizajePorProgramaEducativo(Profesor profesor, int idProgramaEducativo, String idCicloEscolar) {
        List<UnidadaprendizajeImparteProfesor> unidadImparte = new ArrayList();
        List<Unidadaprendizaje> nuevaLista = new ArrayList();
        //Cicloescolar ciclo= ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
        // revisa el ciclo escolar recibido
        for (UnidadaprendizajeImparteProfesor listaUnidades : profesor.getUnidadaprendizajeImparteProfesorList()) {
            if (listaUnidades.getCicloEscolarCESid().getCESid().toString().equalsIgnoreCase(idCicloEscolar)) {
                unidadImparte.add(listaUnidades);
            }
        }
        // verica el programa educativo que se selecciono
        for (UnidadaprendizajeImparteProfesor unidadesImparte : unidadImparte) {
            if (unidadesImparte.getGrupoGPOid().getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid() == idProgramaEducativo) {
                nuevaLista.add(unidadesImparte.getUnidadAprendizajeUAPid());
            }
        }
        return nuevaLista;
    }

    /**
     * Metodo que regresas las unidades de aprendizaje imparte profesor que
     * pertenecen al profesor recibido en el ciclo escolar actual
     *
     * @param profesor
     * @return lista unidadAprendizajeImparte profesor del ciclo escolar actual
     */
    public List<UnidadaprendizajeImparteProfesor> UnidadesAprendizajeImparteProfesorPorProgramaEducativo(Profesor profesor, int idProgramaEducativo, String idCiclo) {
        List<UnidadaprendizajeImparteProfesor> unidadImparte = new ArrayList();
        List<UnidadaprendizajeImparteProfesor> nuevaLista = new ArrayList();
        Cicloescolar ciclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
        // actualizo el profesor en caso que se haya registro un RACT
        profesor = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid());
        // revisa el cuclo escolar actual
        for (UnidadaprendizajeImparteProfesor listaUnidades : profesor.getUnidadaprendizajeImparteProfesorList()) {
            if (listaUnidades.getCicloEscolarCESid().getCESid().toString().equalsIgnoreCase(idCiclo)) {
                unidadImparte.add(listaUnidades);
            }
        }
        // verica el programa educativo que se selecciono
        for (UnidadaprendizajeImparteProfesor unidadesImparte : unidadImparte) {
            if (unidadesImparte.getGrupoGPOid().getPlanEstudioPESid().getProgramaEducativoPEDid().getPEDid() == idProgramaEducativo) {
                nuevaLista.add(unidadesImparte);
            }
        }
        return nuevaLista;
    }

    /**
     * MEtodo para traer las unidades de aprendizaje que pertecen a un area
     * administrativa que coordina el profesor recibido
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorcoordinadorAreaYCicloEscolar(int idProfCoordinador, int idCiclo) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipPorcoordinadorAreaYCicloEscolar(idProfCoordinador, idCiclo);
    }

    /**
     * Trae las uiap del area administrativa del profesor que es coordinados y
     * aparte que pertenescan al profesor ( idProfesor)
     *
     * @param idProfCoordinador
     * @param idCiclo
     * @param idProfesor
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipAreaProfesor(int idProfCoordinador, int idCiclo, int idProfesor) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipAreaProfesor(idProfCoordinador, idCiclo, idProfesor);
    }

    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorPEyCE(int idCiclo, int idPE) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipPorPEyCE(idCiclo, idPE);
    }

    /**
     * Metodo para traer las uaip de un programa educutivo de un ciclo escolar
     * que pertenezcan al profesor recibido
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorProfesorPEyCE(int idCiclo, int idPE, int idProfesor) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipPorProfesorPEyCE(idCiclo, idPE, idProfesor);
    }

    public UnidadaprendizajeImparteProfesor BuscarUaipById(int idUaip) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(idUaip);
    }
    
    
}