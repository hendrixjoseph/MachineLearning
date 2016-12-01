package edu.wright.hendrix11.cs7830.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joe on 10/25/2016.
 */
public class StringParser {
    private static DateFormat readDateFormat = new SimpleDateFormat("M/d/yyyy");
    private static DateFormat dayOfYearDateFormat = new SimpleDateFormat("D");
    private StringParser() {

    }

    public static int parseDollars(String string) {
        return Integer.parseInt(string.replace("$", "").replace(".", ""));
    }

    public static int getDayOfYear(String string) throws ParseException {
        Date input = readDateFormat.parse(string);
        String output = dayOfYearDateFormat.format(input);

        return Integer.parseInt(output);
    }

    public static Integer parseInt(String string) {
        return string.isEmpty() ? null : Integer.parseInt(string);
    }

    public static Double parseDouble(String string) {
        return string.isEmpty() ? null : Double.parseDouble(string);
    }
}
