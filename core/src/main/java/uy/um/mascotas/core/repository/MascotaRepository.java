package uy.um.mascotas.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.mascotas.core.domain.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
