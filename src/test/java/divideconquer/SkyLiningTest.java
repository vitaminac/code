package divideconquer;

import org.junit.Before;
import org.junit.Test;

public class SkyLiningTest {
    private SkyLining obj;

    @Before
    public void setUp() throws Exception {
        this.obj = new SkyLining(6);
        this.obj.addBuilding(0, 1, 3, 4);
        this.obj.addBuilding(1, 2, 9, 7);
        this.obj.addBuilding(2, 4, 12, 4);
        this.obj.addBuilding(3, 6, 8, 9);
        this.obj.addBuilding(4, 11, 13, 6);
        this.obj.addBuilding(5, 14, 15, 2);
    }

    @Test
    public void planning() {
        System.out.println(this.obj.plan());
    }
}