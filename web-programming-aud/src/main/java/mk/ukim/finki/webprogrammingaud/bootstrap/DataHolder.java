package mk.ukim.finki.webprogrammingaud.bootstrap;

import mk.ukim.finki.webprogrammingaud.model.Category;
import mk.ukim.finki.webprogrammingaud.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
/**
* @Component - there will be one instance of the class
 * created when the application starts -> Singleton
 * Also the method init() won't be called by its own,
 * that's why we added the @PostConstruct annotation
* */
public class DataHolder {
    public static List<Category> categories = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        categories.add(new Category("Software", "Software Category"));
        categories.add(new Category("Books", "Books Category"));

        users.add(new User("maja.vue", "mv", "Maja", "Vuevska"));
        users.add(new User("billie.eilish", "be", "Billie", "Eilish"));
    }
}
