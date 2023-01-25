package net.therap.estaurant.controller;

import net.therap.estaurant.command.Password;
import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@SessionAttributes(Constants.PASSWORD)
public class AuthenticationController {

    private static final String HOME = "/";
    private static final String UPDATE_PASSWORD_URL = "update-password";
    private static final String UPDATE_PASSWORD_VIEW = "password-form";
    private static final String SAVE_PASSWORD_URL = "/update-password/update";

    @Autowired
    private UserService userService;

    @InitBinder()
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordValidator());
    }

    @GetMapping(UPDATE_PASSWORD_URL)
    String showUpdatePage(ModelMap modelMap, @SessionAttribute(Constants.ACTIVE_USER) User user) {
        Password password = new Password();
        password.setStoredUserPassword(user.getPassword());
        modelMap.put(Constants.PASSWORD, password);

        return UPDATE_PASSWORD_VIEW;
    }

    @PostMapping(SAVE_PASSWORD_URL)
    String savePassword(
            @SessionAttribute(Constants.ACTIVE_USER) User user,
            @Valid @ModelAttribute(Constants.PASSWORD) Password password,
            BindingResult bindingResult
    ) throws Exception {

        if(bindingResult.hasErrors()){

            return UPDATE_PASSWORD_VIEW;
        }

        user.setPassword(password.getNewPassword());
        userService.saveOrUpdate(user);

        return HOME;
    }
}
