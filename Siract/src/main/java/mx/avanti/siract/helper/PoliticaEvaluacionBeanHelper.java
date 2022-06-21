/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.helper;

/**
 *
 * @author ferna
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Alumno;
import mx.avanti.siract.entity.AlumnoPerteneceGrupo;
import mx.avanti.siract.entity.Articulos;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Criterioevaluacion;
import mx.avanti.siract.entity.Grupo;
import mx.avanti.siract.entity.PoliticaTieneCriterio;
import mx.avanti.siract.entity.Politicaevaluacion;
import mx.avanti.siract.entity.Politicaevaluacioncomentario;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.ProfesorPerteneceProgramaeducativo;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.entity.UnidadaprendizajeImparteProfesor;
import mx.avanti.siract.ui.LoginBean;

public class PoliticaEvaluacionBeanHelper {

    private Politicaevaluacion politica;

    public Politicaevaluacion getProfesor() {
        return politica;
    }
    public List<Politicaevaluacion> politicasaceptadasAlumno(int id){
        return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getPoliticasAceptadasAlumno(id);
    }

    public void setPoliticaevaluacion(Politicaevaluacion politica) {
        this.politica = politica;
    }

    public List<Criterioevaluacion> getAllcriterios() {
        return ServiceFacadeLocator.getInstanceCriterioevaluacionFacade().consultarCriterios();
    }

    public List<Alumno> getAllalumnos() {
        return ServiceFacadeLocator.getInstanceAlumnoFacade().getListaAlumnos();
    }

    public void agregarPolitica(Politicaevaluacion politicaEvaluacion) {
        ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().agregarPolitica(politicaEvaluacion,false);
    }

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
    
    public List<AlumnoPerteneceGrupo> AlumnosPorGrupo(int ce, int uipid,int gpo){
        List<AlumnoPerteneceGrupo> alumnos = new ArrayList();
        return alumnos;
    }
    
      public Cicloescolar cicloEscolarActual(){
       return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
   }
      
      public List<Programaeducativo> programasEducativosProfesor (Profesor profesor,String cicloEscolar){
        List<Programaeducativo> programaEducativos=new ArrayList();

            for(ProfesorPerteneceProgramaeducativo pp: profesor.getProfesorPerteneceProgramaeducativoList()){
           if (pp.getCicloescolar().getCEScicloEscolar().toString().equalsIgnoreCase(cicloEscolar)) {
                   programaEducativos.add(pp.getProgramaeducativo());
                   
                 
               }
            }
           //programaEducativos.add(new Programaeducativo());
           
        return programaEducativos;
        
    }
      
     public List<Programaeducativo> programasEducativosProfesor2 (Profesor profesor,String cicloEscolar){
        List<Programaeducativo> programaEducativos=new ArrayList();

            for(ProfesorPerteneceProgramaeducativo pp: profesor.getProfesorPerteneceProgramaeducativoList()){
           if (pp.getCicloescolar().getCESid().toString().equalsIgnoreCase(cicloEscolar)) {
                   programaEducativos.add(pp.getProgramaeducativo());
               }
            }
           //programaEducativos.add(new Programaeducativo());
           
        return programaEducativos;
        
    }
     
     
     public List<Unidadaprendizaje> unidadesAlumno (int id, int ce){
         List<Unidadaprendizaje> unidades;
         unidades = ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getlistaunidadesalumno(id, ce);
         return unidades;
     }
      public Programaeducativo programaEducativoProfesorPolitica (Profesor profesor,String cicloEscolar){
        Programaeducativo programaEducativos=new Programaeducativo();

            for(ProfesorPerteneceProgramaeducativo pp: profesor.getProfesorPerteneceProgramaeducativoList()){
               if(pp.getCicloescolar().getCEScicloEscolar().equalsIgnoreCase(cicloEscolar)){
                   programaEducativos=pp.getProgramaeducativo();
               }
            }
           //programaEducativos.add(new Programaeducativo());
           
        return programaEducativos;
        
    }
      
     public List<Politicaevaluacion> politicasProfesor(int id) {
        return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getPoliticasProfesor(id);
    }
     
     
      public List<PoliticaTieneCriterio> getAllPoliticaTieneCriterio(int id) {
        return ServiceFacadeLocator.getInstancePoliticaTieneCriterioFacade().getListapoliticasevaluacion(id);
    }
      public Politicaevaluacion buscarpolitica(int id) {
        return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getpoliticaevaluacion(id);
    }
      public Programaeducativo buscarProgramaEducativo(int id){
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativoPorID(id);
    }
      public Unidadaprendizaje buscarUnidadAprendisaje(int id){
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeFacade().getUnidadAprendazajeById(id);
    }
      public Alumno buscarAlumno(int id){
        return ServiceFacadeLocator.getInstanceAlumnoFacade().getMatriculasalumnos(id);
    }
      public List<PoliticaTieneCriterio> buscarPorcentajes(int id){
        return ServiceFacadeLocator.getInstancePoliticaTieneCriterioFacade().getListapoliticasevaluacion(id);
    }
      
      public List<AlumnoPerteneceGrupo> getalumnosporgrupo(int gpoid){
          return ServiceFacadeLocator.getInstanceAlumnoPerteneceGrupoFacade().getListaalumnosgrupo(gpoid);
      }
      
      
    public List<Alumno> AlumnosPorUIP(int UIPId){
          return ServiceFacadeLocator.getInstanceAlumnoFacade().consultarAlumnosPorUIP(UIPId);
    }
    
    public List<Politicaevaluacion> politicasAlumno(int id) {
        return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getPoliticasAlumno(id);
    }
    
    public boolean AceptacionAlumno(Politicaevaluacion politica){
          return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().ActualizarAceptacionAlumno(politica);
    }
    
    public boolean AceptacionRPE(Politicaevaluacion politica){
          return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().ActualizarAceptacionResponsable(politica);
    }
    
    
    
    
    
    public boolean guardarPolitica(Politicaevaluacion politica, 
                    boolean esEnvio, List<PoliticaTieneCriterio> PtcBorrados){
        
        boolean guardadoCorrecto = true;
        boolean badTransaction = false;
        Politicaevaluacion politicaGuardada= new Politicaevaluacion();
        // Agrego politica primero
        
        try{
            if(politica.getPOEId() != null){
                politicaGuardada = ServiceFacadeLocator
                    .getInstancePoliticaevaluacionFacade()
                    .actualizarPolitica(politica, esEnvio);
            }else{
                politicaGuardada = ServiceFacadeLocator
                    .getInstancePoliticaevaluacionFacade()
                    .agregarPolitica(politica, esEnvio);
            }
        }catch(Exception e){
            guardadoCorrecto = false;
        }
        
        for(PoliticaTieneCriterio ptc : PtcBorrados){
            if(ptc.getPtcId() != null){
                guardadoCorrecto  = ServiceFacadeLocator
                        .getInstancePoliticaTieneCriterioFacade()
                        .EliminarPoliticaCriterio(ptc);
            }
        }
        
            
        for(PoliticaTieneCriterio ptc : politica.getPoliticaTieneCriterioList()){
            ptc.setPOEId(politicaGuardada);
            
            if(ptc.getCEVId().getCEVId() == null){
                //TODO es nuevo criterio hay 
                //que agregarlo a la base de datos primerio
            }
            if(ptc.getPtcId()== null){
                ptc.setPtcId(0);
            }
             if(!ServiceFacadeLocator.getInstancePoliticaTieneCriterioFacade()
                     .agregarPoliticaCriterio(ptc)){

                 guardadoCorrecto = false;
             }
        }
        
        
        
         //Si ocurrio un error elimina todos los cambios de la base de 
         //datos dejandola sin cambios 
        
        ServiceFacadeLocator.getInstancePoliticaevaluacionFacade()
                            .terminarTransaccion(guardadoCorrecto);
        
        if(!guardadoCorrecto){
            return false;
        }
        
        return true;
    }
    
    public List<Articulos> findAllArticulos(){
        return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().findAllArticulos();
    }
    
    /// ACeptar politica 
    
    public Profesor RPEporCEyPE(int PE){
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarRPEporPEyCE(PE);
    }
    
    public boolean agregarComentarioPolitica(Politicaevaluacioncomentario comentario){
        return ServiceFacadeLocator.getInstancePoliticaComentarioFacade().agregarComentarioPolitica(comentario);
    }
    public boolean ActualizarRechazoAlumno(Politicaevaluacion politica){
      return  ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().ActualizarRechazoAlumno(politica);
   }
   
   
   public boolean ActualizarRechazoResponsable(Politicaevaluacion politica){
      return  ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().ActualizarRechazoResponsable(politica);
   }
   
   public List<Politicaevaluacion> getPoliticaPorResponsable(int idResponsable){
       return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getPoliticaPorResponsable(idResponsable);
    }
    
    
    //----- consultas
    
    public List<Cicloescolar> getAllCiclos() {
       List<Cicloescolar> ciclosEscolares = ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCiclosEscolares();
        return ciclosEscolares;
    }
    
     public List<Profesor> getProfesoresPorCoordinadoryCE(Integer proid, String cicloEscolarSeleccionado) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorCoordinadoryCE(proid, cicloEscolarSeleccionado);
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
    
     public List<Profesor> getProfesoresPorRPEYCE(Integer rpeProId, String ce) {
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorRPEyCE(rpeProId, ce);
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
    
    
    public List<Programaeducativo> traerTodosProgramasEducativos(){
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().buscarProgramaEducativos();
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
    
    
public boolean alumnosengrupo(int ce,int uip,int gpo){
        boolean iguales = true;
        List<AlumnoPerteneceGrupo> alumnos = new ArrayList();
        List<AlumnoPerteneceGrupo> alumnosclase = new ArrayList();
        List<AlumnoPerteneceGrupo> alumnostaller = new ArrayList();
        List<AlumnoPerteneceGrupo> alumnoslab = new ArrayList();
        alumnos = ServiceFacadeLocator.getInstanceAlumnoPerteneceGrupoFacade().getAlumnosporGrupo(ce, uip, gpo);
        for(AlumnoPerteneceGrupo alu: alumnos){
            if(alu.getUIPId().getUIPtipoSubgrupo().equals("C")){
              alumnosclase.add(alu);
            }
            else if(alu.getUIPId().getUIPtipoSubgrupo().equals("T")){
              alumnostaller.add(alu);
            }
            else{
               alumnoslab.add(alu);
            }
        }
        if(alumnosclase==null){
            alumnosclase=alumnostaller;
        }
        for(AlumnoPerteneceGrupo a : alumnoslab){
            for(AlumnoPerteneceGrupo b : alumnosclase){
                if(a.getAlumnoALId().getALMatricula().equals(b.getAlumnoALId().getALMatricula())){
                    iguales = true;
                    break;
                }
                else{
                    iguales = false;
                }
            }
            if(!iguales)
                break;
        }
        alumnosclase = new ArrayList();
        alumnostaller = new ArrayList();
        alumnoslab = new ArrayList();
        return iguales;
    }
    /**
     * Metodo que regresas las unidades de aprendizaje imparte profesor que
     * pertenecen al profesor recibido en el ciclo escolar actual
     s
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
    
    
    public List<Profesor> getProfesoresByPEYCE(String peid, String ce) {
        int pe = Integer.parseInt(peid);
        return ServiceFacadeLocator.getInstanceProfesorFacade().buscarPorPEyCE(pe, ce);
    }
    
    
     public List<Unidadaprendizaje> getUnidadesaprendisajePorProgramaEducativo(String programaEducativoSeleccionado, String rol, String profId, String ce) {
        List<Unidadaprendizaje> nuevaLista = null;
        List<Unidadaprendizaje> listaUnidadesAprendisaje = null;
        
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
        
        for (int x = 0; x < nuevaLista.size(); x++) {
            for (int y = 0; y < nuevaLista.size(); y++) {
                if (nuevaLista.get(x).getUAPnombre().equals(nuevaLista.get(y).getUAPnombre())) {
                    nuevaLista.remove(y);
                }
            }

        }
        return nuevaLista;
    }
     
     
    public Cicloescolar cicloescolarActual() {

        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
    }
    
//     public List<Unidadaprendizaje> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid) {
//        return getUNIdadesaprendisajeConGrupos(pe, ce, proid, 0);
//    }
//     
//      public List<Unidadaprendizaje> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid, int coordinadorProId) {
//        List<Unidadaprendizaje> nuevaLista = null;
//        List<Object[]> rawResult;
//
//        nuevaLista = new ArrayList();
//        List<Grupo> gruposdeUnidad;
//        List<UnidadaprendizajeImparteProfesor> uaips;
//        Unidadaprendizaje auxiliar;
//        int peid = Integer.parseInt(pe);
//        rawResult = ServiceFacadeLocator
//                .getInstanceUnidadAprendizajeFacade()
//                .buscarPorProfesorYProgramaEducativo(proid, peid, ce, coordinadorProId);
//
//        for (Object[] row : rawResult) {
//            boolean horasCompletas = false;
//            Unidadaprendizaje ua = new Unidadaprendizaje();
//            ua.setUAPid((int) row[23]);
//            ua.setUAPclave((int) row[1]);
//            String nombreUAP = (String) row[2];
//            ua.setUAPetapaFormacion((String) row[3]);
//            ua.setUAPcreditos((int) row[4]);
//            ua.setUAPhorasClase((int) row[5]);
//            ua.setUAPhorasLaboratorio((int) row[6]);
//            ua.setUAPhorasTaller((int) row[7]);
//            ua.setUAPhorasClinica((int) row[8]);
//            ua.setUAPhorasCampo((int) row[9]);
//            ua.setUAPhorasExtraClase((int) row[10]);
//            ua.setUAPtipoCaracter((String) row[11]);
//            ua.setCicloEscolarCESid(ServiceFacadeLocator.getInstanceCicloEscolarFacade().buscarCicloEscolar((int) row[12]));
//            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasClaseCompletas((boolean) row[14]);
//            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasLaboratorioCompletas((boolean) row[15]);
//            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasTallerCompletas((boolean) row[16]);
//            ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).setUAPhorasCampoCompletas((boolean) row[17]);
//            int grupoNumero = (int) row[19];
//            String tipoSubgrupo = (String) row[21];
//            String uipSubgrupo = (String) row[22];
//            ua.setUAPnombre(nombreUAP + " -- " + grupoNumero + " -- " + uipSubgrupo + " -- " + tipoSubgrupo);
//
//            switch (tipoSubgrupo) {
//                case "C":
//                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasClaseCompletas();
//                    break;
//                case "L":
//                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasLaboratorioCompletas();
//                    break;
//                case "T":
//                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasTallerCompletas();
//                    break;
//                case "PC":
//                    horasCompletas = ua.getUnidadaprendizajeTieneContenidotematicoList().get(0).getUAPhorasCampoCompletas();
//                    break;
//            }
//            if (horasCompletas) {
//                nuevaLista.add(ua);
//            }
//        }
//
//        return nuevaLista;
//    }
      
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
     * que pertenezcan al profesor recibido
     *
     * @param idCiclo
     * @param idPE
     * @return
     */
    public List<UnidadaprendizajeImparteProfesor> uaipPorProfesorPEyCE(int idCiclo, int idPE, int idProfesor) {
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().uaipPorProfesorPEyCE(idCiclo, idPE, idProfesor);
    }
    
    public UnidadaprendizajeImparteProfesor uipByID(int id){
        return ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().findUnidadAprendizajeImparteProfesorByID(id);
    }
    
    public List<Programaeducativo> getProgramaesEducativos(Profesor profesor) {
       
        return ServiceFacadeLocator.getInstanceProgramaEducativoFacade().getListaUnidadAprendizajeFFWD(null, null, profesor.getPROid().toString());
        
       
        
    }
    
    
    public List<Politicaevaluacion> getPoliticaPorUIP(int uaiId){
       return ServiceFacadeLocator.getInstancePoliticaevaluacionFacade().getPoliticaPorUIP(uaiId);
   }




 public List<UnidadaprendizajeImparteProfesor> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid) {
        return getUNIdadesaprendisajeConGrupos(pe, ce, proid, 0);
    }
     
      public List<UnidadaprendizajeImparteProfesor> getUNIdadesaprendisajeConGrupos(String pe, String ce, int proid, int coordinadorProId) {
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
      
    
}
