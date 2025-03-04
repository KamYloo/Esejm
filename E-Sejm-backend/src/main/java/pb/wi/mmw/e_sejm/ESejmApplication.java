package pb.wi.mmw.e_sejm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ESejmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ESejmApplication.class, args);
	}

}
