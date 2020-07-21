package foobar.l5;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Dodge the Lasers!
 * =================
 * <p>
 * Oh no! You've managed to escape Commander Lambdas collapsing space station
 * in an escape pod with the rescued bunny prisoners,
 * but Commander Lambda isn't about to let you get away that easily.
 * She's sent her elite fighter pilot squadron after you
 * and they've opened fire!
 * <p>
 * Fortunately, you know something important about the ships trying to shoot you down.
 * Back when you were still Commander Lambdas assistant,
 * she asked you to help program the aiming mechanisms for the starfighters.
 * They undergo rigorous testing procedures,
 * but you were still able to slip in a subtle bug.
 * The software works as a time step simulation:
 * if it is tracking a target that is accelerating away at 45 degrees,
 * the software will consider the targets acceleration to be equal to the square root of 2,
 * adding the calculated result to the targets end velocity at each timestep.
 * However, thanks to your bug, instead of storing the result with proper precision,
 * it will be truncated to an integer before adding the new velocity to your current position.
 * This means that instead of having your correct position,
 * the targeting software will erringly report your position as sum(i=1..n, floor(i*sqrt(2))),
 * not far enough off to fail Commander Lambdas testing, but enough that it might just save your life.
 * <p>
 * If you can quickly calculate the target of the starfighters' laser beams to know
 * how far off they'll be, you can trick them into shooting an asteroid,
 * releasing dust, and concealing the rest of your escape.  Write a function solution(str_n) which,
 * given the string representation of an integer n,
 * returns the sum of (floor(1*sqrt(2)) + floor(2*sqrt(2)) + ... + floor(n*sqrt(2))) as a string.
 * That is, for every number i in the range 1 to n, it adds up all of the integer portions of i*sqrt(2).
 * <p>
 * For example, if str_n was "5", the solution would be calculated as
 * floor(1*sqrt(2)) +
 * floor(2*sqrt(2)) +
 * floor(3*sqrt(2)) +
 * floor(4*sqrt(2)) +
 * floor(5*sqrt(2))
 * = 1+2+4+5+7 = 19
 * so the function would return "19".
 * <p>
 * str_n will be a positive integer between 1 and 10^100, inclusive. Since n can be very large (up to 101 digits!),
 * using just sqrt(2) and a loop won't work.
 * Sometimes, it's easier to take a step back and concentrate not on what you have in front of you,
 * but on what you don't.
 * <p>
 * Languages
 * =========
 * <p>
 * To provide a Java solution, edit Solution.java
 * To provide a Python solution, edit solution.py
 * <p>
 * Test cases
 * ==========
 * Your code should pass the following test cases.
 * Note that it may also be run against hidden test cases not shown here.
 * <p>
 * -- Java cases --
 * Input:
 * Solution.solution('77')
 * Output:
 * 4208
 * <p>
 * Input:
 * Solution.solution('5')
 * Output:
 * 19
 * <p>
 * -- Python cases --
 * Input:
 * solution.solution('77')
 * Output:
 * 4208
 * <p>
 * Input:
 * solution.solution('5')
 * Output:
 * 19
 */
public class DodgeTheLasers {
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigDecimal FACTOR =
            new BigDecimal("1.4142135623730950488016887242096980785696718753769480731766797379907324784621070388503875343276415727350138462309122970249248360558507372126441214971").subtract(BigDecimal.ONE);

    private static BigInteger beattySequence(BigInteger n) {
        // S(sqrt(2), 0) = 0
        if (n.equals(BigInteger.ZERO)) {
            return BigInteger.ZERO;
        } else {
            // n' = floor(sqrt(2) - 1)*n)
            BigInteger n_prime = FACTOR.multiply(new BigDecimal(n)).toBigInteger();
            // S(sqrt(2), n) = p + q - r - S(sqrt(2), n')
            // p = nn'
            BigInteger p = n.multiply(n_prime);
            // q = n(n+1)/2
            BigInteger q = n.multiply(n.add(BigInteger.ONE)).divide(TWO);
            // r = n'(n'+1)/2
            BigInteger r = n_prime.multiply(n_prime.add(BigInteger.ONE)).divide(TWO);
            return p.add(q).subtract(r).subtract(beattySequence(n_prime));
        }
    }

    public static String solution(String str_n) {
        return beattySequence(new BigInteger(str_n)).toString();
    }
}
