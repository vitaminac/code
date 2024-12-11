package business;

import test.TestCase;
import test.TestSuite;
import test.UnitTest;

import static test.Asserts.assertEquals;

@TestSuite
public class PersonTest extends TestCase {
    private static final int AGE = 18;
    private static final String NAME = "full name";

    @UnitTest
    public void testPersonBuilder() {
        PersonBuilder builder = new PersonBuilder();
        final Person person = builder
            .setAge(AGE)
            .setName(NAME)
            .build();

        assertEquals(AGE, person.getAge());
        assertEquals(NAME, person.getName());
    }
}
