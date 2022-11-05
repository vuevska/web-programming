package mk.finki.ukim.mk.lab.bootstrap;

import lombok.Getter;
import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static List<Balloon> balloonList;
    public static List<Order> orderList;

    @PostConstruct
    public void init() {
        balloonList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            balloonList.add(new Balloon("Balloon " + i, "Description " + i));
        }

        orderList = new ArrayList<>();
    }
}
