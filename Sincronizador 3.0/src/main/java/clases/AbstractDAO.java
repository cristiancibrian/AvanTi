package clases;

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
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().saveOrUpdate(obj);

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().save(obj);

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().update(obj);

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().delete(obj);

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public T find(Integer id) {
        System.out.println("Find ----------");
        Object obj = null;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(entityClass);
        String identificador = catMeta.getIdentifierPropertyName();

        String nombre = entityClass.getSimpleName();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            obj = HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id").setString("id", String.valueOf(id)).uniqueResult();
        } catch (Exception x) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return (T) obj;

    }

    @Override
    public List<T> findAll() {
        System.out.println("FindAll ----------");
        List<T> objects = null;
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Query query = HibernateUtil.getSession().createQuery("from " + entityClass.getName());
            objects = query.list();

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();
            //System.out.println("ABSTRACTDAO linea 161:"+entityClass.getCanonicalName());
            result = (List<T>)HibernateUtil.getSession().createSQLQuery(query).addEntity(entityClass.getCanonicalName()).list();
            
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        System.out.println("RESULTADO DE LA VARIABLE result EN ABSTRACTDAO 166:"+result);
        return result;
    }
    
    @Override
    public List<T> executeQueryHql(String query) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery(query).list();
            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = HibernateUtil.getSession().createSQLQuery(query).list();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public void executeUpdate(String query) {
        System.out.println("ExecuteUpdate ----------");
        // ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(getTipo());
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().createSQLQuery(query).executeUpdate();
        } catch (HibernateException x) {
             HibernateUtil.rollbackTransaction();
            System.out.println("" + x);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public T executeQueryUnique(String query) {
        System.out.println("ExecuteQueryUnique ----------");
        T result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = (T) HibernateUtil.getSession().createSQLQuery(query).addEntity(entityClass.getCanonicalName()).uniqueResult();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public T executeQueryNoEntityUnique(String query) {
        System.out.println("ExecuteQueryNoENtityUnique ----------");
        T result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = (T) HibernateUtil.getSession().createSQLQuery(query).uniqueResult();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public List<T> executeQueryNoEntity(String query) {
        System.out.println("ExecuteQueryNoEntity ----------");
        List<T> result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = HibernateUtil.getSession().createSQLQuery(query).list();
        } catch (HibernateException x) {

            System.out.println("" + x);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public List<Object[]> executeNoEntity(String query) {
        System.out.println("ExecuteNoEntity ----------");
        List<Object[]> result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = HibernateUtil.getSession().createSQLQuery(query).list();
        } catch (HibernateException x) {
            System.out.println("" + x);
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
            HibernateUtil.beingTransaccion();
            String nombre = entityClass.getSimpleName();
            result = (T) HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id").setString("id", String.valueOf(value)).uniqueResult();

        } catch (HibernateException x) {
            System.out.println("" + x);
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
            HibernateUtil.beingTransaccion();
            result = HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id").setString("id", String.valueOf(value)).list();
            System.err.println("Listo");
        } catch (HibernateException x) {
            System.out.println("" + x);
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public List<T> findByOneNotParameter(String value, String identificador) {
        System.out.println("FindByOneNotParameter ----------");
        List<T> result = null;
        String nombre = entityClass.getSimpleName();

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = HibernateUtil.getSession().createQuery("from " + nombre
                    + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " <> :id").setString("id", String.valueOf(value)).list();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();
            ClassMetadata catMeta = HibernateUtil.getSession().getSessionFactory().getClassMetadata(entityClass);
            if (asc) {
                result = HibernateUtil.getSession().createQuery("from " + nombre
                        + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id order by " + catMeta.getIdentifierPropertyName() + " asc").setString("id", String.valueOf(value)).list();
            } else {
                result = HibernateUtil.getSession().createQuery("from " + nombre
                        + " as " + nombre.toLowerCase() + " where " + nombre.toLowerCase() + "." + identificador + " = :id order by " + catMeta.getIdentifierPropertyName() + " desc").setString("id", String.valueOf(value)).list();

            }
            System.err.println("Listo");
        } catch (HibernateException x) {
            System.out.println("" + x);
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
        HibernateUtil.beingTransaccion();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(entityClass);
        for (int i = 0; i < param.length; i++) {
            lQuery.setParameter(names[i], param[i]);
        }
        result = lQuery.list();
        return result;
    }

//    public void executeProcedureCall() {
//        HibernateUtil.getSession();
//        HibernateUtil.beingTransaccion();
//
//        ProcedureCall call = HibernateUtil.getSession().createStoredProcedureCall("testing", Arts.class);
//        
//
//    }
    @Override
    public T executeProcedureOne(String procedure, String[] names, String... param) {
        System.out.println("ExecuteProcedureOne ----------");
        T result;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure).addEntity(entityClass);

        for (int i = 0; i < param.length; i++) {
            lQuery.setParameter(names[i], param[i]);
        }
        result = (T) lQuery.uniqueResult();
        return result;
    }

    @Override
    public int executeProcedureInt(String procedure, String[] names, String... param) {
        System.out.println("ExecuteProcedureInt ----------");
        BigInteger result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(procedure);
        for (int i = 0; i < param.length; i++) {
            lQuery.setParameter(names[i], param[i]);
        }
        result = (BigInteger) lQuery.uniqueResult();
        return result.intValue();
    }

    @Override
    public int executeCountQuery(String query) {
        System.out.println("ExecuteCountQuery ----------");
        BigInteger result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            result = (BigInteger) HibernateUtil.getSession().createSQLQuery(query).uniqueResult();
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result.intValue();
    }

    @Override
    public int executeCountQueryDouble(String query) {
        System.out.println("ExecuteCountQueryDouble ----------");
        int result = 0;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Double aux = (Double) HibernateUtil.getSession().createSQLQuery(query).uniqueResult();
            if (aux != null) {
                result = aux.intValue();
            }
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public int executeCountQueryFloat(String query) {
        System.out.println("ExecuteCOuntQueryFloat ----------");
        int result = 0;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            Float aux = (Float) HibernateUtil.getSession().createSQLQuery(query).uniqueResult();
            if (aux != null) {
                result = aux.intValue();
            }
        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    @Override
    public T executeTransformationUniqueQuery(String query, String... param) {
        System.out.println("transformation query ----------");
        T result = null;

        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(query);

            for (int i = 0; i < param.length; i++) {
                lQuery.addScalar(param[i]);
            }

            result = (T) lQuery.setResultTransformer(Transformers.aliasToBean(entityClass)).uniqueResult();

        } catch (HibernateException e) {
            HibernateUtil.rollbackTransaction();
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
            HibernateUtil.beingTransaccion();

            SQLQuery lQuery = HibernateUtil.getSession().createSQLQuery(query);

            for (int i = 0; i < param.length; i++) {
                lQuery.addScalar(param[i]);
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
    public List<T> findFromWhereB(String de, String campo, String criterio, String order) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a." + de + "." + campo + "=" + criterio + " order by a." + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
    
     public List<T> findWhereExtra(String campo, String criterio, String clase, String orden) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + clase + " a where a." + campo + "=" + criterio + " ORDER BY length(" + orden + ")," + orden + " ASC ").list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    public List<T> findWhere(String clase, String campo, String criterio, String orden) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + clase + " where a." + campo + "='" + criterio + "' ORDER BY " + orden + " ASC ").list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }

    public List<T> findFromWhereSimple(String clase, String campo, String criterio, String campo2, String criterio2, String criterio3, String campo3) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        try {
            result = (List<T>) HibernateUtil.getSession().createSQLQuery("select * from " + clase + " where " + campo + "='" + criterio + "' AND " + campo2 + "='" + criterio2 + "' AND " + criterio3 + "='" + campo3 + "'").list();
            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
    
    @Override
    public List findFromWhereSimple2(String clase, String campo, String criterio) {
        List result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        try {
            result = (List) HibernateUtil.getSession().createSQLQuery("select * from " + clase + " where " + campo + "='" + criterio + "'").list();
            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select a  from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + clase + " a join a." + de + " where b." + de2 + "." + campo2 + "=" + criterio2 + " and " + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b join b." + de2
                    + " c where b." + campo + "=" + criterio + " and c." + campo2 + "=" + criterio2 + " order by " + order).list();
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where a." + de2 + "." + campo2 + "=" + criterio2 + " order by " + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getName() + " a join " + de + " b on a.PROid=b.Profesor_PROid join " + de2
                    + " c on b.ProgramaEducativo_PEDid = c.ProgramaEducativo_PEDid where b." + campo + "=" + criterio + " and c." + campo2 + "=" + criterio2 ).list();
            

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from " + this.entityClass.getCanonicalName() + " a join a." + de + " b where b." + campo + "=" + criterio).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where a." + de2 + "." + campo2 + "=" + criterio2 + " and a." + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b where b." + de2 + "." + campo2 + "=" + criterio2 + " and a." + de3 + "." + campo3 + "=" + criterio3 + " order by " + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();
        //ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(getTipo());
        //String nombre = tipo.getSimpleName();
       //String identificador = catMeta.getIdentifierPropertyName();
        try {
        // String query1 = "select ce from Cicloescolar ce join ce.configuracions c join  c.calendarioreportes cr join cr.calendarioreporteTieneAlertas cta where c.confechaInicioSemestre<=current_date order by  cr.crefechaLimite desc";
            Query query = HibernateUtil.getSession().createQuery(query1);
            dto = (T) query.setMaxResults(1).uniqueResult();
        } catch (RuntimeException x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return dto;
    }
     
     public List<T> findAllOrderBy2(String orderBy) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("from " + this.entityClass.getCanonicalName() + " a order by a." + orderBy + " desc").list();
            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
     
      public T findMultipleID(int id, String criterio, String campo_id, String order) {
        T dto = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        //ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(getTipo());
       String nombre = this.entityClass.getSimpleName();
       //String identificador = catMeta.getIdentifierPropertyName();
        try {
//            String query1 = "from " + getTipo().getCanonicalName() + " as" + nombre.toLowerCase() + " where " + identificador + " = :id";
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
        } catch (RuntimeException x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return dto;
    }

    public T findMultipleIDExtra(int id, String criterio, String campo_id, String order, String clase) {
        T dto = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        //ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(getTipo());
        //String nombre = tipo.getSimpleName();
        //String identificador = catMeta.getIdentifierPropertyName();
        try {
//            String query1 = "from " + getTipo().getCanonicalName() + " as" + nombre.toLowerCase() + " where " + identificador + " = :id";
            String query1;
            if (criterio.isEmpty()) {
                query1 = "from " + clase + " as a where " + campo_id + " = :id " + order;

            } else {
                query1 = "from " + clase + " as a where " + criterio + " AND " + campo_id + " = :id " + order;
            }
            System.out.println("^^^^^^query: " + query1);

            Query query = HibernateUtil.getSession().createQuery(query1);
            query.setInteger("id", id);
            //SET MAX RESULT - RESTRINGE EL NUMERO DE RESULTADOS
            dto = (T) query.setMaxResults(1).uniqueResult();
        } catch (RuntimeException x) {
            x.printStackTrace();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b join a." + de2 + " c join c." + de3 + " d"
                    + " join d." + de4 + " where " + campo1 + "=" + criterio1 + " and " + campo2 + "=" + criterio2).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from "
                    + this.entityClass.getCanonicalName() + " a join a." + de + " b join a." + de2 + " c join c." + de3 + " d"
                    + " where " + campo1 + "=" + criterio1 + " and " + campo2 + "=" + criterio2).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {

            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a." + de + "." + campo + "=" + criterio + " order by a." + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a join a.programaeducativo." + de + " b where b.programaeducativo." + igualdad + "=a.programaeducativo." + igualdad + " and b.puesto." + campo + "=" + criterio).list();
            //result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+de+".pedid="+de+".pedid and a."+de+"."+campo+"="+criterio).list();
            System.out.println("PLAN: select a from " + entityClass.getCanonicalName() + " a join " + de + " b where b." + igualdad + "=a." + igualdad + " and b." + campo + "=" + criterio);

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + entityClass.getCanonicalName() + " a where a.programaeducativo.pedid=" + idProgramaeducativo).list();
            //result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+de+".pedid="+de+".pedid and a."+de+"."+campo+"="+criterio).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
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
        HibernateUtil.beingTransaccion();

        try {
//            result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+campo+
//                    "="+criterio).list();
            result = (List<T>) HibernateUtil.getSession().createQuery("select a from " + clase + " a where a." + de + "." + campo + "=" + criterio + " order by " + order).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
    @Override
    public void deleteWhere(String campo, String criterio) {
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
//            result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+campo+
//                    "="+criterio).list();
            HibernateUtil.getSession().createQuery("delete from " + entityClass.getCanonicalName() + " where " + campo + "=" + criterio).executeUpdate();
            HibernateUtil.commitTransaction();

        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }
    @Override
    public List<T> findFromWhereUnic(String de, String campo, String criterio, String clase) {
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
//            result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+campo+
//                    "="+criterio).list();

            result = (List<T>) HibernateUtil.getSession().createQuery("select distinct a from " + clase + " a join a." + de + " b where b." + campo + "=" + criterio).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return result;
    }
    @Override
      public List<T> findall(String clase) {
        // System.out.println("Consultando en: " + tipo.getSimpleName());
        List<T> result = null;
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
            result = (List<T>) HibernateUtil.getSession().createQuery("from " + entityClass.getName()).list();

            if (result == null) {
                System.out.println("nulo");
            }
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return (List<T>) result;
    }
   @Override   
   public void deleteWhere(String clase, String campo, String criterio) {
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();

        try {
//            result = (List<T>) HibernateUtil.getSession().createQuery("select a from "+getTipo().getCanonicalName()+" a where a."+campo+
//                    "="+criterio).list();
            HibernateUtil.getSession().createQuery("delete from " + clase + " a where a." + campo + "=" + criterio).executeUpdate();
            HibernateUtil.commitTransaction();
            HibernateUtil.getSession().flush();
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }
   @Override
   public void deleteUnidad(T obj) {
        System.out.println("Eliminado en: " + entityClass.getSimpleName());
        HibernateUtil.getSession();
        HibernateUtil.beingTransaccion();
        try {
            HibernateUtil.getSession().delete(obj);
        } catch (Exception x) {
            x.printStackTrace();
            HibernateUtil.rollbackTransaction();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }

    @Override
    public int count(String clase, String campo, String criterio) {
        int resultado = 0;
        ClassMetadata catMeta = HibernateUtil.getSessionFactory().getClassMetadata(entityClass.getName());
        String identificador = catMeta.getIdentifierPropertyName();
      // String nombre = tipo.getSimpleName();
        try {
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            String query1 = "select count( distinct a." + identificador + ") from " + clase + " where " + campo + "=" + criterio;
            System.out.println("******************************** " + query1);
            System.out.println("QUERY : "+query1);
            Query query = HibernateUtil.getSession().createQuery(query1);

            resultado = ((Number) query.uniqueResult()).intValue();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
        return resultado;
    }
}
