package exam.sixteen.january;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Cliente {
    private HashSet<Cuenta> cuentas;
    private String dni;
    private String nombre;

    public Cliente(String dni, String nombre) {
        this.cuentas = new HashSet<>();
        this.dni = dni;
        this.nombre = nombre;
    }

    public Cuenta consultarCuenta(long codigo) {
        final Iterator<Cuenta> iterator = this.cuentas.iterator();
        while (iterator.hasNext()) {
            Cuenta cuenta = iterator.next();
            if (cuenta.getCodigo() == codigo) {
                return cuenta;
            }
        }
        return null;
    }

    public ArrayList<Cuenta> ordena() {
        ArrayList<Cuenta> cuentas = new ArrayList<>(this.cuentas);
        cuentas.sort((o1, o2) -> o1.getSaldo() - o2.getSaldo() > 0 ? 1 : -1);
        return cuentas;
    }

    public void addCliente() {

    }
}
