package unnamed;

import java.util.BitSet;

public class NonStandardConversions {
    public static BitSet StringToBitset(String s) {
        BitSet ret = new BitSet(s.length());
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 1) {
                ret.set(i);
            }
        }
        return ret;
    }
}
