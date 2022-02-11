package co.com.mutants.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.com.mutants.domain.model.Human;

@Repository
public interface HumanRepository extends MongoRepository<Human, String> {

}