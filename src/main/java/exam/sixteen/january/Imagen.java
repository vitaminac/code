package exam.sixteen.january;

import java.io.*;
import java.util.function.IntBinaryOperator;

public class Imagen {
    private final int M, N; // dimensiones de la matriz
    private int pixels[][]; // datos de la matriz

    /*
     * Escribir un constructor que inicialice la imagen con dos enteros M y N correspondientes a la dimensión M x N (argumentos del constructor)
     * y valor aleatorio para todos los píxeles.
     * Se recuerda que Math.random() devuelve un valor aleatorio en el intervalo [0,1)
     * */
    public Imagen(int m, int n) {
        this(m, n, (i, j) -> (int) Math.random() * 255);
    }

    private Imagen(int m, int n, IntBinaryOperator operator) {
        this.M = m;
        this.N = n;
        this.setPixels(operator);
    }

    private Imagen(Imagen imagen) {
        this(imagen.M, imagen.N, (i, j) -> imagen.pixels[i][j]);
    }

    /*
     * Escribir un segundo constructor que inicialice la imagen con los valores extraídos de un fichero  de texto,
     * cuyo  nombre se pasará como argumento.
     * Cada línea  de texto  del fichero contendrá un único valor,
     * siendo los dos primeros los correspondientes a la dimensión de la matriz,
     * y los restantes los correspondientes a los valores de los píxeles
     * */
    public Imagen(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("path"))) {
            this.M = Integer.parseInt(reader.readLine());
            this.N = Integer.parseInt(reader.readLine());
            for (int i = 0; i < this.M; i++) {
                for (int j = 0; j < this.N; j++) {
                    pixels[i][j] = Integer.parseInt(reader.readLine());
                }
            }
        }
    }

    public static void main(String[] args) {
        Imagen A = new Imagen(5, 6);
        Imagen B = null;
        try {
            B = new Imagen(" imagen.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Imagen C, D, E;
        try {
            A.sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Imagen c = A.negative();
        } catch (EImagenOriginalYNegativaCoinciden e) {
            e.getMessage();
        }
        D = Imagen.transpose(B);
        E = B.clone();
    }

    /*
     * Programar un método estático que intercambie las filas y las columnas de una imagen,
     * y que devuelva la imagen resultante,
     * sin modificar la original.
     * */
    private static Imagen transpose(Imagen imagen) {
        return new Imagen(imagen.N, imagen.M, (i, j) -> imagen.pixels[j][i]);
    }

    /* Programar un método que realice y devuelva una copia de la imagen original */
    @Override
    public Imagen clone() {
        return new Imagen(this);
    }

    /* Programar un método que calcule tres  valores:
     * la media, el máximo y el mínimo de todos los elementos de la imagen
     * y los escriba en un  fichero de texto llamado “salida.txt”
     * */
    public void sum() throws IOException {
        int[] res = this.getMaxMinMean();
        int max = res[0];
        int min = res[1];
        int mean = res[2];
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("salida.txt")))) {
            writer.println(max);
            writer.println(min);
            writer.println(mean);
        }
    }

    /*
     * Programar un método que devuelva el negativo de la imagen, sin modificar el original:
     * cada  valor del negativo debe ser el simétrico respecto al valor original,
     * tomando como valor central el 128.
     * Es decir, si en el original aparece un 0,
     * en el negativo debe aparecer un 255;
     * si en el original aparece un 1, en el negativo debe aparecer un 254; y  así sucesivamente.
     * Si  todos los valores de la imagen son 128,
     * debe  devolver  la  excepción EImagenOriginalYNegativaCoinciden (excepción propia que hay que programar)
     * */
    public Imagen negative() throws EImagenOriginalYNegativaCoinciden {
        int[] res = this.getMaxMinMean();
        if (res[0] == 128 && res[1] == 128) {
            throw new EImagenOriginalYNegativaCoinciden();
        }
        return new Imagen(this.M, this.N, (i, j) -> 255 - this.pixels[i][j]);
    }

    private int[] getMaxMinMean() {
        int max = 0, min = 255, sum = 0;
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.N; j++) {
                int value = this.pixels[i][j];
                if (value > max) {
                    max = value;
                }
                if (value < min) {
                    min = value;
                }
                sum += value;

            }
        }
        int mean = sum / (this.M * this.N);
        return new int[]{max, min, mean};
    }

    private void setPixels(IntBinaryOperator operator) {
        this.pixels = new int[this.M][this.N];
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.N; j++) {
                this.pixels[i][j] = operator.applyAsInt(i, j);
            }
        }
    }
}
