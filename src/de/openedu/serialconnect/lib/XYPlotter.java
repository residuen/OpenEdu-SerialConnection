package de.openedu.serialconnect.lib;
/*
XYPlotter:
A simple xy-plotter based on JFreeChart.
Get JFreeChart under http://www.jfree.org/jfreechart/
 
Copyright (C) 2010 Karsten Bettray

Dieses Programm ist freie Software. Sie koennen es unter den Bedingungen der GNU General Public License,
wie von der Free Software Foundation veroeffentlicht, weitergeben und/oder modifizieren, entweder gemaess
Version 3 der Lizenz oder (nach Ihrer Option) jeder spaeteren Version.
Die Veroeffentlichung dieses Programms erfolgt in der Hoffnung, dass es Ihnen von Nutzen sein wird, aber
OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FUER
EINEN BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben.
Falls nicht, siehe <http://www.gnu.org/licenses/>.
*/

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// used sources: http://www.java2s.com/Code/Java/Chart/JFreeChartLineChartDemo1.htm
public class XYPlotter extends JFrame implements PlotterInterface
{
	private XYPlot plot = null;
	private XYSeries series1 = null;
	private XYSeries series2 = null;
	private XYSeriesCollection xyDataset = null;
	private JFreeChart chart = null;
	private ChartPanel chartPanel = null;
	private  NumberAxis rangeAxis = null;
	private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	public XYPlotter()
	{
		super("XY-Plotter");

		// initialise the mainwindow
		initFrame(JFrame.EXIT_ON_CLOSE);
		
		createChart();
   }
 	
	public XYPlotter(int closeOp)
	{
		super("XY-Plotter");

		// initialise the mainwindow
		initFrame(closeOp);
		
		createChart();
   }

	/**
	 * Init the mainwindow
	 */
	private void initFrame(int closeOp)
	{
		setDefaultCloseOperation(closeOp);
		setBackground(Color.WHITE);
		setSize(640, 480);
	}
    
    /**
     * Creates a chart and a plotpanel and add it to the mainwindow.
     */
    private void createChart() {
        
        getContentPane().removeAll();
        
    	xyDataset = createDataset("");
    	
        // create the chart...
        chart = ChartFactory.createXYLineChart(
            "",     				 	// chart title	"Line Chart Demo 6"
            "x",						// x axis label
            "f(x)",						// y axis label
            xyDataset,                  // data
            PlotOrientation.VERTICAL,
            true,						// include legend
            true,						// tooltips
            false						// urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        renderer.setSeriesShapesVisible(0, false);	// a thin line will be painted for series1
        renderer.setSeriesLinesVisible(1, false);	// points will be painted for series2
        
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        chartPanel = new ChartPanel(chart);
        getContentPane().add(chartPanel);
    }
    
    /**
     * Creates the dataset.
     * 
     * @return a sample dataset.
     */
    private XYSeriesCollection createDataset(String legendTxt) {

		series1 = new XYSeries("f(x)");
		series2 = new XYSeries("g(x)");
    	
        final XYSeriesCollection dataset = new XYSeriesCollection();
        
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        return dataset;        
    }
    
    /**
     * Set the mainframe visible
     */
	@Override
	public void showPlotter() {
		
		setVisible(true);
 	}
	
    /**
     * Set the mainframe to invisible
     */
	@Override
	public void hidePlotter() {
		
		setVisible(true);
	}

	/**
	 * update data of series1
	 */
	@Override
	public void updateData(double[] x, double[] y) {
		
		// define 2 new xyseries
		XYSeries series1 = new XYSeries("f(x)");
		XYSeries series2 = new XYSeries("g(x)");
    	
    	if(x.length == y.length)
    		for(int i=0; i<x.length; i++)
    			series1.add(x[i], y[i]);
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Array sizes of x and y are not equal!");
    		System.err.println("Array sizes of x and y are not equal!");
    	}
    	
    	updateDataset(series1, series2);
	}
	
	/**
	 * Update data of series1 (function-values) and series2 ( a single markingpoint such like minima, maxima etc.)
	 */
	@Override
	public void updateData(double[] x, double[] y, double markX, double markY) {
		
		updateData(x, y, new double[] { markX }, new double[] { markY });
	}
	/**
	 * 
	 * Update data of series1 (function-values) and series2 (markingpoints such like minima, maxima etc.)
	 */
	@Override
	public void updateData(double[] x, double[] y, double[] marksX, double[] marksY) {
		
		XYSeries series1 = new XYSeries("f(x)");
		XYSeries series2 = new XYSeries("g(x)");
    	
    	if(x.length == y.length)
    		for(int i=0; i<x.length; i++)
    			series1.add(x[i], y[i]);
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Array sizes of x1 and y1 are not equal!");
    		System.err.println("Array sizes of x and y are not equal!");
    	}
    	
