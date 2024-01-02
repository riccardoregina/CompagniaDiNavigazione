package unnamed;

import java.util.Objects;

/**
 * The type Pair.
 */
public class Pair {
    /**
     * The First.
     */
    public Object first;
    /**
     * The Last.
     */
    public Object last;

    /**
     * Instantiates a new Pair.
     *
     * @param first the first
     * @param last  the last
     */
    public Pair(Object first, Object last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(first, pair.first) && Objects.equals(last, pair.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }
}
