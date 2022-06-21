package entidades;

import entidades.Cicloescolar;
import entidades.Grupo;
import entidades.Profesor;
import entidades.Reporteavancecontenidotematico;
import entidades.Unidadaprendizaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(UnidadaprendizajeImparteProfesor.class)
public class UnidadaprendizajeImparteProfesor_ { 

    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, String> uIPsubgrupo;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, String> uIPtipoSubgrupo;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, Profesor> profesorPROid;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, Integer> uIPid;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, Grupo> grupoGPOid;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, Unidadaprendizaje> unidadAprendizajeUAPid;
    public static volatile SingularAttribute<UnidadaprendizajeImparteProfesor, Cicloescolar> cicloEscolarCESid;
    public static volatile ListAttribute<UnidadaprendizajeImparteProfesor, Reporteavancecontenidotematico> reporteavancecontenidotematicoList;

}