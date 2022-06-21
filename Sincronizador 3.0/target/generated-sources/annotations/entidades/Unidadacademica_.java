package entidades;

import entidades.Campus;
import entidades.Profesor;
import entidades.Programaeducativo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Unidadacademica.class)
public class Unidadacademica_ { 

    public static volatile SingularAttribute<Unidadacademica, String> uACtipo;
    public static volatile ListAttribute<Unidadacademica, Programaeducativo> programaeducativoList;
    public static volatile SingularAttribute<Unidadacademica, Integer> uACclave;
    public static volatile SingularAttribute<Unidadacademica, String> uACnombre;
    public static volatile SingularAttribute<Unidadacademica, Campus> campusCAMid;
    public static volatile ListAttribute<Unidadacademica, Profesor> profesorList;
    public static volatile SingularAttribute<Unidadacademica, Integer> uACid;

}