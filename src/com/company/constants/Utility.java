package com.company.constants;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    private Utility() {}

    //decimal formatter
    public static final DecimalFormat twoDecimalsFormatter = new DecimalFormat("0.00");

    //time to string
    public static String timeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LocalTime time = LocalTime.now();
        return "[" + time.format(formatter) + "] ";
    }

    public static String buildLogEntry(String message) {
        return timeToString() + message;
    }
}
