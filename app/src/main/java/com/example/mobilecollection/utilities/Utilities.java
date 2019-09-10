package com.example.mobilecollection.utilities;

import java.text.NumberFormat;
import java.util.Locale;

public final class Utilities {

    public static String format(int amount){
        Locale INDONESIA = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(INDONESIA);
        String currency = format.format(amount);
        return currency;
    }
}
