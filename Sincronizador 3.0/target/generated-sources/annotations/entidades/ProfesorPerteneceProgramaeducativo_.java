package entidades;

import entidades.Cicloescolar;
import entidades.Profesor;
import entidades.ProfesorPerteneceProgramaeducativoPK;
import entidades.Programaeducativo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(ProfesorPerteneceProgramaeducativo.class)
public class ProfesorPerteneceProgramaeducativo_ { 

    public static volatile SingularAttribute<ProfesorPerteneceProgramaeducativo, Cicloescolar> cicloescolar;
    public static volatile SingularAttribute<ProfesorPerteneceProgramaeducativo, ProfesorPerteneceProgramaeducativoPK> profesorPerteneceProgramaeducativoPK;
    public static volatile SingularAttribute<ProfesorPerteneceProgramaeducativo, Programaeducativo> programaeducativo;
    public static volatile SingularAttribute<ProfesorPerteneceProgramaeducativo, Profesor> profesor;

}