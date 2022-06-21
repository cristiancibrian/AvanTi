package mx.avanti.siract.ui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import mx.avanti.siract.business.integration.ServiceFacadeLocator;
import mx.avanti.siract.common.EntidadSemaforo;
import mx.avanti.siract.common.ResumenReporte;
//import mx.avanti.business.delegate.AreaAdministrativaDelegate;
//import mx.avanti.business.delegate.AreaConocimientoDelegate;
//import mx.avanti.business.delegate.ProfesorDelegate;
//import mx.avanti.business.delegate.ProgramaEducativoDelegate;
//import mx.avanti.business.delegate.ReporteavancecontenidotematicoDelegate;
//import mx.avanti.business.delegate.UnidadAprendizajeDelegate;
//import mx.avanti.business.delegate.UnidadaprendizajeImparteProfesorDelegate;
import mx.avanti.siract.entity.Areaadministrativa;
import mx.avanti.siract.entity.Areaconocimiento;
import mx.avanti.siract.entity.Planestudio;
import mx.avanti.siract.entity.Profesor;
import mx.avanti.siract.entity.Programaeducativo;
import mx.avanti.siract.entity.Unidadaprendizaje;
import mx.avanti.siract.integration.ServiceLocator;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joda.time.chrono.StrictChronology;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 * A simple demonstration application showing how to create a bar chart.
 *
 */
public class BarChartCL {

    private static final int HIGH = 350;
    private static final int WIDTH = 600;
    private static final String SHEET_NAME = "Graficos";
    private static final int POSITION_X = 1;
    private static final int POSITION_Y = 13;
    private static final int CHART_WIDTH = 14 + POSITION_X; //numero de columnas
    private static final int CHART_HIGH = 23 + POSITION_Y; //numero de filas
    ArrayList<String> entregados;
    String title;
    String excel;
    double min = 0, max;
    //Codigo por Jesus Pio
    private BarChartModel barModel;
    private PieChartModel pieModel;

    public BarChartCL() {

    }

    public BarChartCL(final String title, ArrayList<String> entregados, String excel) {
        this.title = title;
        this.excel = excel;
        this.entregados = entregados;
        for (int x = 0; x < entregados.size(); x++) {
            System.out.println("Se muestran entregados");
            System.out.println(entregados.get(x));
        }
        // final CategoryDataset dataset = createDataset();
        // final JFreeChart chart = createChart(dataset);
        // this.entregados = entregados;

    }

    public BarChartCL(final String title, String excel) {
        this.title = title;
        this.excel = excel;

        // final CategoryDataset dataset = createDataset();
        // final JFreeChart chart = createChart(dataset);
        // this.entregados = entregados;
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    public CategoryDataset createDataset() {
        System.out.println("dataset");
        // row keys...
        final String series1 = "Total de RACT Entregados";
        final String series2 = "Total de RACT Esperados";

        // column keys...
        ArrayList<String> category = new ArrayList<String>();
        ArrayList<Integer> Ent = new ArrayList<Integer>();
        ArrayList<Integer> Esp = new ArrayList<Integer>();
        // ArrayList<Double> porc = new ArrayList<Double>();
        for (int x = 0; x < entregados.size(); x++) {
            String entregado = entregados.get(x);
            String[] aux = entregado.split("-");
            category.add(aux[0]);
            Ent.add(Integer.parseInt(aux[1]));
            Esp.add(Integer.parseInt(aux[2]));
        }
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int x = 0; x < category.size(); x++) {
            System.out.println(x);
            dataset.addValue(Ent.get(x), series1, category.get(x));
            dataset.addValue(Esp.get(x), series2, category.get(x));
            if (max < Esp.get(x)) {
                max = Esp.get(x);
            }
        }
        max = max + 10;
        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
    public JFreeChart createChart(final CategoryDataset dataset) {
        System.out.println("createChart");
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                "ProgramaEducativo", // domain axis label
                "Cantidad de RACT", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.BLUE);
        plot.setRangeGridlinePaint(Color.BLACK);

        //style customisation
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.035);
        BarRenderer.setDefaultShadowsVisible(true);
        System.setProperty("green", "0X009000");
        Color myColor1 = Color.getColor("green");
        System.setProperty("blue", "0X0000BE");
        Color myColor2 = Color.getColor("green");
        System.setProperty("shadow", "0X848484");
        Color myshadow = Color.getColor("shadow");
        renderer.setSeriesPaint(0, Color.getColor("green"));
        renderer.setSeriesPaint(1, Color.getColor("blue"));
        plot.setShadowGenerator(new DefaultShadowGenerator(10, myshadow, (float) 0.45, 10, 10));

