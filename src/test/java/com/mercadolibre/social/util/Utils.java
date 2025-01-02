package com.mercadolibre.social.util;

import java.time.LocalDate;
import java.util.Random;

public class Utils {
    public static LocalDate generatedDateOfLastMont() {
        LocalDate date = LocalDate.now();
        int random = new Random().nextInt(30);
        return date.minusDays(random);
    }
}
