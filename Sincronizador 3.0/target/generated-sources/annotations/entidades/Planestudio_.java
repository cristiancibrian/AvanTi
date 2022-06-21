package entidades;

import entidades.Areaconocimiento;
import entidades.Grupo;
import entidades.Programaeducativo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Planestudio.class)
public class Planestudio_ { 

    public static volatile ListAttribute<Planestudio, Grupo> grupoList;
    public static volatile SingularAttribute<Planestudio, Programaeducativo> programaEducativoPEDid;
    public static volatile SingularAttribute<Planestudio, Integer> pESid;
    public static volatile SingularAttribute<Planestudio, Integer> pEScreditosObligatorios;
    public static volatile SingularAttribute<Planestudio, Integer> pEStotalCreditos;
    public static volatile SingularAttribute<Planestudio, String> pESvigenciaPlan;
    public static volatile ListAttribute<Planestudio, Areaconocimiento> areaconocimientoList;
    public static volatile SingularAttribute<Planestudio, Integer> pEScreditosOptativos;

}