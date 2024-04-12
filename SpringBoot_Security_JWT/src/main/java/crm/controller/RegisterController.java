package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model, User user){
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(Model model,@ModelAttribute @Valid User user, BindingResult bindingResult) {
        User userFromDB = userService.findByUsername(user.getUsername());

        if (userFromDB != null) {
            model.addAttribute("user",user);
            model.addAttribute("alreadyRegisteredMessage",
                    "Oops!  There is already a user registered with the email provided.");
            return "register";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/register";
        } else {
            userService.saveUser(user);
            return "/login";
        }
    }

    @PostMapping("/register-admin")
    public String processRegistrationFormByAdmin(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        User userFromDB = userService.findByUsername(user.getUsername());

        if (userFromDB != null) {
            model.addAttribute("user", user);
            model.addAttribute("alreadyRegisteredMessage",
                    "Oops!  There is already a user registered with the email provided.");
           throw new RuntimeException("username esxit");
        }

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("username esxit");
        } else {
            userService.saveUserAdmin(user);
            return "redirect:/user/list";
        }
    }

}
