package sk.ukf.latriesta.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.latriesta.dto.ProfileDto;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.UserRepository;
import sk.ukf.latriesta.service.user.OrderService;
import sk.ukf.latriesta.service.user.UserService;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserRepository userRepo;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProfileController(UserRepository userRepo, UserService userService, OrderService orderService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.orderService = orderService;
    }

    @ModelAttribute("pendingCount")
    public Long pendingCount() {
        Long c = orderService.countPendingOrders();
        return c != null ? c : 0L;
    }

    @GetMapping("/profile")
    public String profileView(Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "profile/view";
    }

    @GetMapping("/profile/edit")
    public String profileForm(Model model, Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        ProfileDto dto = new ProfileDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setAvatarUrl(user.getAvatarUrl());
        model.addAttribute("profileDto", dto);
        return "profile/edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("profileDto") ProfileDto dto,
                                BindingResult result,
                                Principal principal,
                                RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "profile/edit";
        }
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        try {
            user.setUsername(dto.getUsername());
            user.setAddress(dto.getAddress());
            user.setPhone(dto.getPhone());
            user.setAvatarUrl(dto.getAvatarUrl());
            if (dto.getPassword() != null && !dto.getPassword().isEmpty() &&
                    dto.getPassword().equals(dto.getConfirmPassword())) {
                user.setPassword(dto.getPassword());
            }
            userRepo.save(user);
            ra.addFlashAttribute("success", "Profil bol aktualizovaný.");
            return "redirect:/profile";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile/edit";
        }
    }
}