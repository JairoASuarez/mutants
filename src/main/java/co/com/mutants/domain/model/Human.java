package co.com.mutants.domain.model;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "human")
public class Human extends Person {

    public Human(String[] dna) {
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
        Human human = (Human) o;
        return Objects.equals(getDNA(), human.getDNA());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDNA());
    }

}