package divideconquer.torneo;

import org.junit.Test;

import static divideconquer.torneo.Calendar.createCalendar;
import static divideconquer.torneo.Calendar.createCalendarBruteForce;

public class CalendarTest {

    @Test
    public void createCalendarBruteForceTest() {
        createCalendarBruteForce(5).print();
    }

    @Test
    public void createCalendarTest() {
        createCalendar(4).print();
        createCalendar(8).print();
        createCalendar(7).print();
        createCalendar(3).print();
        createCalendar(6).print();
        createCalendar(5).print();
        createCalendar(12).print();
    }
}