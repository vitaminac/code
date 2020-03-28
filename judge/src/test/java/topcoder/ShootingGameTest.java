package topcoder;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShootingGameTest {

    @Test
    public void findProbability1() {
        Assert.assertEquals(0.6666666666666667, new ShootingGame().findProbability(400000), 0.000001);
    }

    @Test
    public void findProbability2() {
        Assert.assertEquals(-1, new ShootingGame().findProbability(999997), 0.000001);
    }

    @Test
    public void findProbability3() {
        Assert.assertEquals(0.4072278034440493, new ShootingGame().findProbability(289383), 0.000001);
    }

    @Test
    public void findProbability4() {
        Assert.assertEquals(-1, new ShootingGame().findProbability(930886), 0.000001);
    }

    @Test
    public void findProbability5() {
        Assert.assertEquals(-1, new ShootingGame().findProbability(692777), 0.000001);
    }

    @Test
    public void findProbability6() {
        Assert.assertEquals(-1, new ShootingGame().findProbability(636915), 0.000001);
    }

    @Test
    public void findProbability7() {
        Assert.assertEquals(0.09790212861264695, new ShootingGame().findProbability(89172), 0.000001);
    }

    @Test
    public void findProbability8() {
        Assert.assertEquals(0.5840078566789692, new ShootingGame().findProbability(368690), 0.000001);
    }

    @Test
    public void findProbability9() {
        Assert.assertEquals(0.25370155383770576, new ShootingGame().findProbability(202362), 0.000001);
    }

    @Test
    public void findProbability10() {
        Assert.assertEquals(0.2203158177336295, new ShootingGame().findProbability(180540), 0.000001);
    }

    @Test
    public void findProbability11() {
        Assert.assertEquals(-1, new ShootingGame().findProbability(520059), 0.000001);
    }

    @Test
    public void findProbability12() {
        Assert.assertEquals(1.000001000001E-6, new ShootingGame().findProbability(1), 0.000001);
    }
}