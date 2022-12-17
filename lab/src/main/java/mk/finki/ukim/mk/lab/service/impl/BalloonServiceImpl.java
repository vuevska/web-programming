package mk.finki.ukim.mk.lab.service.impl;


import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.exceptions.BalloonNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.ManufacturerNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.BalloonRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BalloonServiceImpl implements BalloonService {
    private final BalloonRepository balloonRepository;
    private final ManufacturerRepository manufacturerRepository;

    public BalloonServiceImpl(BalloonRepository balloonRepository, ManufacturerRepository manufacturerRepository) {
        this.balloonRepository = balloonRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Balloon> listAll() {
        return balloonRepository.findAll();
    }

    @Override
    public Optional<Balloon> searchByNameOrDescription(String name, String description) {
        return balloonRepository.findAllByNameOrDescription(name, description);
    }
    @Override
    @Transactional
    public Optional<Balloon> saveBalloon(String name, String description, Long manufacturerId) {
        Manufacturer manufacturer = this.manufacturerRepository
                .findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        this.balloonRepository.deleteByName(name);
        return Optional.of(this.balloonRepository.save(new Balloon(name, description, manufacturer)));
    }

    @Override
    @Transactional
    public Optional<Balloon> edit(Long id, String color, String description, Long manufacturerId) {
        Balloon balloon = this.balloonRepository
                .findById(id).orElseThrow(() -> new BalloonNotFoundException(id));
        balloon.setName(color);
        balloon.setDescription(description);
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        balloon.setManufacturer(manufacturer);
        return Optional.of(this.balloonRepository.save(balloon));
    }

    @Override
    public void deleteById(Long id) {
        balloonRepository.deleteById(id);
    }

    @Override
    public Optional<Balloon> findById(Long id) {
        return this.balloonRepository.findById(id);
    }
}
