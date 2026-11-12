import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "uy.um.mascotas")
@EntityScan("uy.um.mascotas.core.domain")
@EnableJpaRepositories("uy.um.mascotas.core.repository")
public class MascotasWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MascotasWebApplication.class, args);
    }
}