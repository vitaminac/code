package oop;

import org.junit.Test;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Practise {

    private final IntSupplier fibonacci = new IntSupplier() {
        private int previous = 0;
        private int current = 1;

        @Override
        public int getAsInt() {
            int nextValue = this.previous + this.current;
            this.previous = this.current;
            this.current = nextValue;
            return this.previous;
        }
    };
    private final int magicNumber = 2;

    @Test
    public void fibonacciTest() {
        IntStream.generate(fibonacci).limit(20).map(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                // magicNumber = 2; <- error, local variable should be final or effectively final
                // return operand * this.magicNumber;
                return operand * magicNumber;
            }
            // :: = method reference
        }).forEach(System.out::println); // El stream es perezoso, si quitariamos esta linea, el siguiente lambda empieza desde el primer elemento generado por el productor.
    }

    @Test
    public void sumSquare2() {
        assertEquals(30, Arrays.stream(new int[]{1, 2, 3, 4}).map(x -> x * x).reduce((x, y) -> x + y).getAsInt());
    }

    @Test
    public void sumSquare() {
        final int result = Arrays.stream(new int[]{1, 2, 3, 4}).map((x) -> (int) Math.pow(x, 2)).reduce((x, y) -> x + y).getAsInt();
        assertEquals("El resultado es 30, que es la suma de cuadrados de todos los elementos", 30, result);
    }

    @Test
    public void testLocal() {
        final HashMap<Integer, Integer> dict = new HashMap<>();
        dict.put(1, 5);
        dict.put(2, 6);
        dict.put(3, 7);
        dict.put(4, 8);
        final int magicNumber = 2;
        assertArrayEquals("nos devuelve un array de los valores de HashMap", new int[]{5, 6, 7, 8}, Arrays.stream(new int[]{1, 2, 3, 4}).map(x -> dict.get(x)).toArray());
        dict.put(5, 10);
    }

    private int timesTwo(int operand) {
        return operand * this.magicNumber;
    }

    @Test
    public void testThis() {
        int[] original = new int[]{1, 2, 3, 4};
        int[] scaledBy2 = new int[]{2, 4, 6, 8};
        int[] scaledBy3 = new int[]{3, 6, 9, 12};
        assertArrayEquals(scaledBy2, Arrays.stream(original).map(x -> x * this.magicNumber).toArray());
        assertArrayEquals("una clase anónima tiene su propia \"this\"", scaledBy3, Arrays.stream(original).map(new IntUnaryOperator() {
            private int magicNumber = 3;

            @Override
            public int applyAsInt(int operand) {
                return operand * this.magicNumber;
            }
        }).toArray());
        assertArrayEquals("accede this de la clase exterior usando nombre", scaledBy2, Arrays.stream(original).map(new IntUnaryOperator() {
            private int magicNumber = 3;

            @Override
            public int applyAsInt(int operand) {
                return operand * Practise.this.magicNumber;
            }
        }).toArray());
        assertArrayEquals("referencia del método", scaledBy2, Arrays.stream(original).map(this::timesTwo).toArray());
    }


    @Test
    public void arrayStream() {
        System.out.println();

        // seguira con la secuencia de terminaba anteriormente
        IntStream.generate(fibonacci).limit(20)
                 // .map(x -> x * this.magicNumber) // error: lambda no tiene su contexto propio de "this"
                 .map(x -> x * magicNumber).forEach(System.out::println);
    }

    @Test
    public void readStream() throws Exception {
        final Scanner reader = new Scanner(new FileInputStream("build.gradle"));
        Stream.generate(reader::nextLine).limit(10).map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void testCompareTo() {
        List<Integer> arrayToBeSorted = Arrays.stream(new int[]{2, 3, 5, 4, 1}).boxed().collect(Collectors.toList());
        arrayToBeSorted.sort((o1, o2) -> o1 - o2);
        List<Integer> result = Arrays.stream(new int[]{1, 2, 3, 4, 5}).boxed().collect(Collectors.toList());
        assertArrayEquals(result.toArray(), arrayToBeSorted.toArray());
    }
}
