package mx.avanti.siract.util;

import java.util.Comparator;

/**
 *
 * @author Alex
 */
public class SortbyPEClave implements Comparator<ProgramaEducativoReportes>{
    @Override
    public int compare(ProgramaEducativoReportes a, ProgramaEducativoReportes b){
        return a.getPe().getPEDclave()- b.getPe().getPEDclave();
    }
}
