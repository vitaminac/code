package dav.torneo;

import org.junit.Test;

import static dav.torneo.Calendar.createCalendarBruteForce;

public class CalendarTest {

    @Test
    public void createCalendarBruteForceTest() {
        createCalendarBruteForce(5).print();
    }
}