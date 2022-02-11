package co.com.mutants.domain.model;

import org.springframework.data.annotation.Id;

public abstract class Person {

    @Id
    protected String dna;

    protected Person(String[] dna) {
        this.setDNA(String.join(",", dna));
    }

    public String getDNA() {
        return dna;
    }

    public void setDNA(String dna) {
        this.dna = dna;
    }

}