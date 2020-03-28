package lintcode;

public class P212SpaceReplacement {
    /*
     * @param string: An array of Char
     * @param length: The true length of the string
     * @return: The true length of new string
     */
    public int replaceBlank(char[] string, int length) {
        int count = 0;
        for (int i = 0; i < length; i++) if (string[i] == ' ') ++count;
        int newLength = length + count * 2;
        for (int i = length - 1, j = newLength - 1; i >= 0; i--) {
            if (string[i] == ' ') {
                string[j--] = '0';
                string[j--] = '2';
                string[j--] = '%';
            } else {
                string[j--] = string[i];
            }
        }
        return newLength;
    }
}
