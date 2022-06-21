package principal;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Principal {
    static int number;
    static String ciclo;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(Integer[] args) {
        int op;
        int opcion = -1;
        if (args.length > 0) {
            opcion = args[0];
        }
        Principal p = new Principal();
        p.ejecutarPA(opcion);

//        do{
//            File file24=new File("./fileasd.txt");
//            try {
//                file24.createNewFile();
//            } catch (IOException ex) {
//                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            op=JOptionPane.showOptionDialog(null, "Seleccione :D", "Procedimientos Almacenados", JOptionPane.YES_NO_OPTION, 1, null, new Object[]{"ProEd(ing)", "Campus", "UnidadAc", "Carreras", "CarrPlan", "ProfP", "UniAprImpProf", "TituGrupos"}, "ProEd");
//            System.out.println(op); 
//            op = recibir(number);
//            if(op== -1)
//                System.exit(0);
//            p.ejecutarPA(op);
//        }while(op!=-1);
    }

    public static int recibir(int numero) {
        number = numero;
        return number;
    }

    public void ejecutarPA(int op) {
        
        //CODIGO PARA OBTENER EL CICLO ESCOLAR
        try {            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            java.util.Date date = sdf.parse(sdf.format(new java.util.Date()));
            String anio = dateFormat.format(date).substring(0, 4);
            String mes = dateFormat.format(date).substring(5, 7);
            // LA VARIABLE CICLO SE ASIGNA DEPENDIENDO DEL MES, SE DETERMINA EL 
            // CICLO AUTOMATICAMENTE Y SE GUARDA EN UNA VARIABLE
            if (Integer.parseInt(mes) >= 1 && Integer.parseInt(mes) <= 6) {
                ciclo =  anio + "_1";
            }
            if (Integer.parseInt(mes) >= 7 && Integer.parseInt(mes) <= 12) {
                ciclo = anio + "_2";
            }

        }catch (Exception ex) {
            System.out.println("Error Ciclo Escolar");
        }
        
        try {
            //DriverManager.registerDriver((Driver) Class.forName("com.informix.jdbc.IfxDriver").newInstance());
            /**
             * El Driver de abajo funciona para la conexión local
             */
            //DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            Connection connection = DriverManager.getConnection("jdbc:" + "informix-sqli" + "://" + "148.231.10.20" + ":" + "1522" + "/" + "escolares_pruebas" + ":INFORMIXSERVER=" + "x1", "aluming1", "lzw327m");
         //   Connection connection = DriverManager.getConnection("jdbc:" + "informix-sqli"+ "://"+"148.231.10.31"+":"+"1542"+"/"+"escolares_pruebas"+":INFORMIXSERVER="+"desa","aluming1","lzw327m");
            /**
             * Para una conexión local a mi base de datos:
             * @param url
             */
//            String url = "jdbc:mysql://localhost:3306/siract";
//            Connection connection = DriverManager.getConnection(url,"root","root");
            CallableStatement cs = null;
            ResultSet resultSet = null;
            ResultSetMetaData metaData = null;
            
            switch (op) {
                case 0://ok
                    File file = new File("./carreras" + ciclo + ".txt");
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    //BufferedWriter bw=new BufferedWriter(fw);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp1252"));
                    cs = connection.prepareCall("call ingeneria_carrerasporunidadacademica(?)");
                    cs.setInt(1, 140);
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.println(metaData.getColumnName(3));
                    //bw.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3));
                    //bw.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getInt(1) + " -- ");
                        System.out.print(resultSet.getString(2) + " -- ");
                        System.out.println(resultSet.getInt(3));
                        bw.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim());
                        bw.write(System.getProperty("line.separator"));
                    }
                    bw.close();
                    break;
                case 1://ok
                    File file1 = new File("./campus" + ciclo + ".txt");
                    file1.createNewFile();
                    FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
                    //BufferedWriter bw1=new BufferedWriter(fw1);
                    BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_campus_p()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.println(metaData.getColumnName(2));
                    //bw1.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2));
                    //bw1.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1) + " -- ");
                        System.out.println(resultSet.getString(2));
                        bw1.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim());
                        bw1.write(System.getProperty("line.separator"));
                    }
                    bw1.close();
                    break;
                case 2://ok
                    File file2 = new File("./unidad" + ciclo + ".txt");
                    file2.createNewFile();
                    FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
                    //BufferedWriter bw2=new BufferedWriter(fw2);
                    BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_unidadacademica_p()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.println(metaData.getColumnName(4));
                    //bw2.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4));
                    //bw2.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1) + " -- ");
                        System.out.print(resultSet.getString(2) + " -- ");
                        System.out.print(resultSet.getString(3) + " -- ");
                        System.out.println(resultSet.getString(4));
                        bw2.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim());
                        bw2.write(System.getProperty("line.separator"));
                    }
                    bw2.close();
                    break;
                case 3://ok
                    File file3 = new File("./carreras2" + ciclo + ".txt");
                    file3.createNewFile();
                    FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
                    //BufferedWriter bw3=new BufferedWriter(fw3);
                    BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file3), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_carreras_p()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.println(metaData.getColumnName(4));
                    //bw3.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4));
                    //bw3.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1) + " -- ");
                        System.out.print(resultSet.getString(2) + " -- ");
                        System.out.print(resultSet.getString(3) + " -- ");
                        System.out.println(resultSet.getString(4));
                        bw3.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim());
                        bw3.write(System.getProperty("line.separator"));
                    }
                    bw3.close();
                    break;
                case 4://ok
                    File file4 = new File("./plan" + ciclo + ".txt");
                    file4.createNewFile();
                    FileWriter fw4 = new FileWriter(file4.getAbsoluteFile());
                    //BufferedWriter bw4=new BufferedWriter(fw4);
                    BufferedWriter bw4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file4), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_carrea_plan_p()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.print(metaData.getColumnName(6) + " -- ");
                    System.out.println(metaData.getColumnName(7));
                    //bw4.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4)+"--"+metaData.getColumnName(5)+"--"+metaData.getColumnName(6)+"--"+metaData.getColumnName(7));
                    //bw4.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        try {
                            System.out.print(resultSet.getString(1) + " -- ");
                            System.out.print(resultSet.getString(2) + " -- ");
                            System.out.print(resultSet.getString(3) + " -- ");
                            System.out.print(resultSet.getString(4) + " -- ");
                            System.out.print(resultSet.getString(5) + " -- ");
                            System.out.print(resultSet.getString(6) + " -- ");
                            System.out.println(resultSet.getString(7));
                            bw4.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim());
                            bw4.write(System.getProperty("line.separator"));
                        } catch (Exception e) {

                        }
                    }
                    bw4.close();
                    break;
                case 5:
                    File file5 = new File("./profesor" + ciclo + ".txt");
                    file5.createNewFile();
                    FileWriter fw5 = new FileWriter(file5.getAbsoluteFile());
                    //BufferedWriter bw5=new BufferedWriter(fw5);
                    BufferedWriter bw5 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file5), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_profesor_p()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.println(metaData.getColumnName(6));
                    //bw5.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4));
                    //bw5.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1).trim() + " -- ");
                        System.out.print(resultSet.getString(2).trim() + " -- ");
                        System.out.print(resultSet.getString(3).trim() + " -- ");
                        System.out.print(resultSet.getString(4).trim() + " -- ");
                        System.out.println(resultSet.getString(5).trim());
                        bw5.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim() + "--" + resultSet.getString(8).trim());
                        bw5.write(System.getProperty("line.separator"));
                    }
                    bw5.close();
                    break;
                case 6:
                    File file6 = new File("./uaip" + ciclo + ".txt");
                    file6.createNewFile();
                    FileWriter fw6 = new FileWriter(file6.getAbsoluteFile());
                    // BufferedWriter bw6=new BufferedWriter(fw6);
                    BufferedWriter bw6 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file6), "Cp1252"));
                    cs = connection.prepareCall("call ingenieria_unidadaprendizaje_imparte_profesor()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.print(metaData.getColumnName(6) + " -- ");
                    System.out.print(metaData.getColumnName(7) + " -- ");
                    System.out.print(metaData.getColumnName(8) + " -- ");
                    System.out.print(metaData.getColumnName(9) + " -- ");
                    System.out.print(metaData.getColumnName(10) + " -- ");
                    System.out.print(metaData.getColumnName(11) + " -- ");
                    System.out.print(metaData.getColumnName(12) + " -- ");
                    System.out.println(metaData.getColumnName(13));
                    //bw6.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4)+"--"+metaData.getColumnName(5)+"--"+metaData.getColumnName(6)+"--"+metaData.getColumnName(7)+"--"+metaData.getColumnName(8)+"--"+metaData.getColumnName(9)+"--"+metaData.getColumnName(10)+"--"+metaData.getColumnName(11)+"--"+metaData.getColumnName(12)+"--"+metaData.getColumnName(13));
                    //bw6.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1).trim() + " -- ");
                        System.out.print(resultSet.getString(2).trim() + " -- ");
                        System.out.print(resultSet.getString(3).trim() + " -- ");
                        System.out.print(resultSet.getString(4).trim() + " -- ");
                        System.out.print(resultSet.getString(5).trim() + " -- ");
                        System.out.print(resultSet.getString(6).trim() + " -- ");
                        System.out.print(resultSet.getString(7).trim() + " -- ");
                        System.out.print(resultSet.getString(8).trim() + " -- ");
                        System.out.print(resultSet.getString(9).trim() + " -- ");
                        System.out.print(resultSet.getString(10).trim() + " -- ");
                        System.out.print(resultSet.getString(11).trim() + " -- ");
                        System.out.print(resultSet.getString(12).trim() + " -- ");
                        System.out.println(resultSet.getString(13).trim());
                        bw6.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim() + "--" + resultSet.getString(8).trim() + "--" + resultSet.getString(9).trim() + "--" + resultSet.getString(10).trim() + "--" + resultSet.getString(11).trim() + "--" + resultSet.getString(12).trim() + "--" + resultSet.getString(13).trim());
                        bw6.write(System.getProperty("line.separator"));
                    }
                    bw6.close();
                    break;
                case 7:
                    File file7 = new File("./uaip2" + ciclo + ".txt");
                    file7.createNewFile();
                    FileWriter fw7 = new FileWriter(file7.getAbsoluteFile());
                    //BufferedWriter bw7=new BufferedWriter(fw7);//Normal
                    BufferedWriter bw7 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file7), "Cp1252"));//ANSI
                    cs = connection.prepareCall("call ingenieria_titular_grupos()");
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.print(metaData.getColumnName(6) + " -- ");
                    System.out.print(metaData.getColumnName(7) + " -- ");
                    System.out.print(metaData.getColumnName(8) + " -- ");
                    System.out.print(metaData.getColumnName(9) + " -- ");
                    System.out.print(metaData.getColumnName(10) + " -- ");
                    System.out.print(metaData.getColumnName(11) + " -- ");
                    System.out.println(metaData.getColumnName(12));
                    //bw7.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4)+"--"+metaData.getColumnName(5)+"--"+metaData.getColumnName(6)+"--"+metaData.getColumnName(7)+"--"+metaData.getColumnName(8)+"--"+metaData.getColumnName(9)+"--"+metaData.getColumnName(10)+"--"+metaData.getColumnName(11)+"--"+metaData.getColumnName(12));
                    //bw7.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1).trim() + " -- ");
                        System.out.print(resultSet.getString(2).trim() + " -- ");
                        System.out.print(resultSet.getString(3).trim() + " -- ");
                        System.out.print(resultSet.getString(4).trim() + " -- ");
                        System.out.print(resultSet.getString(5).trim() + " -- ");
                        System.out.print(resultSet.getString(6).trim() + " -- ");
                        System.out.print(resultSet.getString(7).trim() + " -- ");
                        System.out.print(resultSet.getString(8).trim() + " -- ");
                        System.out.print(resultSet.getString(9).trim() + " -- ");
                        System.out.print(resultSet.getString(10).trim() + " -- ");
                        System.out.print(resultSet.getString(11).trim() + " -- ");
                        System.out.println(resultSet.getString(12).trim());
                        bw7.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim() + "--" + resultSet.getString(8).trim() + "--" + resultSet.getString(9).trim() + "--" + resultSet.getString(10).trim() + "--" + resultSet.getString(11).trim() + "--" + resultSet.getString(12).trim());
                        bw7.write(System.getProperty("line.separator"));
                    }
                    bw7.close();
                    break;
                case 8:
                    File file8 = new File("./alumno" + ciclo + ".txt");
                    file8.createNewFile();
                    FileWriter fw8 = new FileWriter(file8.getAbsoluteFile());
                    //BufferedWriter bw8=new BufferedWriter(fw8);//Normal
                    BufferedWriter bw8 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file8), "Cp1252"));//ANSI
