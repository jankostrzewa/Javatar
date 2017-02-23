package pl.edu.pw.fizyka.pojava.javatar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import pl.edu.pw.fizyka.pojava.javatar.WaveFunctionCalculation.Sector;

class AnimateBeforeGrating extends JPanel implements Runnable
{
	private static final long serialVersionUID = -705555689585339843L;

	static final int startWallPosition = 200;
	private static final int yWaveStart = 1;
	private static final int yWaveEnd = 271;
	private DistanceToScreen distanceScreenPanel;
	private WavelengthChooser wavelengthChooser;
	private GratingConstantChooser gratingConstantChooser;
	private JCheckBox checkbox;
	private Thread thread;
	private int w;
	private int h;
	private boolean isRunning = false;
	private boolean isFirstRun = true;
	private WaveFunctionCalculation calculation;
	static final float scaleFactor = ((700 - startWallPosition) / 2500f);
	private Sector[][] sector;
	private long currentTime = 0;
	private Chart chart;

	AnimateBeforeGrating(DistanceToScreen distanceScreenPanel, WavelengthChooser wavelengthChooser, GratingConstantChooser gratingConstantChooser, JCheckBox checkbox,
						 Chart chart)
	{
		this.distanceScreenPanel = distanceScreenPanel;
		this.wavelengthChooser = wavelengthChooser;
		this.gratingConstantChooser = gratingConstantChooser;
		this.checkbox = checkbox;
		this.chart = chart;
	}

	void init(WaveFunctionCalculation sc)
	{
		calculation = sc;
		sector = calculation.InitializeSectors();
		w = getPreferredSize().width;
		h = getPreferredSize().height;
		thread = new Thread(this);
		thread.start();
	}

	void start()
	{
		isRunning = true;
		isFirstRun = false;
	}

	void stop()
	{
		isRunning = false;
	}

	public void run()
	{
		while (thread != null)
		{

			repaint();

			try
			{
				Thread.sleep(100);
			} catch (InterruptedException ignored)
			{}
		}
	}

	synchronized public void paint(Graphics g)
	{
		int gratingConst = Math.round(gratingConstantChooser.getGratingValue() * scaleFactor / 2);
		int distanceToScreen = distanceScreenPanel.getDistanceValue();
		int beforeWallDistance = Math.round((780 - (80 + scaleFactor * distanceToScreen)));

		if (isRunning || !isFirstRun)
		{
			double spaceBeetweenWaves = wavelengthChooser.getWavelengthValue() * scaleFactor;

			int distanceBetweenWallAndScreen = Math.round(distanceToScreen * scaleFactor);
			g.setColor(Color.white);
			g.fillRect(0, 0, w, h);

			int color;

			for (int j = beforeWallDistance; j > 0; j--)
			{
				color = (int) Math.round((Math.cos(((beforeWallDistance - j) / spaceBeetweenWaves * 2 * Math.PI) + (currentTime / 5f)) + 1) * 127.5);
				g.setColor(wvColor(wavelengthChooser.getWavelengthValue(), color / 255f));
				g.drawLine(j, yWaveStart, j, yWaveEnd);
			}

			double max = -1;
			double min = 1;
			sector = calculation.getSector(sector, checkbox.isSelected(), currentTime);
			for (int i = 0; i < distanceBetweenWallAndScreen; i++)
			{
				for (int j = 0; j < sector[i].length; j++)
				{
					if (min > sector[i][j].sum)
						min = sector[i][j].sum;
					if (max < sector[i][j].sum)
						max = sector[i][j].sum;

				}
			}
			max = 0.0001f;

			double diff = Math.abs(max - min);
			double scale = 255 / diff;
			for (int i = 0; i < distanceBetweenWallAndScreen; i++)
			{
				for (int j = 0; j < sector[i].length; j++)
				{
					if (sector[i][j].sum > max)
						sector[i][j].sum = max;
					int saturation;
					saturation = (int) (scale * (sector[i][j].sum - min));

					g.setColor(wvColor(wavelengthChooser.getWavelengthValue(), saturation / 255f));
					g.drawLine(beforeWallDistance + 2 + i, j, beforeWallDistance + 2 + i, j);
				}
			}
			int select = distanceBetweenWallAndScreen - 1;
			if (select < 0)
				select = 0;
			chart.refresh(sector[select]);
			if (isRunning)
			{
				currentTime += 1;
			}
		}
		else {
			g.setColor(Color.white);
			g.fillRect(0, 0, w, h);
		}
		g.setColor(Color.black);
		g.drawLine(beforeWallDistance + 1, yWaveStart, beforeWallDistance + 1, yWaveEnd);
		g.setColor(Color.white);
		g.drawLine(beforeWallDistance + 1, 136 - gratingConst, beforeWallDistance + 1, 136 - gratingConst);
		g.drawLine(beforeWallDistance + 1, 136 + gratingConst, beforeWallDistance + 1, 136 + gratingConst);
		g.setColor(Color.black);
		g.fillRect(700, 0, 3, this.getHeight());
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		
		String s = GUI.T.get(TransKey.SCREEN);
		
		for (int i = 0; i< s.length(); i++)
		{
			if (GUI.T.get(TransKey.SCREEN).length() == 5) 
				g.drawChars(new char[]{s.charAt(i)}, 0, 1, 730, 90 + 30*i);
			else if (GUI.T.get(TransKey.SCREEN).length() == 6)
			g.drawChars(new char[]{s.charAt(i)}, 0, 1, 730, 70 + 30*i);
		}
		
	}

	synchronized public void update(Graphics g)
	{
		paint(g);
	}

	static Color wvColor(float wl, float gamma)
	{
		float r = 0;
		float g = 0;
		float b = 0;
		float s = 1;
		final float[] bands = { 380, 420, 440, 490, 510, 580, 645, 700, 780, Float.MAX_VALUE };
		int band = bands.length - 1;
		for (int i = 0; i < bands.length; i++)
		{
			if (wl <= bands[i])
			{
				band = i;
				break;
			}
		}
		switch (band)
		{
		case 0:
			r = 0;
			g = 0;
			b = 0;
			s = 0;
			break;
		case 1:
			r = (440 - wl) / (440 - 380);
			g = 0;
			b = 1;
			s = .3f + .7f * (wl - 380) / (420 - 380);
			break;
		case 2:
			r = (440 - wl) / (440 - 380);
			g = 0;
			b = 1;
			break;
		case 3:
			r = 0;
			g = (wl - 440) / (490 - 440);
			b = 1;
			break;
		case 4:
			r = 0;
			g = 1;
			b = (510 - wl) / (510 - 490);
			break;
		case 5:
			r = (wl - 510) / (580 - 510);
			g = 1;
			b = 0;
			break;
		case 6:
			r = 1;
			g = (645 - wl) / (645 - 580);
			b = 0;
			break;
		case 7:
			r = 1;
			g = 0;
			b = 0;
			break;
		case 8:
			r = 1;
			g = 0;
			b = 0;
			s = .3f + .7f * (780 - wl) / (780 - 700);
			break;
		case 9:
			r = 0;
			g = 0;
			b = 0;
			s = 0;
			break;
		}
		s *= gamma;
		r *= s;
		g *= s;
		b *= s;
		float r1 = r + (1f - r) * (1f - gamma);
		float g1 = g + (1f - g) * (1f - gamma);
		float b1 = b + (1f - b) * (1f - gamma);
		return new Color(r1, g1, b1);
	}
}
