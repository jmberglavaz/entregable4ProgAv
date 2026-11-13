package uy.um.mascotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "uy.um.mascotas")
public class MascotasWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MascotasWebApplication.class, args);
    }
}