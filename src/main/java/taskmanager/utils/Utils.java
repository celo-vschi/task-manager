package taskmanager.utils;

import java.util.Random;

public class Utils {

    private static final Random random = new Random();

    public static Long generatePid() {
        return random.nextLong();
    }

}