    	if(marksX.length == marksY.length)
    		for(int i=0; i<marksX.length; i++)
    			series2.add(marksX[i], marksY[i]);
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Array sizes of marking coordinates are not equal!");
    		System.err.println("Array sizes of marking coordinates are not equal!");
    	}
    	
    	updateDataset(series1, series2);
	}
	
	/**
	 * add the new series to the dataset
	 * @param series1
	 * @param series2
	 */
	private void updateDataset(XYSeries series1, XYSeries series2) {
		
    	// Remove all dataset
		xyDataset.removeAllSeries();
		
		// dereferencing the serieses
		this.series1 = null;
		this.series2 = null;
		
		// adding the new serieses to dataset 
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);
		
		// referencing the serieses
		this.series1 = series1;
		this.series2 = series2;
	}
	
	/**
	 * Adds a new value to the function-plot (the red linechart)
	 */
	@Override
	public void addFunctionValue(double x, double y) {
		
		series1.add(x, y);
	}
	
	/**
	 * Adds a new value to the points-plot (the blue bullets)
	 */
	@Override
	public void addPoint(double x, double y) {
		
		series2.add(x, y);
	}
	
	@Override
	public void message(final String messageText) {
		
		doMessage(messageText);
	}
	
	/**
	 * Clear the series from the linechart
	 */
	@Override
	public void clearFunction() {

		series1.clear();
	}

	/**
	 * Clear the series from the points
	 */
	@Override
	public void clearPoints() {

		series2.clear();
	}

	/**
	 * return the Chartpanel
	 * @return
	 */
 	public ChartPanel getChartPanel() {
 		
		return chartPanel;
	}
 	
	private void doMessage(final String messageText) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				JOptionPane.showMessageDialog(null, messageText);
				System.err.println(messageText);
			}
		} );
	}
	
	private static void printHelp()
	{
		System.out.println("XYPlotter: How to use it:\n");
		System.out.println("java -jar XYPlotter\nnothing will happen, just use it like to see in the examplefiles.\n");
		System.out.println("java -jar XYPlotter \"2,2;3,7.5;4,3\"\npaints a line between these three points\n");
		System.out.println("java -jar XYPlotter \"2,2;3,7.5;4,3\" \"2,6;3,5;4,4\"\npaints a line between the first three points and paints 3 points at (2,5) (3,5) and (4,4)");
	}
	
	/**
	 * call-methods:
	 * 	- java -jar XYPlotter -> nothing will happen
	 * 	- java -jar XYPlotter "2,2;3,7.5;4,3" -> paints a line between these three points
	 * 	- java -jar XYPlotter "2,2;3,7.5;4,3" "2,6;3,5;4,4" -> paints a line between the first three points and paints 3 points at (2,5) (3,5) and (4,4)
	 * @param args
	 */
	public static void main(String[] args) {
		
		XYPlotter plotter = null;
		String[] splitStr = null;
		String[] splitValue = null;
		double[] x1 = null;
		double[] y1 = null;
		double[] x2 = null;
		double[] y2 = null;
		
		if(args.length==0 || (args.length==1 && args[0].contains("-h"))) {
			
			printHelp();
		}
		else
		{
			if(args.length==1 || args.length==2) {
				
				plotter = new XYPlotter();
				
				splitStr = args[0].replaceAll("\"", "").split(";");
				
				x1 = new double[splitStr.length];
				y1 = new double[splitStr.length];
				
				for(int i=0; i<x1.length; i++)
				{
					splitValue =  splitStr[i].split(",");
					
					x1[i] = Double.parseDouble(splitValue[0]);
					y1[i] = Double.parseDouble(splitValue[1]);
				}
				
				plotter.updateData(x1, y1);	// Now give the plotter the results, so it will paint a magic xy-plot
				
				plotter.showPlotter();		// to see the plot, make the frame visible
			
				if(args.length==2) {
					
					splitStr = args[1].replaceAll("\"", "").split(";");
					
					x2 = new double[splitStr.length];
					y2 = new double[splitStr.length];
					
					for(int i=0; i<x2.length; i++)
					{
						splitValue =  splitStr[i].split(",");
						
						x2[i] = Double.parseDouble(splitValue[0]);
						y2[i] = Double.parseDouble(splitValue[1]);
					}
					
					plotter.updateData(x1, y1, x2, y2);
				}
			}
		}
	}
}