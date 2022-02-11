package co.com.mutants.domain.model;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mutant")
public class Mutant extends Person {

    public Mutant(String[] dna) {
        super(dna);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mutant mutant = (Mutant) o;
        return Objects.equals(getDNA(), mutant.getDNA());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDNA());
    }

}