        //font customisation
        final Font titleFont = new Font("calibri", 0, 20);
        chart.getTitle().setFont(titleFont);
        CategoryAxis axis = plot.getDomainAxis();
        ValueAxis axis2 = plot.getRangeAxis();
        Font font = new Font("calibri", 0, 15);
        axis.setTickLabelFont(font);//Font de category
        Font font2 = new Font("calibri", 0, 15);
        axis2.setTickLabelFont(font2);
        Font font3 = new Font("calibri", 0, 15);
        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);
        LegendTitle legend = chart.getLegend();
        legend.setBorder(0, 0, 0, 0);
        DecimalFormat decimalformat1 = new DecimalFormat("##,###");
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1));
        renderer.setItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)//inclinacion de las categorias
        );
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(min, max);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    /**
     * Este metoo agregara la grafica el libro de excel
     *
     * @param chart
     * @param wb
     * @throws Exception
     */
    public void addChartToExcel(JFreeChart chart, HSSFWorkbook wb, int renglon) throws Exception {
        final BufferedImage buffer = chart.createBufferedImage(WIDTH, HIGH);
        //final FileOutputStream file = new FileOutputStream(excel+".xls");

        ByteArrayOutputStream img_bytes = new ByteArrayOutputStream();
        ImageIO.write(buffer, "png", img_bytes);
        img_bytes.flush();
        // renglon el numero de renglon en que se encuentra el ultimo registro de la 
        //tabla y se pone aqui para la grafica se inserte debajo de la tabla
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) POSITION_X, renglon + 2, (short) 8, renglon +30);
        int index = wb.addPicture(img_bytes.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
        HSSFSheet sheet = wb.getSheet(SHEET_NAME);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        patriarch.createPicture(anchor, index);

    }

    public void runGrap(HSSFWorkbook wb) {
        ArrayList<String> Plan = new ArrayList<String>();
        ArrayList<String> Programa = new ArrayList<String>();
        ArrayList<String> Ciclo = new ArrayList<String>();
        ArrayList<String> AC = new ArrayList<String>();
        ArrayList<String> UnidadApren = new ArrayList<String>();
        ArrayList<String> entregados;
        //----------------------------------------------------------------------
        String UnidadA = "Ingeniería";
        Plan.add("2009-2");
        AC.add("Administrativa");
        AC.add("Programacion e Ingeniera de Software");
        AC.add("Contable");
        AC.add("Tratamiento de la Informacion");
        AC.add("Redes y Arquitectura de Computadoras");
        AC.add("Matematicas");
        Programa.add("Licenciado en Sistemas Computacionales");
        Programa.add("Ingeniería Civil");
        Ciclo.add("2009-2");
        Ciclo.add("2013-5");
        Ciclo.add("2013-2");
        Ciclo.add("2013-1");
        Ciclo.add("2012-5");
        Ciclo.add("2012-2");
        Ciclo.add("2012-1");
        Ciclo.add("2011-5");
        Ciclo.add("2011-2");
        Ciclo.add("2011-1");
        UnidadApren.add("Introducción a los sistemas computacionales");
        UnidadApren.add("Contabilidad");
        UnidadApren.add("Introducción a la programación");
        UnidadApren.add("Programación estructurada");
        int op = 1;
    }

    //Generar solo tabla
    //Jesus Pio
    public BarChartModel generarGraficaDeBarras(int x, int y, String reporte) {

        BarChartModel model2 = new BarChartModel();
        ChartSeries enviados = new ChartSeries();
        enviados.setLabel("Enviados");
        enviados.set("Enviados", x);

        ChartSeries esperados = new ChartSeries();
        esperados.setLabel("Esperados");
        esperados.set("Esperados", y);

        int w = y - x;
        ChartSeries noEnviados = new ChartSeries();
        noEnviados.setLabel("No enviados");
        noEnviados.set("No enviados", w);

        if (reporte.equals("entregados")) {
            model2.addSeries(enviados);
            model2.addSeries(esperados);
        }
        if (reporte.equals("noentregados")) {
            model2.addSeries(esperados);
            model2.addSeries(noEnviados);
        }
        if (reporte.equals("entregadosynoentregados")) {
            model2.addSeries(esperados);
            model2.addSeries(enviados);
            model2.addSeries(noEnviados);

        }

        return model2;
    }

    public PieChartModel createPieModel(int x, int y, String reporte) {
        int porEnv = (x * 100) / y;
        int porNoEnv = ((y - x) * 100) / y;
        if (reporte.equals("Porcentaje de Avance Global Completo")) {
            pieModel = new PieChartModel();
            pieModel.set("Esperados", 100 - porEnv);
            pieModel.set("Enviados", porEnv);
        }
        if (reporte.equals("Porcentaje de Avance Global Incompleto")) {
            pieModel = new PieChartModel();
            pieModel.set("Esperados", 100 - porNoEnv);
            pieModel.set("No enviados", porNoEnv);
        }
        if (reporte.equals("Porcentaje de Avance Global Completo e Incompleto")) {
            pieModel = new PieChartModel();
            pieModel.set("No enviados", 100 - porEnv);
            pieModel.set("Enviados", porEnv);
        }

        pieModel.setTitle(reporte);
        pieModel.setLegendPosition("w");
        pieModel.setShadow(false);
        return pieModel;
    }

    public BarChartModel createBarModel(int x, int y, String reporte) {

        barModel = generarGraficaDeBarras(x, y, reporte);

        barModel.setTitle(reporte);
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Programa Educativo");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("RACT");
        yAxis.setMin(0);
        yAxis.setMax(y);

        return barModel;
    }

    public BarChartModel getModel() {
        return barModel;
    }

    public void setModel(BarChartModel model) {
        this.barModel = model;
    }

    //noRACT 0 = Todos
    /**
     * Metodo para crear la grafica de que se pondra en el excel del criterio de
     * programa educativo
     *
     * @param criterio entregados, no entregados...ETC
     * @param noRACT numero de ract del cual se va a generar la grafica
     * @param programasEducativos Lista de programas educativos que se
     * seleccionaron
     * @return
     */
    public JFreeChart crearGraficaBarrasProgramaEducativo(String criterio, String noRACT, List<Programaeducativo> programasEducativos, int idCicloEscolar, Vector entregadosRactSeleccionado) {
        // el tipo de datos para crear la grafica
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // titulo de la grafica 
        String ractLabel = getLabelByCriteria(criterio);

        int noVector = 0;
        int entregados = 0;
        int esperados = 0;
        /**
         * de nuevo reccorro los programas educativos y cada uno de sus planes
         * de estudio
         */
        for (Programaeducativo programa : programasEducativos) {
            for (Planestudio planE : programa.getPlanestudioList()) {
                List<Planestudio> plan = new ArrayList();
                /**
                 * Creo una nueva lista de planes de estudio porque en este caso
                 * el metodo que uso para calcular entregados y esperados
                 * utiliza una lista de todos los planes pero en este caso se
                 * ira calculando uno por uno
                 */
                plan.add(planE);
                // *** el if siguiente es por si tiene grupos en el ciclo que se busca se agregara a la grafica
                // ** en caso contrario no lo tomara en cuenta 
//               if (ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().countUaipPorAreaConocimientoYcicloEscolar(planE.getPESid(), idCicloEscolar) != 0) {


                if (criterio.equalsIgnoreCase("entregados") || criterio.equalsIgnoreCase("noentregados") || criterio.equalsIgnoreCase("entregadosynoentregados")) {
                    esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProgramaEducativoRACT(plan, String.valueOf(idCicloEscolar));
                    /**
                     * Si se selecciono todos los ract entra a la siguiente
                     * validacion trae los entregados por todos los ract
                     */
                    if (!noRACT.equalsIgnoreCase("4")) {
                        entregados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().enviadosPorProgramaEducativoRACT(plan, noRACT, String.valueOf(idCicloEscolar));
                        if (criterio.equalsIgnoreCase("noentregados")) {
                            entregados = esperados - entregados;
                        }
                        System.out.println("Soy la tabla e imprimire: " + entregados);
                        dataset.addValue(entregados, ractLabel, planE.getPESvigenciaPlan() + " " + programa.getPEDnombre());
                    } else {
                        entregados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().enviadosPorProgramaEducativoGeneral(plan, String.valueOf(idCicloEscolar));
                        esperados *= 3;
                        if (criterio.equalsIgnoreCase("noentregados")) {
                            entregados = esperados - entregados;
                        }
                        dataset.addValue(entregados, ractLabel, planE.getPESvigenciaPlan() + " " + programa.getPEDnombre());

                    }

                    dataset.addValue(esperados, "Esperados", planE.getPESvigenciaPlan() + " " + programa.getPEDnombre());
                }
                
                else if(criterio.equalsIgnoreCase("Porcentaje de Avance Global Completo") || criterio.equalsIgnoreCase("Porcentaje de Avance Global Incompleto") || criterio.equalsIgnoreCase("Porcentaje de Avance Global Completo e Incompleto")) {
                    esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProgramaEducativoRACT(plan, String.valueOf(idCicloEscolar));
                    if(esperados>0){
                        
                        entregados = (int) entregadosRactSeleccionado.elementAt(noVector);
                        noVector++;
                        esperados = ServiceFacadeLocator.getInstanceUnidadAprendizajeImparteProfesorFacade().esperadosPorProgramaEducativoRACT(plan, String.valueOf(idCicloEscolar));
                        dataset.addValue(entregados, ractLabel, planE.getPESvigenciaPlan() + " " + programa.getPEDnombre());
                        dataset.addValue(esperados, "Esperados", planE.getPESvigenciaPlan() + " " + programa.getPEDnombre());
                    }
                }
//              
            }

        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Programa educativo", // x axis label
                "RACT", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.getCategoryPlot()
                .getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chart.getCategoryPlot()
                .getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.getCategoryPlot()
                .getDomainAxis().setMaximumCategoryLabelLines(3);

        return chart;
    }

    public JFreeChart crearGraficaBarrasAreaConocimiento(String criterio, String noRACT, List<ResumenReporte> resumenes, int idCicloEscolar) {
        // el tipo de datos para crear la grafica
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // titulo de la grafica 
        String ractLabel = getLabelByCriteria(criterio);

        int entregados = 0;
        int esperados = 0;
        /**
         * de nuevo reccorro los programas educativos y cada uno de sus planes
         * de estudio
         */
        for (ResumenReporte area : resumenes) {

            esperados = area.getNumEsperados();

            /**
             * Si se selecciono todos los ract entra a la siguiente validacion
             * trae los entregados por todos los ract
             */
            if (!noRACT.equalsIgnoreCase("4")) {
                entregados = area.getNumEntregados();
                dataset.addValue(entregados, ractLabel, area.getNombreAreaConocimiento());
            } else {
                entregados = area.getNumEntregados();
                dataset.addValue(entregados, ractLabel, area.getNombreAreaConocimiento());
             
            }

            dataset.addValue(esperados, "Esperados", area.getNombreAreaConocimiento());
//                }
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Área de conocimiento", // x axis label
                "RACT", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.getCategoryPlot()
                .getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chart.getCategoryPlot()
                .getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.getCategoryPlot()
                .getDomainAxis().setMaximumCategoryLabelLines(3);

        return chart;
    }
    
    
    public JFreeChart crearGraficaBarrasAreaAdministrativa(String criterio, String noRACT, List<ResumenReporte> resumenes, int idCicloEscolar) {
        // el tipo de datos para crear la grafica
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // titulo de la grafica 
        String ractLabel = getLabelByCriteria(criterio);

        int entregados = 0;
        int esperados = 0;
        /**
         * de nuevo reccorro los programas educativos y cada uno de sus planes
         * de estudio
         */
        for (ResumenReporte area : resumenes) {

            esperados = area.getNumEsperados();

            /**
             * Si se selecciono todos los ract entra a la siguiente validacion
             * trae los entregados por todos los ract
             */
            if (!noRACT.equalsIgnoreCase("4")) {
                entregados = area.getNumEntregados();
                dataset.addValue(entregados, ractLabel, area.getNombreAreaAdministrativa());
            } else {
                entregados = area.getNumEntregados();
                dataset.addValue(entregados, ractLabel, area.getNombreAreaAdministrativa());
            
            }

            dataset.addValue(esperados, "Esperados", area.getNombreAreaAdministrativa());
//                }
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Área de conocimiento", // x axis label
                "RACT", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.getCategoryPlot()
                .getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chart.getCategoryPlot()
                .getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.getCategoryPlot()
                .getDomainAxis().setMaximumCategoryLabelLines(3);

        return chart;
    }
    
    
    public JFreeChart crearGraficaBarrasUnidadAprendizaje(String criterio, String noRACT, List<ResumenReporte> resumenes, int idCicloEscolar) {
        // el tipo de datos para crear la grafica
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // titulo de la grafica 
        String ractLabel = getLabelByCriteria(criterio);

        int entregados = 0;
        int esperados = 0;
        /**
         * de nuevo reccorro los programas educativos y cada uno de sus planes
         * de estudio
         */
        for (ResumenReporte unidad : resumenes) {

            esperados = unidad.getNumEsperados();

            /**
             * Si se selecciono todos los ract entra a la siguiente validacion
             * trae los entregados por todos los ract
             */
            if (!noRACT.equalsIgnoreCase("4")) {
                entregados = unidad.getNumEntregados();
                dataset.addValue(entregados, ractLabel, unidad.getNombreUnidadAprendizaje());
            } else {
                entregados = unidad.getNumEntregados();
                dataset.addValue(entregados, ractLabel, unidad.getNombreUnidadAprendizaje());
             
            }

            dataset.addValue(esperados, "Esperados", unidad.getNombreUnidadAprendizaje());
//                }
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Área de conocimiento", // x axis label
                "RACT", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.getCategoryPlot()
                .getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chart.getCategoryPlot()
                .getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.getCategoryPlot()
                .getDomainAxis().setMaximumCategoryLabelLines(3);

        return chart;
    }

    public JFreeChart crearGraficaBarrasProfesor(String criterio, String noRACT, List<ResumenReporte> resumenes, int idCicloEscolar) {
        // el tipo de datos para crear la grafica
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // titulo de la grafica 
        String ractLabel = getLabelByCriteria(criterio);

        int entregados = 0;
        int esperados = 0;
        /**
         * de nuevo reccorro los programas educativos y cada uno de sus planes
         * de estudio
         */
        for (ResumenReporte profesor : resumenes) {

            esperados = profesor.getNumEsperados();

            /**
             * Si se selecciono todos los ract entra a la siguiente validacion
             * trae los entregados por todos los ract
             */
            if (!noRACT.equalsIgnoreCase("4")) {
                entregados = profesor.getNumEntregados();
                dataset.addValue(entregados, ractLabel, profesor.getNombreProfesor());
            } else {
                entregados = profesor.getNumEntregados();
                dataset.addValue(entregados, ractLabel, profesor.getNombreProfesor());
                
            }

            dataset.addValue(esperados, "Esperados", profesor.getNombreProfesor());
//                }
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Área de conocimiento", // x axis label
                "RACT", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        chart.getCategoryPlot()
                .getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        chart.getCategoryPlot()
                .getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.getCategoryPlot()
                .getDomainAxis().setMaximumCategoryLabelLines(3);

        return chart;
    }
    // create the chart...
//    public JFreeChart crearGraficaBarrasAreasConocimiento(String criterio, int noRACT, List<String> clavesAreasConocimiento, List<String> selectCicloEscolarList, String pedClave) {
//        List<Areaconocimiento> areasConocimiento = new ArrayList();
////         AreaConocimientoDelegate areasConocimientoDelegate=new AreaConocimientoDelegate();
//
//        for (String aux : clavesAreasConocimiento) {
//            // areasConocimiento.add(areasConocimientoDelegate.findAreaConocimientoByClave(aux));
//            areasConocimiento.add(ServiceFacadeLocator.getInstanceAreaConocimientoFacade().findAreaConocimientoByClave(aux));
//        }
////        UnidadaprendizajeImparteProfesorDelegate uaipDelegate=new UnidadaprendizajeImparteProfesorDelegate();
////        ReporteavancecontenidotematicoDelegate reporteavanceDelegate=new ReporteavancecontenidotematicoDelegate();
//
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        String ractLabel = getLabelByCriteria(criterio);
//
//        int auxInt = 0;
//        int auxInt2 = 0;
//
//        for (int i = 0; i < areasConocimiento.size(); i++) {
////            if(noRACT==0){
////              for(int j=0;j<3;j++){
////
////            auxInt=reporteavanceDelegate.countReportesDeAreaConocimientos(areasConocimiento.get(i).getAcoid(), j+1, criterio);
////            dataset.addValue(auxInt, "RACT "+(String.valueOf(j+1)), areasConocimiento.get(i).getAconombre());
////            }
////            }else{
////            auxInt=reporteavanceDelegate.countReportesDeAreaConocimientos(areasConocimiento.get(i).getAcoid(),noRACT, criterio,selectCicloEscolarList,pedClave);
//            auxInt = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countReportesDeAreaConocimientos(areasConocimiento.get(i).getAcoid(), noRACT, criterio, selectCicloEscolarList, pedClave);
//            dataset.addValue(auxInt, ractLabel, areasConocimiento.get(i).getAconombre());
//
//            //       auxInt2=reporteavanceDelegate.countEsperadosAreaConocimientos(areasConocimiento.get(i).getAcoid(), noRACT,selectCicloEscolarList,pedClave);
//            auxInt2 = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countEsperadosAreaConocimientos(areasConocimiento.get(i).getAcoid(), noRACT, selectCicloEscolarList, pedClave);
//            System.out.println("VALOR DE AUX INT----------" + auxInt2 + "---------" + areasConocimiento.get(i).getAcoid() + "--------" + criterio + "------------" + selectCicloEscolarList);
//
//            if (noRACT != 0 && noRACT != 4) {
//
//            } else {
//                auxInt2 *= 3;
//            }
//            dataset.addValue(auxInt2, "Esperados", areasConocimiento.get(i).getAconombre());
////            }
//        }
//
//        // create the chart...
//        final JFreeChart chart = ChartFactory.createBarChart(
//                "Gráfica de barras", // chart title
//                "Área de conocimiento", // x axis label
//                "RACTS", // y axis label
//                dataset, // data
//                PlotOrientation.VERTICAL,
//                true, // include legend
//                true, // tooltips
//                false // urls
//        );
//
//        chart.getCategoryPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//        //COLORES QUE SE ASIGNARAN SI HAY MAS DE UNA LINEA
//        if (noRACT == 0) {
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 0, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(0, 255, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, new Color(0, 0, 255, 155));
//        }
//        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
//        chart.setBackgroundPaint(Color.white);
//        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(3);
//        return chart;
//    }
//
//crearGraficaBarrasUnidad                                                                                  
    public JFreeChart crearGraficaBarrasUnidadAprendizaje(List<EntidadSemaforo> entidades, String reporte, String ract) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String ractLabel = getLabelByCriteria(reporte);
        int entregados = 0;
        int esperados = 0;
        int valor;
        Planestudio plan;

        for (EntidadSemaforo entS : entidades) {
            dataset.addValue(entS.getNumEnviados(), ractLabel, entS.getNombreUnidad());
            dataset.addValue(entS.getNumEsperados(), "Esperados", entS.getNombreUnidad());
        }

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de barras", // chart title
                "Unidad de aprendizaje", // x axis label
                "NO. RACT'S", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );
        chart.getCategoryPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //COLORES QUE SE ASIGNARAN SI HAY MAS DE UNA LINEA
        if (ract.equalsIgnoreCase("0")) {
            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 0, 0, 155));
            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(0, 255, 0, 155));
            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, new Color(0, 0, 255, 155));
        }
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        chart.setBackgroundPaint(Color.white);
        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(3);
        return chart;
    }

