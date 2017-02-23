package pl.edu.pw.fizyka.pojava.javatar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

class GUI extends JFrame
{
	private static final long serialVersionUID = -7275281610999365831L;
	private WavelengthChooser wavelengthChooser;
	private GratingConstantChooser gratingConstant;
	private DistanceToScreen distanceScreen;
	private AnimateBeforeGrating animation;
	private boolean isAnimating = false;
	private JCheckBox checkBox;
	private final Chart plot;
	private JLabel anime;
	private JButton init;
	private JButton scoreImport;
	private JButton scoreExport;
	private JButton chartExport;
	private JButton animationExport ;
	private JButton english ;
	private JButton polish;
	static final Translations T = new Translations();

	private GUI() throws HeadlessException
	{
		super(GUI.T.get(TransKey.INTERFERENCE));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setSize(850, 647);

		setResizable(false);

		/* Animation panel */
		distanceScreen = new DistanceToScreen();
		wavelengthChooser = new WavelengthChooser();
		gratingConstant = new GratingConstantChooser();
		checkBox = new JCheckBox(GUI.T.get(TransKey.LOGARITHMIC));
		plot = new Chart();
		animation = new AnimateBeforeGrating(distanceScreen, wavelengthChooser, gratingConstant, checkBox, plot);
		animation.setBackground(Color.decode("#fbfbfb"));
		animation.setPreferredSize(new Dimension(780, 273));
		anime = new JLabel(GUI.T.get(TransKey.ANIME));
		animation.add(anime);
		this.add(animation);

		/* Controls & input panel */
		JPanel controls = new JPanel();
		controls.setLayout(null);
		controls.setPreferredSize(new Dimension(387, 320));
		controls.setSize(new Dimension(387, 273));

		init = new JButton(GUI.T.get(TransKey.ANIMATE));
		init.setBounds(70, 10, 250, 40);
		controls.add(init);
		init.addActionListener(e -> {
            if (isAnimating)
            {
                animation.stop();
                init.setText(GUI.T.get(TransKey.ANIMATE));
            }
            else
            {
                animation.start();
                init.setText(GUI.T.get(TransKey.STOP_ANIMATE));
            }
            isAnimating = !isAnimating;

        });

		wavelengthChooser.setBounds(10, 55, 210, 100);
		controls.add(wavelengthChooser);

		distanceScreen.setBounds(10, 160, 210, 70);
		controls.add(distanceScreen);

		gratingConstant.setBounds(10, 235, 210, 70);
		controls.add(gratingConstant);

		scoreImport = new JButton(GUI.T.get(TransKey.IMPORT_DATA_FROM_FILE));
		scoreImport.setBounds(230, 200, 150, 30);
		controls.add(scoreImport);
		scoreImport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(GUI.T.get(TransKey.CHOOSE_FILE));
            int result = chooser.showDialog(null, GUI.T.get(TransKey.CHOOSE));
            if (JFileChooser.APPROVE_OPTION != result)
            {
                return;
            }

            File file = chooser.getSelectedFile();
            try
            {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextInt())
                {
                    wavelengthChooser.importData(scanner.nextInt());
                    distanceScreen.importData(scanner.nextInt());
                    gratingConstant.importData(scanner.nextInt());
                }
                scanner.close();
            } catch (IOException ignored)
            {
            }
        });

		scoreExport = new JButton(GUI.T.get(TransKey.SAVE_DATA_TO_FILE));
		scoreExport.setBounds(230, 235, 150, 30);
		controls.add(scoreExport);
		scoreExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(GUI.T.get(TransKey.CHOOSE_FILE));
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {

                @Override
                public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
				}

                @Override
                public String getDescription()
                {
                    return GUI.T.get(TransKey.DATA) + " (*.txt)";
                }

            });
            int result = chooser.showDialog(null, GUI.T.get(TransKey.CHOOSE));
            if (JFileChooser.APPROVE_OPTION != result)
            {
                return;
            }
            File saveFile = chooser.getSelectedFile();
            if (!saveFile.getName().toLowerCase().endsWith(".txt"))
                saveFile = new File(saveFile.getAbsoluteFile() + ".txt");
            try
            {
                PrintWriter printer = new PrintWriter(saveFile);
                printer.println(wavelengthChooser.getWavelengthValue());
                printer.println(distanceScreen.getDistanceValue());
                printer.println(gratingConstant.getGratingValue());
                printer.flush();
                printer.close();
            } catch (IOException ignored)
            {
            }
        });

		checkBox.setBounds(230, 60, 150, 30);
		controls.add(checkBox);
		this.add(controls);

		/* Plot panel */

		plot.setPreferredSize(new Dimension(387, 350));
		this.add(plot);

		RefineryUtilities.centerFrameOnScreen(this);

		WaveFunctionCalculation calculation = new WaveFunctionCalculation(wavelengthChooser, gratingConstant, 780 - (AnimateBeforeGrating.startWallPosition + 80));
		animation.init(calculation);

		
		english = new JButton(GUI.T.get(TransKey.ENGLISH));
		
		english.setBounds(230, 95, 150, 30);
		controls.add(english);
		english.addActionListener(e -> {
            T.lang = Languages.ENGLISH;
            changeTrans();
        });
		
		polish = new JButton(GUI.T.get(TransKey.POLISH));
		
		polish.setBounds(230, 130, 150, 30);
		controls.add(polish);
		
		
		polish.addActionListener(e -> {
            T.lang = Languages.POLISH;
            changeTrans();
        });
		
		chartExport = new JButton(GUI.T.get(TransKey.SAVE_CHART_TO_FILE));
		
		chartExport.setBounds(230, 165, 150, 30);
		controls.add(chartExport);
		chartExport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(" ");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {

                @Override
                public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
				}

                @Override
                public String getDescription()
                {
                    return GUI.T.get(TransKey.CHAR_IN) + " (*.png)";
                }

            });

            int selected = fileChooser.showSaveDialog(GUI.this);

            if (selected == JFileChooser.APPROVE_OPTION)
            {
                BufferedImage im = new BufferedImage(plot.getWidth(), plot.getHeight(), BufferedImage.TYPE_INT_ARGB);
                plot.paint(im.getGraphics());
                try
                {
                    File f = fileChooser.getSelectedFile();
                    if (!f.getName().toLowerCase().endsWith(".png"))
                        f = new File(f.getAbsoluteFile() + ".png");
                    ImageIO.write(im, "PNG", f);
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

        });

		animationExport = new JButton(GUI.T.get(TransKey.SAVE_ANIMATION_TO_FILE));
		animationExport.setBounds(230, 270, 150, 30);
		controls.add(animationExport);
		animationExport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(" ");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {

                @Override
                public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
				}

                @Override
                public String getDescription()
                {
                    return GUI.T.get(TransKey.ANIMATION_IN) + " (*.png)";
                }

            });

            int selected = fileChooser.showSaveDialog(GUI.this);

            if (selected == JFileChooser.APPROVE_OPTION)
            {
                BufferedImage im = new BufferedImage(animation.getWidth(), animation.getHeight(), BufferedImage.TYPE_INT_ARGB);
                animation.paint(im.getGraphics());
                try
                {
                    File f = fileChooser.getSelectedFile();
                    if (!f.getName().toLowerCase().endsWith(".png"))
                        f = new File(f.getAbsoluteFile() + ".png");
                    ImageIO.write(im, "PNG", f);
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }

        });
	}

	public static void main(String[] args)
	{
		GUI mainWindow = new GUI();
		mainWindow.setVisible(true);
	}
	
	private void changeTrans()
	{
		setTitle(GUI.T.get(TransKey.INTERFERENCE));
		checkBox.setText(GUI.T.get(TransKey.LOGARITHMIC));
		animationExport.setText(GUI.T.get(TransKey.SAVE_ANIMATION_TO_FILE));
		chartExport.setText(GUI.T.get(TransKey.SAVE_CHART_TO_FILE));
		scoreExport.setText(GUI.T.get(TransKey.SAVE_DATA_TO_FILE));
		scoreImport.setText(GUI.T.get(TransKey.IMPORT_DATA_FROM_FILE));
		init.setText(GUI.T.get(TransKey.ANIMATE));
		anime.setText(GUI.T.get(TransKey.ANIME));
		english.setText(GUI.T.get(TransKey.ENGLISH));
		polish.setText(GUI.T.get(TransKey.POLISH));
		wavelengthChooser.changeTrans();
		distanceScreen.changeTrans();
		gratingConstant.changeTrans();
	}

}