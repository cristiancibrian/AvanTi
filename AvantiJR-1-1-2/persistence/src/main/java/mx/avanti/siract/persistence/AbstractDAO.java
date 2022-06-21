package mx.avanti.siract.persistence;

/**
 *
 * @author saitbc
 */
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;

/**
 * Generic abstract class avoid to extends another DAO for make generic methods
 * with the other DAO
 *
 * @param <PK>
 * @param <T>
 */
public abstract class AbstractDAO<PK extends Serializable, T> implements InterfaceDAO<T> {

    private Class<T> entityClass;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public AbstractDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public void saveOrUpdate(T obj) {
        System.out.println("SaveOrUpdate ----------");

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().saveOrUpdate(obj);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }
    
    

    @Override
    public void save(T obj) {
        System.out.println("Save ----------");

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().save(obj);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void update(T obj) {
        System.out.println("update ----------");

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().update(obj);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public void delete(T obj) {
        System.out.println("Delete ----------");

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            HibernateUtil.getSession().delete(obj);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public T find(Integer id) {
        System.out.println("Find ----------");
        T obj = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            obj = (T) HibernateUtil.getSession().get(entityClass, id);
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return obj;
    }

    @Override
    public List<T> findAll() {
        System.out.println("FindAll ----------");
        List<T> objects = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            objects = HibernateUtil.getSession().createCriteria(entityClass).list();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return objects;
    }

    @Override
    public List<T> executeQuery(String query) {
        System.out.println("ExecuteQuery ----------");
        List<T> result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            result = HibernateUtil.getSession().createSQLQuery(query).addEntity(entityClass.getCanonicalName()).list();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<Object[]> executeQueryObjects(String query) {
        System.out.println("ExecuteQueryObjects ----------");
        List<Object[]> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createSQLQuery(query).list();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public T findByOneParameterUnique(String value, String identificador) {
        System.out.println("FindByOneParameterUnique ----------");
        T result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            String nombre = entityClass.getSimpleName();
            result = (T) HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id").setString("id", String.valueOf(value)).uniqueResult();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findByOneParameter(String value, String identificador) {
        System.out.println("FindByOneParameter ----------");
        List<T> result = null;
        String nombre = entityClass.getSimpleName();

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            result = HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id").setString("id", String.valueOf(value)).list();
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findByOneParameterOrder(String value, String identificador, Boolean asc) {
        System.out.println("FindByOneParameterOrder ----------");
        List<T> result = null;
        String nombre = entityClass.getSimpleName();

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            ClassMetadata catMeta = HibernateUtil.getSession().getSessionFactory().getClassMetadata(entityClass);

            if (asc) {
                result = HibernateUtil.getSession().createQuery("from " + nombre
                        + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id order by " + catMeta.getIdentifierPropertyName() + " asc").setString("id", String.valueOf(value)).list();
            } else {
                result = HibernateUtil.getSession().createQuery("from " + nombre
                        + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id order by " + catMeta.getIdentifierPropertyName() + " desc").setString("id", String.valueOf(value)).list();
            }
        } catch (HibernateException ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    //Procedures
    @Override
    public List<T> executeProcedure(String procedure, String[] names, String... param) {
        System.out.println("ExecuteProcedure ----------");
        List<T> result;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(entityClass);

        for (int i = 0; i < param.length; i++) {
            lQuery.setParameter(names[i], param[i]);
        }

        result = lQuery.list();
        return result;
    }

    @Override
    public T executeProcedureOne(String procedure, String[] names, String... param) {
        System.out.println("ExecuteProcedureOne ----------");
        T result;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(entityClass);

        for (int i = 0; i < param.length; i++) {
            lQuery.setParameter(names[i], param[i]);
        }

        result = (T) lQuery.uniqueResult();
        return result;
    }

    @Override
    public int executeCountQuery(String query) {
        System.out.println("ExecuteCountQuery ----------");
        int result = 0;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            result = ((BigInteger) HibernateUtil.getSession().createSQLQuery(query).uniqueResult()).intValue();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> executeTransformationQuery(String query, String... param) {
        System.out.println("transformation query ----------");
        List<T> result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(query);

            for (String param1 : param) {
                lQuery.addScalar(param1);
            }

            result = lQuery.setResultTransformer(Transformers.aliasToBean(entityClass)).list();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> executeQueryHql(String query) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery(query).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereB(String de, String campo, String criterio, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a." + de + "." + campo + "=" + criterio + " order by a." + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public List<T> findWhereExtra(String campo, String criterio, String clase, String orden) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + clase + " a where a." + campo + "=" + criterio + " ORDER BY length(" + orden + ")," + orden + " ASC ").list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findWhere(String clase, String campo, String criterio, String orden) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + clase + " where a." + campo + "='" + criterio + "' ORDER BY " + orden + " ASC ").list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereNoDistinct(String clase, String de, String campo, String criterio) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a  from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhere(String clase, String de, String campo, String criterio) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWheres(String clase, String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + clase + " a join a." + de + " where b." + de2 + "." + campo2 + "=" + criterio2 + " and " + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereDoble(String de, String campo, String criterio, String de2, String campo2, String criterio2, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where a." + de2 + "." + campo2 + "=" + criterio2 + " order by " + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereDobleJ(String de, String campo, String criterio, String de2, String campo2, String criterio2) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getName() + " a join " + de + " b on a.PROid=b.Profesor_PROid join " + de2
                    + " c on b.ProgramaEducativo_PEDid = c.ProgramaEducativo_PEDid where b." + campo + "=" + criterio + " and c." + campo2 + "=" + criterio2).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhere(String de, String campo, String criterio) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from " + this.entityClass.getCanonicalName() + " a join a." + de + " b where b." + campo + "=" + criterio).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereTriple(String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where a." + de2 + "." + campo2 + "=" + criterio2 + " and a." + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereTriple2(String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where b." + de2 + "." + campo2 + "=" + criterio2 + " and a." + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.err.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public T ExecuteQueryHqlUnitario(String query1) {
        T dto = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            Query query = HibernateUtil.getSession().createQuery(query1);
            dto = (T) query.setMaxResults(1).uniqueResult();
        } catch (RuntimeException ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return dto;
    }

    @Override
    public List<T> findAllOrderBy2(String orderBy) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("from " + this.entityClass.getCanonicalName() + " a order by a." + orderBy + " desc").list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public T findMultipleID(int id, String criterio, String campo_id, String order) {
        T dto = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();
        String nombre = this.entityClass.getSimpleName();

        try {
            String query1;

            if (criterio.isEmpty()) {
                query1 = "from " + this.entityClass.getCanonicalName() + " as " + nombre.toLowerCase() + " where " + campo_id + " = :id " + order;
            } else {
                query1 = "from " + this.entityClass.getCanonicalName() + " as " + nombre.toLowerCase() + " where " + criterio + " AND " + campo_id + " = :id " + order;
            }

            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setInteger("id", id);
            //SET MAX RESULT - RESTRINGE EL NUMERO DE RESULTADOS
            dto = (T) query.setMaxResults(1).uniqueResult();
        } catch (RuntimeException ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return dto;
    }

    @Override
    public T findMultipleIDExtra(int id, String criterio, String campo_id, String order, String clase) {
        T dto = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            String query1;

            if (criterio.isEmpty()) {
                query1 = "from " + clase + " as a where " + campo_id + " = :id " + order;

            } else {
                query1 = "from " + clase + " as a where " + criterio + " AND " + campo_id + " = :id " + order;
            }

            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setInteger("id", id);
            //SET MAX RESULT - RESTRINGE EL NUMERO DE RESULTADOS
            dto = (T) query.setMaxResults(1).uniqueResult();
        } catch (RuntimeException ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return dto;
    }

    @Override
    public List<T> findFromDoubleWhereQuadraJoin(String de, String de2, String de3, String de4, String campo1, String criterio1, String campo2, String criterio2) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b join a." + de2 + " c join c." + de3 + " d"
                    + " join d." + de4 + " where " + campo1 + "=" + criterio1 + " and " + campo2 + "=" + criterio2).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromDoubleWhereTripleJoin(String de, String de2, String de3, String campo1, String criterio1, String campo2, String criterio2) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b join a." + de2 + " c join c." + de3 + " d"
                    + " where " + campo1 + "=" + criterio1 + " and " + campo2 + "=" + criterio2).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereB2(String de, String campo, String criterio, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a." + de + "." + campo + "=" + criterio + " order by a." + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findPlanesWherePuesto(String de, String igualdad, String campo, String criterio) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a join a.programaeducativo." + de + " b where b.programaeducativo." + igualdad + "=a.programaeducativo." + igualdad + " and b.puesto." + campo + "=" + criterio).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findPlanesWherePE(int idProgramaeducativo) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a.programaeducativo.pedid=" + idProgramaeducativo).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereBExtra(String de, String campo, String criterio, String order, String clase) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select a from " + clase + " a where a." + de + "." + campo + "=" + criterio + " order by " + order).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public List<T> findFromWhereUnic(String de, String campo, String criterio, String clase) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beginTransaccion();

        try {
            result = HibernateUtil.getSession().createQuery("select distinct a from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return result;
    }

    @Override
    public int count(String clase, String campo, String criterio) {
        int resultado = 0;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(entityClass.getName());
        String identificador = catMeta.getIdentifierPropertyName();

        try {
            HibernateUtil.getSession();
            HibernateUtil.beginTransaccion();
            String query1 = "select count( distinct a." + identificador + ") from " + clase + " where " + campo + "=" + criterio;
            Query query = HibernateUtil.getSession().createQuery(query1);
            resultado = ((Number) query.uniqueResult()).intValue();
        } catch (Exception ex) {
            HibernateUtil.rollbackTransaction();
            System.out.println(ex);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }

        return resultado;
    }
}
