package entidades;

import entidades.Areaconocimiento;
import entidades.Cicloescolar;
import entidades.Coordinadorareaadministrativa;
import entidades.UnidadaprendizajeImparteProfesor;
import entidades.UnidadaprendizajeTieneContenidotematico;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Unidadaprendizaje.class)
public class Unidadaprendizaje_ { 

    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasCampo;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPclave;
    public static volatile SingularAttribute<Unidadaprendizaje, String> uAPnombre;
    public static volatile ListAttribute<Unidadaprendizaje, UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasClase;
    public static volatile SingularAttribute<Unidadaprendizaje, Cicloescolar> cicloEscolarCESid;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasTaller;
    public static volatile ListAttribute<Unidadaprendizaje, Areaconocimiento> areaconocimientoList;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPid;
    public static volatile SingularAttribute<Unidadaprendizaje, String> uAPetapaFormacion;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasClinica;
    public static volatile ListAttribute<Unidadaprendizaje, UnidadaprendizajeTieneContenidotematico> unidadaprendizajeTieneContenidotematicoList;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPcreditos;
    public static volatile ListAttribute<Unidadaprendizaje, Coordinadorareaadministrativa> coordinadorareaadministrativaList;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasLaboratorio;
    public static volatile SingularAttribute<Unidadaprendizaje, Integer> uAPhorasExtraClase;
    public static volatile SingularAttribute<Unidadaprendizaje, String> uAPtipoCaracter;

}