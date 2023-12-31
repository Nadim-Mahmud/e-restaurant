package net.therap.estaurant.controller;

import net.therap.estaurant.command.Credentials;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.estaurant.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
public class AuthenticationController {

    private static final String HOME = "/";
    private static final String HOME_VIEW = "home";

    private static final String LOGIN = "login";
    private static final String LOGIN_URL = "login-page";
    private static final String LOGIN_VIEW = "login-page";
    private static final String LOGOUT_URL = "logout";

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @GetMapping(HOME)
    public String guestHome(ModelMap modelMap) {
        modelMap.put(GUEST, GUEST);
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(LOGIN_URL)
    public String showLoginPage(ModelMap modelMap) {
        modelMap.put(CREDENTIALS, new Credentials());
        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);

        return LOGIN_VIEW;
    }

    @PostMapping(LOGIN)
    public String login(@Valid @ModelAttribute(CREDENTIALS) Credentials credentials,
                        ModelMap modelMap,
                        HttpSession httpSession) throws Exception {

        if (userService.isValidCredential(credentials)) {
            httpSession.setAttribute(ACTIVE_USER, userService.findByEmail(credentials.getEmail()));

            return REDIRECT;
        }

        modelMap.put(LOGIN_PAGE, LOGIN_PAGE);
        modelMap.put(INVALID_LOGIN, INVALID_LOGIN);

        return LOGIN_VIEW;
    }

    @GetMapping(LOGOUT_URL)
    public String logOut(HttpSession httpSession, Model model) {
        httpSession.removeAttribute(ACTIVE_USER);
        httpSession.invalidate();

        if (model.containsAttribute(ACTIVE_USER)) {
            model.asMap().remove(ACTIVE_USER);
        }

        return REDIRECT;
    }
}
