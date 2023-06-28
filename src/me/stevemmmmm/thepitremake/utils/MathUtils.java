//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.stevemmmmm.thepitremake.utils;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {
    public MathUtils() {
    }

    public static int biasedRandomness(int min, int max, double bias) {
        if (min == max) {
            return min;
        } else if (max >= min && min >= 0) {
            double d = (double)(max - min);
            double r = 15.0 * d / bias;
            double x = Double.parseDouble(new DecimalFormat("#0.0").format(ThreadLocalRandom.current().nextDouble(0, r + 1)).replace(',', '.'));
            return (int)Math.round(d * Math.pow(Math.E, -bias / 15.0 * x) + ((double)max - d));
        } else {
            throw new IllegalArgumentException("Invalid arguments!");
        }
    }
}
