/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.business.delegate;

import java.util.ArrayList;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.integration.ServiceLocator;

/**
 *
 * @author Eduardo
 */
public class DelegateEsperados {
    
    private ArrayList<String> resultado;
    
    //-----------------------------------------------AREA DE CONOCIMIENTO--------------------------------------//
    
    /***
     * Metodo que regresa lista de contadores en String por area de conocimiento
     * @param UnidadAcademica Id de la Unidad Academica
     * @param AC Lista de String de claves de Área de conocimientos 
     * @param PE Lista de String de Plan de Estudio
     * @param PrE Lista de String de Clave de programas educativos
     * @param CE Lista de String de Clave de Ciclos Escolares
     * @return Regresa Reportes entregados por area de conocimiento
     */
    public ArrayList<String> consultaEntregadosAreaConocimiento(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosAreaConocimiento();
        return resultado; 
    }
    
    /**
     * Metodo que regresa lista de contadores en String por area de conocimiento de reportes entregados
     * @param UnidadAcademica Id de la Unidad Academica
     * @param AC Lista de String de claves de Área de conocimientos 
     * @param PE Lista de String de Plan de Estudio
     * @param PrE Lista de String de Clave de programas educativos
     * @param CE Lista de String de Clave de Ciclos Escolares
     * @param num Numero(Periodo) de ract a Buscar
     * @return Regresa reportes entregados por area de conocimiento
     */
    public ArrayList<String> consultaEntregadosAreaConocimiento(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE, String num){
        ServiceLocator.getInstanceEsperados().initAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosAreaConocimiento(num);
        return resultado; 
    }
    
    /**
     * 
     * @param UnidadAcademica
     * @param AC
     * @param PE
     * @param PrE
     * @param CE
     * @return ArrayList<String> de reportes esperados por area de conocmiento
     */
    public ArrayList<String> consultaEsperadosAreaConocimiento(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEsperadosAreaConocimiento();
        return resultado; 
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
    public ArrayList<String> consultaFullAreaConocimiento(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initAreaConocimiento(UnidadAcademica, AC, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getRactsAreaConocimiento();
        return resultado; 
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
    public ArrayList<String> consultaEntregadosProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosProgramaEducativo();
        return resultado; 
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
    public ArrayList<String> consultaEntregadosProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String ract){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosProgramaEducativo(ract);
        return resultado; 
    }
     /**
      * 
      * @param UnidadAcademica
      * @param PE
      * @param PrE
      * @param CE
      * @return  Lista de reportes  Esperados por programa educativo
      */
    public ArrayList<String> consultaEsperadosProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEsperadosProgramaEducativo();
        return resultado;
    }
    /***
    * 
    * @param UnidadAcademica
    * @param PE
    * @param PrE
    * @param CE
    * @return Lista de de reportes entregados y esperados por programa educativo
    */
    public ArrayList<String> consultaFullProgramaEdu(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getRactsProgramaEducativo();
        return resultado;
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
    public ArrayList<String> consultaAtiempoProgramaEducativo(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getAtiempoProgramaEducativo(r1,r2,r3);
        return resultado;
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
    public ArrayList<String> consultaEnLimiteProgramaEducativo(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEnLimiteProgramaEducativo(r1,r2,r3);
        return resultado;
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
    public ArrayList<String> consultaDespuesLimiteProgramaEducativo(int UnidadAcademica,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,String r1,String r2,String r3){
        ServiceLocator.getInstanceEsperados().initProgramaEducativo(UnidadAcademica, PE, PrE, CE);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getDespuesLimiteProgramaEducativo(r1,r2,r3);
        return resultado;
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
    public ArrayList<String> consultaEntregadosUnidadAprend(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initUnidadAprendizaje(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosUnidadAprendizaje();
        return resultado;
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
    public ArrayList<String> consultaEntregadosUnidadAprend(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi,String ract){
        ServiceLocator.getInstanceEsperados().initUnidadAprendizaje(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosUnidadAprendizaje(ract);
        return resultado;
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
    public ArrayList<String> consultaEsperadosUnidadAprend(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initUnidadAprendizaje(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEsperadosUnidadAprendizaje();
        return resultado;
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
    public ArrayList<String> consultaFullUnidadAprend(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initUnidadAprendizaje(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getRactsUnidadAprendizaje();
        return resultado;
    }

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
    public ArrayList<String> consultaEntregadosProfesor(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosProfesor();
        return resultado;
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
    public ArrayList<String> consultaEntregadosProfesor(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi,String ract){
        ServiceLocator.getInstanceEsperados().initProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEntregadosProfesor(ract);
        return resultado;
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
    public ArrayList<String> consultaEsperadosProfesor(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getEsperadosProfesor();
        return resultado;
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
    public ArrayList<String> consultaFullProfesor(int UnidadAcademica,ArrayList<String> AC,ArrayList<String> PE,ArrayList<Integer> PrE,ArrayList<String> CE,ArrayList<String> aprendi){
        ServiceLocator.getInstanceEsperados().initProfesor(UnidadAcademica, AC, PE, PrE, CE, aprendi);
        resultado = null;
        resultado = ServiceLocator.getInstanceEsperados().getRactsProfesor();
        return resultado;
    }
    
    /**
     * 
     * @param UnidadAcademica
     * @return  porcentaje consulta de semaforo por unidad academica 
     */
    public double consultaSemaforo(int UnidadAcademica){
        double porcentaje = ServiceLocator.getInstanceEsperados().contarSemaforo(UnidadAcademica);
        return porcentaje;
    }
    
    /**
     * 
     * @param UnidadAcademica
     * @return Lsita de objetos para semaforo de programa educativo
     */
    
    public ArrayList<Object[]> semaforoProgEd(int UnidadAcademica){
        DelegateCicloEscolar ceDelegate=new DelegateCicloEscolar();
        Cicloescolar cesActual=ceDelegate.getCicloEscolarActual();
        ArrayList<Object[]> progEds = ServiceLocator.getInstanceEsperados().contarSemaforoProgEdValor(UnidadAcademica,cesActual.getCESid().toString());
        return progEds;
    }
    
    /**
     * 
     * @param UnidadAcademica
     * @return Lista de objetos para semaforo de programa educativo
     */
    public ArrayList<Object[]> semaforoProgEdValor(int UnidadAcademica,String... ract){
        DelegateCicloEscolar ceDelegate=new DelegateCicloEscolar();
        Cicloescolar cesActual=ceDelegate.getCicloEscolarActual();
        ArrayList<Object[]> progEds = ServiceLocator.getInstanceEsperados().contarSemaforoProgEdValor(UnidadAcademica,cesActual.getCESid().toString(),ract);
        return progEds;
    }
}

