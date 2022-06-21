package mx.avanti.siract.dao;

import java.util.List;
import mx.avanti.siract.entity.Practicalaboratorio;
import mx.avanti.siract.entity.Practicataller;
import mx.avanti.siract.entity.Subtemaunidad;
import mx.avanti.siract.entity.Temaunidad;
import mx.avanti.siract.entity.Unidad;
import mx.avanti.siract.entity.UnidadaprendizajeTieneContenidotematico;
import mx.avanti.siract.persistence.AbstractDAO;
import mx.avanti.siract.persistence.HibernateUtil;
import org.hibernate.HibernateException;

/**
 *
 * @author
 */
public class UnidadAprendizajeTieneContenidotematicoDAO extends AbstractDAO<Integer, UnidadaprendizajeTieneContenidotematico> {

    public void clonarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico, String tipoGrupo) {
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();

            switch (tipoGrupo) {
                case "C":
                    agregarContenidoClase(contenidoTematico.getUnidadList());
                    break;
                case "L":
                    agregarContenidoLaboratorio(contenidoTematico.getPracticalaboratorioList());
                    break;
                case "T":
                    agregarContenidoTaller(contenidoTematico.getPracticatallerList());
                    break;
            }
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    public void versionarContenidoTematico(UnidadaprendizajeTieneContenidotematico contenidoTematico) {
        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();

            List<Unidad> listaUnidad = contenidoTematico.getUnidadList();
            List<Practicalaboratorio> listaLab = contenidoTematico.getPracticalaboratorioList();
            List<Practicataller> listaTaller = contenidoTematico.getPracticatallerList();
            contenidoTematico.setUnidadList(null);
            contenidoTematico.setPracticalaboratorioList(null);
            contenidoTematico.setPracticatallerList(null);

            HibernateUtil.getSession().save(contenidoTematico);
            agregarContenidoClase(listaUnidad);
            agregarContenidoLaboratorio(listaLab);
            agregarContenidoTaller(listaTaller);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    public void agregarContenidoClase(List<Unidad> listaUnidad) {
        for (Unidad unidad : listaUnidad) {
            List<Temaunidad> listaTema = unidad.getTemaunidadList();
            unidad.setTemaunidadList(null);
            HibernateUtil.getSession().save(unidad);

            for (Temaunidad tema : listaTema) {
                List<Subtemaunidad> listaSubtema = tema.getSubtemaunidadList();
                tema.setSubtemaunidadList(null);
                HibernateUtil.getSession().save(tema);

                for (Subtemaunidad subtema : listaSubtema) {
                    HibernateUtil.getSession().save(subtema);
                }
            }
        }
    }

    public void agregarContenidoLaboratorio(List<Practicalaboratorio> listaLab) {
        for (Practicalaboratorio lab : listaLab) {
            HibernateUtil.getSession().save(lab);
        }
    }

    public void agregarContenidoTaller(List<Practicataller> listaTaller) {
        for (Practicataller taller : listaTaller) {
            HibernateUtil.getSession().save(taller);
        }
    }

    /**
     * Metodo para obtener el maximo ID en contenido temático.
     *
     * @return maximoID.
     */
    public Integer obtenerMaximoID() {
        Integer result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            result = (Integer) HibernateUtil.getSession().createSQLQuery("CALL obtenerMaximoIdUAPTieneCT();").uniqueResult();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.closeSession();
        }

        return result;
    }
}
