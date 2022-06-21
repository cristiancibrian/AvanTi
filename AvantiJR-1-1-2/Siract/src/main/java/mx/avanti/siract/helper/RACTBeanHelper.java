package mx.avanti.siract.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.common.NodoCT;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.CalendarioreporteTieneAlerta;
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
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Y
 */
public class RACTBeanHelper {

    private boolean transaccionCorrecta = true;
    List<Unidadaprendizaje> listaUnidadesAprendizaje;
    List<Profesor> profesores;
    List<Grupo> grupos;
    List<Programaeducativo> programaesEducativos;
    Profesor profesor;
    Reporteavancecontenidotematico auxReporteAvancecontenidotematico;
    Reporte auxReporte;
    String numeroReporte;
    boolean validarTiempo = true;
    List<TreeNode> listaSeleccionados;
    List<NodoMultiClass> listaPDF = new ArrayList();
    List<Profesor> profesorUA;
    List<Areaadministrativa> areasAdministrativas;
    int idProfesorporUASelect;
    String reporteSeleccionado = "";
    float porcentajeAvance = 0;
    String rolSeleccionado;
    List<String> comentarios;// lista utilizada para guardar observaciones
    List<Date> fechas;
    HashMap<Integer, Unidad> mapUnidad;
    HashMap<Integer, Temaunidad> mapTema;
    HashMap<Integer, Subtemaunidad> mapSubtema;
    HashMap<Integer, Practicataller> mapPractica;
    HashMap<Integer, Practicalaboratorio> mapPracticaL;
    List<Reporte> reportesAnteriores;
    int temasImpartidos = 0, temasParciales = 0;

    public boolean isTransaccionCorrecta() {
        return transaccionCorrecta;
    }

    public void setTransaccionCorrecta(boolean bandera) {
        this.transaccionCorrecta = bandera;
    }

    public List<Date> getFechas() {
        return fechas;
    }

    public void setFechas(List<Date> fechas) {
        this.fechas = fechas;
    }

    public String getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(String rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public float getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(float porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public int getIdProfesorporUASelect() {
        return idProfesorporUASelect;
    }

    public void setIdProfesorporUASelect(int idProfesorporUASelect) {
        this.idProfesorporUASelect = idProfesorporUASelect;
    }

    public List<Cicloescolar> getAllCiclos() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares();
    }

    public void setProfesorUA(List<Profesor> profesorUA) {
        this.profesorUA = profesorUA;
    }

    public String getReporteSeleccionado() {
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(String reporteSeleccionado) {
        this.reporteSeleccionado = reporteSeleccionado;
    }

    public Cicloescolar cicloescolarActual() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
    }

    public List<NodoMultiClass> getListaPDF() {
        return listaPDF;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setListaPDF(List<NodoMultiClass> listaPDF) {
        this.listaPDF = listaPDF;
    }

    public List<TreeNode> getListaSeleccionados() {
        return listaSeleccionados;
    }

    public void setListaSeleccionados(List<TreeNode> listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
    }

    public RACTBeanHelper() {
        init();

    }

    private void init() {
        listaSeleccionados = new ArrayList();
        comentarios = new ArrayList();
    }

    public void setProgramaesEducativos(List<Programaeducativo> programaesEducativos) {
        this.programaesEducativos = programaesEducativos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public List<Unidadaprendizaje> getUnidadesaprendizajeConGrupos(String programaEducativoSeleccionado) {
        List<Unidadaprendizaje> nuevaLista = null;
        if (profesor != null && profesor.getPROid() != null) {
            nuevaLista = new ArrayList();
            List<UnidadaprendizajeImparteProfesor> uaips;
            Unidadaprendizaje auxiliar;
            listaUnidadesAprendizaje = UnidadesAprendizajePorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));
            //Variable de que depende si la unidad de aprendizaje se muestra o no
            boolean horasCompletas = false;

            for (Unidadaprendizaje unidadaprendizaje : listaUnidadesAprendizaje) {

                //ADAPTACION ESPECIAL DEL QUERY
                uaips = UnidadesAprendizajeImparteProfesorPorProgramaEducativo(profesor, Integer.parseInt(programaEducativoSeleccionado));

                for (UnidadaprendizajeImparteProfesor uaip : uaips) {
                    Grupo grupo;

                    do {
                        //TODO: check this line
                        Cicloescolar ce = cicloescolarActual();
                        grupo = uaip.getGrupoGPOid();
                        System.out.println("grupo :" + grupo);
                        if (grupo != null) {
                            //LOS VALORES QUE SE MUESTRAN EN LA SELECCION reporte
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
                            if (horasCompletas) {
                                auxiliar = uaip.getUnidadAprendizajeUAPid();

                                //SE ASIGNA EL ID DE UNIDAD APRENDIZAJE IMPARTE PROFESOR PARA PODER IDENTIFICAR CADA SET DE PROFESOR/UNIDADAPRENDIZAJE/GRUPO
                                auxiliar.setUAPid(uaip.getUnidadAprendizajeUAPid().getUAPid());
                                //revisar que el tipo que de la unidad de aprendizaje tiene las horas copletas
                                nuevaLista.add(auxiliar);
                            }
                        }
                        grupo = null;
                    } while (grupo != null);
                }
                //FIN DE CREAR COPIA DE LA UNIDADDEAPRENDISAJE
            }
        }
        return nuevaLista;
    }

    /**
     * Se agrego este metodo para guardar los comentarios, anteriormente iban y
     * los guardaba directamente en la base de datos cuando se daba aceptar en
     * la ventana del comentario , ahora los guarda en una variable local hasta
     * que el reporte sea guardado
     *
     * hay variables recibidas innecesarios , no se cambio por conflictos ,
     * cambiar despues de ser posible
     *
     * variables corregidas para version 2.0
     *
     * @param selectedNodes el nodo en el cual se esta haciendo el comentario
     *
     */
    public void guardarComentario(TreeNode selectedNodes) {
        // bandera para validad repetidos
        // convierto en el nodo recibido en string para poder compararlo mejor
        // y agrego un dato mas, si esta seleccionado o no
        String NodoSeleccionado = selectedNodes.getData().toString() + "-//-" + selectedNodes.isSelected();

        // separo con split para poder comparar un comentario previo
        String[] comparacion = NodoSeleccionado.split("-//-");
        // este nodo agregare a la lista de nodos con comentarios
        String nodoComentario;

        /**
         * recorro la lista de comentarios para verificar si se modifica un
         * comentario del mismo nodo y no volverlo a agregar, si encuentra
         * coincidente borrara el comentario anterior para agregar el nuevo
         */
        for (int x = 0; x < comentarios.size(); x++) {
            if (comparacion[4].equalsIgnoreCase(comentarios.get(x).split("-//-")[4])) {
                comentarios.remove(comentarios.get(x));
                System.out.println("########### encontre coincidencia en comentario y borre el anterior");
            }
        }

        nodoComentario = selectedNodes.getData().toString() + "-//-" + selectedNodes.isSelected();
        comentarios.add(nodoComentario);
    }

    /**
     * Este metodo trae las fechas de corte y limite para los 3 racts y lo mete
     * en una variable local llamada fechas.
     */
    public void obtenerFechas() {
        List<Calendarioreporte> calendario = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().findAll();
        int aux = calendario.size();
        fechas = new ArrayList<>();
        fechas.add(calendario.get(aux - 3).getCREfechaCorte());
        fechas.add(calendario.get(aux - 3).getCREfechaLimite());
        fechas.add(calendario.get(aux - 2).getCREfechaCorte());
        fechas.add(calendario.get(aux - 2).getCREfechaLimite());
        fechas.add(calendario.get(aux - 1).getCREfechaCorte());
        fechas.add(calendario.get(aux - 1).getCREfechaLimite());
    }

    /**
     * Este metodo te trae el numero de reporte para la fecha en la cual se esta
     * ingresando al sistema
     *
     * @return un String con el numero de reporte
     */
    public String obtenerReporteEnFecha() {
        String numReporte = "0";
        Calendarioreporte calendario = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().calendarioreporteFechaActual();

        if (calendario == null) {
            System.out.println("NO HAY UN NUMERO DE REPORTE PARA LA FECHA ACTUAL");
        } else {
            calendario.getCalendarioreporteTieneAlertaList();
            CalendarioreporteTieneAlerta calendarioReporteAlerta = calendario.getCalendarioreporteTieneAlertaList().get(0);
            numReporte = calendarioReporteAlerta.getCALnumeroReporte().toString();
            System.out.println("EL NUMERO DE REPORTE ES: " + numReporte);
        }

        return numReporte;
    }

    //METODO PARA OBTENER EL NMERO DEL REPORTE ACTUAL BASADO EN EL ULTIMO REPORTE ENVIADO
    public String obtenerNumeroReporte(UnidadaprendizajeImparteProfesor unidadImpart, int id_profesor, int id_grupo, int id_unidadAprendizajeImparteProfesor, String tipo, String subGrupo) {
        numeroReporte = "0";

        int auxNumReporte = 1;
        Reporteavancecontenidotematico reporteAuxiliarNumero = new Reporteavancecontenidotematico();
        reporteAuxiliarNumero.setRACnumero(reporteSeleccionado);
        reporteAuxiliarNumero.setRACid(0);
        if (unidadImpart.getReporteavancecontenidotematicoList().size() >= 1) {
            reporteAuxiliarNumero = unidadImpart.getReporteavancecontenidotematicoList().get(unidadImpart.getReporteavancecontenidotematicoList().size() - 1);
        }
        if (reporteAuxiliarNumero == null || reporteAuxiliarNumero.getRACid() == 0) {
            System.out.println("===NO SE ENCONTRO REPORTE");
        } else {
            numeroReporte = reporteAuxiliarNumero.getRACnumero();
            if (reporteAuxiliarNumero.getRACstatus().equals("Enviado")) {
                auxNumReporte = Integer.parseInt(numeroReporte) + 1;
                System.out.println("AUMENTANDO EL NUMERO DE REPORTE");
            }
        }

        numeroReporte = String.valueOf(auxNumReporte);
        System.out.println("NUMERO DEREPORTE " + numeroReporte);
        return numeroReporte;
    }

    /**
     * Metodo para validar si un reporte ya ha sido enviado
     *
     * @param unidadImpartida de tipo unidadAprendizajeImparteProfesor
     * @return Boolean si fue enviado o no
     */
    public boolean validarReporteEnviado(UnidadaprendizajeImparteProfesor unidadImpartida) {
        Reporteavancecontenidotematico auxReporteavanceContenidotematico2 = new Reporteavancecontenidotematico();
        if (unidadImpartida.getReporteavancecontenidotematicoList().size() >= 1) {
            auxReporteavanceContenidotematico2 = unidadImpartida.getReporteavancecontenidotematicoList().get(unidadImpartida.getReporteavancecontenidotematicoList().size() - 1);
        }
        if (auxReporteavanceContenidotematico2 == null || !obtenerReporteEnFecha().equals(auxReporteavanceContenidotematico2.getRACnumero()) || !auxReporteavanceContenidotematico2.getRACstatus().equals("Enviado")) {
            return false;

        } else {
            System.out.println("Conflicto al comprobar datos previos al enviar");
            return true;
        }
    }

    // A partir de aqui agregare metodos para limpiar la clase y hacerlo acorde
    // a la version 2.0 de siract 8/01/2019
    /**
     * Metodo para obtener los programas educativos del usuario(profesor)
     * logueado
     *
     * @param profesor
     * @param cicloEscolar
     * @return lista de programas educativos que pertencen al profesor logueado
     */
    public List<Programaeducativo> programasEducativosProfesor(Profesor profesor, String cicloEscolar) {
        List<Programaeducativo> programaEducativos = new ArrayList();

        for (ProfesorPerteneceProgramaeducativo pp : profesor.getProfesorPerteneceProgramaeducativoList()) {
            if (pp.getCicloescolar().getCEScicloEscolar().equalsIgnoreCase(cicloEscolar)) {
                programaEducativos.add(pp.getProgramaeducativo());
                System.out.println("programa en helper : " + pp.getProgramaeducativo().getPEDnombre());
            }
        }
        //programaEducativos.add(new Programaeducativo());

        return programaEducativos;

    }

    /**
     * Metodo para obtener el ciclo escolar actual
     *
     * @return ciclo escolar actual
     */
    public Cicloescolar cicloEscolarActual() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
    }

