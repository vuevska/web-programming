package mk.finki.ukim.mk.lab.bootstrap;

import lombok.Getter;
import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
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
    public static List<Manufacturer> manufacturers;
    @PostConstruct
    public void init() {
        balloonList = new ArrayList<>();
        manufacturers = new ArrayList<>();

        Manufacturer Amazon = new Manufacturer("Amazon", "USA", "NY");
        Manufacturer AliExpress = new Manufacturer("AliExpress", "China", "Shanghai");
        Manufacturer BalloonMk = new Manufacturer("BalloonMk", "Macedonia", "Skopje");
        Manufacturer PartyMaker = new Manufacturer("PartyMaker", "Macedonia", "Ohrid");
        Manufacturer MakeYourOwn = new Manufacturer("MakeYourOwn", "Macedonia", "Bitola");
        manufacturers.add(Amazon);
        manufacturers.add(AliExpress);
        manufacturers.add(BalloonMk);
        manufacturers.add(PartyMaker);
        manufacturers.add(MakeYourOwn);

        balloonList.add(new Balloon("Green", "Big", Amazon));
        balloonList.add(new Balloon("Pink", "Medium", BalloonMk));
        balloonList.add(new Balloon("Purple", "Small", MakeYourOwn));
        balloonList.add(new Balloon("Orange", "Medium", AliExpress));
        balloonList.add(new Balloon("Gold", "Special", PartyMaker));
        balloonList.add(new Balloon("Red", "Small", BalloonMk));
        balloonList.add(new Balloon("Blue", "Big", Amazon));
        balloonList.add(new Balloon("Silver", "Special", AliExpress));
        balloonList.add(new Balloon("Gray", "Small", MakeYourOwn));
        balloonList.add(new Balloon("Whitle", "Big", PartyMaker));

        orderList = new ArrayList<>();
    }
}
