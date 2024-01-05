package unnamed;

import java.util.BitSet;

public class NonStandardStringFunctions {
    public static boolean isAlpha (String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
