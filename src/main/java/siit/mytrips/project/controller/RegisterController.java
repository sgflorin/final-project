package siit.mytrips.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import siit.mytrips.project.model.User;
import siit.mytrips.project.persistence.UserRepository;

import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCrypt;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView("registerpage");
        User user = new User();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/confirmeduser", method = RequestMethod.POST)
    public ModelAndView saveuser(@ModelAttribute User user) {

        ModelAndView model = new ModelAndView("customlogin");

        if (user != null && user.getId() != -1 ) {
            Optional<User> personalOpt = userRepository.findById(user.getId());

            if (personalOpt.isPresent()) {
                User persisted = personalOpt.get();
                persisted.setId(user.getId());
                persisted.setFirstName(user.getFirstName());
                persisted.setLastName(user.getLastName());
                persisted.setUsername(user.getUsername());
                persisted.setPassword(bCrypt.encode(user.getPassword()));
                persisted.setEmail(user.getEmail());

                userRepository.save(persisted);
            } else {
                user.setPassword(bCrypt.encode(user.getPassword()));
                userRepository.save(user);
            }
        }

        model.addObject("userSaved", user.getFirstName() + " --- with ID: " + user.getId());
        return model;
    }
}
