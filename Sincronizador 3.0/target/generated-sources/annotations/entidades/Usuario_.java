package entidades;

import entidades.Catalogoreportes;
import entidades.Profesor;
import entidades.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> uSUcontrasenia;
    public static volatile SingularAttribute<Usuario, Integer> uSUid;
    public static volatile ListAttribute<Usuario, Catalogoreportes> catalogoreportesList;
    public static volatile ListAttribute<Usuario, Rol> rolList;
    public static volatile ListAttribute<Usuario, Profesor> profesorList;
    public static volatile SingularAttribute<Usuario, String> uSUusuario;

}