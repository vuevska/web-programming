package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/balloons")
public class BalloonController {

    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;
    //private final OrderService orderService;

    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
        //this.orderService = orderService;
    }

    @GetMapping
    public String getBalloonsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Balloon> balloons = this.balloonService.listAll();
        model.addAttribute("balloons", balloons);
        return "listBalloons";
    }

    @GetMapping("access_denied")
    public String accessDeniedPage() {
        return "access_denied";
    }

    //save color
    @PostMapping("/save-color")
    public String saveBalloonColor(HttpServletRequest request) {
        String color = request.getParameter("color");
        request.getSession().setAttribute("color", color);
        return "selectBalloonSize";
    }

    @PostMapping("/save-size")
    public String saveBalloonSize(HttpServletRequest request) {
        String size = request.getParameter("size");
        request.getSession().setAttribute("size", size);
        return "deliveryInfo";
    }

    //add balloon
    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddBalloonPage(Model model) {
        /*List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);*/
        return "add-balloon";
    }

    //add balloon
    @PostMapping("/add")
    public String saveBalloon(@RequestParam(required = false) Long id,
                              @RequestParam String color,
                              @RequestParam String description,
                              @RequestParam Long manufacturer) {
        if (id != null) {
            this.balloonService.edit(id, color, description, manufacturer);
        } else {
            this.balloonService.saveBalloon(color, description, manufacturer);
        }
        return "redirect:/balloons";
    }

    //edit balloon
    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditBalloonPage(@PathVariable Long id, Model model) {
        if (this.balloonService.findById(id).isPresent()) {
            Balloon balloon = this.balloonService.findById(id).get();
            /*List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);*/
            model.addAttribute("balloon", balloon);
            return "add-balloon";
        }
        return "redirect:/balloons?error=BalloonNotFound";
    }

    //delete balloon
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBalloon(@PathVariable Long id) {
        this.balloonService.deleteById(id);
        return "redirect:/balloons";
    }

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        //model.addAttribute("users", this.userService.listAllUsers());
    }
}