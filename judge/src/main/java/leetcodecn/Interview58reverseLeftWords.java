package leetcodecn;

public class Interview58reverseLeftWords {
    private void reverse(char[] str, int left, int right) {
        while (left < right) {
            str[left] ^= str[right];
            str[right] ^= str[left];
            str[left] ^= str[right];
            left += 1;
            right -= 1;
        }
    }

    public String reverseLeftWords(String s, int n) {
        char[] str = s.toCharArray();
        this.reverse(str, 0, n - 1);
        this.reverse(str, n, str.length - 1);
        this.reverse(str, 0, str.length - 1);
        return String.valueOf(str);
    }
}
