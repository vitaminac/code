package oop.session.company;

import java.util.ArrayList;

public class Phase {
    private TypePhase tipo;
    private ArrayList<Iteration> iteraciones;

    public Phase(TypePhase tipo, ArrayList<Iteration> iteracions) {
        this.tipo = tipo;
        this.iteraciones = iteraciones;
    }
}
