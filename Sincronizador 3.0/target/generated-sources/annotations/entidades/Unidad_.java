package entidades;

import entidades.Temaunidad;
import entidades.UnidadaprendizajeTieneContenidotematico;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Unidad.class)
public class Unidad_ { 

    public static volatile ListAttribute<Unidad, Temaunidad> temaunidadList;
    public static volatile SingularAttribute<Unidad, Float> uNIhoras;
    public static volatile SingularAttribute<Unidad, Boolean> uNIhorasCompletas;
    public static volatile SingularAttribute<Unidad, String> uNInombre;
    public static volatile SingularAttribute<Unidad, Float> uNIvalorPorcentaje;
    public static volatile SingularAttribute<Unidad, UnidadaprendizajeTieneContenidotematico> contenidoTematicoCTid;
    public static volatile SingularAttribute<Unidad, Integer> uNIid;
    public static volatile SingularAttribute<Unidad, Integer> uNInumero;

}