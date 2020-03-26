package oop.empresas;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Empresa {
    private final ArrayList<Empleado> empleados;
    private final ArrayList<Cliente> clients;

    public Empresa(ArrayList<Empleado> empleados, ArrayList<Cliente> clients) {
        this.empleados = new ArrayList<>(empleados);
        this.clients = new ArrayList<>(clients);
    }

    public ArrayList<Empleado> getEmpleados() {
        return this.empleados;
    }

    public ArrayList<Cliente> getClients() {
        return this.clients;
    }

    public void addEmpleado(Empleado empleado) {
        this.getEmpleados().add(empleado);
    }

    public void addCliente(Cliente cliente) {
        this.getClients().add(cliente);
    }

    public void mostrar() {
        Stream<Persona> personaStream = Stream.concat(this.getEmpleados().stream(), this.getClients().stream());
        personaStream.forEach(Persona::mostrar);
    }
}
