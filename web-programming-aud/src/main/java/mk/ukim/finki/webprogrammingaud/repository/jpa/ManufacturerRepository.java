package mk.ukim.finki.webprogrammingaud.repository.jpa;

import mk.ukim.finki.webprogrammingaud.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
