package oop.empresas;

public class Cliente extends Persona {
    private int telefono;

    public Cliente(String nombre, int anoDeNacimiento, int telefono) {
        super(nombre, anoDeNacimiento);
        this.telefono = telefono;
    }

    @Override
    public void mostrar() {
        super.mostrar();
        System.out.print(this.telefono);
    }
}
