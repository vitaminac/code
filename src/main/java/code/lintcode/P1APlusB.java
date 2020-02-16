package code.lintcode;

public class P1APlusB {
    /**
     * @param a: An integer
     * @param b: An integer
     * @return: The sum of a and b
     */
    public int aplusb(int a, int b) {
        int result = a ^ b;
        int carry = (a & b) << 1;
        if (carry == 0) return result;
        else return this.aplusb(result, carry);
    }
}