//                    cs = connection.prepareCall("call ingenieria_datos_alumno_p(?)");
                    cs = connection.prepareCall("call ingenieria_datos_alumno_p()");  //Sin parametros
//                    cs.setInt(1, 140); // Unidad academica
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.print(metaData.getColumnName(6) + " -- ");
                    System.out.println(metaData.getColumnName(7));
                    
                    //bw8.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4)+"--"+metaData.getColumnName(5)+"--"+metaData.getColumnName(6)+"--"+metaData.getColumnName(7)+"--"+metaData.getColumnName(8)+"--"+metaData.getColumnName(9)+"--"+metaData.getColumnName(10)+"--"+metaData.getColumnName(11)+"--"+metaData.getColumnName(12));
                    //bw8.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        try{
                        System.out.print(resultSet.getString(1).trim() + " -- ");
                        System.out.print(resultSet.getString(2).trim() + " -- ");
                        System.out.print(resultSet.getString(3).trim() + " -- ");
                        System.out.print(resultSet.getString(4).trim() + " -- ");
                        System.out.print(resultSet.getString(5).trim() + " -- ");
                        System.out.print(resultSet.getString(6).trim() + " -- ");
                        System.out.println(resultSet.getString(7).trim());
                        bw8.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim());
                        bw8.write(System.getProperty("line.separator"));
                        }catch (Exception e){
                            
                        }
                    }
                    bw8.close();
                    break;
                case 9:
                    File file9 = new File("./apg" + ciclo + ".txt");
                    file9.createNewFile();
                    FileWriter fw9 = new FileWriter(file9.getAbsoluteFile());
                    //BufferedWriter bw9=new BufferedWriter(fw9);//Normal
                    BufferedWriter bw9 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file9), "Cp1252"));//ANSI
