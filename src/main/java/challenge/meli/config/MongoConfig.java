package challenge.meli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "challenge.meli")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public MongoClient mongoClient() {

		MongoClientURI uri = new MongoClientURI(
				"mongodb://josemasuarez:123321@meli-shard-00-00-njt7y.mongodb.net:27017,meli-shard-00-01-njt7y.mongodb.net:27017,meli-shard-00-02-njt7y.mongodb.net:27017/test?ssl=true&replicaSet=meli-shard-0&authSource=admin");

		return new MongoClient(uri);
	}

	@Override
	protected String getDatabaseName() {
		return "meli";
	}
	
    @Override
    protected String getMappingBasePackage() {
        return "challenge.meli";
    }

}
