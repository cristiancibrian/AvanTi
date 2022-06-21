package entidades;

import entidades.Alerta;
import entidades.Calendarioreporte;
import entidades.Cicloescolar;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(Configuracion.class)
public class Configuracion_ { 

    public static volatile SingularAttribute<Configuracion, Integer> cONid;
    public static volatile ListAttribute<Configuracion, Calendarioreporte> calendarioreporteList;
    public static volatile SingularAttribute<Configuracion, Alerta> alertaALEid;
    public static volatile SingularAttribute<Configuracion, Date> cONfechaInicioSemestre;
    public static volatile SingularAttribute<Configuracion, Cicloescolar> cicloEscolarCESid;
    public static volatile SingularAttribute<Configuracion, Integer> cONnumeroSemanas;

}