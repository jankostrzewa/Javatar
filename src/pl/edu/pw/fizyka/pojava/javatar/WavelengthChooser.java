package pl.edu.pw.fizyka.pojava.javatar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.text.MaskFormatter;

class WavelengthChooser extends JPanel
{
	private static final long serialVersionUID = -7254877579228172428L;
	private JFormattedTextField waveLength = null;
	private JSlider slider;
	private JLabel label;

	WavelengthChooser()
	{

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel top = new JPanel();
		label = new JLabel(GUI.T.get(TransKey.WAVELENGTH) + " [nm]");
		top.add(label);

		try
		{
			waveLength = new JFormattedTextField(new MaskFormatter("###"));
		} catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		waveLength.setPreferredSize(new Dimension(50, 20));
		top.add(waveLength);
		waveLength.setText("550");
		JPanel waveLenghtPanel = new JPanel();
		waveLenghtPanel.setLayout(new BorderLayout());

		slider = new JSlider(400, 700);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(100);
		waveLenghtPanel.add(slider, BorderLayout.NORTH);
		JPanel spectrum = new JPanel()
		{

			private static final long serialVersionUID = -8038009172465630153L;

			@Override
			public void paint(Graphics g)
			{
				super.paint(g);

				float scale = 300f / this.getWidth();
				for (int i = 0; i < this.getWidth(); i++)
				{
					g.setColor(AnimateBeforeGrating.wvColor(400 + Math.round(i * scale), 1));
					g.drawLine(i, 0, i, this.getHeight());
				}

			}
		};

		addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getX() >= 6 && e.getX() <= 194)
				{
					int selected = (e.getX() - 5) * 300 / 189 + 400;
					waveLength.setText("" + selected);
					slider.setValue(selected);
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{

			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (e.getX() >= 6 && e.getX() <= 194)
				{
					int selected = (e.getX() - 5) * 300 / 189 + 400;
					waveLength.setText("" + selected);
					slider.setValue(selected);
				}

			}
		});

		slider.addChangeListener(e -> waveLength.setText("" + slider.getValue()));

		waveLength.addKeyListener(new KeyListener()
		{
					@Override
					public void keyTyped(KeyEvent e)
					{

						if ((int) e.getKeyChar() == 10)
						{
							int i;
							try
							{
								i = Integer.parseInt(waveLength.getText());
							} catch (Exception ex)
							{
								i = 400;
							}
							if (i < 400)
								i = 400;
							else if (i > 700)
								i = 700;

							slider.setValue(i);
							System.out.printf("Lambda = %d \n", i);
						}
					}

					@Override
					public void keyReleased(KeyEvent e)
					{

					}

					@Override
					public void keyPressed(KeyEvent e)
					{

					}
				});

		waveLenghtPanel.add(spectrum);
		this.add(top, BorderLayout.NORTH);
		this.add(waveLenghtPanel);

	}

	void importData(int tempWave)
	{
		waveLength.setText("" + tempWave);
		slider.setValue(tempWave);
	}

	int getWavelengthValue()
	{
		if (waveLength != null)
			try
			{
				return Integer.parseInt(waveLength.getText().trim());
			} catch (Exception ignored)
			{

			}
		return 400;
	}

	void changeTrans()
	{
		label.setText(GUI.T.get(TransKey.WAVELENGTH) + " [nm]");
	}
}
