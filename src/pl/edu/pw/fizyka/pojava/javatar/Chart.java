package pl.edu.pw.fizyka.pojava.javatar;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.edu.pw.fizyka.pojava.javatar.WaveFunctionCalculation.Sector;

class Chart extends JPanel
{
	private static final long serialVersionUID = -8468635309399154794L;
	private XYSeries goals = new XYSeries(GUI.T.get(TransKey.INTENSITY_VALUE));

	Chart()
	{

		XYDataset xyDataset = new XYSeriesCollection(goals);

		JFreeChart chart = ChartFactory.createXYLineChart(
				GUI.T.get(TransKey.INTENSITY_CHART),
				GUI.T.get(TransKey.SCREEN),
				GUI.T.get(TransKey.INTENSITY),
				xyDataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);
		chart.getXYPlot().getRangeAxis().setAutoRange(false);
		chart.getXYPlot().getRangeAxis().setRange(-0.0001, 0.0001);
		ChartPanel cp = new ChartPanel(chart);
		cp.setPreferredSize(new Dimension(387, 320));
		cp.setMouseWheelEnabled(true);
		add(cp);
	}

	void refresh(Sector[] sector)
	{
		goals.clear();
		for (int i = 0; i < sector.length; i++)
			goals.add(i, sector[i].sum);
	}

}
