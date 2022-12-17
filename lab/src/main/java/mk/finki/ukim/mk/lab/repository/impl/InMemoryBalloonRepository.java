package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryBalloonRepository {

    public List<Balloon> findAllBalloons() {
        return DataHolder.balloonList;
    }

    public List<Balloon> findAllByNameOrDescription(String text) {
        return DataHolder.balloonList.stream()
                .filter(b -> b.getName().contains(text) || b.getDescription().contains(text))
                .collect(Collectors.toList());
    }

    public Optional<Balloon> save(String name, String description, Manufacturer manufacturer) {
        DataHolder.balloonList.removeIf(b -> b.getName().equals(name));
        Balloon balloon = new Balloon(name, description, manufacturer);
        DataHolder.balloonList.add(balloon);
        return Optional.of(balloon);
    }

    public void deleteById(Long id) {
        DataHolder.balloonList.removeIf(b -> b.getId().equals(id));
    }

    public Optional<Balloon> findById(Long id) {
        return DataHolder.balloonList
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }
}
