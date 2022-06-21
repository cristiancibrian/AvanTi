package entidades;

import entidades.Cicloescolar;
import entidades.Practicalaboratorio;
import entidades.Practicascampo;
import entidades.Practicasclinica;
import entidades.Practicataller;
import entidades.Unidad;
import entidades.Unidadaprendizaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-22T14:44:21")
@StaticMetamodel(UnidadaprendizajeTieneContenidotematico.class)
public class UnidadaprendizajeTieneContenidotematico_ { 

    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Boolean> uAPhorasCampoCompletas;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Integer> cTid;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Boolean> uAPhorasTallerCompletas;
    public static volatile ListAttribute<UnidadaprendizajeTieneContenidotematico, Practicataller> practicatallerList;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Boolean> uAPhorasLaboratorioCompletas;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Boolean> uAPhorasClaseCompletas;
    public static volatile ListAttribute<UnidadaprendizajeTieneContenidotematico, Practicascampo> practicascampoList;
    public static volatile ListAttribute<UnidadaprendizajeTieneContenidotematico, Practicalaboratorio> practicalaboratorioList;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Cicloescolar> cicloEscolarCESid;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Integer> version;
    public static volatile ListAttribute<UnidadaprendizajeTieneContenidotematico, Practicasclinica> practicasclinicaList;
    public static volatile SingularAttribute<UnidadaprendizajeTieneContenidotematico, Unidadaprendizaje> unidadAprendizajeUAPid;
    public static volatile ListAttribute<UnidadaprendizajeTieneContenidotematico, Unidad> unidadList;

}