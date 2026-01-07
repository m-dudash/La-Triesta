package sk.ukf.latriesta.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.ukf.latriesta.dto.RegistrationDto;
import sk.ukf.latriesta.service.user.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }


    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "auth/register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationDto") RegistrationDto registrationDto,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Heslá sa nezhodujú");
            return "auth/register";
        }

        try {
            userService.registerUser(registrationDto);
            model.addAttribute("success", "Registrácia úspešná!  Môžete sa prihlásiť.");
            return "redirect:/auth/login? registered=true";
        } catch (RuntimeException e) {
            model. addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}