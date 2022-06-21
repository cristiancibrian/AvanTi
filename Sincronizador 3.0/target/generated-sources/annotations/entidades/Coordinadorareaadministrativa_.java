package entidades;

import entidades.Areaadministrativa;
import entidades.Profesor;
import entidades.Unidadaprendizaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Coordinadorareaadministrativa.class)
public class Coordinadorareaadministrativa_ { 

    public static volatile ListAttribute<Coordinadorareaadministrativa, Unidadaprendizaje> unidadaprendizajeList;
    public static volatile SingularAttribute<Coordinadorareaadministrativa, Profesor> profesorPROid;
    public static volatile SingularAttribute<Coordinadorareaadministrativa, Integer> cOAid;
    public static volatile SingularAttribute<Coordinadorareaadministrativa, Areaadministrativa> areaAdministrativaAADid;

}