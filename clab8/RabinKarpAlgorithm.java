public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {

        if (input == null || pattern == null || input.length() ==0 || pattern.length() == 0
                || pattern.length() > input.length())
            return -1;

        int hpattern = hashString(pattern);
        RollingString rs = new RollingString(input.substring(0, pattern.length()), pattern.length());
        if (rs.hashCode() == hpattern && rs.toString().equals(pattern)){
            return 0;
        } else {

            for (int i = 1; i < input.length() - pattern.length() + 1; i++) {
                rs.addChar(input.charAt(i + pattern.length() - 1));
                if (rs.hashCode() == hpattern && rs.toString().equals(pattern)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static int hashString(String s) {
        char[] chars = s.toCharArray();
        int hash = 0;
        for (int i = 0; i < chars.length; i++) {
            hash = (hash * RollingString.UNIQUECHARS % RollingString.PRIMEBASE + Character.hashCode(chars[i]))
                    % RollingString.PRIMEBASE;
        }
        return hash;
    }
}
