package net.therap.estaurant.controller;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.OrderLineItemService;
import net.therap.estaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@RequestMapping("/chef/*")
public class ChefController {

    private static final String HOME_URL_EMPTY = "";
    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String CHEF_NOTIFICATION_URL = "notification";
    private static final String CHEF_NOTIFICATION_VIEW = "chef-notification";

    @Autowired
    private UserService userService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @GetMapping({HOME_URL, HOME_URL_EMPTY})
    public String chefHome(HttpSession httpSession) {
        httpSession.setAttribute(Constants.ACTIVE_USER, userService.findById(32));

        return HOME_VIEW;
    }

    @GetMapping(CHEF_NOTIFICATION_URL)
    public String showChefNotification(
            @SessionAttribute(Constants.ACTIVE_USER) User user,
            ModelMap modelMap
    ){
        modelMap.put(Constants.ORDER_LINE_ITEM_LIST, orderLineItemService.getOrderedNotificationByUserId(user.getId()));
        modelMap.put(Constants.NAV_ITEM, Constants.NOTIFICATION);

        return CHEF_NOTIFICATION_VIEW;
    }

}
