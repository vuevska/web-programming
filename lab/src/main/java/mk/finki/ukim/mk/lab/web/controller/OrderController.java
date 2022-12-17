package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllOrdersPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Order> orders = this.orderService.findAll();
        model.addAttribute("orders", orders);
        return "userOrders";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             LocalDateTime dateCreated, HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("color");
        String description = (String) request.getSession().getAttribute("size");
        User user = (User) request.getSession().getAttribute("user");
        this.orderService.placeOrder(name, description, dateCreated, user.getId());
        //this.orderService.placeOrder(name, description, dateCreated);
        return "redirect:/ConfirmationInfo";
    }

    @PostMapping("/filter-orders")
    public String filterOrders(@RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               LocalDateTime from,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               LocalDateTime to,
                               RedirectAttributes redirectAttributes) {
        List<Order> filterOrders = this.orderService.findAllOrdersByDateBetween(from, to);
        redirectAttributes.addFlashAttribute("filterOrders", filterOrders);
        return "redirect:/orders";
    }
}
