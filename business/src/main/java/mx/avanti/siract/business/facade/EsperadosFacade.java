/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.siract.business.facade;

import java.util.ArrayList;
import mx.avanti.business.delegate.DelegateEsperados;

/**
 *
 * @author Eduardo
 */
public class EsperadosFacade {
    private final DelegateEsperados delegate;
    
    public EsperadosFacade() {
        delegate = new DelegateEsperados();
    }
    
     //-----------------------------------------------AREA DE CONOCIMIENTO--------------------------------------//
    /***
     * Metodo que se utiliza para obtener la cantidad de reportes entregados por cada Área de conocimiento.
     * @param UnidadAcademica Id de la Unidad Academica
     * @param AC Lista de String de claves de Áreas de conocimientos 
     * @param PE Lista de Sring de claves de Plan estudio
     * @param PrE  Lista de Integers de de claves de Programas educativos
     * @param CE Lista de String de  ciclo escolar 
     * @return Regresa cantidad de Reportes entregados por area de conocimiento
     */
    public ArrayList<String> getAreaConocimientoEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE) {

        return delegate.consultaEntregadosAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
    }
    /**
     * Metodo que se utiliza para obtener la cantidad de reportes entregados por cada Área de conocimiento con un RACTnumero en especifico.
     * @param UnidadAcademica int del Id de la Unidad Academica
     * @param AC Lista de String de claves de Áreas de conocimientos 
     * @param PE Lista de Sring de claves de Plan estudio
     * @param PrE  Lista de Integers de de claves de Programas educativos
     * @param CE Lista de String de  ciclo escolar
     * @param num String del RACTnumero
     * @return Regresa lista de String de cantidad de Reportes entregados por area de conocimiento
     */
    public ArrayList<String> getAreaConocimientoEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String num) {

        return delegate.consultaEntregadosAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE, num);
    }
    /**
     * 
     * @param UnidadAcademica int del Id de la Unidad Academica
     * @param AC Lista de String de claves de Áreas de conocimientos 
     * @param PE Lista de Sring de claves de Plan estudio
     * @param PrE  Lista de Integers de de claves de Programas educativos
     * @param CE Lista de String de  ciclo escolar
     * @return ArrayList<String> de reportes esperados por area de conocmiento
     */
    public ArrayList<String> getAreaConocimientoEsperados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE) {
        return delegate.consultaEsperadosAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @return Regresa ArrayList De esperados y completados por area 
     * de conocimiento
     */
    public ArrayList<String> getFullAreaConocimiento(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE) {

        return delegate.consultaFullAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
    }

    //------------------------------------------------PROGRAMA EDUCATIVO---------------------------------------//
    /**
     * 
     * @param UnidadAcademica
     * @param PE ArrayList<String> de programa educativo
     * @param PrE ArrayList<String>
     * @param CE ArrayList<String> 
     * @return  Lista de estregados por Programa Educativo
     */
    public ArrayList<String> getProgramaEduEntregados(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){

        return delegate.consultaEntregadosProgramaEdu(UnidadAcademica, PE, PrE, CE);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param PE
     * @param PrE
     * @param CE
     * @param ract
     * @return Lista de entregados en ract especifico por programa educativo
     */
    public ArrayList<String> getProgramaEduEntregados(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String ract){

        return delegate.consultaEntregadosProgramaEdu(UnidadAcademica, PE, PrE, CE, ract);
        
    }
    /**
      * 
      * @param UnidadAcademica
      * @param PE
      * @param PrE
      * @param CE
      * @return  Lista de reportes  Esperados por programa educativo
      */
    public ArrayList<String> getProgramaEduEsperados(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){

        return delegate.consultaEsperadosProgramaEdu(UnidadAcademica, PE, PrE, CE);
    }
    /***
    * 
    * @param UnidadAcademica
    * @param PE
    * @param PrE
    * @param CE
    * @return Lista de de reportes entregados y esperados por programa educativo
    */
    public ArrayList<String> getFullProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){

        return delegate.consultaFullProgramaEdu(UnidadAcademica, PE, PrE, CE);
    }
    /***
     * 
     * @param UnidadAcademica
     * @param PE
     * @param PrE
     * @param CE
     * @param r1
     * @param r2
     * @param r3
     * @return Lista de reporte entregados a tiempo 
     * dentro del programa educativo
     */
    public ArrayList<String> getAtiempoProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){

        return delegate.consultaAtiempoProgramaEducativo(UnidadAcademica, PE, PrE, CE, r1,r2,r3);
    }
    /***
     * 
     * @param UnidadAcademica
     * @param PE
     * @param PrE
     * @param CE
     * @param r1
     * @param r2
     * @param r3
     * @return Lista de Reportes Entregados en limite de tiempo
     * por programa educativo
     */
    public ArrayList<String> getEnLimiteProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){

        return delegate.consultaEnLimiteProgramaEducativo(UnidadAcademica, PE, PrE, CE, r1,r2,r3);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param PE
     * @param PrE
     * @param CE
     * @param r1
     * @param r2
     * @param r3
     * @return Lista de reportes entregados despues de tiempo
     */
    public ArrayList<String> getDespuesLimiteProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){

        return delegate.consultaDespuesLimiteProgramaEducativo(UnidadAcademica, PE, PrE, CE, r1,r2,r3);
    }

    //------------------------------------------------UNIDAD APRENDIZAJE---------------------------------------//
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return Lista de reportes entregados unidad de aprendizaje 
     */
    public ArrayList<String> getUnidadAprendEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaEntregadosUnidadAprend(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @param ract
     * @return  Lista De reportes entregados por unidad de aprendizaje 
     * por ract especifico 
     */
    public ArrayList<String> getUnidadAprendEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi, String ract){

        return delegate.consultaEntregadosUnidadAprend(UnidadAcademica, AC, PE, PrE, CE, aprendi,ract);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return Lista de reportes entregados por unidad de aprendizaje 
     */
    public ArrayList<String> getUnidadAprendEsperados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaEsperadosUnidadAprend(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    
    public ArrayList<String> getFullUnidadAprend(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaFullUnidadAprend(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return Consulta de reportes por entregados y esperado 
     * por unidad de aprendizaje
     */
    //--------------------------------------------------PROFESOR-----------------------------------------------//
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return Lista de reportes entregados por profesor
     */
    public ArrayList<String> getProfesorEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaEntregadosProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @param ract
     * @return Lista de reportes entregados por profesor
     */
    public ArrayList<String> getProfesorEntregados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi,String ract){

        return delegate.consultaEntregadosProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi, ract);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return Lista de consultas de esperador por profesor
     */
    public ArrayList<String> getProfesorEsperados(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaEntregadosProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @param aprendi
     * @return  consulta de reportes esperados y entregados por profesor 
     */
    public ArrayList<String> getFullProfesor(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){

        return delegate.consultaFullProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
    }
    /**
     * 
     * @param UnidadAcademica
     * @return  porcentaje consulta de semaforo por unidad academica 
     */
    public double getSemaforo(int UnidadAcademica){

        return delegate.consultaSemaforo(UnidadAcademica);
    }
    /**
     * 
     * @param UnidadAcademica
     * @return Lsita de objetos para semaforo de programa educativo
     */
    public ArrayList<Object[]> getSemadoroProgEd(int UnidadAcademica){

        return delegate.semaforoProgEd(UnidadAcademica);
    }
    /**
     * 
     * @param UnidadAcademica
     * @return Lista de objetos para semaforo de programa educativo
     */
    public ArrayList<Object[]> getSemadoroProgEdValor(int UnidadAcademica, String... ract){

        return delegate.semaforoProgEdValor(UnidadAcademica,ract);
    }
}

