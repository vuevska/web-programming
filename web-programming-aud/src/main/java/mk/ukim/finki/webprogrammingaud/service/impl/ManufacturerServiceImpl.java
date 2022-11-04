package mk.ukim.finki.webprogrammingaud.service.impl;

import mk.ukim.finki.webprogrammingaud.model.Manufacturer;
import mk.ukim.finki.webprogrammingaud.repository.InMemoryManufacturerRepository;
import mk.ukim.finki.webprogrammingaud.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final InMemoryManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(InMemoryManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return this.manufacturerRepository.findById(id);
    }
}
