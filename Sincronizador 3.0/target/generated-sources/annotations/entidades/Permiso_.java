package entidades;

import entidades.RolHasPermiso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Permiso.class)
public class Permiso_ { 

    public static volatile SingularAttribute<Permiso, Integer> pERid;
    public static volatile SingularAttribute<Permiso, String> pERtipo;
    public static volatile ListAttribute<Permiso, RolHasPermiso> rolHasPermisoList;
    public static volatile SingularAttribute<Permiso, Integer> pERvalor;

}