package entidades;

import entidades.Configuracion;
import entidades.ProfesorPerteneceProgramaeducativo;
import entidades.Unidadaprendizaje;
import entidades.UnidadaprendizajeImparteProfesor;
import entidades.UnidadaprendizajeTieneContenidotematico;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Cicloescolar.class)
public class Cicloescolar_ { 

    public static volatile ListAttribute<Cicloescolar, Unidadaprendizaje> unidadaprendizajeList;
    public static volatile ListAttribute<Cicloescolar, Configuracion> configuracionList;
    public static volatile ListAttribute<Cicloescolar, UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList;
    public static volatile SingularAttribute<Cicloescolar, String> cEScicloEscolar;
    public static volatile ListAttribute<Cicloescolar, UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    public static volatile SingularAttribute<Cicloescolar, Integer> cESid;
    public static volatile ListAttribute<Cicloescolar, ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;

}