//                    cs = connection.prepareCall("call ingenieria_grupos_alumno_p(?)");
                    cs = connection.prepareCall("call ingenieria_grupos_alumno_p()");
//                    cs = connection.prepareCall("call ingenieria_grupos_alumno_p(?, ?, ?)");
//                    cs.setInt(1, 140); // Unidad academica
//                    cs.setInt(2, 1); // Periodo
//                    cs.setInt(3, 2022); //Ciclo Escolar
                    resultSet = cs.executeQuery();
                    metaData = resultSet.getMetaData();
                    System.out.print(metaData.getColumnName(1) + " -- ");
                    System.out.print(metaData.getColumnName(2) + " -- ");
                    System.out.print(metaData.getColumnName(3) + " -- ");
                    System.out.print(metaData.getColumnName(4) + " -- ");
                    System.out.print(metaData.getColumnName(5) + " -- ");
                    System.out.print(metaData.getColumnName(6) + " -- ");
                    System.out.print(metaData.getColumnName(7) + " -- ");
                    System.out.print(metaData.getColumnName(8) + " -- ");
                    System.out.print(metaData.getColumnName(9) + " -- ");
                    System.out.println(metaData.getColumnName(10));
                    //bw9.write(metaData.getColumnName(1)+"--"+metaData.getColumnName(2)+"--"+metaData.getColumnName(3)+"--"+metaData.getColumnName(4)+"--"+metaData.getColumnName(5)+"--"+metaData.getColumnName(6)+"--"+metaData.getColumnName(7)+"--"+metaData.getColumnName(8)+"--"+metaData.getColumnName(9)+"--"+metaData.getColumnName(10)+"--"+metaData.getColumnName(11)+"--"+metaData.getColumnName(12));
                    //bw9.write(System.getProperty("line.separator"));
                    while (resultSet.next()) {
                        System.out.print(resultSet.getString(1).trim() + " -- ");
                        System.out.print(resultSet.getString(2).trim() + " -- ");
                        System.out.print(resultSet.getString(3).trim() + " -- ");
                        System.out.print(resultSet.getString(4).trim() + " -- ");
                        System.out.print(resultSet.getString(5).trim() + " -- ");
                        System.out.print(resultSet.getString(6).trim() + " -- ");
                        System.out.print(resultSet.getString(7).trim() + " -- ");
                        System.out.print(resultSet.getString(8).trim() + " -- ");
                        System.out.print(resultSet.getString(9).trim() + " -- ");
                        System.out.println(resultSet.getString(10).trim());
                        bw9.write(resultSet.getString(1).trim() + "--" + resultSet.getString(2).trim() + "--" + resultSet.getString(3).trim() + "--" + resultSet.getString(4).trim() + "--" + resultSet.getString(5).trim() + "--" + resultSet.getString(6).trim() + "--" + resultSet.getString(7).trim() + "--" + resultSet.getString(8).trim() + "--" + resultSet.getString(9).trim() + "--" + resultSet.getString(10).trim());
                        bw9.write(System.getProperty("line.separator"));
                    }
                    bw9.close();
                    break;
                case -1:
                    System.exit(0);
                    break;
            }
            cs.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

