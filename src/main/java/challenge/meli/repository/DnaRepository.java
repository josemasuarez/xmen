package challenge.meli.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import challenge.meli.model.DNA;

public interface DnaRepository extends MongoRepository<DNA, String> {

	Long countByDnaType(String type);
}
