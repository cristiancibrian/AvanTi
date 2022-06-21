/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 * @author balta
 */
public interface InterfaceDAO<T> {
    public void save(T t);

    public void delete(T t);

    public T find(int id);

    public List<T> findAll();

    public void saveOrUpdate(T instance);

    public List<T> executeQuery(String query);
}
