package mk.ukim.finki.webprogrammingaud.bootstrap;

import mk.ukim.finki.webprogrammingaud.model.Category;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
// se anotira so @Component so cel da bide kreirana edna instanca koga kje se starta aplikacijata -> Singleton
// i zatoa treba da bide povikan metodot init, no sam po sebe nema da bide povikan, zatoa se dodava anotacija
// @PostConstruct pred metodot
public class DataHolder {
    public static List<Category> categories = new ArrayList<>();

    @PostConstruct
    public void init() {
        categories.add(new Category("Software", "Software Category"));
        categories.add(new Category("Books", "Books Category"));
    }
}
