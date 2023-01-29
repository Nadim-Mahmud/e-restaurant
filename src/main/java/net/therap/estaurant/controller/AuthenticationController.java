package net.therap.estaurant.controller;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import net.therap.estaurant.command.Credentials;
import net.therap.estaurant.command.Password;
import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.Item;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@SessionAttributes(Constants.PASSWORD)
public class AuthenticationController {

    private static final String HOME = "/";
    private static final String HOME_VIEW = "home";

    private static final String UPDATE_PASSWORD_URL = "update-password";
    private static final String UPDATE_PASSWORD_VIEW = "password-form";
    private static final String SAVE_PASSWORD_URL = "/update-password/update";

    private static final String LOGIN = "login";
    private static final String LOGIN_URL = "login-page";
    private static final String LOGIN_VIEW = "login-page";

    private static final String LOGOUT_URL = "logout";

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @InitBinder("password")
    public void passwordBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordValidator());
    }

    @GetMapping(HOME)
    public String guestHome(ModelMap modelMap) {
        modelMap.put(Constants.GUEST, Constants.GUEST);
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
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
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {

            return UPDATE_PASSWORD_VIEW;
        }

        user.setPassword(password.getNewPassword());
        userService.saveOrUpdate(user);

        return Constants.REDIRECT;
    }

    @GetMapping(LOGIN_URL)
    public String showLoginPage(ModelMap modelMap) {
        modelMap.put(Constants.CREDENTIALS, new Credentials());
        modelMap.put(Constants.LOGIN_PAGE, Constants.LOGIN_PAGE);

        return LOGIN_VIEW;
    }

    @PostMapping(LOGIN)
    public String login(
            @Valid @ModelAttribute(Constants.CREDENTIALS) Credentials credentials,
            ModelMap modelMap,
            HttpSession httpSession
    ) throws Exception {

        if (userService.isValidCredential(credentials)) {
            httpSession.setAttribute(Constants.ACTIVE_USER, userService.findByEmail(credentials.getEmail()));

            return Constants.REDIRECT;
        }

        modelMap.put(Constants.LOGIN_PAGE, Constants.LOGIN_PAGE);
        modelMap.put(Constants.INVALID_LOGIN, Constants.INVALID_LOGIN);

        return LOGIN_VIEW;
    }

    @GetMapping(LOGOUT_URL)
    public String logOut(HttpSession httpSession, Model model) {
        httpSession.removeAttribute(Constants.ACTIVE_USER);
        httpSession.invalidate();

        if (model.containsAttribute(Constants.ACTIVE_USER)) {
            model.asMap().remove(Constants.ACTIVE_USER);
        }

        return Constants.REDIRECT;
    }
}
