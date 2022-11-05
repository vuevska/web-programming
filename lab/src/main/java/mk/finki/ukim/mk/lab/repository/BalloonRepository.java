package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Balloon;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BalloonRepository {

    public List<Balloon> findAllBalloons() {
        return DataHolder.balloonList;
    }

    public List<Balloon> findAllByNameOrDescription(String text) {
        return DataHolder.balloonList.stream()
                .filter(b -> b.getName().contains(text) || b.getDescription().contains(text))
                .collect(Collectors.toList());
    }

}
