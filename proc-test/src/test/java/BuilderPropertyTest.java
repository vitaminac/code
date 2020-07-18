import org.junit.Test;

public class BuilderPropertyTest {

    @Test
    public void testBuilder() {
        PersonBuilder builder = new PersonBuilder();
        Person person = builder.setAge(5).setName("ABC").build();
        System.out.println(person);
    }
}
