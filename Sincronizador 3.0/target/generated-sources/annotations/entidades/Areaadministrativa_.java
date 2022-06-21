package entidades;

import entidades.Coordinadorareaadministrativa;
import entidades.Programaeducativo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Areaadministrativa.class)
public class Areaadministrativa_ { 

    public static volatile SingularAttribute<Areaadministrativa, Programaeducativo> programaEducativoPEDid;
    public static volatile ListAttribute<Areaadministrativa, Coordinadorareaadministrativa> coordinadorareaadministrativaList;
    public static volatile SingularAttribute<Areaadministrativa, Integer> aADid;
    public static volatile SingularAttribute<Areaadministrativa, String> aADnombre;

}