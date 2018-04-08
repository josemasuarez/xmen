package challenge.meli.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import challenge.meli.model.Organism;

public interface OrganismRepository extends MongoRepository<Organism, String> {

	Long countByOrganismType(String type);
}
