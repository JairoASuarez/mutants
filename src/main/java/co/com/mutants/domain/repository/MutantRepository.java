package co.com.mutants.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.com.mutants.domain.model.Mutant;

@Repository
public interface MutantRepository extends MongoRepository<Mutant, String> {

}