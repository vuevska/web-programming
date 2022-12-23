package mk.finki.ukim.mk.lab.selenium;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.UserFullName;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    @Autowired
    UserService userService;
    @Autowired
    ManufacturerService manufacturerService;
    @Autowired
    BalloonService balloonService;
    private HtmlUnitDriver driver;
    private static User adminUser;
    private static final String admin = "admin";
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if(this.driver != null) {
            this.driver.close();
        }
    }

    private void initData() {
        if (!dataInitialized) {

            Manufacturer m1 = manufacturerService.save("Man1", "Macedonia", "Skopje").get();
            Manufacturer m2 = manufacturerService.save("Man2", "Macedonia", "Ohrid").get();
            balloonService.saveBalloon("Balloon1", "b1", m1.getId());
            balloonService.saveBalloon("Balloon2", "b2", m2.getId());

            UserFullName adminFull = new UserFullName();
            adminFull.setName(admin);
            adminFull.setSurname(admin);
            adminUser = userService.register(adminFull, admin, admin, admin, LocalDate.of(2000, 10, 10), Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() {
        BalloonsPage balloonsPage = BalloonsPage.to(this.driver);
        balloonsPage.assertElements(2, 0, 0, 0);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        balloonsPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), admin);
        balloonsPage.assertElements(2, 2, 2, 1);

        LoginPage.logout(this.driver);

        balloonsPage = BalloonsPage.to(this.driver);
        balloonsPage.assertElements(2, 0, 0, 0);
    }
}
