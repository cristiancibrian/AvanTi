package entidades;

import entidades.AlumnoPerteneceGrupo;
import entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Alumno.class)
public class Alumno_ { 

    public static volatile SingularAttribute<Alumno, String> aLNombre;
    public static volatile SingularAttribute<Alumno, String> aLApellidoPater;
    public static volatile SingularAttribute<Alumno, String> aLCorreo;
    public static volatile ListAttribute<Alumno, AlumnoPerteneceGrupo> alumnoPerteneceGrupoList;
    public static volatile SingularAttribute<Alumno, String> aLApellidoMaterno;
    public static volatile SingularAttribute<Alumno, Usuario> usuarioUSUid;
    public static volatile SingularAttribute<Alumno, Integer> aLId;
    public static volatile SingularAttribute<Alumno, String> aLMatricula;

}