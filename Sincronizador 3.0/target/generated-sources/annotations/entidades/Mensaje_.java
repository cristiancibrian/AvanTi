package entidades;

import entidades.Alerta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Mensaje.class)
public class Mensaje_ { 

    public static volatile SingularAttribute<Mensaje, Integer> mENid;
    public static volatile SingularAttribute<Mensaje, Alerta> alertaALEid;
    public static volatile SingularAttribute<Mensaje, String> mENmensaje;

}