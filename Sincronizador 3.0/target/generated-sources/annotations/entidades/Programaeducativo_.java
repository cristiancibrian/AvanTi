package entidades;

import entidades.Areaadministrativa;
import entidades.Planestudio;
import entidades.Profesor;
import entidades.ProfesorPerteneceProgramaeducativo;
import entidades.Unidadacademica;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Programaeducativo.class)
public class Programaeducativo_ { 

    public static volatile SingularAttribute<Programaeducativo, Integer> pEDid;
    public static volatile ListAttribute<Programaeducativo, Areaadministrativa> areaadministrativaList;
    public static volatile SingularAttribute<Programaeducativo, Unidadacademica> unidadAcademicaUACid;
    public static volatile SingularAttribute<Programaeducativo, String> pEDnombre;
    public static volatile SingularAttribute<Programaeducativo, Integer> pEDclave;
    public static volatile ListAttribute<Programaeducativo, Planestudio> planestudioList;
    public static volatile ListAttribute<Programaeducativo, Profesor> profesorList;
    public static volatile ListAttribute<Programaeducativo, ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;

}