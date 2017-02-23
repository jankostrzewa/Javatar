package pl.edu.pw.fizyka.pojava.javatar;

class Translations
{
	Languages lang = Languages.POLISH;

	String get(TransKey key)
	{
		if (Languages.ENGLISH.equals(lang))
		{
			switch (key)
			{
			case INTERFERENCE:
				return "Interference";
			case LOGARITHMIC:
				return "Logarithmic";
			case ANIME:
				return "Anime";
			case ANIMATE:
				return "Animate";
			case STOP_ANIMATE:
				return "Stop animate";
			case IMPORT_DATA_FROM_FILE:
				return "Import data from file";
			case CHOOSE_FILE:
				return "Choose file";
			case CHOOSE:
				return "Choose";
			case SAVE_DATA_TO_FILE:
				return "Save data tofile";
			case DATA:
				return "Data";
			case CHAR_IN:
				return "Chart in";
			case SAVE_ANIMATION_TO_FILE: 
				return "Save animation to file";
			case SAVE_CHART_TO_FILE:
				return "Save chart to file";
			case ANIMATION_IN:
				return "Snimation in";
			case INTENSITY_VALUE:
				return "Intensity value";
			case INTENSITY_CHART:
				return "Intensity chart";
			case SCREEN:
				return "Screen";
			case INTENSITY:
				return "Intensity";
			case DISTANCE_TO_SCREEN:
				return "Distance to screen";
			case GRATING_CONSTANT:
				return "Grating constant";
			case ENGLISH:
				return "English";
			case POLISH:
				return "Polish";
			case WAVELENGTH:
				return "Wavelength";
				
			default:
				return "";
			}
		}
		else if (Languages.POLISH.equals(lang))
		{
			switch (key)
			{
			case INTERFERENCE:
				return "Interferencja";
			case LOGARITHMIC:
				return "Skala logarytmiczna";
			case ANIME:
				return "Animuj";
			case ANIMATE:
				return "Animuj";
			case STOP_ANIMATE:
				return "Zatrzymaj animację";
			case IMPORT_DATA_FROM_FILE:
				return "Zaimportuj dane z pliku";
			case CHOOSE_FILE:
				return "Wybierz plik";
			case CHOOSE:
				return "Wybierz...";
			case SAVE_DATA_TO_FILE:
				return "Zapisz dane do pliku";
			case DATA:
				return "Dane";
			case CHAR_IN:
				return "Wykres w rozszerzeniu";
			case SAVE_ANIMATION_TO_FILE:
				return "Zapisz animację do pliku";
			case SAVE_CHART_TO_FILE:
				return "Zapisz wykres do pliku";
			case ANIMATION_IN:
				return "Animacja w rozszerzeniu";
			case INTENSITY_VALUE:
				return "Natężenie";
			case INTENSITY_CHART:
				return "Wykres natężenia";
			case SCREEN:
				return "Ekran";
			case INTENSITY:
				return "Natężenie";
			case DISTANCE_TO_SCREEN:
				return "Dystans od ekranu";
			case GRATING_CONSTANT:
				return "Stała szczelin";
			case ENGLISH:
				return "Angielski";
			case POLISH:
				return "Polski";
			case WAVELENGTH:
				return "Długość fali";
			default:
				return "";
			}
		}
		return null;
	}
}
