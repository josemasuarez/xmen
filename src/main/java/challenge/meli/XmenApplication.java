package challenge.meli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "challenge.meli")
public class XmenApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmenApplication.class, args);
	}
}
