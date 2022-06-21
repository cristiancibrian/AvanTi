package entidades;

import entidades.Permiso;
import entidades.Rol;
import entidades.RolHasPermisoPK;
import entidades.Subpermisos;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(RolHasPermiso.class)
public class RolHasPermiso_ { 

    public static volatile SingularAttribute<RolHasPermiso, RolHasPermisoPK> rolHasPermisoPK;
    public static volatile SingularAttribute<RolHasPermiso, Permiso> permiso;
    public static volatile SingularAttribute<RolHasPermiso, Subpermisos> subpermisos;
    public static volatile SingularAttribute<RolHasPermiso, Rol> rol;

}