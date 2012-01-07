package de.openedu.serialconnect.lib;
public interface PlotterInterface {

	public void showPlotter();
	public void hidePlotter();
	public void updateData(double[] x, double[] y);
	public void updateData(double[] x, double[] y, double marksX, double marksY);
	public void updateData(double[] x, double[] y, double[] marksX, double[] marksY);
	public void addFunctionValue(double x, double y);
	public void addPoint(double x, double y);
	public void clearFunction();
	public void clearPoints();
	public void message(String messageText);
}