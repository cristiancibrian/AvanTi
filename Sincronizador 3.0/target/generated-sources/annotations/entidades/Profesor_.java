package entidades;

import entidades.Areaconocimiento;
import entidades.Coordinadorareaadministrativa;
import entidades.ProfesorPerteneceProgramaeducativo;
import entidades.Programaeducativo;
import entidades.Unidadacademica;
import entidades.UnidadaprendizajeImparteProfesor;
import entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Profesor.class)
public class Profesor_ { 

    public static volatile ListAttribute<Profesor, Unidadacademica> unidadacademicaList;
    public static volatile SingularAttribute<Profesor, String> pROrfc;
    public static volatile ListAttribute<Profesor, UnidadaprendizajeImparteProfesor> unidadaprendizajeImparteProfesorList;
    public static volatile ListAttribute<Profesor, Areaconocimiento> areaconocimientoList;
    public static volatile SingularAttribute<Profesor, Integer> pROnumeroEmpleado;
    public static volatile ListAttribute<Profesor, Programaeducativo> programaeducativoList;
    public static volatile SingularAttribute<Profesor, String> pROnombre;
    public static volatile SingularAttribute<Profesor, String> pROapellidoPaterno;
    public static volatile ListAttribute<Profesor, Coordinadorareaadministrativa> coordinadorareaadministrativaList;
    public static volatile SingularAttribute<Profesor, Integer> pROid;
    public static volatile SingularAttribute<Profesor, Usuario> usuarioUSUid;
    public static volatile SingularAttribute<Profesor, String> pROapellidoMaterno;
    public static volatile ListAttribute<Profesor, ProfesorPerteneceProgramaeducativo> profesorPerteneceProgramaeducativoList;

}