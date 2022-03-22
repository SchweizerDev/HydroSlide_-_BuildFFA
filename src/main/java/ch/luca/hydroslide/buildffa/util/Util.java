package ch.luca.hydroslide.buildffa.util;

import java.text.DecimalFormat;

public class Util {

    public static String asString(long l) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(l).replace(",", "'");
    }
}
