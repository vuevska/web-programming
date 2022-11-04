package mk.ukim.finki.webprogrammingaud.bootstrap;

import mk.ukim.finki.webprogrammingaud.model.Category;
import mk.ukim.finki.webprogrammingaud.model.Manufacturer;
import mk.ukim.finki.webprogrammingaud.model.Product;
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
    public static List<Manufacturer> manufacturers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        categories.add(new Category("Software", "Software Category"));
        categories.add(new Category("Books", "Books Category"));

        users.add(new User("maja.vue", "mv", "Maja", "Vuevska"));
        users.add(new User("billie.eilish", "be", "Billie", "Eilish"));

        Manufacturer manufacturer = new Manufacturer("Nike", "NY NY");
        manufacturers.add(manufacturer);
        Category category = new Category("Sport", "Sport Category");
        products.add(new Product("Basketball", 120.50, 5, category, manufacturer));
        products.add(new Product("Volleyball", 100.50, 4, category, manufacturer));
        products.add(new Product("Tennis ball", 90.30, 10, category, manufacturer));
    }
}
