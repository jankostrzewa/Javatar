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

class GratingConstantChooser extends JPanel
{
	private static final long serialVersionUID = 8116385601468117742L;
	private JFormattedTextField gratingConstant = null;
	private JSlider slider2;
	private JLabel label;
	GratingConstantChooser()
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel top1 = new JPanel();
		label = new JLabel(GUI.T.get(TransKey.GRATING_CONSTANT) + " [nm]:");
		top1.add(label);

		try
		{
			gratingConstant = new JFormattedTextField(new MaskFormatter("####"));
		} catch (ParseException e1)
		{
			e1.printStackTrace();
		}
		gratingConstant.setPreferredSize(new Dimension(50, 20));
		gratingConstant.setText("682");
		top1.add(gratingConstant);

		JPanel grating = new JPanel();
		grating.setLayout(new BorderLayout());
		
		slider2 = new JSlider(0, 1365);
		slider2.setPaintLabels(true);
		slider2.setMajorTickSpacing(250);
		grating.add(slider2, BorderLayout.NORTH);

		slider2.addChangeListener(e -> gratingConstant.setText("" + slider2.getValue()));

		gratingConstant.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{

				if ((int) e.getKeyChar() == 10)
				{
					int i;
					try
					{
						i = Integer.parseInt(gratingConstant.getText().trim());
					} catch (Exception ex)
					{
						i = 0;

					}
					if (i > 1365)
						i = 1365;

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

		add(grating);
		add(top1, BorderLayout.NORTH);
	}

	void importData(int tempGrate)
	{
		gratingConstant.setText("" + tempGrate);
		slider2.setValue(tempGrate);
	}

	int getGratingValue()
	{
		if (gratingConstant != null)
			try
			{
				return Integer.parseInt(gratingConstant.getText().trim());
			} catch (Exception ignored)
			{

			}
		return 532;

	}
	
	void changeTrans()
	{
		label.setText(GUI.T.get(TransKey.GRATING_CONSTANT) + " [nm]:");
	}

}
