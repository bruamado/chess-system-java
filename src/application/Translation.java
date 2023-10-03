package application;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Translation {
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("res.bundle", Locale.getDefault());
	
	public static String get(String txt) {
		return resourceBundle.getString(txt);
	}
	
	public static void setLanguage(Languages lang) {
		if (lang == Languages.PT) {Locale.setDefault(new Locale("pt", "BR"));}
		if (lang == Languages.EN) {Locale.setDefault(new Locale("en", "US"));}
		resourceBundle = ResourceBundle.getBundle("res.bundle", Locale.getDefault());
	}
}
