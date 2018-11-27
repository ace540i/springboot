package spectra.billing.prh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import spectra.billing.prh.repository.CustomRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class PrhApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrhApplication.class, args);
	}
}
