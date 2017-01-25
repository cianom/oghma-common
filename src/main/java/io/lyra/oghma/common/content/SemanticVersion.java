package io.lyra.oghma.common.content;

/**
 * Slim wrapper around a semantic version string. Provides support for comparing.
 *
 * @author Cian.
 */
public class SemanticVersion implements Comparable<SemanticVersion> {

    private final String version;

    public SemanticVersion(final String version) {
        this.version = version;
    }

    /**
     * Compares two version strings.
     * <p>
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     * <p>
     * Note: It does not work if "1.10" is supposed to be equal to "1.10.0".
     *
     * @param v1 the first version to compare against.
     * @param v2 the second version to compare against.
     * @return The result is a negative integer if str1 is numerically less than str2.
     * The result is a positive integer if str1 is numerically greater than str2.
     * The result is zero if the strings are numerically equal.
     */
    private static int compareTo(final SemanticVersion v1, final SemanticVersion v2) {

        final String[] v1Split = v1.getVersion().split("\\.");
        final String[] v2Split = v2.getVersion().split("\\.");
        int i = 0;
        // Set index to first non-equal ordinal or length of shortest version string.
        while (i < v1Split.length && i < v2Split.length && v1Split[i].equals(v2Split[i])) {
            i++;
        }
        // Compare first non-equal ordinal number.
        if (i < v1Split.length && i < v2Split.length) {
            final int diff = Integer.valueOf(v1Split[i]).compareTo(Integer.valueOf(v2Split[i]));
            return Integer.signum(diff);
        }
        // The strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(v1Split.length - v2Split.length);
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int compareTo(final SemanticVersion o) {
        return SemanticVersion.compareTo(this, o);
    }

}
