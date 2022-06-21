/* --------------------
 * MeterChartDemo2.java
 * --------------------
 * (C) Copyright 2005, by Object Refinery Limited.
 *
 */
package mx.avanti.siract.util;

import com.lowagie.text.Image;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
//import mx.avanti.business.delegate.CicloEscolarDelegate;
//import mx.avanti.business.delegate.ProgramaEducativoDelegate;
//import mx.avanti.business.delegate.ReporteavancecontenidotematicoDelegate;
//import mx.avanti.business.delegate.UnidadaprendizajeImparteProfesorDelegate;
import mx.avanti.siract.entity.Cicloescolar;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.helper.FiltrosBeanHelper;
import mx.avanti.siract.integration.ServiceLocator;
import mx.avanti.siract.ui.FiltrosBeanUI;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;

/**
 * A simple demonstration application showing how to create a meter chart.
 */
public class MeterChartDemo {

    private static final int HIGH = 350;
    private static final int WIDTH = 600;
    private static final String SHEET_NAME = "Graficos";
    private static final int POSITION_X = 1;
    private static final int POSITION_Y = 13;
    private static final int CHART_WIDTH = 6; //numero de columnas
    private static final int CHART_HIGH = 24; //numero de filas
    private static DefaultValueDataset dataset;
    String title;
    String excel;
    double min = 0, max;
    ArrayList<String> Entregados;

    public MeterChartDemo(String title, ArrayList<String> Entregados, String excel) {
        this.Entregados = Entregados;
        this.excel = excel;
        this.title = title;
        
    }

    /**
     * Creates a new demo.
     *
     * @param title the frame title.
     */
    public MeterChartDemo(String title) {
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(500, 270));

    }

    public void addChartToExcel(JFreeChart chart, HSSFWorkbook wb) throws Exception {
        final BufferedImage buffer = chart.createBufferedImage(WIDTH, HIGH);
        //final FileOutputStream file = new FileOutputStream(excel+".xls");

        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
        ImageIO.write(buffer, "png", img_bytes);
        img_bytes.flush();
        /*Esta es la parte para agregar todo lo gneraod al excel*/
 /*Esta es la parte para agregar todo lo gneraod al excel*/
 /*Esta es la parte para agregar todo lo gneraod al excel*/
        //HSSFWorkbook wb = new HSSFWorkbook();
        //wb.createSheet(SHEET_NAME);
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) POSITION_X, POSITION_Y, (short) CHART_WIDTH, CHART_HIGH);
        int index = wb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
        HSSFSheet sheet = wb.getSheet(SHEET_NAME);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, index);
        //wb.write(file);
        //file.close();
    }

    public void addChartsToExcel(List<JFreeChart> charts, HSSFWorkbook wb, String type) throws Exception {
        int xMargin = 0;
        int yMargin = 0;
        wb.createSheet("Semáforo");

        for (JFreeChart chart : charts) {
            final BufferedImage buffer = chart.createBufferedImage(WIDTH, HIGH);
            //final FileOutputStream file = new FileOutputStream(excel+".xls");

            ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
            ImageIO.write(buffer, "png", img_bytes);
            img_bytes.flush();
            /*Esta es la parte para agregar todo lo gneraod al excel*/
 /*Esta es la parte para agregar todo lo gneraod al excel*/
 /*Esta es la parte para agregar todo lo gneraod al excel*/
            //HSSFWorkbook wb = new HSSFWorkbook();
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) (POSITION_X + (xMargin * 6)), POSITION_Y + (yMargin * 12), (short) (CHART_WIDTH + (xMargin * 6)), CHART_HIGH + (yMargin * 12));
            int index = wb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
            HSSFSheet sheet = wb.getSheet("Semáforo");

            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            patriarch.createPicture(anchor, index);

            xMargin++;
            if (xMargin != 0 && xMargin % 4 == 0) {
                yMargin++;
                xMargin = 0;
            }
        }

        HSSFSheet sheet = wb.getSheet("Semáforo");
        
        ////
        String titulo;
        if(type.contains("RACT")){
            titulo="Semáforo de Reporte de Avance de Contenido Temático del RACT #"+type.charAt(type.length()-1);
        }else{
            titulo="Semáforo de Reporte de Avance de Contenido Temático "+type+"  por Programa Educativo ";
        }
            
        
        HSSFRow row1 = sheet.createRow(7);
        row1.setHeight((short) 600);
        HSSFCell cell = row1.createCell(1);
        cell.setCellValue(titulo);

        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 20);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        style.setFont(font);
        cell.setCellStyle(style);
        
        //Cabecera
        
        BufferedImage image=null;
        try {
            image = ImageIO.read(getClass().getResource("/uabclogo.png"));
        } catch (IOException e) {
            System.out.println("No se encontro imagen");
        }
        ByteArrayOutputStream imgLogo_bytes = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imgLogo_bytes);
            imgLogo_bytes.flush();
        
        int uabcLogo = wb.addPicture(imgLogo_bytes.toByteArray(), Workbook.PICTURE_TYPE_PNG);
        
        
        // Aqui modifique Alex Cota, para utilzar el metodo de cabezera ya puesto en la clase FiltrosBeanUI
        new FiltrosBeanUI().cabezeraGeneralExcel(sheet, style,wb);
        
        
