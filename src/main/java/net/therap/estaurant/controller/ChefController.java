package net.therap.estaurant.controller;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.Status;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.OrderLineItemService;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.CookingTimeGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author nadimmahmud
 * @since 1/25/23
 */
@Controller
@RequestMapping("/chef/*")
@SessionAttributes(Constants.ORDER_LINE_ITEM)
public class ChefController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String REDIRECT_CHEF_NOTIFICATION_URL = "chef/notification";
    private static final String CHEF_NOTIFICATION_URL = "notification";
    private static final String CHEF_NOTIFICATION_VIEW = "chef-notification";
    private static final String CHEF_ACCEPT_FORM_URL = "notification/form";
    private static final String CHEF_ACCEPT_FORM_VIEW = "chef-order-accept-form";
    private static final String CHEF_ACCEPT_FORM_SAVE_URL = "notification/form/save";
    private static final String ORDER_LINE_ITEM_ID_PARAM = "orderLineItemId";
    private static final String MARK_PREPARED_URL = "notification/mark-prepared";

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @GetMapping({HOME_URL})
    public String chefHome(ModelMap modelMap) {
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());

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

    @GetMapping(CHEF_ACCEPT_FORM_URL)
    public String showOrderAcceptForm(@RequestParam(ORDER_LINE_ITEM_ID_PARAM) String orderLineItemId, ModelMap modelMap){
        modelMap.put(Constants.ORDER_LINE_ITEM, orderLineItemService.findById(Integer.parseInt(orderLineItemId)));
        modelMap.put(Constants.NAV_ITEM, Constants.NOTIFICATION);

        return CHEF_ACCEPT_FORM_VIEW;
    }

    @PostMapping(CHEF_ACCEPT_FORM_SAVE_URL)
    public String saveAcceptForm(
            @Validated(CookingTimeGroup.class) @ModelAttribute(Constants.ORDER_LINE_ITEM) OrderLineItem orderLineItem,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus
    ) throws Exception {

        if(bindingResult.hasErrors()){
            modelMap.put(Constants.NAV_ITEM, Constants.NOTIFICATION);

            return CHEF_ACCEPT_FORM_VIEW;
        }

        orderLineItem.setAcceptedAt(new Date());
        orderLineItem.setStatus(Status.PREPARING);
        orderLineItemService.saveOrUpdate(orderLineItem);
        sessionStatus.setComplete();

        return Constants.REDIRECT + REDIRECT_CHEF_NOTIFICATION_URL;
    }

    @PostMapping(MARK_PREPARED_URL)
    public String markOrderLineItemPrepared(@RequestParam(ORDER_LINE_ITEM_ID_PARAM) String orderLineItemId, ModelMap modelMap) throws Exception {
        OrderLineItem orderLineItem = orderLineItemService.findById(Integer.parseInt(orderLineItemId));
        orderLineItem.setStatus(Status.PREPARED);
        orderLineItemService.saveOrUpdate(orderLineItem);

        return Constants.REDIRECT + REDIRECT_CHEF_NOTIFICATION_URL;
    }
}
