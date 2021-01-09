package com.josep.hris.web;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UsersController {
    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(Model model)
    {
        RegistrationForm users = new RegistrationForm();
        model.addAttribute("users", users);
        return "users/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@Valid RegistrationForm users, BindingResult result, Model model)
    {
//        validator.validate(users, result);
        System.out.println("Masuk");
        if (result.hasErrors()) {
            System.out.println(users.getPhone());
            System.out.println(users.getFullName());
            model.addAttribute("users", result);
            return "users/registration";
        }
        userService.register(users);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout)
    {
        if (error != null)
            model.addAttribute("message", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
