package mk.finki.ukim.mk.lab;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.UserFullName;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LabApplicationTests {

    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    OrderService orderService;

    @Autowired
    BalloonService balloonService;

    private static Manufacturer m1;
    private static Manufacturer m2;
    private static Optional<Balloon> b1;
    private static Optional<Balloon> b2;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {
            m1 = manufacturerService.save("Manufacturer 1", "Country 1", "Address 1").get();
            m2 = manufacturerService.save("Manufacturer 2", "Country 2", "Address 2").get();
            b1 = balloonService.saveBalloon("Balloon 1", "Description 1", m1.getId());
            b2 = balloonService.saveBalloon("Balloon 2", "Description 2", m2.getId());
            String user = "user";
            String admin = "admin";
            UserFullName userFull = new UserFullName();
            userFull.setName("user");
            userFull.setSurname("user");
            UserFullName adminFull = new UserFullName();
            adminFull.setName("admin");
            adminFull.setSurname("admin");
            userService.register(userFull, user, user, user, LocalDate.of(2001, 2, 3), Role.ROLE_USER);
            userService.register(adminFull, admin, admin, admin, LocalDate.of(2000, 10, 10), Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetProducts() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/balloons");
        this.mockMvc.perform(productRequest).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("balloons"))
                .andExpect(MockMvcResultMatchers.view().name("listBalloons"));
    }

}
