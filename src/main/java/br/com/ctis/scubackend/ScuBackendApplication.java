package br.com.ctis.scubackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe responsável por inicializar a aplicação
 */
@SpringBootApplication
@EntityScan(
			basePackages = {
						"br.com.ctis.scubackend.server.entity"
			}
)
@EnableJpaRepositories(
			basePackages = {
						"br.com.ctis.scubackend.server.repository"
			}
)
public class ScuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScuBackendApplication.class, args);
	}

}
