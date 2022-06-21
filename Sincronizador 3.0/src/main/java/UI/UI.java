/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import entidades.Cicloescolar;
import metodos.GRUtxt;
import metodos.Main;
import metodos.PEDtxt;
import metodos.PEStxt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Paul Miranda
 */
public class UI {
    JFrame frame;
    JButton examinarArchivos, sincronizar;
    JLabel etiqueta, etiqueta2;
    Cicloescolar cicloEscolar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String ciclo, path;
    JFileChooser fileChooser;
    JTextArea textArea;
    JDialog dialogo;
    File file;
    int contador = 0;
    static String agregados;

    public UI() {
        //Se crea el frame donde estarán los componentes
        frame = new JFrame("Sincronizador SIRACT");
        frame.setSize(440, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Código para obtener el ciclo escolar actual 
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdf.parse(sdf.format(new Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            ciclo = "";
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                ciclo = "" + anio + "-" + 1;
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                ciclo = "" + anio + "-" + 2;
            }
        } catch (Exception ex) {
        }

        //componentes de la primera etiqueta
        etiqueta = new JLabel("Agregar el archivo \"unidad" + ciclo + "\":");
        etiqueta.setBounds(10, 10, 400, 40);
        frame.add(etiqueta);

        //se crea el file chooser
        fileChooser = new JFileChooser();

        //componentes del text area 
        textArea = new JTextArea();
        textArea.setBounds(10, 40, 200, 20);
        frame.add(textArea);

        //componentes del boton examinar
        examinarArchivos = new JButton("Examinar");
        examinarArchivos.setBounds(220, 40, 100, 20);
        frame.add(examinarArchivos);

        //componentes de la segunda etiqueta
        etiqueta2 = new JLabel("Sincronizar con la base de datos:");
        etiqueta2.setBounds(10, 70, 200, 40);
        frame.add(etiqueta2);

        //componentes del botón sincronizar
        sincronizar = new JButton("Sincronizar");
        sincronizar.setBounds(10, 100, 200, 20);
        frame.add(sincronizar);

        //se llama al método acciones donde se tienen todos los action listener del código
        acciones();

        frame.setVisible(true);
    }

    public void acciones() {
        examinarArchivos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dialogo.setVisible(true);

                // Cuando se presione el boton examinar archivos abrira el examinador de archivos donde se podrá buscar el archivo
                // que ocupamos

                int returnValue = fileChooser.showOpenDialog(frame);
                //Cuando se seleccione el archivo guardara el archivo en la variable file y pondrá el nombre del archivo
                //en el text area
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Contador " + contador);
                    file = fileChooser.getSelectedFile();
                    /**
                     * Este switch toma el valor de contandor, el cual aumentara en uno cada vez que el botón Sincronizar sea 
                     * presionado. Dependiendo de el valor del contador será el archivo txt que se tendrá que ingresasr ya que
                     * estos siguen un cierto orden. Dependiendo de el contador es el archivo que se tiene que ingresar por lo tanto
                     * se valida el nombre del archivo para verificar que se esta ingresando el archivo correcto y no uno que no 
                     * corresponda. En caso de ingresarse un archivo incorrecto se mostrará una alerta.
                     */
                    switch (contador) {
                        case 0:
                            if (file.getName().contains("unidad") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 1:
                            if (file.getName().contains("carreras") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 2:
                            if (file.getName().contains("plan") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 3:
                            if (file.getName().contains("uaip22") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 4:
                            if (file.getName().contains("profesor") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 5:
                            if (file.getName().contains("profesor") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 6:
                            if (file.getName().contains("profesor") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 7:
                            if (file.getName().contains("uaip") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;

                        case 8:
                            if (file.getName().contains("uaip22") == true) {
                                textArea.append(file.getName());
                            } else {
                                JOptionPane.showMessageDialog(null, "ARCHIVO INCORRECTO");
                            }
                            break;
                    }
                }
            }
        });

        sincronizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Este if verifica si se seleccionó un archivo. Cuando se selecciona un archivo correcto este se muestra en el
                 * text area, por lo tanto si el text area esta vacío significa que no se seleccionó un archivo y muestra una
                 * alerta
                 */
                if (textArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "NO SE SELECCIONO ARCHIVO");
                } else {
                    /**
                     * En este switch se verifica el valor de contador que incrementa cada vez que se presiona el botón Sincronizar
                     * Dependiendo del valor del contador se va a ejecutar la clase que corresponde según el orden que esta establecido
                     * (para ver el orden establecido consultar el manual). Despues de ejecutar la aplicación va a cambiar el JLabel
                     * con la instrucción de ingresar el siguiente archivo según el orden.
                     */
                    switch (contador) {
                        case 0:
                            path = file.getPath();
                            System.out.println(path);
                            Main.main(new String[]{path});
                            etiqueta.setText("Agregar el archivo \"carreras" + ciclo + "\":");
                            break;
                        case 1:
                            path = file.getPath();
                            PEDtxt pedTxt = new PEDtxt();
                            pedTxt.recibir(path);
                            PEDtxt.main(new String[]{path});
                            etiqueta.setText("Agregar el archivo \"plan" + ciclo + "\":");
                            break;

                        case 2:
                            path = file.getPath();
                            PEStxt pesTxt = new PEStxt();
                            pesTxt.recibir(path);
                            PEStxt.main(new String[]{path});
                            recibir(agregados);
                            System.out.println("ESTOY IMPRIMIENDO ESTO EN GUI: " + agregados);
                            etiqueta.setText("Agregar el archivo \"uaip2" + ciclo + "\":");
                            break;

                        case 3:
                            path = file.getPath();
                            GRUtxt gruTxt = new GRUtxt();
                            gruTxt.recibir(path);
                            GRUtxt.main(new String[]{path});
                            etiqueta.setText("Agregar el archivo \"profesor" + ciclo + "\":");
                            break;

                        case 4:
//                            path = file.getPath();
//                            PROtxtConUsuario ptcu = new PROtxtConUsuario();
//                            ptcu.recibir(path);
//                            PROtxtConUsuario.main(new String[]{path});
//                            etiqueta.setText("Agregar el archivo \"profesor"+ciclo+"\":");
                            break;

                        case 5:
//                            path = file.getPath();
//                            PROtxtConPertenecePED ptcpPED= new PROtxtConPertenecePED();
//                            ptcpPED.recibir(path);
//                            PROtxtConPertenecePED.main(new String[]{path});
//                            etiqueta.setText("Agregar el archivo \"profesor"+ciclo+"\":");
                            break;

                        case 6:
//                            path = file.getPath();
//                            USUtxtConRol utcRol = new USUtxtConRol();
//                            utcRol.recibir(path);
//                            PROtxtConPertenecePED.main(new String[]{path});
//                            etiqueta.setText("Agregar el archivo \"uaip"+ciclo+"\":");
                            break;

                        case 7:
//                            path = file.getPath();
//                            UAPtxt uapTxt = new UAPtxt();
//                            uapTxt.recibir(path);
//                            PROtxtConPertenecePED.main(new String[]{path});
//                            etiqueta.setText("Agregar el archivo \"uaip2"+ciclo+"\":");
                            break;

                        case 8:
//                            path = file.getPath();
//                            UAPIPtxt2 uapipTxt = new UAPIPtxt2();
//                            uapipTxt.recibir(path);
//                            PROtxtConPertenecePED.main(new String[]{path});
                            break;
                    }
                    //Se incremeneta el contador y el text area donde se muestra el archivo seleccionado se limpia.
                    contador++;
                    textArea.setText(null);
                }

            }
        });
    }

    public static void recibir(String saved) {
        agregados = saved;
    }

    public static void main(String[] args) {
        UI ui = new UI();
    }
}