//        HSSFPatriarch drawing = sheet.createDrawingPatriarch();
//        /* Create an anchor point */
//        ClientAnchor my_anchor = new HSSFClientAnchor();
//        /* Define top left corner, and we can resize picture suitable from there */
//        my_anchor.setCol1(1);
//        my_anchor.setRow1(1);
//        /* Invoke createPicture and pass the anchor point and ID */
//        HSSFPicture my_picture = drawing.createPicture(my_anchor, uabcLogo);
//        /* Call resize method, which resizes the image */
//        double escalaRes = 1;
//        my_picture.resize(escalaRes);
////        definiremos el estilo para estas Celdas
////        Definiremos el nombre de la escuela
//        HSSFRow row = sheet.createRow(2);
//        row.setHeight((short) 600);
//        cell = row.createCell(3);
//        cell.setCellValue("Universidad Autónoma de Baja California");
//        cell.setCellStyle(style);
//        row = sheet.createRow(3);
//        row.setHeight((short) 600);
//        cell = row.createCell(3);
//        cell.setCellValue("Facultad de Ingeniería");
//        cell.setCellStyle(style);
//        row = sheet.createRow(4);
//        row.setHeight((short) 600);
//        cell = row.createCell(3);
//        cell.setCellValue("Mexicali");
//        cell.setCellStyle(style);
//
//        ServiceLocator.getInstanceBaseDAO().setTipo(Object.class);
//        ArrayList<Object> temp = (ArrayList<Object>) ServiceLocator.getInstanceBaseDAO().executeSQL("select curdate()");
//        Object fechaActual = temp.get(0);
//
//        cell = row.createCell(11);
//        cell.setCellValue(fechaActual.toString());
//        cell.setCellStyle(style);
        
        ///////////////////////////////////////////
        
        
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset a dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(ValueDataset dataset) {
        MeterPlot plot = new MeterPlot(dataset);
        plot.addInterval(new MeterInterval("High", new Range(80.0, 100.0)));
        plot.setDialOutlinePaint(Color.white);
        JFreeChart chart = new JFreeChart("Meter Chart 2",
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        return chart;
    }

    //Genera los gráficos de los semáforos
    public List<JFreeChart> crearGraficaSemaforoProgramaEducativo(String criterio, int noRACT, int idUA,FiltrosBeanHelper filtrosBeanHelper) {
        List<JFreeChart> resultados = new ArrayList<JFreeChart>();
        List<Programaeducativo> programasEducativos = new ArrayList();
    
//        ProgramaEducativoDelegate programaeducativoDelegate = new ProgramaEducativoDelegate();

//        UnidadaprendizajeImparteProfesorDelegate uaipDelegate = new UnidadaprendizajeImparteProfesorDelegate();
//        ReporteavancecontenidotematicoDelegate reporteavanceDelegate = new ReporteavancecontenidotematicoDelegate();
//
//        CicloEscolarDelegate ceDelegate = new CicloEscolarDelegate();
        Cicloescolar cesActual = ServiceFacadeLocator.getInstanceCicloEscolarFacade().cicloescolarActual();

        DefaultCategoryDataset datasetCat = new DefaultCategoryDataset();
        
        String ractLabel = "";
        if (noRACT != 0 && noRACT != 4) {
            ractLabel = "RACT " + (String.valueOf(noRACT));
        } else {
            ractLabel = "Todos los RACT";
        }

        double auxInt = 0;
        double auxInt2 = 0;
        
        List<Object> lista;
        List<String> ces = new ArrayList<>();
        ces.add(cesActual.getCEScicloEscolar());
        List<ProgramaEducativoReportes> reportes = new ArrayList<>();
        for (Programaeducativo programa : filtrosBeanHelper.getProgramaeducativoByUA(32)) {
            ProgramaEducativoReportes obj = new ProgramaEducativoReportes();
            System.out.println("Entro PE : "+programa.getPEDnombre());
            
            
            obj.setCountEntregados(filtrosBeanHelper.countReportesDeProgramaEducativo(programa.getPEDid(),noRACT,"Entregados",ces));
          
            obj.setCountEsperados(filtrosBeanHelper.countEsperadosProgramaEducativo(programa.getPEDid(),4,ces ));
            
            obj.setPe(programa);
            reportes.add(obj );
            
        }
        Collections.sort(reportes,new SortbyPEClave());
       
        //List<Object[]> reportes = ServiceLocator.getInstanceEsperados().contarSemaforoProgEdValor(idUA, cesActual.getCesid().toString());
        System.out.println("MeterChartDemo.java:261  // Entrando "+reportes.size());
        for (ProgramaEducativoReportes reporte : reportes) {

            //Determina si se maneja un Ract en específico o se hará de manera general
            //Asigna los valores a utilizar
//            if (noRACT != 0 && noRACT != 4) {
////                auxInt = Integer.parseInt(reporte[4+noRACT].toString());
////                dataset.addValue(auxInt, ractLabel, reporte[2].toString());
////                auxInt2 = (Integer.parseInt(reporte[3].toString()))/3;
//            } else {
////                auxInt = Integer.parseInt(reporte[4].toString());
////                dataset.addValue(auxInt, ractLabel, reporte[2].toString());
//                
////                datasetCat.addValue(auxInt, ractLabel, reporte.getPe().getPednombre());
//            }

            auxInt = reporte.getCountEntregados();

                auxInt2 = reporte.getCountEsperados();
                
                if(auxInt2==0)
                    continue;

//            datasetCat.addValue(auxInt2, "esperados", reporte.getPe().getPednombre());
            
//            DefaultValueDataset value;
            double value=0;
            if (auxInt2 != 0 || auxInt!=0) {
//                value = new DefaultValueDataset((auxInt / auxInt2) * 100);
                value = (auxInt/(auxInt2* (noRACT==4? 3:1)))*100;
            } else {
//                value = new DefaultValueDataset(0);
            
            }
            
            
            System.out.println("MeterChartDemo.java:284  // SE HA AGREGADO EL VALOR " + auxInt + " /  " + auxInt2 + "  --  " + (auxInt2>0 ? (auxInt/(auxInt2*3.0))*100: 0) );
            System.out.println("Division del : "+reporte.getPe().getPEDnombre()+" = "+value);
            
            MeterPlot plot = new MeterPlot(new DefaultValueDataset(value));
           
            
            ////////    Ciclos para poner etiqueta por cada raya a traves de intervalo de 5 ///////////// -- Alex Cota
            plot.setRange(new Range(0, 100));
            for(int x=0; x<=25; x++){
                if((x%5==0 && x!=0) || x==25){
                    
            plot.addInterval(new MeterInterval("Battery LOW", new Range(x-5, x),
                    Color.red, new BasicStroke(2.0F), new Color(255, 0, 0, 128)));
                }
            }
 
            for(int x=30; x<=75; x++){
                if(x%5==0 || x==75){
                plot.addInterval(new MeterInterval("Moderate", new Range(x-5, x),
                    Color.yellow, new BasicStroke(2.0F), new Color(255, 255, 0, 64)));
            }
            }
            for(int x=80; x<=100; x++){
                if(x%5==0 || x==100){
                    plot.addInterval(new MeterInterval("Battery FULL", new Range(x-5, x),
                    Color.green, new BasicStroke(2.0F), new Color(0, 255, 0, 64)));
                }
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
            
            plot.setNeedlePaint(Color.darkGray);
            plot.setDialBackgroundPaint(Color.white);
            plot.setDialOutlinePaint(Color.black);
            plot.setDialShape(DialShape.PIE);
            plot.setUnits("% Recibido");
            plot.setMeterAngle(180);
            plot.setTickLabelsVisible(true);
            plot.setTickLabelFont(new Font("Arial", 1, 15));
            plot.setTickLabelPaint(Color.black);
            plot.setTickSize(5D);
            plot.setTickPaint(Color.gray);
            plot.setValuePaint(Color.black);
            plot.setValueFont(new Font("Arial", 1, 20));

            plot.setDialOutlinePaint(Color.white);
            JFreeChart chart = new JFreeChart(reporte.getPe().getPEDnombre(),
                    JFreeChart.DEFAULT_TITLE_FONT, plot, false);
            resultados.add(chart);

        }

//        for(String aux:clavesProgramas){
//               
//                programasEducativos.add(programaeducativoDelegate.findProgramaEducativoByClave(aux));
//            }
//        for (int i = 0; i < programasEducativos.size(); i++) {
//            if(noRACT==0){
//              for(int j=0;j<3;j++){
//
//            auxInt=reporteavanceDelegate.countReportesDeProgramaEducativo(programasEducativos.get(i).getPedid(), j+1, criterio);
//            dataset.addValue(auxInt, "RACT "+(String.valueOf(j+1)), programasEducativos.get(i).getPednombre());
//            }
//            }else{

//        }
        // create the chart...
//        final JFreeChart chart = ChartFactory.createLineChart(
//            "Grafica de Lineas",      // chart title
//            "Programa educativo",                      // x axis label
//            "RACT",                      // y axis label
//            dataset,                  // data
//            PlotOrientation.VERTICAL,
//            true,                     // include legend
//            true,                     // tooltips
//            false                     // urls
//        );
//                chart.getCategoryPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        return resultados;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        dataset = new DefaultValueDataset(50.0);
        JFreeChart chart = createChart(dataset);
        JPanel panel = new JPanel(new BorderLayout());
        JSlider slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider) e.getSource();
                dataset.setValue(new Integer(s.getValue()));
            }
        });
        panel.add(new ChartPanel(chart));
        panel.add(BorderLayout.SOUTH, slider);
        return panel;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {

    }

}
