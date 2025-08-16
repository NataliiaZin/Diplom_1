package praktikum.util;

import java.util.Random;

public class NumUtils {

    public static float getRandFloat() {
        Random random = new Random();
        return Float.intBitsToFloat(random.nextInt());
    }
}
