package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;

import java.util.List;
import java.util.Optional;

public interface BalloonService {
    List<Balloon> listAll();
    Optional<Balloon> searchByNameOrDescription(String name, String description);
    Optional<Balloon> saveBalloon(String name, String description, Long manufacturerId);

    Optional<Balloon> edit(Long id, String color, String description, Long manufacturerId);
    void deleteById(Long id);
    Optional<Balloon> findById(Long id);
}
