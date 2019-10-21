package siit.mytrips.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import siit.mytrips.project.model.Trip;

@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String addTrip(Model model) {
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        return "profilepage";
    }
}
