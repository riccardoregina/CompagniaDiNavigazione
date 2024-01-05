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

    public static String bitStringToGiorni(String bitString) {
        StringBuilder sb = new StringBuilder();

        sb.append((bitString.charAt(1) == '1') ? 'L' : '-');
        sb.append((bitString.charAt(2) == '1') ? "Ma" : '-');
        sb.append((bitString.charAt(3) == '1') ? "Me" : '-');
        sb.append((bitString.charAt(4) == '1') ? 'G' : '-');
        sb.append((bitString.charAt(5) == '1') ? 'V' : '-');
        sb.append((bitString.charAt(6) == '1') ? 'S' : '-');
        sb.append((bitString.charAt(0) == '1') ? 'D' : '-');

        return sb.toString();
    }
}
