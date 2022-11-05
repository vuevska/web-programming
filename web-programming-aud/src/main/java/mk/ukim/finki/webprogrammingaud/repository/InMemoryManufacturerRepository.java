package mk.ukim.finki.webprogrammingaud.repository;

import mk.ukim.finki.webprogrammingaud.bootstrap.DataHolder;
import mk.ukim.finki.webprogrammingaud.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {

    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    public Optional<Manufacturer> findById(Long id) {
        return DataHolder.manufacturers
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public Optional<Manufacturer> save(String name, String address) {
        Manufacturer manufacturer = new Manufacturer(name, address);
        DataHolder.manufacturers.add(manufacturer);
        return Optional.of(manufacturer);
    }

    public boolean deleteById(Long id) {
        return DataHolder.manufacturers.removeIf(m -> m.getId().equals(id));
    }
}
