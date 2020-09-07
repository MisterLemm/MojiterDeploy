package com.example.mojiter.controller;

import com.example.mojiter.domain.User;
import com.example.mojiter.domain.dto.CaptchaResponseDto;
import com.example.mojiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam ("password2") String passwordConfirmation,
                               @RequestParam("g-recaptcha-response") String captchaResponse,
                               @Valid User user,
                               BindingResult bindingResult,
                               Model model) {

        String captchaUrl = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto responseDto = restTemplate.
                postForObject(captchaUrl, Collections.emptyList(), CaptchaResponseDto.class);
        if (!responseDto.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirmation);
        if (isConfirmEmpty){
            model.addAttribute("password2Error", "password confirmation cannot be empty");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)){
            model.addAttribute("passwordError", "Passwords are not identical");
        }

        if (isConfirmEmpty || bindingResult.hasErrors() || !responseDto.isSuccess()){
            Map<String, String> errors = ControllerUtil.getErrorsMap(bindingResult);

            model.addAttribute(errors);

            return "registration";
        }
        if (!userService.addUser(user)){
            model.addAttribute("usernameError", "That Mojito-drinkee is already in the club");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate (Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if (isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code was not found");
        }
        return "login";
    }
}
