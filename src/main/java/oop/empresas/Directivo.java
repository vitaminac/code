package oop.empresas;

import java.util.ArrayList;

public class Directivo extends Empleado {
    private final ArrayList<Empleado> subordinados;
    private String categoria;

    public Directivo(String nombre, int anoDeNacimiento, double sueldo, String categoria, ArrayList<Empleado> subordinados) {
        super(nombre, anoDeNacimiento, sueldo);
        this.categoria = categoria;
        this.subordinados = new ArrayList<Empleado>(subordinados);
    }

    @Override
    public void mostrar() {
        super.mostrar();
        System.out.print("tengo siguiente subordinados");
        for (Empleado e : this.getSubordinados()) {
            e.mostrar();
        }
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Empleado> getSubordinados() {
        return this.subordinados;
    }
}
