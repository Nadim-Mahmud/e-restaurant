package net.therap.estaurant.controller;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.*;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.propertyEditor.StringToIntEditor;
import net.therap.estaurant.service.*;
import net.therap.estaurant.validator.OrderLineItemValidator;
import net.therap.estaurant.validator.OrderValidator;
import net.therap.estaurant.validator.QuantityGroup;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/24/23
 */

@Controller
@RequestMapping("/waiter/*")
@SessionAttributes({Constants.ORDER})
public class WaiterController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String ORDER_REDIRECT_URL = "waiter/orders";
    private static final String ORDERS_URL = "orders";
    private static final String ORDER_VIEW = "order-list";
    private static final String ORDER_FORM_URL = "new-order";
    private static final String ORDER_FORM_VIEW = "order-form";
    private static final String ORDER_FORM_SAVE_URL = "new-order/list/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String ORDER_CANCEL_URL = "order/cancel";

    private static final String NEXT_PAGE = "new-order/next-page";
    private static final String ORDER_ITEMS_URL = "new-order/items";
    private static final String ORDER_ITEMS_VIEW = "order-item-form";
    private static final String ADD_ORDER_ITEM_URL = "new-order/items/add";
    private static final String REMOVE_ORDER_ITEM_URL = "new-order/items/remove";
    private static final String REDIRECT_ORDER_ITEMS_URL = "waiter/new-order/items";
    private static final String ORDER_LINE_ITEM_ID = "orderLineItemId";

    private static final String REDIRECT_WAITER_NOTIFICATION_URL = "waiter/notification";
    private static final String WAITER_NOTIFICATION_URL = "waiter/notification";
    private static final String WAITER_NOTIFICATION_VIEW = "waiter-notification";
    private static final String WAITER_NOTIFICATION_SERVED = "notification/mark-served";


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private OrderValidator orderValidator;

    @InitBinder()
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
        webDataBinder.registerCustomEditor(Integer.class, "quantity", new StringToIntEditor());
    }

    @InitBinder(Constants.ORDER)
    public void orderBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(orderValidator);
    }

    @GetMapping(HOME_URL)
    public String adminHome(ModelMap modelMap) {
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(ORDERS_URL)
    public String showOrders(ModelMap modelMap) {
        modelMap.put(Constants.ORDER_LIST, orderService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDERS);

        return ORDER_VIEW;
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(
            @RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
            ModelMap modelMap
    ) {
        Order order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) : new Order();
        modelMap.put(Constants.ORDER, order);
        modelMap.put(Constants.RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDER_FORM);

        return ORDER_FORM_VIEW;
    }

    @PostMapping(NEXT_PAGE)
    public String orderTableValidationPage(
            @Valid @ModelAttribute(Constants.ORDER) Order order,
            BindingResult bindingResult,
            ModelMap modelMap
    ) {

        if (bindingResult.hasErrors()) {
            modelMap.put(Constants.RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
            modelMap.put(Constants.NAV_ITEM, Constants.ORDER_FORM);

            return ORDER_FORM_VIEW;
        }

        return Constants.REDIRECT + REDIRECT_ORDER_ITEMS_URL;
    }


    @GetMapping(ORDER_ITEMS_URL)
    public String showOrderItems(
            @SessionAttribute(Constants.ORDER) Order order,
            ModelMap modelMap
    ) {
        modelMap.put(Constants.ORDER_LINE_ITEM, new OrderLineItem());
        modelMap.put(Constants.ORDER_LINE_ITEM_LIST, order.getOrderLineItemList());
        modelMap.put(Constants.ITEM_LIST, itemService.findAvailable());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDER_FORM);

        return ORDER_ITEMS_VIEW;
    }

    @PostMapping(ADD_ORDER_ITEM_URL)
    public String addOrderItem(
            @SessionAttribute(Constants.ORDER) Order order,
            @Validated(QuantityGroup.class) @ModelAttribute(Constants.ORDER_LINE_ITEM) OrderLineItem orderLineItem,
            BindingResult bindingResult,
            ModelMap modelMap
    ) {
        orderLineItem.setStatus(Status.ORDERED);
        modelMap.put(Constants.ITEM_LIST, itemService.findAvailable());
        modelMap.put(Constants.ORDER_LINE_ITEM, orderLineItem);
        modelMap.put(Constants.ORDER_LINE_ITEM_LIST, order.getOrderLineItemList());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDER_FORM);

        OrderLineItemValidator.validate(order.getOrderLineItemList(), orderLineItem, bindingResult);

        if (!bindingResult.hasErrors()) {
            order.getOrderLineItemList().add(orderLineItem);
        }

        return ORDER_ITEMS_VIEW;
    }

    @PostMapping(REMOVE_ORDER_ITEM_URL)
    public String RemoveOrderItem(
            @SessionAttribute(Constants.ORDER) Order order,
            @RequestParam(ORDER_LINE_ITEM_ID) String orderLineItemId
    ) {
        order.getOrderLineItemList().remove(new OrderLineItem(Integer.parseInt(orderLineItemId)));

        return Constants.REDIRECT + REDIRECT_ORDER_ITEMS_URL;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateResOrder(
            @SessionAttribute(Constants.ORDER) Order order,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (order.getOrderLineItemList().isEmpty()) {
            redirectAttributes.addFlashAttribute(Constants.EMPTY_LIST, Constants.EMPTY_LIST);

            return Constants.REDIRECT + REDIRECT_ORDER_ITEMS_URL;
        }

        order.setStatus(Status.ORDERED);
        orderService.saveOrUpdate(order);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + ORDER_REDIRECT_URL;
    }

    @PostMapping(ORDER_CANCEL_URL)
    public String deleteResTable(
            @RequestParam(ORDER_ID_PARAM) String orderID,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        orderService.delete(Integer.parseInt(orderID));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + ORDER_REDIRECT_URL;
    }

    @GetMapping(WAITER_NOTIFICATION_URL)
    public String showWaiterNotification(ModelMap modelMap){
        modelMap.put(Constants.ORDER_LIST, orderService.getOrderListWithStatus());
        modelMap.put(Constants.NAV_ITEM, Constants.NOTIFICATION);

        return WAITER_NOTIFICATION_VIEW;
    }

    @PostMapping(WAITER_NOTIFICATION_SERVED)
    public String markServed(@RequestParam(ORDER_ID_PARAM) String orderId) throws Exception {
        Order order = orderService.findById(Integer.parseInt(orderId));
        order.setStatus(Status.SERVED);
        orderService.saveOrUpdate(order);

        return Constants.REDIRECT + REDIRECT_WAITER_NOTIFICATION_URL;
    }

}
