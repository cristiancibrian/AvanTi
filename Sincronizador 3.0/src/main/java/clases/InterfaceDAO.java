/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.List;

/**
 * @author laboratorio
 */
public interface InterfaceDAO<T> {

    public void save(T t);

    public void update(T t);

    public void delete(T t);

    public T find(Integer id);

    public List<T> findAll();

    public void saveOrUpdate(T instance);

    public List<T> executeQuery(String query);

    public List<Object[]> executeQueryObjects(String query);

    public T executeQueryUnique(String query);

    public void executeUpdate(String query);

    public List<Object[]> executeNoEntity(String query);

    public T executeQueryNoEntityUnique(String query);

    public List<T> executeQueryNoEntity(String query);

    public T findByOneParameterUnique(String value, String identificador);

    public List<T> findByOneParameter(String value, String identificador);

    public List<T> findByOneNotParameter(String value, String identificador);

    public List<T> findByOneParameterOrder(String value, String identificador, Boolean asc);

    public List<T> executeProcedure(String procedure, String[] names, String... param);

    public T executeProcedureOne(String procedure, String[] names, String... param);

    public int executeProcedureInt(String procedure, String[] names, String... param);

    public int executeCountQuery(String query);

    public int executeCountQueryDouble(String query);

    public int executeCountQueryFloat(String query);

    public List<T> executeTransformationQuery(String query, String... param);

    public T executeTransformationUniqueQuery(String query, String... param);

    public List<T> executeQueryHql(String query);

    public List<T> findFromWhereB(String de, String campo, String criterio, String order);

    public List<T> findFromWhere(String de, String campo, String criterio);

    public List<T> findFromWhereDoble(String de, String campo, String criterio, String de2, String campo2, String criterio2, String order);

    public List<T> findFromWheres(String clase, String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order);

    public List<T> findFromWhere(String clase, String de, String campo, String criterio);

    public List<T> findFromWhereNoDistinct(String clase, String de, String campo, String criterio);

    public List findFromWhereSimple2(String clase, String campo, String criterio);

    public List<T> findFromWhereSimple(String clase, String campo, String criterio, String campo2, String criterio2, String criterio3, String campo3);

    public List<T> findWhere(String clase, String campo, String criterio, String orden);

    public List<T> findWhereExtra(String campo, String criterio, String clase, String orden);

    public List<T> findFromWhereTriple2(String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order);

    public List<T> findFromWhereTriple(String de, String de2, String campo2, String criterio2, String de3, String campo3, String criterio3, String order);

    public T ExecuteQueryHqlUnitario(String query1);

    public List<T> findAllOrderBy2(String orderBy);

    public T findMultipleID(int id, String criterio, String campo_id, String order);

    public T findMultipleIDExtra(int id, String criterio, String campo_id, String order, String clase);

    public List<T> findFromDoubleWhereQuadraJoin(String de, String de2, String de3, String de4, String campo1, String criterio1, String campo2, String criterio2);

    public List<T> findFromDoubleWhereTripleJoin(String de, String de2, String de3, String campo1, String criterio1, String campo2, String criterio2);

    public List<T> findFromWhereB2(String de, String campo, String criterio, String order);

    public List<T> findPlanesWherePE(int idProgramaeducativo);

    public List<T> findPlanesWherePuesto(String de, String igualdad, String campo, String criterio);

    public List<T> findFromWhereBExtra(String de, String campo, String criterio, String order, String clase);

    public void deleteWhere(String campo, String criterio);

    public List<T> findFromWhereUnic(String de, String campo, String criterio, String clase);

    public List<T> findall(String clase);

    public void deleteWhere(String clase, String campo, String criterio);

    public void deleteUnidad(T obj);

    public int count(String clase, String campo, String criterio);

    public List<T> findFromWhereDobleJ(String de, String campo, String criterio, String de2, String campo2, String criterio2);
//TODO: Es posible agregar los métodos para el Criteria

}