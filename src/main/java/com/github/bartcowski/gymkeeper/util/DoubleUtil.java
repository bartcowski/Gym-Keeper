package com.github.bartcowski.gymkeeper.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtil {

    public static double roundDoubleToTwoDecimalPlaces(double originalValue) {
        return new BigDecimal(originalValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