    /**
     * Metodo que regresas las unidades de aprendizaje imparte profesor que
     * pertenecen al profesor recibido en el ciclo escolar actual
     *
     * @param profesor
     * @param idProgramaEducativo
     * @return lista unidadAprendizajeImparte profesor del ciclo escolar actual
     */
    public List<UnidadaprendizajeImparteProfesor> UnidadesAprendizajeImparteProfesorPorProgramaEducativo(Profesor profesor, int idProgramaEducativo) {
        List<UnidadaprendizajeImparteProfesor> unidadImparte = new ArrayList();
        List<UnidadaprendizajeImparteProfesor> nuevaLista = new ArrayList();
        Cicloescolar ciclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
        // actualizo el profesor en caso que se haya registro un RACT
        profesor = ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorById(profesor.getPROid());
        // revisa el cuclo escolar actual
        for (UnidadaprendizajeImparteProfesor listaUnidades : profesor.getUnidadaprendizajeImparteProfesorList()) {
            if (listaUnidades.getCicloEscolarCESid().getCESid() == ciclo.getCESid()) {
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
     * Metodo que regresas las unidades de aprendizaje que pertenecen al
     * profesor recibido en el ciclo escolar actual
     *
     * @param profesor
     * @param idProgramaEducativo
     * @return lista unidadAprendizaje del ciclo escolar actual
     */
    public List<Unidadaprendizaje> UnidadesAprendizajePorProgramaEducativo(Profesor profesor, int idProgramaEducativo) {
        List<UnidadaprendizajeImparteProfesor> unidadImparte = new ArrayList();
        List<Unidadaprendizaje> nuevaLista = new ArrayList();
        Cicloescolar ciclo = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
        // revisa el cuclo escolar actual
        for (UnidadaprendizajeImparteProfesor listaUnidades : profesor.getUnidadaprendizajeImparteProfesorList()) {
            if (listaUnidades.getCicloEscolarCESid().getCESid() == ciclo.getCESid()) {
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
     * Metodo para traer los nodos segun la unidadAprendizajeImparteProfesor
     * recibida.
     *
     * @param unidadImpartida de tipo unidadAprendizajeImparte profesor
     * @param unidadAprendizaje
     * @param numReporte
     * @param version
     * @return TreeNode con los nodos correspientes a la unidad recibida y al
     * ract acutal
     */
    public TreeNode TraerNodosTree(UnidadaprendizajeImparteProfesor unidadImpartida, Unidadaprendizaje unidadAprendizaje, String numReporte, String version) {
        String tipo = unidadImpartida.getUIPtipoSubgrupo();
        listaSeleccionados = new ArrayList<>();
        listaPDF = new ArrayList();
        reportesAnteriores = new ArrayList();
        limpiarComentarios();

        switch (tipo) {
            case "C":
                return traerUnidades(unidadAprendizaje, unidadImpartida, numReporte, Integer.parseInt(version));
            case "L":
                return traerPracticasLaboratorio(unidadAprendizaje, unidadImpartida, numReporte, Integer.parseInt(version));
            case "T":
                return traerPracticasTaller(unidadAprendizaje, unidadImpartida, numReporte, Integer.parseInt(version));
            default:
                return traerPracticasCampo(unidadAprendizaje, unidadImpartida, numReporte, Integer.parseInt(version));
        }
    }

    /**
     * Metodo para ajustar las unidades, temas, subtemas a un treeNode segun la
     * unidadAprendizajeImparteProfesor recibida
     *
     * @param unidadAprendizaje
     * @param unidadImpartada
     * @param numReporte
     * @param version
     * @return TreeNode de unidades,Temas, Subtemas
     */
    public TreeNode traerUnidades(Unidadaprendizaje unidadAprendizaje, UnidadaprendizajeImparteProfesor unidadImpartada, String numReporte, int version) {
        unidadImpartada = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(unidadImpartada.getUIPid());
        UnidadaprendizajeTieneContenidotematico contenidoTematicoActual = llenarCTClase(unidadAprendizaje, version);

        for (Reporteavancecontenidotematico reporteAvance : unidadImpartada.getReporteavancecontenidotematicoList()) {
            reportesAnteriores.addAll(reporteAvance.getReporteList());
        }
        //REVISAR QUE ESTES LLAMANDO LOS METODOS NUEVOS CON V EN TRAERTEMASV Y TRAERSUBTEMAS V, REVISAR POR CHECA VARIAS VECES EL PRUEBA 2 Y POR QUE ENTRA DETECTAA OTRAS UNIDADES COMO SI HUBIERA ALGUN CAMBIO DE NOMBRE
        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasClase() * 16);
        boolean unidadConVersion;
        boolean versionado = false;
        boolean seencontroUnidadV;
//        Unidad unidadVer = mapUnidad.get(reportesAnteriores.get(0).getReportePK().getUNIid());
//
//        if (unidadVer != null) {
//            if (contenidoTematicoActual.getCTid().intValue() != unidadVer.getContenidoTematicoCTid().getCTid()) {
//                versionado = true;
//            }
//        }

        Reporte reporteTema;
        Reporte reporteSubtema;
        Reporte reporteUnidad;
        for (Unidad unidad : ordenarUnidades(contenidoTematicoActual.getUnidadList())) {
            seencontroUnidadV = false;
            //VALIDAR BIEN EL VERSIONADO
//            if (versionado = true) {
            //ESTE IF BUSCA COMO COLOCARLO SIN AFECTAR A LOS NO VERSIONADOS.

            unidadConVersion = false;
            reporteUnidad = verificarUnidadEnReporteAnterior(unidad);
            Reporteavancecontenidotematico UltimoReporteA = null;
            List<Reporte> UltimosReportes = null;
            if (unidadImpartada.getReporteavancecontenidotematicoList().size() >= 1) {
                UltimoReporteA = unidadImpartada.getReporteavancecontenidotematicoList().get(unidadImpartada.getReporteavancecontenidotematicoList().size() - 1);
                UltimosReportes = UltimoReporteA.getReporteList();
            }
            if (UltimosReportes != null) {
                for (Reporte repo : UltimosReportes) {
                    for (Temaunidad tema : ordenarTemas(unidad.getTemaunidadList())) {
                        if (repo.getImpartido() && repo.getReportePK().getTUNid() == tema.getTUNid() && repo.getReportePK().getSUTid() == 0 && repo.getReporteAvanceContenidoTematicoRACid().getRACstatus().equals("Parcial")) {
                            if (buscarUnidadVersionada(unidad.getUNIid())) {
                                seencontroUnidadV = true;
                            }
                        }
                        for (Subtemaunidad subtema : ordenarSubtemas(tema.getSubtemaunidadList())) {
                            if (repo.getImpartido() && repo.getReportePK().getSUTid() == subtema.getSUTid() && repo.getReporteAvanceContenidoTematicoRACid().getRACstatus().equals("Parcial")) {
                                if (buscarUnidadVersionada(unidad.getUNIid())) {
                                    seencontroUnidadV = true;
                                }
                            }
                        }
                    }

                }
            }
            if (reporteUnidad == null) {
                if (buscarUnidadVersionada(unidad.getUNIid())) {
                    seencontroUnidadV = true;
                }
                int nomRepetido = 0;
                for (Unidad mUnidad : mapUnidad.values()) {
                    if (mUnidad.getUNInombre().equals(unidad.getUNInombre())) {
                        nomRepetido++;
                    }
                }
                if (nomRepetido <= 1) {
                    unidadConVersion = true;
                }
            }

            for (Temaunidad tema : ordenarTemas(unidad.getTemaunidadList())) {
                reporteTema = verificarTemaEnReporteAnterior(tema);
                if (reporteTema == null) {
                    int nomRepetido = 0;
                    for (Temaunidad mTemaUnidad : mapTema.values()) {
                        if (mTemaUnidad.getTUNnombre().equals(tema.getTUNnombre())) {
                            nomRepetido++;
                        }
                    }
                    if (nomRepetido <= 1) {
                        unidadConVersion = true;
                    }
                }

                for (Subtemaunidad subtema : ordenarSubtemas(tema.getSubtemaunidadList())) {
                    reporteSubtema = verificarSubTemaEnReporteAnterior(subtema);
                    if (reporteSubtema == null) {
                        int nomRepetido = 0;
                        for (Subtemaunidad mSubtemaUnidad : mapSubtema.values()) {
                            if (mSubtemaUnidad.getSUTnombre().equals(subtema.getSUTnombre())) {
                                nomRepetido++;
                            }
                        }
                        if (nomRepetido <= 1) {
                            unidadConVersion = true;
                        }
                    }
                }
            }

            if (unidadConVersion == true || seencontroUnidadV) {
                nodoRaiz.getChildren().add(traerTemasV(unidad, unidad.getUNIid()));
            } else {
                nodoRaiz.getChildren().add(traerTemas(unidad, unidad.getUNIid()));
            }

//            } else {
////                nodoRaiz.getChildren().add(traerTemas(unidad, unidad.getUNIid()));
//            }
        }

        mapUnidad.clear();
        mapTema.clear();
        mapSubtema.clear();
        reportesAnteriores.clear();

        return nodoRaiz;
    }

    public boolean buscarUnidadVersionada(int unidadid) {
        for (Reporte rep : reportesAnteriores) {
            if (rep.getReportePK().getUNIid() == unidadid && rep.getReportePK().getTUNid() == 0 && rep.getReportePK().getSUTid() == 0) {
                return true;
            }
        }
        return false;
    }

    public TreeNode traerTemas(Unidad unidad, int perteneceAUnidad) {
        TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(unidad));
        listaPDF.add(new NodoMultiClass(unidad));
        temasImpartidos = 0;
        temasParciales = 0;
        Reporte reporteUnidad = verificarUnidadEnReporteAnterior(unidad);

        for (Temaunidad tema : ordenarTemas(unidad.getTemaunidadList())) {
            nivel1.getChildren().add(traerSubtemas(tema, perteneceAUnidad, reporteUnidad));
        }
        //revisar aqui

        if (reporteUnidad != null && (nivel1.getChildCount() == temasImpartidos && temasParciales == 0)) {
            ((NodoMultiClass) nivel1.getData()).setParcialSelected(false);
        } else if ((reporteUnidad == null && temasImpartidos != 0) || (reporteUnidad == null && temasParciales != 0) || (reporteUnidad != null && temasImpartidos != 0) || (reporteUnidad != null && temasImpartidos != 0)) {
            ((NodoMultiClass) nivel1.getData()).setParcialSelected(true);
        }

        if (reporteUnidad != null && reporteUnidad.getImpartido()) {
            nivel1 = establecerNodoSeleccionado(reporteUnidad, nivel1);
        }
        if (reporteUnidad != null) {
            ((NodoMultiClass) nivel1.getData()).setObservaciones(reporteUnidad.getREPobservacion());
        }

        return nivel1;
    }

    public TreeNode traerSubtemas(Temaunidad tema, int perteneceAUnidad, Reporte reporteUnidad) {
        TreeNode nivel2 = new DefaultTreeNode(new NodoMultiClass(tema, perteneceAUnidad));
        listaPDF.add(new NodoMultiClass(tema));
        int perteneceATema = tema.getTUNid(), subtemasImpartidos = 0;
        Reporte reporteTema = verificarTemaEnReporteAnterior(tema);

        for (Subtemaunidad subtema : ordenarSubtemas(tema.getSubtemaunidadList())) {
            TreeNode nivel3 = new DefaultTreeNode(new NodoMultiClass(subtema, perteneceAUnidad, perteneceATema));
            listaPDF.add(new NodoMultiClass(subtema));
            Reporte reporteSubtema = verificarSubTemaEnReporteAnterior(subtema);
            if (reporteSubtema != null && reporteSubtema.getImpartido() == true) {
                subtemasImpartidos++;
                nivel3 = establecerNodoSeleccionado(reporteSubtema, nivel3);
            }
            if (reporteSubtema != null) {
                ((NodoMultiClass) nivel3.getData()).setObservaciones(reporteSubtema.getREPobservacion());
            }
            nivel2.getChildren().add(nivel3);
        }

        if (reporteTema != null || (nivel2.getChildCount() == subtemasImpartidos)) {
            ((NodoMultiClass) nivel2.getData()).setParcialSelected(false);
        } else if ((reporteTema == null && subtemasImpartidos != 0)) {
            ((NodoMultiClass) nivel2.getData()).setParcialSelected(true);
            temasParciales++;
        }

        if (reporteTema != null && reporteTema.getImpartido() == true) {
            temasImpartidos++;
            nivel2 = establecerNodoSeleccionado(reporteTema, nivel2);

        }
        if (reporteTema != null) {
            ((NodoMultiClass) nivel2.getData()).setObservaciones(reporteTema.getREPobservacion());
        }
        return nivel2;
    }

    public TreeNode traerTemasV(Unidad unidad, int perteneceAUnidad) {
        TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(unidad));
        listaPDF.add(new NodoMultiClass(unidad));
        temasImpartidos = 0;
        temasParciales = 0;
        Reporte reporteUnidad = verificarUnidadEnReporteAnteriorV(unidad);

        for (Temaunidad tema : ordenarTemas(unidad.getTemaunidadList())) {
            nivel1.getChildren().add(traerSubtemasV(tema, perteneceAUnidad, reporteUnidad));
        }
        //revisar aqui

        if (reporteUnidad != null && (nivel1.getChildCount() == temasImpartidos && temasParciales == 0)) {
            ((NodoMultiClass) nivel1.getData()).setParcialSelected(false);
        } else if ((reporteUnidad == null && temasImpartidos != 0) || (reporteUnidad == null && temasParciales != 0) || (reporteUnidad != null && temasImpartidos != 0) || (reporteUnidad != null && temasImpartidos != 0)) {
            ((NodoMultiClass) nivel1.getData()).setParcialSelected(true);
        }

        if (reporteUnidad != null && reporteUnidad.getImpartido()) {
            nivel1 = establecerNodoSeleccionado(reporteUnidad, nivel1);
        }
        if (reporteUnidad != null) {
            ((NodoMultiClass) nivel1.getData()).setObservaciones(reporteUnidad.getREPobservacion());
        }

        return nivel1;
    }

    public TreeNode traerSubtemasV(Temaunidad tema, int perteneceAUnidad, Reporte reporteUnidad) {
        TreeNode nivel2 = new DefaultTreeNode(new NodoMultiClass(tema, perteneceAUnidad));
        listaPDF.add(new NodoMultiClass(tema));
        int perteneceATema = tema.getTUNid(), subtemasImpartidos = 0;
        Reporte reporteTema = verificarTemaEnReporteAnteriorV(tema);

        for (Subtemaunidad subtema : ordenarSubtemas(tema.getSubtemaunidadList())) {
            TreeNode nivel3 = new DefaultTreeNode(new NodoMultiClass(subtema, perteneceAUnidad, perteneceATema));
            listaPDF.add(new NodoMultiClass(subtema));
            Reporte reporteSubtema = verificarSubTemaEnReporteAnteriorV(subtema);
            if (reporteSubtema != null && reporteSubtema.getImpartido() == true) {
                subtemasImpartidos++;
                nivel3 = establecerNodoSeleccionado(reporteSubtema, nivel3);
            }
            if (reporteSubtema != null) {
                ((NodoMultiClass) nivel3.getData()).setObservaciones(reporteSubtema.getREPobservacion());
            }
            nivel2.getChildren().add(nivel3);
        }

        if (reporteTema != null || (nivel2.getChildCount() == subtemasImpartidos)) {
            ((NodoMultiClass) nivel2.getData()).setParcialSelected(false);
        } else if ((reporteTema == null && subtemasImpartidos != 0)) {
            ((NodoMultiClass) nivel2.getData()).setParcialSelected(true);
            temasParciales++;
        }

        if (reporteTema != null && reporteTema.getImpartido() == true) {
            temasImpartidos++;
            nivel2 = establecerNodoSeleccionado(reporteTema, nivel2);

        }
        if (reporteTema != null) {
            ((NodoMultiClass) nivel2.getData()).setObservaciones(reporteTema.getREPobservacion());
        }
        return nivel2;
    }

//    public TreeNode traerTemasV(Unidad unidad, int perteneceAUnidad) {
//        TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(unidad));
//        listaPDF.add(new NodoMultiClass(unidad));
//        temasImpartidos = 0;
//        temasParciales = 0;
//        for (Temaunidad tema : ordenarTemas(unidad.getTemaunidadList())) {
//            nivel1.getChildren().add(traerSubtemasV(tema, perteneceAUnidad, null));
//        }
//
//        return nivel1;
//    }
//
//    public TreeNode traerSubtemasV(Temaunidad tema, int perteneceAUnidad, Reporte reporteUnidad) {
//        TreeNode nivel2 = new DefaultTreeNode(new NodoMultiClass(tema, perteneceAUnidad));
//        listaPDF.add(new NodoMultiClass(tema));
//        int perteneceATema = tema.getTUNid();
//
//        for (Subtemaunidad subtema : ordenarSubtemas(tema.getSubtemaunidadList())) {
//            TreeNode nivel3 = new DefaultTreeNode(new NodoMultiClass(subtema, perteneceAUnidad, perteneceATema));
//            listaPDF.add(new NodoMultiClass(subtema));
//            nivel2.getChildren().add(nivel3);
//        }
//        return nivel2;
//    }
    /**
     * Metodo para ajustar las practicas Laboratorio de una
     * UnidadAprendizajeImparte Profesor segun el ract correspondiente
     *
     * @param unidadAprendizaje
     * @param unidadImpartada
     * @param reporteSelec
     * @param version
     * @return TreeNode con las practicas Laboratorio
     */
    public TreeNode traerPracticasLaboratorio(Unidadaprendizaje unidadAprendizaje, UnidadaprendizajeImparteProfesor unidadImpartada, String reporteSelec, int version) {
        UnidadaprendizajeTieneContenidotematico contenidoTematicoActual = llenarCTLab(unidadAprendizaje, version);

        for (Reporteavancecontenidotematico reporteAvance : unidadImpartada.getReporteavancecontenidotematicoList()) {
            reportesAnteriores.addAll(reporteAvance.getReporteList());
        }

        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasLaboratorio() * 16);

        for (Practicalaboratorio practica : ordenarPracticasLaboratorio(contenidoTematicoActual.getPracticalaboratorioList())) {
            TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(practica));
            listaPDF.add(new NodoMultiClass(practica));
            Reporte reporteAuxiliar = verificarPracticaLabEnReporteAnterior(practica);

            if (reporteAuxiliar != null && reporteAuxiliar.getImpartido() == true) {
                nivel1 = establecerNodoSeleccionado(reporteAuxiliar, nivel1);
            }

            if (reporteAuxiliar != null) {
                ((NodoMultiClass) nivel1.getData()).setObservaciones(reporteAuxiliar.getREPobservacion());
            }
            nodoRaiz.getChildren().add(nivel1);
        }
        mapPracticaL.clear();
        reportesAnteriores.clear();

        return nodoRaiz;
    }

    /**
     * Metodo para ajustar las practicas taller de una UnidadAprendizajeImparte
     * Profesor segun el ract correspondiente
     *
     * @param unidadAprendizaje
     * @param unidadImpartada
     * @param reporteSelec
     * @param version
     * @return TreeNode con las practicas taller
     */
    public TreeNode traerPracticasTaller(Unidadaprendizaje unidadAprendizaje, UnidadaprendizajeImparteProfesor unidadImpartada, String reporteSelec, int version) {
        UnidadaprendizajeTieneContenidotematico contenidoTematicoActual = llenarCTTaller(unidadAprendizaje, version);
        for (Reporteavancecontenidotematico reporteAvance : unidadImpartada.getReporteavancecontenidotematicoList()) {
            reportesAnteriores.addAll(reporteAvance.getReporteList());
        }

        List<Practicataller> practicasTall = ordenarPracticasTaller(contenidoTematicoActual.getPracticatallerList());
        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasTaller() * 16);

        for (Practicataller practica : practicasTall) {
            TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(practica));
            listaPDF.add(new NodoMultiClass(practica));
            Reporte reporteAuxiliar = verificarPracticaTallerEnReporteAnterior(practica);

            if (reporteAuxiliar != null && reporteAuxiliar.getImpartido() == true) {
                nivel1 = establecerNodoSeleccionado(reporteAuxiliar, nivel1);
            }

            if (reporteAuxiliar != null) {
                ((NodoMultiClass) nivel1.getData()).setObservaciones(reporteAuxiliar.getREPobservacion());
            }

            nodoRaiz.getChildren().add(nivel1);
        }
        mapPractica.clear();
        reportesAnteriores.clear();

