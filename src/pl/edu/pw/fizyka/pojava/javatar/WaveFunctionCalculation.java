package pl.edu.pw.fizyka.pojava.javatar;

class WaveFunctionCalculation
{
	private GratingConstantChooser gratingConstantChooser;
	private WavelengthChooser wavelengthChooser;
	private int xScreenSize;
	private int yScreenSize;

	WaveFunctionCalculation(WavelengthChooser wavelengthChooser, GratingConstantChooser gratingConstantChooser, int x)
	{
		this.gratingConstantChooser = gratingConstantChooser;
		this.wavelengthChooser = wavelengthChooser;
		xScreenSize = x;
		yScreenSize = 273;
	}

	private void updatehalfs()
	{
		double distScale = AnimateBeforeGrating.scaleFactor;
		halfDist = (int) (distScale * (gratingConstantChooser.getGratingValue() / 2));
		lambda = (int) (wavelengthChooser.getWavelengthValue() * distScale);
	}

	private int halfDist = 0;
	private int lambda = 0;

	class Sector
	{
		private int x, y;
		private double r1, r2;
		double psi1, psi2, sum;

		private Sector(int i, int j)
		{
			x = i + 1;
			y = (yScreenSize / 2) - j + 1;
			r1 = 0;
			r2 = 0;
			psi1 = 0;
			psi2 = 0;
			sum = 0;
		}
	}

	Sector[][] InitializeSectors()
	{
		Sector[][] allSectors = new Sector[xScreenSize][yScreenSize]; // line 30 changed per suggestions
		for (int i = 0; i < xScreenSize; i++)
		{
			for (int j = 0; j < yScreenSize; j++)
			{
				allSectors[i][j] = new Sector(i, j);
			}
		}
		return allSectors;
	}

	private Sector[][] WaveFunctionValue(Sector[][] a, boolean isLogharitmic, long time)
	{
		for (int i = 0; i < xScreenSize; i++)
		{
			for (int j = 0; j < yScreenSize; j++)
			{
				a[i][j].r1 = Math.sqrt(Math.pow(a[i][j].x, 2) + Math.pow(a[i][j].y - halfDist, 2));
				a[i][j].r2 = Math.sqrt(Math.pow(a[i][j].x, 2) + Math.pow(a[i][j].y + halfDist, 2));

				a[i][j].psi1 = (1 / Math.pow(a[i][j].r1, 2) * Math.cos(6.28 * a[i][j].r1 / lambda - time / 5f));
				a[i][j].psi2 = (1 / Math.pow(a[i][j].r2, 2) * Math.cos(6.28 * a[i][j].r2 / lambda - time / 5f));

				a[i][j].sum = (a[i][j].psi1 + a[i][j].psi2);
				if (isLogharitmic)
					a[i][j].sum = Math.log(a[i][j].sum);
			}
		}
		return a;
	}

	Sector[][] getSector(Sector[][] sector, boolean isLogharitmic, long time)
	{
		updatehalfs();
		return WaveFunctionValue(sector, isLogharitmic, time);
	}

}