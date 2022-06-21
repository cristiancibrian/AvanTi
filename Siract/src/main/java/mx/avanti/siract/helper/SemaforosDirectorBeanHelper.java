package mx.avanti.siract.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.entity.Calendarioreporte;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadacademica;

/**
 *
 * @author Edgar
 */
public class SemaforosDirectorBeanHelper {

    //<editor-fold defaultstate="collapsed" desc="Declaración de objetos tipo entidad">
    private Cicloescolar cicloescolar;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public SemaforosDirectorBeanHelper() {
    }
    //</editor-fold>

    /**
     * Con este metodo se obtienen tanto entregados como esperados de todas las
     * tablas en semaforos.
     *
     * @param tipoReporte El tipo de reporte que se selecciono
     * @param cicloEscolar El ciclo escolar seleccionado
     * @param numRACT El numero de RACT
     * @param planesEstudio Los planes de estudio de un programa educativo
     * @return
     */
    public int obtenerEnviadosOEsperados(String tipoReporte, String cicloEscolar, String numRACT, List<Planestudio> planesEstudio) {
        int respuesta = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().obtenerEnviadosOEsperados(tipoReporte, cicloEscolar, numRACT, planesEstudio);
        return respuesta;
    }

    /**
     * Metodo para consultar programas educativos por unidad academica.
     *
     * @param idUAC id de unidad academica.
     * @return Lista de programas educativos encontrados
     */
    public List<Programaeducativo> consultarPEDsPorUAC(int idUAC) {
        List<Programaeducativo> listaProgramaEducativo = new ArrayList<>();
        List<Unidadacademica> listaUnidadAcademica = ServiceFacadeLocator.getInstanceUnidadAcademicaFacade().BuscarUnidadesAcademicas();

        if (idUAC != 0) {
            for (int i = 0; i < listaUnidadAcademica.size(); i++) {
                if (listaUnidadAcademica.get(i).getUACid() == idUAC) {
                    listaProgramaEducativo.addAll(listaUnidadAcademica.get(i).getProgramaeducativoList());
                }
            }
        } else {
            for (int i = 0; i < listaUnidadAcademica.size(); i++) {
                listaProgramaEducativo.addAll(listaUnidadAcademica.get(i).getProgramaeducativoList());
            }
        }

        return listaProgramaEducativo;
    }

    /**
     * Metodo para consultar la lista de todos los ciclos escolares.
     *
     * @return
     */
    public List<Cicloescolar> obtenerListaCiclosEscolares() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().getListaOrdenada();
    }

    /**
     * Metodo para consultar el ciclo escolar mas actual.
     *
     * @return
     */
    public Cicloescolar obtenerCicloEscolarActual() {
        return ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();
    }

    /**
     * Metodo para obtener las fechas para cada RACT.
     *
     * @return
     */
    public List<Date> obtenerFechas() {
        List<Calendarioreporte> calendario = ServiceFacadeLocator.getInstanceCalendarioReporteFacade().findAll();
        int aux = calendario.size();

        List<Date> fechas = new ArrayList<>();
        fechas.add(calendario.get(aux - 3).getCREfechaCorte());
        fechas.add(calendario.get(aux - 3).getCREfechaLimite());
        fechas.add(calendario.get(aux - 2).getCREfechaCorte());
        fechas.add(calendario.get(aux - 2).getCREfechaLimite());
        fechas.add(calendario.get(aux - 1).getCREfechaCorte());
        fechas.add(calendario.get(aux - 1).getCREfechaLimite());

        return fechas;
    }

    /**
     * Metodo que calcula el porcentaje que se desplegara en cada semaforo.
     *
     * @param esperados
     * @param enviados
     * @return
     */
    public double calcularPorcentaje(double esperados, double enviados) {
        return esperados == 0 ? 0 : (enviados * 100) / esperados;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters de objetos tipo entidad">
    public Cicloescolar getCicloescolar() {
        return cicloescolar;
    }

    public void setCicloescolar(Cicloescolar cicloescolar) {
        this.cicloescolar = cicloescolar;
    }
    //</editor-fold>
}
