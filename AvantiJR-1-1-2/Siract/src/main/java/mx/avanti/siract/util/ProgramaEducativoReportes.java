package mx.avanti.siract.util;

import mx.avanti.siract.entity.Programaeducativo;

/**
 *
 * @author Alex
 */
public class ProgramaEducativoReportes {

    public int countEsperados;
    public int countEntregados;
    public Programaeducativo pe;
    public int countEntregadosRACT1;
    public int countEntregadosRACT2;
    public int countEntregadosRACT3;

    public ProgramaEducativoReportes(int countEsperados, int countEntregados, Programaeducativo pe) {
        this.countEsperados = countEsperados;
        this.countEntregados = countEntregados;
        this.pe = pe;
    }

    public ProgramaEducativoReportes() {
    }

    public int getCountEsperados() {
        return countEsperados;
    }

    public void setCountEsperados(int countEsperados) {
        this.countEsperados = countEsperados;
    }

    public int getCountEntregados() {
        return countEntregados;
    }

    public void setCountEntregados(int countEntregados) {
        this.countEntregados = countEntregados;
    }

    public Programaeducativo getPe() {
        return pe;
    }

    public void setPe(Programaeducativo pe) {
        this.pe = pe;
    }

    public int getCountEntregadosRACT1() {
        return countEntregadosRACT1;
    }

    public void setCountEntregadosRACT1(int countEntregadosRACT1) {
        this.countEntregadosRACT1 = countEntregadosRACT1;
    }

    public int getCountEntregadosRACT2() {
        return countEntregadosRACT2;
    }

    public void setCountEntregadosRACT2(int countEntregadosRACT2) {
        this.countEntregadosRACT2 = countEntregadosRACT2;
    }

    public int getCountEntregadosRACT3() {
        return countEntregadosRACT3;
    }

    public void setCountEntregadosRACT3(int countEntregadosRACT3) {
        this.countEntregadosRACT3 = countEntregadosRACT3;
    }
}
