/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

/**
 * @author Eduardo
 */
public class Main {
    
    public static void main(String[] args) {
        
        String path = "";
        
        if (args.length > 0) {
            path = args[0];
        }
        
        UACtxt aCtxt = new UACtxt();
        aCtxt.recibir(path);
        //PEDtxt eDtxt = new PEDtxt();
        //PROtxtConUsuario procu = new PROtxtConUsuario();

        aCtxt.unidadAcademica();
        //eDtxt.programaEducativo();
    }
}