        return nodoRaiz;
    }

    /**
     * Metodo para ajustar las practicas campo de una UnidadAprendizajeImparte
     * Profesor segun el ract correspondiente
     *
     * @param unidadAprendizaje
     * @param unidadImpartada
     * @param reporteSelec
     * @param version
     * @return TreeNode con las practicas Campo
     */
    public TreeNode traerPracticasCampo(Unidadaprendizaje unidadAprendizaje, UnidadaprendizajeImparteProfesor unidadImpartada, String reporteSelec, int version) {
        UnidadaprendizajeTieneContenidotematico contenidoTematicoActual = unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(version - 1);

        for (Reporteavancecontenidotematico reporteAvance : unidadImpartada.getReporteavancecontenidotematicoList()) {
            reportesAnteriores.addAll(reporteAvance.getReporteList());
        }

        TreeNode nodoRaiz = new DefaultTreeNode(new NodoCT());
        ((NodoCT) nodoRaiz.getData()).setHoras(unidadAprendizaje.getUAPhorasCampo() * 16);

        for (Practicascampo practica : ordenarPracticasCampo(contenidoTematicoActual.getPracticascampoList())) {
            TreeNode nivel1 = new DefaultTreeNode(new NodoMultiClass(practica));
            listaPDF.add(new NodoMultiClass(practica));
            Reporte reporteAuxiliar = verificarPracticaCampoEnReporteAnterior(reportesAnteriores, practica.getPRCid());

            if (reporteAuxiliar != null) {
                nivel1 = establecerNodoSeleccionado(reporteAuxiliar, nivel1);
            }

            nodoRaiz.getChildren().add(nivel1);
        }

        reportesAnteriores.clear();

        return nodoRaiz;
    }

    public TreeNode establecerNodoSeleccionado(Reporte reporteAux, TreeNode nodo) {
        ((NodoMultiClass) nodo.getData()).setObservaciones(reporteAux.getREPobservacion());

        if (reporteAux.getReporteAvanceContenidoTematicoRACid().getRACstatus().equals("Enviado")) {
            nodo.setSelectable(false);
            nodo.setSelected(true);
            ((NodoMultiClass) nodo.getData()).setImpartido(true);
            ((NodoMultiClass) nodo.getData()).setDeReporteAnterior(true);
        } else {
            if (!((NodoMultiClass) nodo.getData()).getParcialSelected() && reporteAux.getImpartido() == true) {
                nodo.setSelected(true);
                listaSeleccionados.add(nodo);
            }
        }

        return nodo;
    }

    public void limpiarComentarios() {
        try {
            comentarios.clear();
        } catch (NullPointerException e) {
            System.out.println("lista de comentarios Vacia ");
        }
    }

    /**
     * Verifica el Unidad ya fue registrado previamente en la lista de reportes
     *
     * @param unidad
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarUnidadEnReporteAnterior(Unidad unidad) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Unidad unidadAux = mapUnidad.get(rep.getReportePK().getUNIid());

            if (rep.getReportePK().getUNIid() != 0 && unidadAux.getUNInombre().equals(unidad.getUNInombre()) && unidadAux.getUNInumero().intValue() == unidad.getUNInumero().intValue() && rep.getReportePK().getTUNid() == 0 && rep.getReportePK().getSUTid() == 0) {
                reporte = rep;
                System.out.println("HOla");
            }
        }

        return reporte;
    }

    /**
     * Verifica el Tema ya fue registrado previamente en la lista de reportes
     *
     * @param tema
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarTemaEnReporteAnterior(Temaunidad tema) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Temaunidad temaAux = mapTema.get(rep.getReportePK().getTUNid());

            if (rep.getReportePK().getTUNid() != 0 && temaAux.getTUNnombre().equals(tema.getTUNnombre()) && temaAux.getTUNnumero().equals(tema.getTUNnumero()) && rep.getReportePK().getSUTid() == 0) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Verifica el subtema ya fue registrado previamente en la lista de reportes
     *
     * @param subtema
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarSubTemaEnReporteAnterior(Subtemaunidad subtema) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Subtemaunidad subtemaAux = mapSubtema.get(rep.getReportePK().getSUTid());

            if (rep.getReportePK().getSUTid() != 0 && subtemaAux.getSUTnombre().equals(subtema.getSUTnombre()) && subtemaAux.getSUTnumero().equals(subtema.getSUTnumero())) {
                reporte = rep;
            }
        }

        return reporte;
    }

    public Reporte verificarUnidadEnReporteAnteriorV(Unidad unidad) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Unidad unidadAux = mapUnidad.get(rep.getReportePK().getUNIid());

            if (rep.getReportePK().getUNIid() == unidad.getUNIid() && unidadAux.getUNInombre().equals(unidad.getUNInombre()) && unidadAux.getUNInumero().intValue() == unidad.getUNInumero().intValue() && rep.getReportePK().getTUNid() == 0 && rep.getReportePK().getSUTid() == 0) {
                reporte = rep;
                System.out.println("HOla");
            }
        }

        return reporte;
    }

    /**
     * Verifica el Tema ya fue registrado previamente en la lista de reportes
     *
     * @param tema
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarTemaEnReporteAnteriorV(Temaunidad tema) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Temaunidad temaAux = mapTema.get(rep.getReportePK().getTUNid());

            if (rep.getReportePK().getTUNid() == tema.getTUNid() && temaAux.getTUNnombre().equals(tema.getTUNnombre()) && temaAux.getTUNnumero().equals(tema.getTUNnumero()) && rep.getReportePK().getSUTid() == 0) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Verifica el subtema ya fue registrado previamente en la lista de reportes
     *
     * @param subtema
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarSubTemaEnReporteAnteriorV(Subtemaunidad subtema) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Subtemaunidad subtemaAux = mapSubtema.get(rep.getReportePK().getSUTid());

            if (rep.getReportePK().getSUTid() == subtema.getSUTid() && subtemaAux.getSUTnombre().equals(subtema.getSUTnombre()) && subtemaAux.getSUTnumero().equals(subtema.getSUTnumero())) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Verifica el Taller ya fue registrado previamente en la lista de reportes
     *
     * @param practica
     * @param reportes
     * @param idTaller
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarPracticaTallerEnReporteAnterior(Practicataller practica) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Practicataller practicaAux = mapPractica.get(rep.getReportePK().getPRTid());
            if (rep.getReportePK().getPRTid() != 0 && practicaAux.getPRTnombre().equals(practica.getPRTnombre()) && practicaAux.getPRTnumero() == practica.getPRTnumero()) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Verifica el Practica Campo ya fue registrado previamente en la lista de
     * reportes
     *
     * @param reportes
     * @param idCampo
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarPracticaCampoEnReporteAnterior(List<Reporte> reportes, int idCampo) {
        Reporte reporte;
        reporte = null;
        /*Verifica solo practicas campo */
        for (Reporte rep : reportes) {
            if (rep.getReportePK().getPRCid() == idCampo) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Verifica el laboratorio ya fue registrado previamente en la lista de
     * reportes
     *
     * @param practica
     * @return Variable tipo Reporte del reporte que se encontro
     */
    public Reporte verificarPracticaLabEnReporteAnterior(Practicalaboratorio practica) {
        Reporte reporte = null;
        for (Reporte rep : reportesAnteriores) {
            Practicalaboratorio practicaAux = mapPracticaL.get(rep.getReportePK().getPRLid());
            if (rep.getReportePK().getPRLid() != 0 && practicaAux.getPRLnombre().equals(practica.getPRLnombre()) && practicaAux.getPRLnumero() == practica.getPRLnumero()) {
                reporte = rep;
            }
        }

        return reporte;
    }

    /**
     * Este metodo recibe y guarda todos los datos del avance del ract en la
     * base de datos y verifica que se hayan enviado correctamente, si no
     * muestra un error y deja los datos intactos.
     *
     * @param auxReporteAvance
     * @param auxReporte
     * @param tieneC
     * @param agregaC
     * @param noTieneC
     * @param esNuevo
     */
    public void enviarTodo(Reporteavancecontenidotematico auxReporteAvance, List<Reporte> auxReporte, List<Reporte> tieneC, List<Reporte> noTieneC, List<Reporte> agregaC, boolean esNuevo) {
        auxReporteAvance.setReporteList(null);

        if (esNuevo == true) {
            try {
                auxReporteAvance = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().agregarReporteavancecontenidotematico(auxReporteAvance);
                System.out.println("##### guardare reporteAvanceContenidoTematico con id " + auxReporteAvance.getRACid());
                System.out.println("Se guarda reporte linea 3010 ractHelper");
            } catch (Exception e) {
                setTransaccionCorrecta(false);
            }
        } else {
            try {
                auxReporteAvance = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().actualizarRACT(auxReporteAvance);
                System.out.println("se modifico reporteAvanceContenidoTematico con id: " + auxReporteAvance.getRACid());

            } catch (Exception e) {
                setTransaccionCorrecta(false);
            }
        }

        for (Reporte i : auxReporte) {
            i.setReporteAvanceContenidoTematicoRACid(auxReporteAvance);
            i.getReportePK().setREPid(auxReporteAvance.getRACid());
            System.out.println("\n\n ################33 este es el id de reporte: " + auxReporteAvance.getRACid());
            System.out.println("\n\n ###########3 este es el reporte que guardare:" + auxReporteAvance.toString());
            if (ServiceFacadeLocator.getInstanceReporteFacade().agregarReporte(i) == true) {
            } else {
                setTransaccionCorrecta(false);
                break;
            }
        }

        for (Reporte i : tieneC) {
            i.setReporteAvanceContenidoTematicoRACid(auxReporteAvance);
            i.getReportePK().setREPid(auxReporteAvance.getRACid());
            try {
                ServiceFacadeLocator.getInstanceReporteFacade().actualizarReporte(i);
            } catch (Exception e) {
                setTransaccionCorrecta(false);
                break;
            }
        }

        for (Reporte i : noTieneC) {
            i.setReporteAvanceContenidoTematicoRACid(auxReporteAvance);
            i.getReportePK().setREPid(auxReporteAvance.getRACid());
            if (ServiceFacadeLocator.getInstanceReporteFacade().eliminarReporte(i) == false) {
                setTransaccionCorrecta(false);
                break;
            } else {
                System.out.println("%%%%%%% Reporte Eliminado ####### ");
            }
        }

        for (Reporte i : agregaC) {
            i.setReporteAvanceContenidoTematicoRACid(auxReporteAvance);
            i.getReportePK().setREPid(auxReporteAvance.getRACid());
            System.out.println("&&&&&&&&&& este es el id de reporte: " + auxReporteAvance.getRACid());
            if (ServiceFacadeLocator.getInstanceReporteFacade().agregarReporte(i) == true) {
            } else {
                setTransaccionCorrecta(false);
                break;
            }
        }
        //setTransaccionCorrecta(false);
        //Si ocurrio un error elimina todos los cambios de la base de datos dejandola sin cambios y se muestra un mensaje de error
        ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().terminarTransaccion(isTransaccionCorrecta());
        if (esNuevo == false) {
            if (isTransaccionCorrecta() == false) {
                PrimeFaces.current().executeScript("PF('error').show();");
            }
        } else {
            if (isTransaccionCorrecta() == false) {
                PrimeFaces.current().executeScript("PF('error').show();");
            }
        }
    }

    /**
     * Metodo para guardar Reporte de avance contenido tematico asi como los
     * reportes que se desprenden de este, dentro de este metodo se busca los
     * reportes para saber si son parciales y modificarlos o crear un nuevo
     * reporte segun el numero de reporte al que se quiera acceder
     *
     *   ***** Tratar de modificar las variables que recibe ya que por
     * modificacion siract 2.0 quedan unas sin utilizarse********
     *
     * @param unidadImparte Nueva para 2.0 ya que tiene todos los atributos
     * necesarios
     * @param numeroRep numero de reporte que se esta creando
     * @param selectedNodes nodos seleccionados en el tree de ract xhtml
     * @param porcentajeAvance porcentaje que se completo
     * @param id_profesor ** probablemente innecesaria
     * @param id_grupo ** probablemente innecesaria
     * @param subGrupo ** probablemente innecesaria
     * @param tipo_grupo ** probablemente innecesaria
     * @param id_unidadAprendizajeImparteProfesor ** probablemente innecesaria
     * @param enviar
     * @return Boolean para saber si se puedo guardar o no el reporte
     */
    public boolean guardarReporteAvance(UnidadaprendizajeImparteProfesor unidadImparte, String numeroRep, TreeNode[] selectedNodes, float porcentajeAvance, int id_profesor, int id_grupo, String subGrupo, String tipo_grupo, int id_unidadAprendizajeImparteProfesor, boolean enviar) {
        boolean esNuevo;
        List<Reporte> guardarCNuevos = new ArrayList();
        List<Reporte> tieneC = new ArrayList();
        List<Reporte> noTieneC = new ArrayList();
        List<Reporte> agregaC = new ArrayList();

        if ((Integer.parseInt(obtenerNumeroReporte(unidadImparte, id_profesor, id_grupo, id_unidadAprendizajeImparteProfesor, tipo_grupo, subGrupo))) <= 3) {
            // verifico si el uaip tiene reportes  si no creo uno
            if (unidadImparte.getReporteavancecontenidotematicoList().size() >= 1) {
                auxReporteAvancecontenidotematico = unidadImparte.getReporteavancecontenidotematicoList().get(unidadImparte.getReporteavancecontenidotematicoList().size() - 1);
            } else {
                auxReporteAvancecontenidotematico = null;
            }
            if (auxReporteAvancecontenidotematico == null || !auxReporteAvancecontenidotematico.getRACnumero().equalsIgnoreCase(numeroRep)) {
                System.out.println("SE CREARA UN NUEVO REPORTE");
                auxReporteAvancecontenidotematico = new Reporteavancecontenidotematico();
                auxReporteAvancecontenidotematico.setRACid(0);
                auxReporteAvancecontenidotematico.setUnidadAprendizajeimparteprofesorUIPid(unidadImparte);
                List<Reporte> reportesVacio = new ArrayList();
                auxReporteAvancecontenidotematico.setReporteList(reportesVacio);

                /*entra aqui si ya hay un reporte para este RACT */
            } else {
                System.out.println("## se encontro un reporte para este ract: ");
                System.out.println("REPORTE #" + auxReporteAvancecontenidotematico.getRACnumero() + " con id " + auxReporteAvancecontenidotematico.getRACid());
                if (auxReporteAvancecontenidotematico.getRACstatus().equals("Parcial")) {
                    System.out.println("SE ENCONTRO UN REPORTE SIN TERMINAR");
                } else {

                    System.out.println("SE ENCONTRO UN REPORTE EN ESTADO DE ENVIADO");
                    auxReporteAvancecontenidotematico = new Reporteavancecontenidotematico();
                    auxReporteAvancecontenidotematico.setRACid(0);
                    auxReporteAvancecontenidotematico.setUnidadAprendizajeimparteprofesorUIPid(unidadImparte);

                    /*Asigo id 0 para guardar uno nuevo y ademas asgino todos sus
            atributos*/
                }
            }
            auxReporteAvancecontenidotematico.setRACavanceGlobal(porcentajeAvance);
            auxReporteAvancecontenidotematico.setRACfechaElaboracion(new Date());
            auxReporteAvancecontenidotematico.setRACstatus("Parcial");
            auxReporteAvancecontenidotematico.setRACnumero(obtenerReporteEnFecha());
            auxReporteAvancecontenidotematico.setUnidadAprendizajeimparteprofesorUIPid(unidadImparte);
            // List<Reporte> reportesVacio = new ArrayList();
            // auxReporteAvancecontenidotematico.setReporteList(reportesVacio);

            if (auxReporteAvancecontenidotematico.getRACnumero().equals("0")) {
                auxReporteAvancecontenidotematico.setRACnumero(obtenerReporteEnFecha());
            }
            /*Si se presiono guardar y enviar se hara lo siguiente */
            if (enviar) {
                auxReporteAvancecontenidotematico.setRACstatus("Enviado");
            }
            auxReporteAvancecontenidotematico.setRACnumero(obtenerReporteEnFecha());
            Date fecha = Calendar.getInstance().getTime();
            System.out.println(fecha);
            /**
             * validare si el reporte ya esta registrado en caso de ser parcial
             * para mandar a metodo update en vez de guardar
             */
            esNuevo = auxReporteAvancecontenidotematico.getRACid() == 0;

            Reporteavancecontenidotematico auxReporteA = auxReporteAvancecontenidotematico;

            int racId = auxReporteAvancecontenidotematico.getRACid();

            if (selectedNodes != null && selectedNodes.length > 0) {
                System.out.println("-------------GUARDABDO CON " + selectedNodes.length + " NODOS***********************************8");
                List<Reporte> listaReportes = new ArrayList();
                for (TreeNode node : selectedNodes) {
                    if (node != null && node.isSelectable() == true) {
                        auxReporte = new Reporte();
                        auxReporte.setReportePK(new ReportePK());
                        // pongo la informacion del nodo en un string
                        String infoNodo = node.getData().toString() + "-//-" + node.isSelected();
                        System.out.println("############# estoy guardando : " + infoNodo);
                        // divido la informacion con split
                        String[] todo = infoNodo.split("-//-");
                        if (auxReporte.getImpartido() == null) {
                            auxReporte.setImpartido(true);
                        }
                        /**
                         * recorro la lista de comentarios para ver los
                         * comentarios si coincide la informacion del nodo con
                         * la del comentario agregare el comentario al nodo para
                         * que la guarde correctamente
                         *
                         */
                        for (int x = 0; x < comentarios.size(); x++) {
                            if (infoNodo.equalsIgnoreCase(comentarios.get(x))) {
                                auxReporte.setREPobservacion(todo[6].trim());

                            }
                        }

                        auxReporte.setREPobservacion(todo[6].trim());
                        System.out.println("------------------- esta observacion: " + auxReporte.getREPobservacion());
                        switch (todo[0]) {
                            case "unidad":
                                auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                break;
                            case "temaunidad":
                                auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                                auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                break;
                            case "subtemaunidad":
                                auxReporte.getReportePK().setSUTid(Integer.parseInt(todo[3]));
                                auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                                auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                break;
                            case "practicalaboratorio":
                                auxReporte.getReportePK().setPRLid(Integer.parseInt(todo[1]));
                                break;
                            case "practicataller":
                                auxReporte.getReportePK().setPRTid(Integer.parseInt(todo[1]));
                                break;
                            case "practicaCampo":
                                auxReporte.getReportePK().setPRCid(Integer.parseInt(todo[1]));
                                break;

                        }
                        auxReporte.getReportePK().setREPid(racId);

                        auxReporte.setReporteAvanceContenidoTematicoRACid(auxReporteA);


                        /* Buscar la manera de cambiar este agregar para validar
                        si el reporta ya esta parcial solo agregue los nuevos
                        y no los viejos;
                        de momento en delegate se usa saveorupdate pero eso
                        gasta mucha memoria y hace conexion a la bd de manera inncesaria
                        26/01/2018  */
                        guardarCNuevos.add(auxReporte);
                        listaReportes.add(auxReporte);
                    }

                }
                /*Si el ultimo ract encontrado es el ract actual*/
                if (auxReporteAvancecontenidotematico.getRACnumero().equalsIgnoreCase(numeroRep)) {
                    /* se recorre los reportes pasados del mismo ract y se compara
                    los registros que se hicieron o los que estab registrados, para
                    borrar los que estan registrados pero en la edicion de en este ract no se
                    seleccionaron, para solucionar problema de guardar el reporte
                    y editarlo despues, seleccionando y quitando la seleccion  */
                    List<Reporte> reportess = auxReporteAvancecontenidotematico.getReporteList();
                    //List<Reporte> reportess= ServiceFacadeLocator.getInstanceReporteFacade().consultarReportesPorID(racId);
                    int bandera;

                    for (int x = 0; x < reportess.size(); x++) {
                        bandera = 0;

                        for (int y = 0; y < listaReportes.size(); y++) {
                            // inicia IF para validar
                            if ((listaReportes.get(y).getReportePK().getPRTid() == reportess.get(x).getReportePK().getPRTid() && reportess.get(x).getReportePK().getPRTid() != 0)
                                    // validacion para practicas laboratorio
                                    || (listaReportes.get(y).getReportePK().getPRLid() == reportess.get(x).getReportePK().getPRLid() && reportess.get(x).getReportePK().getPRLid() != 0)
                                    // Validacion para practicas Campo
                                    || (listaReportes.get(y).getReportePK().getPRCid() == reportess.get(x).getReportePK().getPRCid() && reportess.get(x).getReportePK().getPRCid() != 0)
                                    // validacion para unidades
                                    || (listaReportes.get(y).getReportePK().getUNIid() == reportess.get(x).getReportePK().getUNIid()
                                    && listaReportes.get(y).getReportePK().getTUNid() == reportess.get(x).getReportePK().getTUNid()
                                    && listaReportes.get(y).getReportePK().getSUTid() == reportess.get(x).getReportePK().getSUTid()
                                    && reportess.get(x).getReportePK().getUNIid() != 0 && reportess.get(x).getReportePK().getTUNid() == 0
                                    && reportess.get(x).getReportePK().getSUTid() == 0)
                                    // Validacion para Temas
                                    || (listaReportes.get(y).getReportePK().getTUNid() == reportess.get(x).getReportePK().getTUNid()
                                    && listaReportes.get(y).getReportePK().getSUTid() == reportess.get(x).getReportePK().getSUTid()
                                    && reportess.get(x).getReportePK().getTUNid() != 0
                                    && reportess.get(x).getReportePK().getSUTid() == 0)
                                    // Validacion para Subtemas
                                    || (listaReportes.get(y).getReportePK().getSUTid() == reportess.get(x).getReportePK().getSUTid()
                                    && reportess.get(x).getReportePK().getSUTid() != 0)) {
                                // fin de la sentencia de if

                                bandera = 1;
                                break;
                            } else {

                            }

                        }
                        if (bandera == 0) {
                            //  si tiene un comentario no lo borrara solo lo marcara como no impartido
                            if (!reportess.get(x).getREPobservacion().equalsIgnoreCase("")) {
                                reportess.get(x).setImpartido(false);
                                tieneC.add(reportess.get(x));
                            } //  si no tiene comentario lo borrara
                            else {
                                noTieneC.add(reportess.get(x));
                            }
                        }
                    }
                }
                /**
                 * ahora recorrera de nuevo los nodos que tienen un comentario
                 * si estaban seleccionados ya se agregaron en el for anterior
                 * si no se agregaran en el siguiente
                 */

                int ban;// bandera para validar comentarios antes de seleccionar la unidad

                auxReporteA.setReporteList(listaReportes);
                // for para recorrer los comentarios en unidades que no se hayan seleccionado
                for (int x = 0; x < comentarios.size(); x++) {
                    ban = 0;
                    String[] todo = comentarios.get(x).split("-//-");
                    // verifico que no este seleccionado
                    if (todo[7].equalsIgnoreCase("false")) {
                        for (TreeNode node : selectedNodes) {

                            if (node.getData().toString().split("-//-")[4].equalsIgnoreCase(todo[4])) {
                                ban = 1;
                                break;

                            }
                        }
                        if (ban == 1) {
                            System.out.println("##################%%%%%%%%%%%%%%%%%% Ya estaba seleccionado no hare nada");
                        } else {

                            auxReporte = new Reporte();
                            auxReporte.setReportePK(new ReportePK());

                            if (auxReporte.getImpartido() == null) {
                                auxReporte.setImpartido(false);
                            }

                            auxReporte.setREPobservacion(todo[6].trim());
                            System.out.println("------------------- esta observacion: " + auxReporte.getREPobservacion());
                            switch (todo[0]) {
                                case "unidad":
                                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                    break;
                                case "temaunidad":
                                    auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                    break;
                                case "subtemaunidad":
                                    auxReporte.getReportePK().setSUTid(Integer.parseInt(todo[3]));
                                    auxReporte.getReportePK().setTUNid(Integer.parseInt(todo[2]));
                                    auxReporte.getReportePK().setUNIid(Integer.parseInt(todo[1]));
                                    break;
                                case "practicalaboratorio":
                                    auxReporte.getReportePK().setPRLid(Integer.parseInt(todo[1]));
                                    break;
                                case "practicataller":
                                    auxReporte.getReportePK().setPRTid(Integer.parseInt(todo[1]));
                                    break;
                                case "practicaCampo":
                                    auxReporte.getReportePK().setPRCid(Integer.parseInt(todo[1]));
                                    break;

                            }
                            auxReporte.getReportePK().setREPid(racId);
                            auxReporte.setReporteAvanceContenidoTematicoRACid(auxReporteA);
                            agregaC.add(auxReporte);
                            listaReportes.add(auxReporte);
                        }
                    }
                }

                auxReporteA.setReporteList(listaReportes);

            }
            //Se envia toda la informacion generada del RACT para que se guarde en la base de datos
            try {
                enviarTodo(auxReporteAvancecontenidotematico, guardarCNuevos, tieneC, noTieneC, agregaC, esNuevo);
            } catch (RuntimeException e) {
                System.out.println("Ocurrio un error al enviar el reporte/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
            }

            try {
                comentarios.clear();

            } catch (NullPointerException e) {
                System.out.println("lista de comentarios Vacia ");
            }
            return true;

        } else {
            System.out.println("EL REPORTE NO SERA ENVIADO");
            return false;
        }

    }

    /**
     * Método para comparar la unidad actual con las de la versión pasada para
     * comprobar si ya existía
     *
     * @param unidadActual
     * @param unidadesARevisar
     * @return
     */
    public Unidad encontrarUnidadDistintasVersiones(Unidad unidadActual, List<Unidad> unidadesARevisar) {
        for (Unidad unidad : unidadesARevisar) {
            if (unidad.getUNInombre().equals(unidadActual.getUNInombre()) && unidad.getUNIhoras() == unidadActual.getUNIhoras()) {
                return unidad;
            }
        }

        return null;
    }

    /**
     * Método para comprobar si el tema actual existía en los temas de la
     * versión anterior
     *
     * @param temaActual
     * @param temasARevisar
     * @return
     */
    public Temaunidad encontrarTemaunidadDistintasVersiones(Temaunidad temaActual, List<Temaunidad> temasARevisar) {
        System.out.println("Temas");
        for (Temaunidad tema : temasARevisar) {
            if (tema.getTUNnombre().equals(temaActual.getTUNnombre()) && Objects.equals(tema.getTUNhoras(), temaActual.getTUNhoras())) {
                return tema;
            }
        }
        return null;
    }

    /**
     * Método para comprobar si el subtema actual existía en los subtemas de la
     * versión anterior
     *
     * @param subtemaActual
     * @param subtemasARevisar
     * @return
     */
    public Subtemaunidad encontrarSubtemaunidadDistintasVersiones(Subtemaunidad subtemaActual, List<Subtemaunidad> subtemasARevisar) {
        System.out.println("Subtemas");
        for (Subtemaunidad subtema : subtemasARevisar) {
            if (subtema.getSUTnombre().equals(subtemaActual.getSUTnombre()) && Objects.equals(subtema.getSUThoras(), subtemaActual.getSUThoras())) {
                return subtema;
            }
        }
        return null;
    }

    /**
     * Método para comparar la práctica de taller actual y saber si existe
     * dentro de las prácticas de la versión pasada (también se podría usar a la
     * inversa, una práctica pasada con las actuales)
     *
     * @param practicaActual
     * @param practicasARevisar
     * @return
     */
    public Practicataller encontrarPracticatallerDistintasVersiones(Practicataller practicaActual, List<Practicataller> practicasARevisar) {
        for (Practicataller practica : practicasARevisar) {
            if (practica.getPRTnombre().equals(practicaActual.getPRTnombre()) && Objects.equals(practica.getPRThoras(), practicaActual.getPRThoras())) {
                return practica;
            }
        }
        return null;
    }

    /**
     * Método para comparar la práctica de laboratorio actual y saber si existe
     * dentro de las prácticas pasadas
     *
     * @param practicaActual
     * @param practicasARevisar
     * @return
     */
    public Practicalaboratorio encontrarPracticalaboratorioDistintasVersiones(Practicalaboratorio practicaActual, List<Practicalaboratorio> practicasARevisar) {
        for (Practicalaboratorio practica : practicasARevisar) {
            if (practica.getPRLnombre().equals(practicaActual.getPRLnombre()) && Objects.equals(practica.getPRLhoras(), practicaActual.getPRLhoras())) {
                return practica;
            }
        }
        return null;
    }

    /**
     * Metodo para ordenar una lista de unidades por su número de unidad
     *
     * @param unidades
     * @return Unidades ordenadas por numero
     */
    public List<Unidad> ordenarUnidades(List<Unidad> unidades) {
        Collections.sort(unidades, (Unidad u1, Unidad u2) -> u1.getUNInumero() > u2.getUNInumero() ? 1 : -1);

        return unidades;
    }

    /**
     * Metodo para ordenar una lista de temas por su número de tema
     *
     * @param temas
     * @return Unidades ordenados por numero
     */
    public List<Temaunidad> ordenarTemas(List<Temaunidad> temas) {
        Collections.sort(temas, (Temaunidad tu1, Temaunidad tu2) -> tu1.getTUNnumero().compareTo(tu2.getTUNnumero()));

        return temas;
    }

    /**
     * Metodo para ordenar una lista de subtemas por su número de subtema
     *
     * @param subtemas
     * @return Subtemas ordenanos por numero
     */
    public List<Subtemaunidad> ordenarSubtemas(List<Subtemaunidad> subtemas) {
        Collections.sort(subtemas, (Subtemaunidad stu1, Subtemaunidad stu2) -> stu1.getSUTnumero().compareTo(stu2.getSUTnumero()));

        return subtemas;
    }

    /**
     * Metodo para ordenar una lista de practicas de taller por su número de
     * practica
     *
     * @param practicasTaller
     * @return Practicas de taller ordenadas por numero
     */
    public List<Practicataller> ordenarPracticasTaller(List<Practicataller> practicasTaller) {
        Collections.sort(practicasTaller, (Practicataller pt1, Practicataller pt2) -> pt1.getPRTnumero() > pt2.getPRTnumero() ? 1 : -1);

        return practicasTaller;
    }

    /**
     * Metodo para ordenar una lista de practicas de laboratorio por su número
     * de practica
     *
     * @param practicasLaboratorio
     * @return Practicas de laboratorio ordenadas por numero
     */
    public List<Practicalaboratorio> ordenarPracticasLaboratorio(List<Practicalaboratorio> practicasLaboratorio) {
        Collections.sort(practicasLaboratorio, (Practicalaboratorio pl1, Practicalaboratorio pl2) -> pl1.getPRLnumero() > pl2.getPRLnumero() ? 1 : -1);

        return practicasLaboratorio;
    }

    /**
     * Metodo para ordenar una lista de practicas de campo por su número de
     * practica
     *
     * @param practicasCampo
     * @return Practicas de campo ordenadas por numero
     */
    public List<Practicascampo> ordenarPracticasCampo(List<Practicascampo> practicasCampo) {
        Collections.sort(practicasCampo, (Practicascampo pc1, Practicascampo pc2) -> pc1.getPRCnumero() > pc2.getPRCnumero() ? 1 : -1);

        return practicasCampo;
    }

    public UnidadaprendizajeTieneContenidotematico llenarCTClase(Unidadaprendizaje unidadAprendizaje, int version) {
        List<Unidad> listaUnidad = new ArrayList<>();
        mapUnidad = new HashMap();
        mapTema = new HashMap();
        mapSubtema = new HashMap();

        for (UnidadaprendizajeTieneContenidotematico contenidoTem : unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList()) {
            listaUnidad.addAll(contenidoTem.getUnidadList());
        }

        for (Unidad unidad : listaUnidad) {
            mapUnidad.put(unidad.getUNIid(), unidad);

            for (Temaunidad tema : unidad.getTemaunidadList()) {
                mapTema.put(tema.getTUNid(), tema);

                for (Subtemaunidad subtema : tema.getSubtemaunidadList()) {
                    mapSubtema.put(subtema.getSUTid(), subtema);
                }
            }
        }

        return unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(version - 1);
    }

    public UnidadaprendizajeTieneContenidotematico llenarCTTaller(Unidadaprendizaje unidadAprendizaje, int version) {
        List<Practicataller> listaPractica = new ArrayList<>();
        mapPractica = new HashMap();

        for (UnidadaprendizajeTieneContenidotematico contenidoTem : unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList()) {
            listaPractica.addAll(contenidoTem.getPracticatallerList());
        }

        for (Practicataller practica : listaPractica) {
            mapPractica.put(practica.getPRTid(), practica);
        }

        return unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(version - 1);
    }

    public UnidadaprendizajeTieneContenidotematico llenarCTLab(Unidadaprendizaje unidadAprendizaje, int version) {
        List<Practicalaboratorio> listaPracticaL = new ArrayList<>();
        mapPracticaL = new HashMap();

        for (UnidadaprendizajeTieneContenidotematico contenidoTem : unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList()) {
            listaPracticaL.addAll(contenidoTem.getPracticalaboratorioList());
        }

        for (Practicalaboratorio practicaL : listaPracticaL) {
            mapPracticaL.put(practicaL.getPRLid(), practicaL);
        }

        return unidadAprendizaje.getUnidadaprendizajeTieneContenidotematicoList().get(version - 1);
    }
}
