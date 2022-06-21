package mx.avanti.siract.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CriteriaUtil<T> extends AbstractDAO<Integer, T> {

    private Class<T> tipo;

    /**
     * @param tipo Clase que implementa al DTO deseado
     */
    public CriteriaUtil(Class<T> tipo) {
        this.tipo = tipo;
    }

    /**
     * @param criterios
     * @return
     */
    public int rowsCountByCriteria(List<CriterioBusqueda> criterios) {
        return this.rowsCount(this.getCriteria(criterios));
    }

    /**
     * Determina el n˙mero de registros en el resultado de acuerdo a un criterio
     * dado
     *
     * @param criteria
     * @return n˙mero de filas
     */
    private int rowsCount(Criteria criteria) {
        try {
            criteria.setProjection(Projections.rowCount());

            int rows = ((Integer) criteria.list().get(0));
            return rows;
        } catch (HibernateException e) {
            System.out.println("rows count criteria:" + e.getMessage());
        }
        return 0;
    }

    /**
     * @param criterios
     * @return
     */
    public List<T> getByCriteria(List<CriterioBusqueda> criterios) {
        return executeCriteria(this.getCriteria(criterios));
    }

    /**
     * Ejecuta una consulta basada en un criterio.
     *
     * @return resultado de la consulta
     */
    private List<T> executeCriteria(Criteria criteria) {
        System.out.println("Ejecuta criterio");
        List<T> resultSet;
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        s.beginTransaction();
        resultSet = (List<T>) criteria.list();
        HibernateUtil.closeSession();
        return resultSet;
    }

    /**
     * @param criterios
     * @return
     */
    private Criteria getCriteria(List<CriterioBusqueda> criterios) {
        System.out.println("Ejecuta getcriterio");
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();

        s.beginTransaction();
        Criteria criteria = s.createCriteria(tipo).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<String> listaAlias = new ArrayList<>();

        for (CriterioBusqueda criterio : criterios) {
            switch (criterio.getTypeCriterio()) {
                case OrProperty:
                    getCriteriaForOrPropertyType(criteria, criterio, listaAlias);
                    break;
                case OrBetween:
                    try {
                        getCriteriaForOrBetweenType(criteria, criterio, listaAlias);
                    } catch (Exception e) {
                        //Do something
                    }
                    break;
                case OrList:
                    getCriteriaForOrListType(criteria, criterio, listaAlias);
                    break;
                case Like:
                    getCriteriaForLikeType(criteria, criterio, listaAlias, true);
                    break;
                case NotLike:
                    getCriteriaForLikeType(criteria, criterio, listaAlias, false);
                    break;
                case Between:
                    try {
                        getCriteriaForBetweenType(criteria, criterio, listaAlias);
                    } catch (Exception e) {
                        //Do something
                    }
                    break;
                case NotNull:
                    getCriteriaForNotNullType(criteria, criterio, listaAlias);
                    break;
                case Equal:
                    getCriteriaForEqualType(criteria, criterio, listaAlias);
                    break;
                case Order:
                    getCriteriaForOrderType(criteria, criterio, listaAlias);
                    break;
                default:
                    break;

            }
        }

        return criteria;
    }

    /**
     * Method auxiliar para llenar la lista de criterios cuando el tipo de
     * criterio de b˙squeda es "OrProperty"
     *
     * @param c Criteria a generar
     * @param criterio Criterios de b˙squeda
     * @param listaAlias Alias de los criterios
     */
    private void getCriteriaForOrPropertyType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        List<String> singleProperties = getSingleProperties(c, criterio, listaAlias);
        c.add(getInnerExpression(singleProperties, criterio.getValor()));
    }

    private void getCriteriaForOrBetweenType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias)
            throws Exception {
        List<String> singleProperties = getSingleProperties(c, criterio, listaAlias);
        Object from;
        Object to;
        List<Date> rangoFechas = (ArrayList<Date>) criterio.getValor();
        from = rangoFechas.get(0);
        to = rangoFechas.get(1);
        c.add(Restrictions.or(
                Restrictions.between(
                        singleProperties.get(0), from, to),
                Restrictions.between(
                        singleProperties.get(1), from, to)));

    }

    private void getCriteriaForOrListType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        if (criterio.getProperty().contains(".")) {
            String propiedad = criterio.getProperty();
            String prop = getSingleProperty(c, propiedad, listaAlias);
            c.add(Restrictions.in(prop, (List<Object>) criterio.getValor()));
        } else {
            c.add(Restrictions.in(criterio.getProperty(),
                    (List<Object>) criterio.getValor()));
        }
    }

    /**
     * @param c
     * @param criterio
     * @param listaAlias
     * @param like true indica tipo Like, false indica Not Like
     */
    private void getCriteriaForLikeType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias, boolean like) {
        String prop = criterio.getProperty();
        if (prop.contains(".")) {
            prop = getSingleProperty(c, prop, listaAlias);
        }
        if (like) {
            if (criterio.getValor() instanceof String) {
                c.add(Restrictions.like(prop, criterio.getValor()).ignoreCase());
            } else {
                c.add(Restrictions.like(prop, criterio.getValor()));
            }
        } else {
            if (criterio.getValor() instanceof String) {
                c.add(Restrictions.not(
                        Restrictions.like(
                                prop, criterio.getValor()).
                                ignoreCase()));
            } else {
                c.add(Restrictions.not(
                        Restrictions.like(
                                prop, criterio.getValor())));
            }
        }

    }

    private void getCriteriaForBetweenType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias)
            throws Exception {
        Object from;
        Object to;

        List<Object> values = (List<Object>) criterio.getValor();
        if (values.get(0) instanceof Integer) {
            List<Integer> rangoEnteros = (ArrayList<Integer>) criterio.getValor();
            from = rangoEnteros.get(0);
            to = rangoEnteros.get(1);

        } else {
            List<Date> rangoFechas = (ArrayList<Date>) criterio.getValor();
            from = rangoFechas.get(0);
            to = rangoFechas.get(1);
        }

        if (criterio.getProperty().contains(".")) {
            String propiedad = criterio.getProperty();
            String prop = getSingleProperty(c, propiedad, listaAlias);
            c.add(Restrictions.between(prop, from, to));
        } else {
            c.add(Restrictions.between(criterio.getProperty(), from, to));

        }

    }

    private void getCriteriaForNotNullType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        String propiedad = criterio.getProperty();
        if (propiedad.contains(".")) {
            String prop = getSingleProperty(c, propiedad, listaAlias);
            c.add(Restrictions.isNotNull(prop));
        } else {
            c.add(Restrictions.isNotNull(propiedad));
        }
    }

    /**
     * @param c
     * @param criterio
     * @param listaAlias
     */
    private void getCriteriaForEqualType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        String propiedad = criterio.getProperty();
        if (propiedad.contains(".")) {
            String prop = getSingleProperty(c, propiedad, listaAlias);
            c.add(Restrictions.eq(prop, criterio.getValor()));
        } else {
            c.add(Restrictions.eq(propiedad, criterio.getValor()));
        }
    }

    /**
     * @param c
     * @param criterio
     * @param listaAlias
     */
    private void getCriteriaForOrderType(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        String propiedad = criterio.getProperty();
        if (propiedad.contains(".")) {
            String alias = propiedad.split("[.]")[0];
            if (!listaAlias.contains(alias)) {
                c.createAlias(alias, alias);
                listaAlias.add(alias);
            }
        }
        if (criterio.getValor().toString().compareTo("asc") == 0) {
            c.addOrder(Order.asc(propiedad));
        } else {
            c.addOrder(Order.desc(propiedad));
        }
    }

    private ArrayList<String> getSingleProperties(Criteria c, CriterioBusqueda criterio, List<String> listaAlias) {
        List<String> propiedades = criterio.getProperties();
        ArrayList<String> singleProperties = new ArrayList<>();

        for (String property : propiedades) {
            singleProperties.add(getSingleProperty(c, property,
                    listaAlias));
        }
        return singleProperties;
    }

    private String getSingleProperty(Criteria criteria, String property,
            List<String> aleas_list) {
        String alias = "";
        String alias_value = "";

        int size = property.split("[.]").length;

        for (int i = 0; i < size - 1; i++) {
            alias_value = property.split("[.]")[i];

            alias += (i > 0) ? "." + alias_value : alias_value;

            if (aleas_list.contains(alias) == false) {
                criteria.createAlias(alias, alias_value);
                aleas_list.add(alias);
            }
        }

        return alias_value + "." + property.split("[.]")[size - 1];
    }

    private Criterion getInnerExpression(List<String> properties, Object value) {
        if (properties.size() < 2) // condiciÛn de seguridad
        {
            return null;
        }

        if (properties.size() == 2) {
            if (value instanceof String) {
                return Restrictions.or(Restrictions.like(properties.get(0), value)
                        .ignoreCase(), Restrictions
                                .like(properties.get(1), value).ignoreCase());
            } else {
                return Restrictions.or(Restrictions.like(properties.get(0), value),
                        Restrictions.like(properties.get(1), value));
            }

        } else {
            int last_index = properties.size() - 1;

            String property = properties.get(last_index);

            properties.remove(last_index);

            if (value.getClass() == String.class) {
                return Restrictions.or(Restrictions.like(property, value)
                        .ignoreCase(), getInnerExpression(properties, value));
            } else {
                return Restrictions.or(Restrictions.like(property, value),
                        getInnerExpression(properties, value));
            }

        }
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipoClass(Class<T> tipo) {
        this.tipo = tipo;
    }
}
