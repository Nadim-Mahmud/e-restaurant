package net.therap.estaurant.controller;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.*;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.propertyEditor.StringToIntEditor;
import net.therap.estaurant.service.*;
import net.therap.estaurant.validator.OrderLineItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/24/23
 */

@Controller
@RequestMapping("/waiter/*")
@SessionAttributes({Constants.ORDER, Constants.ORDER_LINE_ITEM_LIST})
public class WaiterController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String ORDER_REDIRECT_URL = "waiter/orders";
    private static final String ORDERS_URL = "orders";
    private static final String ORDER_VIEW = "order-list";
    private static final String ORDER_FORM_URL = "new-order/add/order-form";
    private static final String ORDER_FORM_VIEW = "order-form";
    private static final String ORDER_FORM_SAVE_URL = "new-order/add/order-form/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String ORDER_DELETE_URL = "order/delete";

    private static final String ADD_ORDER_ITEM = "new-order/add";
    private static final String ORDER_ITEMS_URL = "new-order";
    private static final String ORDER_ITEMS_VIEW = "order-item-list";
    private static final String ORDER_LINE_ITEM_ID = "orderLineItemId";


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @InitBinder()
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
        webDataBinder.registerCustomEditor(Integer.class, "quantity", new StringToIntEditor());
    }

    @GetMapping(HOME_URL)
    public String adminHome(HttpSession httpSession, ModelMap modelMap) {
        httpSession.setAttribute(Constants.ACTIVE_USER, userService.findById(29));
        modelMap.put("role", "Waiter");

        return HOME_VIEW;
    }

    @GetMapping(ORDERS_URL)
    public String showOrders(ModelMap modelMap) {
        modelMap.put(Constants.ORDER_LIST, orderService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDER_LIST);

        return ORDER_VIEW;
    }

    @GetMapping(ORDER_ITEMS_URL)
    public String showOrderItems(
            @RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
            @RequestParam(value = ORDER_LINE_ITEM_ID, required = false) String orderLineItemId,
            ModelMap modelMap
    ) {
        Order order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) : new Order();
        OrderLineItem orderLineItem = Objects.nonNull(orderLineItemId) ? orderLineItemService.findById(Integer.parseInt(orderLineItemId)) : new OrderLineItem();
        List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();

        modelMap.put(Constants.ORDER, order);
        modelMap.put(Constants.ORDER_LINE_ITEM, orderLineItem);
        modelMap.put(Constants.ORDER_LINE_ITEM_LIST, orderLineItemList);
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.ORDER_FORM);

        return ORDER_ITEMS_VIEW;
    }

    @PostMapping(ADD_ORDER_ITEM)
    public String addOrderItem(
            @SessionAttribute(Constants.ORDER_LINE_ITEM_LIST) List<OrderLineItem> orderLineItemList,
            @Valid @ModelAttribute(Constants.ORDER_LINE_ITEM) OrderLineItem orderLineItem,
            BindingResult bindingResult,
            ModelMap modelMap
    ) {
        orderLineItem.setStatus(Status.ORDERED);
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());
        modelMap.put(Constants.ORDER_LINE_ITEM, orderLineItem);
        modelMap.put(Constants.ORDER_LINE_ITEM_LIST, orderLineItemList);

        OrderLineItemValidator.validate(orderLineItemList, orderLineItem, bindingResult);

        if(! bindingResult.hasErrors()){
            orderLineItemList.add(orderLineItem);
        }

        return ORDER_ITEMS_VIEW;
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(
            @ModelAttribute(Constants.ORDER) Order order,
            ModelMap modelMap
    ) {
        modelMap.put(Constants.RESTAURANT_TABLE_LIST, restaurantTableService.findAll());

        return ORDER_FORM_VIEW;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateResOrder(
            @ModelAttribute(Constants.ORDER) Order order,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

//        if (bindingResult.hasErrors()) {
//            modelMap.put(Constants.NAV_ITEM, Constants.RESTAURANT_TABLE);
//
//            return ORDER_FORM_VIEW;
//        }
        order.setStatus(Status.ORDERED);
        //order.getOrderLineItemList().get(0).setOrder(order);
        System.out.println(order);
        orderService.saveOrUpdate(order);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + ORDER_REDIRECT_URL;
    }

}
