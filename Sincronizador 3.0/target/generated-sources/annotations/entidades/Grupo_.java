package entidades;

import entidades.Planestudio;
import entidades.UnidadaprendizajeImparteProfesor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, Integer> gPOid;
    public static volatile ListAttribute<Grupo, UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    public static volatile SingularAttribute<Grupo, Integer> gPOnumero;
    public static volatile SingularAttribute<Grupo, Planestudio> planEstudioPESid;

}