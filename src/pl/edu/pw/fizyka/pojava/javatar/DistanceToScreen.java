package pl.edu.pw.fizyka.pojava.javatar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.text.MaskFormatter;

class DistanceToScreen extends JPanel
{

	private static final long serialVersionUID = 4211847550578578922L;
	private JSlider slider2;
	private JFormattedTextField distanceScreen = null;
	private JLabel label;

	DistanceToScreen()
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel top = new JPanel();
		label = new JLabel(GUI.T.get(TransKey.DISTANCE_TO_SCREEN) + " [nm]:");
		top.add(label);

		try
		{
			distanceScreen = new JFormattedTextField(new MaskFormatter("####"));
		} catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		distanceScreen.setPreferredSize(new Dimension(50, 20));
		distanceScreen.setText("1250");
		top.add(distanceScreen);

		JPanel toScreen = new JPanel();
		toScreen.setLayout(new BorderLayout());
		slider2 = new JSlider(0, 2500);
		slider2.setInverted(true);
		slider2.setPaintLabels(true);
		slider2.setMajorTickSpacing(500);
		toScreen.add(slider2, BorderLayout.SOUTH);

		slider2.addChangeListener(e -> distanceScreen.setText("" + slider2.getValue()));

		distanceScreen.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{

				if ((int) e.getKeyChar() == 10)
				{
					int i;
					try
					{
						i = Integer.parseInt(distanceScreen.getText().trim());
					} catch (Exception ex)
					{
						i = 2500;

					}
					if (i > 2500)
						i = 2500;

					slider2.setValue(i);
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

		add(toScreen);
		add(top, BorderLayout.NORTH);
	}

	void importData(int tempDist)
	{
		distanceScreen.setText("" + tempDist);
		slider2.setValue(tempDist);
	}

	int getDistanceValue()
	{
		if (distanceScreen != null)
			try
			{
				return Integer.parseInt(distanceScreen.getText().trim());
			} catch (Exception ignored)
			{

			}
		return 0;

	}

	void changeTrans()
	{
		label.setText(GUI.T.get(TransKey.DISTANCE_TO_SCREEN) + " [nm]:");
	}

}
