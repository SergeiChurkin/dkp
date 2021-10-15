package org.frozenfish.controller;

import org.frozenfish.entity.User;
import org.frozenfish.model.CaptchaResponse;
import org.frozenfish.repo.UserRepo;
import org.frozenfish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        String url = String.format(CAPTCHA_URL,secret,captchaResponse);
        CaptchaResponse responseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);


        if(user.getUsername().isEmpty()){
            model.addAttribute("usernameError", "Имя пользователя не может быть пустым");
            return "registration";
        }

        if(user.getPassword().isEmpty()){
            model.addAttribute("passwordError", "Пароль не может быть пустым");
            return "registration";
        }

        if (passwordConfirm.isEmpty()) {
            model.addAttribute("password2Error", "Введите подтверждение пароля");
            return "registration";
        }

        if (!user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли должны совпадать!");
            model.addAttribute("password2Error", "Пароли должны совпадать!");
            return "registration";
        }


        if (bindingResult.hasErrors() || !Objects.requireNonNull(responseDto).isSuccess()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute(user);
            return "registration";
        }



        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Такое имя пользователя существует");
            return "registration";
        }


        return "redirect:/login";
    }
}
