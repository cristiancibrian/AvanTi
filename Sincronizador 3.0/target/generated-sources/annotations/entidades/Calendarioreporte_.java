package entidades;

import entidades.CalendarioreporteTieneAlerta;
import entidades.Configuracion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Calendarioreporte.class)
public class Calendarioreporte_ { 

    public static volatile SingularAttribute<Calendarioreporte, Date> cREfechaCorte;
    public static volatile SingularAttribute<Calendarioreporte, Date> cREfechaLimite;
    public static volatile ListAttribute<Calendarioreporte, Configuracion> configuracionList;
    public static volatile ListAttribute<Calendarioreporte, CalendarioreporteTieneAlerta> calendarioreporteTieneAlertaList;
    public static volatile SingularAttribute<Calendarioreporte, Integer> cREid;

}