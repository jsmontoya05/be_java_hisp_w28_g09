package com.mercadolibre.social.util;

import java.time.LocalDate;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static ObjectMapper mapper = new ObjectMapper();

    public static LocalDate generatedDateOfLastTwoWeeks() {
        LocalDate date = LocalDate.now();
        int random = new Random().nextInt(13);
        return date.minusDays(random);
    }
}
