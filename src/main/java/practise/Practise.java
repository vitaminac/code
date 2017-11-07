package practise;

import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Practise {
    private final int magicNumber = 3;

    public static void main(String[] args) {
        final int magicNumber = 2;
        Arrays.stream(new int[]{1, 2, 3, 4})
                .map(x -> x * magicNumber)
                .forEach(System.out::println);

        IntSupplier fibonacci = new IntSupplier() {
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
        IntStream.generate(fibonacci)
                .limit(20)
                .map(new IntUnaryOperator() {
                    @Override
                    public int applyAsInt(int operand) {
                        // magicNumber = 2; <- error, local variable should be final or effectively final
                        // return operand * this.magicNumber;
                        return operand * magicNumber;
                    }
                    // :: = method reference
                })
                .forEach(System.out::println); // El stream es perezoso, si quitariamos esta linea, el siguiente lambda empieza desde el primer elemento generado por el productor.

        System.out.println();

        // seguira con la secuencia de terminaba anteriormente
        IntStream.generate(fibonacci)
                .limit(20)
                // .map(x -> x * this.magicNumber) // error: lambda no tiene su contexto propio de "this"
                .map(x -> x * magicNumber)
                .forEach(System.out::println);
    }
}
