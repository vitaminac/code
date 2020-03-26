package oop;

import java.util.Objects;

public class Cuenta implements Comparable<Cuenta> {
    private long codigo;
    private double saldo;

    public Cuenta(long codigo, double saldo) {
        this.codigo = codigo;
        this.saldo = saldo;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    @Override
    public int compareTo(Cuenta o) {
        return (int) ((int) this.codigo - o.codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cuenta cuenta = (Cuenta) o;
        return getCodigo() == cuenta.getCodigo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo());
    }

    @Override
    public String toString() {
        return "codigo:" + this.codigo + "saldo:" + this.saldo;
    }
}
