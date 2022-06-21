/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import clases.AbstractDAO;
import clases.HibernateUtil;
import entidades.Alerta;
import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;

/**
 * @author Adriana
 */
public class AlertaDAO extends AbstractDAO<Integer, Alerta> {
    /**
     * Metodo para encontrar una aleerta por su tipo
     *
     * @param tipo de alerta
     * @return Alerta encontrada
     */
    public Alerta findAlerta(String tipo) {
        Alerta obj = null;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata("Alerta");
        String nombre = "Alerta";
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            String query1 = "from " + "Alerta" + " as " + nombre.toLowerCase() + " where ALEtipo=:tipo";
            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setString("tipo", tipo);
            obj = (Alerta) query.uniqueResult();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.closeSession();
        }
        return obj;
    }


}
