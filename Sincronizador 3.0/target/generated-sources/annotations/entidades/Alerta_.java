package entidades;

import entidades.CalendarioreporteTieneAlerta;
import entidades.Configuracion;
import entidades.Mensaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Alerta.class)
public class Alerta_ { 

    public static volatile SingularAttribute<Alerta, String> aLEtipo;
    public static volatile ListAttribute<Alerta, Configuracion> configuracionList;
    public static volatile ListAttribute<Alerta, CalendarioreporteTieneAlerta> calendarioreporteTieneAlertaList;
    public static volatile ListAttribute<Alerta, Mensaje> mensajeList;
    public static volatile SingularAttribute<Alerta, String> aLEcolor;
    public static volatile SingularAttribute<Alerta, Integer> aLEid;

}