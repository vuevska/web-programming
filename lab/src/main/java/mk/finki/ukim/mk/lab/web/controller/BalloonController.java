package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
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


    public BalloonController(BalloonService balloonService, ManufacturerService manufacturerService) {
        this.balloonService = balloonService;

        this.manufacturerService = manufacturerService;
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

    //save color
    @PostMapping("/save-color")
    public String saveBalloonColor(HttpServletRequest request) {
        String color = request.getParameter("color");
        request.getSession().setAttribute("color", color);
        return "redirect:/selectBalloon";
    }

    //add balloon
    @GetMapping("/add-form")
    public String getAddBalloonPage(Model model) {
        /*List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);*/
        return "add-balloon";
    }

    //add balloon
    @PostMapping("/add")
    public String saveBalloon(@RequestParam String color,
                              @RequestParam String description,
                              @RequestParam Long manufacturer) {
        this.balloonService.saveBalloon(color, description, manufacturer);
        return "redirect:/balloons";
    }

    //edit balloon
    @GetMapping("/edit-form/{id}")
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
    public String deleteBalloon(@PathVariable Long id) {
        this.balloonService.deleteById(id);
        return "redirect:/balloons";
    }

    // show orders
    @GetMapping("/orders")
    public String getAllOrdersPage() {
        return "redirect:/userOrders";
    }

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
    }

}