package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService, UserService userService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllOrdersPage(@RequestParam(required = false) String error,
                                   HttpServletRequest request,
                                   Model model,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                   LocalDateTime from,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                   LocalDateTime to,
                                   @RequestParam(required = false)
                                   Long userId) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("users", this.userService.listAllUsers());
        User user = (User) request.getSession().getAttribute("user");
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());
        List<Order> orders = null;
        if (from == null || to == null) {
            orders = this.shoppingCartService.listAllOrdersInShoppingCart(shoppingCart.getId());
        } else {
            orders = this.orderService.findAllOrdersByDateBetween(from, to);
        }

        if (userId != null) {
            orders = this.shoppingCartService.listAllOrdersInShoppingCart(userId);
        }
        model.addAttribute("orders", orders);
        return "userOrders";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             LocalDateTime dateCreated,
                             HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("color");
        String description = (String) request.getSession().getAttribute("size");

        User user = (User) request.getSession().getAttribute("user");
        Order order = this.orderService.placeOrder(name, description, dateCreated, user.getId());

        this.shoppingCartService.addOrderToShoppingCart(user.getUsername(), order.getId());
        return "redirect:/ConfirmationInfo";
    }
}
