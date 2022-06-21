package entidades;

import entidades.Subtemaunidad;
import entidades.Unidad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Temaunidad.class)
public class Temaunidad_ { 

    public static volatile SingularAttribute<Temaunidad, Float> tUNhoras;
    public static volatile SingularAttribute<Temaunidad, Boolean> tUNhorasCompletas;
    public static volatile SingularAttribute<Temaunidad, Integer> tUNid;
    public static volatile SingularAttribute<Temaunidad, String> tUNnombre;
    public static volatile SingularAttribute<Temaunidad, Float> tUNvalorPorcentaje;
    public static volatile ListAttribute<Temaunidad, Subtemaunidad> subtemaunidadList;
    public static volatile SingularAttribute<Temaunidad, Unidad> unidadUNIid;
    public static volatile SingularAttribute<Temaunidad, String> tUNnumero;

}