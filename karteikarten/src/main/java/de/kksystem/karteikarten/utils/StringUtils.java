package de.kksystem.karteikarten.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	// Überprüfe, ob tatsächlich ein Integer
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    if (str.isEmpty()) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (str.length() == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	// entfernt html-code eines Strings und gibt demzufolge nur den eigentlichen text zurück
	public static String getText(String htmlText) {
		String result = "";
		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer text = new StringBuffer(htmlText.length());
		while (matcher.find()) {
			matcher.appendReplacement(text, " ");
		}
		matcher.appendTail(text);
		result = text.toString().trim();
		return result;
	}
	
}