//    public JFreeChart crearGraficaBarrasProfesor(String criterio, int noRACT, List<String> listaNumEmpProfesores, List<String> listaClavesUA, List<String> listaClavesGrupos, List<String> selectCicloEscolarList) {
//        String clavesGrupo = "";
//        String clavesUA = "";
//        List<Profesor> profesores = new ArrayList();
////         ProfesorDelegate profesorDelegate=new ProfesorDelegate();
//
//        for (String claveGrupo : listaClavesGrupos) {
//            if (clavesGrupo.isEmpty()) {
//                clavesGrupo += claveGrupo;
//            } else {
//                clavesGrupo += "," + claveGrupo;
//            }
//        }
//
//        for (String claveUA : listaClavesUA) {
//            if (clavesUA.isEmpty()) {
//                clavesUA += claveUA;
//            } else {
//                clavesUA += "," + claveUA;
//            }
//        }
//        String extraQuery = "";
//        if (!clavesGrupo.isEmpty()) {
//            extraQuery += " AND  a.unidadaprendizajeImparteProfesor.grupo.gponumero IN (" + clavesGrupo + ")";
//        }
//        if (!clavesUA.isEmpty()) {
//            extraQuery += " AND  a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave IN (" + clavesUA + ")";
//        }
//        for (String aux : listaNumEmpProfesores) {
////////                profesores.add(profesorDelegate.findProfesorByNumEmp(aux));
//            profesores.add(ServiceFacadeLocator.getInstanceProfesorFacade().findProfesorByNumEmp(aux));
//        }
////        UnidadaprendizajeImparteProfesorDelegate uaipDelegate=new UnidadaprendizajeImparteProfesorDelegate();
////        ReporteavancecontenidotematicoDelegate reporteavanceDelegate=new ReporteavancecontenidotematicoDelegate();
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        String ractLabel = getLabelByCriteria(criterio);
//        int auxInt = 0;
//        int auxInt2 = 0;
//        for (int i = 0; i < profesores.size(); i++) {
//            extraQuery = "";
//            if (!clavesGrupo.isEmpty()) {
//                extraQuery += " AND  a.unidadaprendizajeImparteProfesor.grupo.gponumero IN (" + clavesGrupo + ")";
//            }
//            if (!clavesUA.isEmpty()) {
//                extraQuery += " AND  a.unidadaprendizajeImparteProfesor.unidadaprendizaje.uapclave IN (" + clavesUA + ")";
//            }
////            auxInt=reporteavanceDelegate.countReportesDeProfesor(profesores.get(i).getProid().toString(), noRACT, criterio,extraQuery,selectCicloEscolarList);
//            auxInt = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countReportesDeProfesor(profesores.get(i).getProid().toString(), noRACT, criterio, extraQuery, selectCicloEscolarList);
//            dataset.addValue(auxInt, ractLabel, profesores.get(i).getPronombre());
//
//            extraQuery = "";
//            if (!clavesGrupo.isEmpty()) {
//                extraQuery += " AND  a.grupo.gponumero IN (" + clavesGrupo + ")";
//            }
//            if (!clavesUA.isEmpty()) {
//                extraQuery += " AND  a.unidadaprendizaje.uapclave IN (" + clavesUA + ")";
//            }
////            auxInt2=reporteavanceDelegate.countEsperadosProfesor(profesores.get(i).getProid(), noRACT,extraQuery,selectCicloEscolarList);
//            auxInt2 = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countEsperadosProfesor(profesores.get(i).getProid(), noRACT, extraQuery, selectCicloEscolarList);
//            if (noRACT != 0 && noRACT != 4) {
//
//            } else {
//                auxInt2 *= 3;
//            }
//            dataset.addValue(auxInt2, "Esperados", profesores.get(i).getPronombre());
//        }
//
//        // create the chart...
//        final JFreeChart chart = ChartFactory.createBarChart(
//                "Gráfica de barras", // chart title
//                "Profesor", // x axis label
//                "RACTS", // y axis label
//                dataset, // data
//                PlotOrientation.VERTICAL,
//                true, // include legend
//                true, // tooltips
//                false // urls
//        );
//
//        chart.getCategoryPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        //COLORES QUE SE ASIGNARAN SI HAY MAS DE UNA LINEA
//        if (noRACT == 0) {
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 0, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(0, 255, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, new Color(0, 0, 255, 155));
//        }
//        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
//        chart.setBackgroundPaint(Color.white);
//        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(3);
//        return chart;
//    }
//
//    public JFreeChart crearGraficaBarrasAreasAdministrativas(String criterio, int noRACT, List<String> clavesAreasAdministrativas, List<String> selectCicloEscolarList, String pedClave) {
//        List<Areaadministrativa> areasConocimiento = new ArrayList();
////         AreaAdministrativaDelegate areasAdministrativaDelegate=new AreaAdministrativaDelegate();
//
//        for (String aux : clavesAreasAdministrativas) {
////                areasConocimiento.add(areasAdministrativaDelegate.findAreaAdById(Integer.parseInt(aux)));
//            areasConocimiento.add(ServiceFacadeLocator.getInstanceAreaAdministrativaFacade().findAreaAdById(Integer.parseInt(aux)));
//        }
////        UnidadaprendizajeImparteProfesorDelegate uaipDelegate=new UnidadaprendizajeImparteProfesorDelegate();
////        ReporteavancecontenidotematicoDelegate reporteavanceDelegate=new ReporteavancecontenidotematicoDelegate();
//
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        String ractLabel = getLabelByCriteria(criterio);
//        
//        
//        
//        int auxInt = 0;
//        int auxInt2 = 0;
//
//        for (int i = 0; i < areasConocimiento.size(); i++) {
////            if(noRACT==0){
////              for(int j=0;j<3;j++){
////
////            auxInt=reporteavanceDelegate.countReportesDeAreaConocimientos(areasConocimiento.get(i).getAcoid(), j+1, criterio);
////            dataset.addValue(auxInt, "RACT "+(String.valueOf(j+1)), areasConocimiento.get(i).getAconombre());
////            }
////            }else{
////            auxInt=reporteavanceDelegate.countReportesDeAreaAdmin(areasConocimiento.get(i).getAadid(),noRACT, criterio,selectCicloEscolarList,pedClave);
//            auxInt = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countReportesDeAreaAdmin(areasConocimiento.get(i).getAadid(), noRACT, criterio, selectCicloEscolarList, pedClave);
//            dataset.addValue(auxInt, ractLabel, areasConocimiento.get(i).getAadnombre());
//
////              auxInt2=reporteavanceDelegate.countEsperadosAreaAdmin(areasConocimiento.get(i).getAadid(), noRACT,selectCicloEscolarList,pedClave);
//            auxInt2 = ServiceFacadeLocator.getReporteAvanceContenidoTematicoFacade().countEsperadosAreaAdmin(areasConocimiento.get(i).getAadid(), noRACT, selectCicloEscolarList, pedClave);
//            System.out.println("VALOR DE AUX INT----------" + auxInt2 + "---------" + areasConocimiento.get(i).getAadnombre() + "--------" + criterio + "------------" + selectCicloEscolarList);
//
//            if (noRACT != 0 && noRACT != 4) {
//
//            } else {
//                auxInt2 *= 3;
//            }
//            dataset.addValue(auxInt2, "Esperados", areasConocimiento.get(i).getAadnombre());
////            }
//        }
//
//        // create the chart...
//        final JFreeChart chart = ChartFactory.createBarChart(
//                "Gráfica de barras", // chart title
//                "Área Conocimiento", // x axis label
//                "RACTS", // y axis label
//                dataset, // data
//                PlotOrientation.VERTICAL,
//                true, // include legend
//                true, // tooltips
//                false // urls
//        );
//
//        chart.getCategoryPlot().getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//        //COLORES QUE SE ASIGNARAN SI HAY MAS DE UNA LINEA
//        if (noRACT == 0) {
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new Color(255, 0, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(1, new Color(0, 255, 0, 155));
//            chart.getCategoryPlot().getRenderer().setSeriesPaint(2, new Color(0, 0, 255, 155));
//        }
//        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
//        chart.setBackgroundPaint(Color.white);
//        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(3);
//        return chart;
//    }
    private String getLabelByCriteria(String reporte) {
        String nombre = "";
        switch (reporte) {
            case "entregados":
                nombre = "Entregados ";
                break;
            case "noentregados":
                nombre = "No entregados ";
                break;
            case "entregadosynoentregados":
                nombre = "Entregados";
                break;
            case "entregadosatiempo":
                nombre = "Entregados a tiempo ";
                break;
            case "entregadosdespueslimite":
                nombre = "Entregados después de fecha límite ";
                break;
            case "entregadosatiempoenfechalimiteydespues":
                nombre = "Entregados a tiempo y despues de fecha límite ";
                break;
            case "Porcentaje de Avance Global Completo":
                nombre = "Porcentaje de avance global completo ";
                break;
            case "Porcentaje de Avance Global Incompleto":
                nombre = "Porcentaje de avance global incompleto ";
                break;
            case "Porcentaje de Avance Global Completo e Incompleto":
                nombre = "Porcentaje de avance global completo e incompleto ";
                break;
        }
        return nombre;
    }

}


