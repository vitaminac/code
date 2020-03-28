public class Main {
    public static void main(String[] args) {
        PersonBuilder builder = new PersonBuilder();
        Person person = builder.setAge(5).setName("ABC").build();
        System.out.println(person);
    }
}
