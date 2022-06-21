package dao;

import clases.AbstractDAO;
import entidades.Campus;

/**
 * @author Paul Miranda
 */
public class CampusDAO extends AbstractDAO<Integer, Campus> {
    public Campus getCampusByID(int id) {
        Campus result = null;
        result = this.executeQueryUnique("Select * from Campus where CAMid = '" + id + "' ");
        return result;
    }
}
