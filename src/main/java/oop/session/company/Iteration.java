package oop.session.company;

import java.util.ArrayList;
import java.util.Date;

public class Iteration {
    private Date comienzo;
    private ArrayList<Artifact> artefacts;

    public Iteration(Date start, ArrayList<Artifact> artefacts) {
        this.comienzo = start;
        this.artefacts = artefacts;
    }

    public void produce(Artifact artifact) {
        this.artefacts.add(artifact);
    }
}
