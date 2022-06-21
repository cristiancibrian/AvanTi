package entidades;

import entidades.Reporte;
import entidades.UnidadaprendizajeImparteProfesor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Reporteavancecontenidotematico.class)
public class Reporteavancecontenidotematico_ { 

    public static volatile SingularAttribute<Reporteavancecontenidotematico, String> rACnumero;
    public static volatile SingularAttribute<Reporteavancecontenidotematico, Date> rACfechaElaboracion;
    public static volatile SingularAttribute<Reporteavancecontenidotematico, String> rACstatus;
    public static volatile SingularAttribute<Reporteavancecontenidotematico, Float> rACavanceGlobal;
    public static volatile SingularAttribute<Reporteavancecontenidotematico, UnidadaprendizajeImparteProfesor> unidadAprendizajeimparteprofesorUIPid;
    public static volatile SingularAttribute<Reporteavancecontenidotematico, Integer> rACid;
    public static volatile ListAttribute<Reporteavancecontenidotematico, Reporte> reporteList;

}