package oop.company;

import java.util.ArrayList;

public class Proyect {
    private String Name;
    private float advance;
    private ArrayList<Cycle> cycles;

    public Proyect(String Name, float advance, ArrayList<Cycle> cycles) {
        this.Name = Name;
        this.advance = advance;
        this.cycles = cycles;
    }

    public String getName() {
        return Name;
    }

    public float getAdvance() {
        return advance;
    }

    public ArrayList<Cycle> getCycles() {
        return cycles;
    }
}