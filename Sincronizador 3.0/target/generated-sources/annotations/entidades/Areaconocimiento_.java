package entidades;

import entidades.Planestudio;
import entidades.Profesor;
import entidades.Unidadaprendizaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Areaconocimiento.class)
public class Areaconocimiento_ { 

    public static volatile ListAttribute<Areaconocimiento, Unidadaprendizaje> unidadaprendizajeList;
    public static volatile SingularAttribute<Areaconocimiento, Integer> aCOclave;
    public static volatile SingularAttribute<Areaconocimiento, String> aCOnombre;
    public static volatile ListAttribute<Areaconocimiento, Profesor> profesorList;
    public static volatile SingularAttribute<Areaconocimiento, Integer> aCOid;
    public static volatile SingularAttribute<Areaconocimiento, Planestudio> planEstudioPESid;

}