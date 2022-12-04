package mk.ukim.finki.webprogrammingaud.repository.impl;

import mk.ukim.finki.webprogrammingaud.bootstrap.DataHolder;
import mk.ukim.finki.webprogrammingaud.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoryRepository {
    public List<Category> findAll() {
        return DataHolder.categories;
    }

    public Category save(Category category) {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            return null;
        }
        DataHolder.categories.removeIf(c -> c.getName().equals(category.getName()));
        DataHolder.categories.add(category);
        return category;
    }

    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
    public Optional<Category> findByName(String name) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    public List<Category> search(String text) {
        return DataHolder.categories.stream()
                .filter(c -> c.getName().contains(text) || c.getDescription().contains(text))
                .collect(Collectors.toList());
    }

    public void delete(String name) {
        if (name == null) {
            return;
        }
        DataHolder.categories.removeIf(c -> c.getName().equals(name));
    }
}
