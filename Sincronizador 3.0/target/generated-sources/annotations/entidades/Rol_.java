package entidades;

import entidades.RolHasPermiso;
import entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, Integer> rOLid;
    public static volatile ListAttribute<Rol, Usuario> usuarioList;
    public static volatile ListAttribute<Rol, RolHasPermiso> rolHasPermisoList;
    public static volatile SingularAttribute<Rol, String> rOLtipo;
    public static volatile SingularAttribute<Rol, Integer> rOLprioridad;

}