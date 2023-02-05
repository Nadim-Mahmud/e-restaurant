package net.therap.estaurant.controller;

import net.therap.estaurant.entity.*;
import net.therap.estaurant.propertyEditor.CategoryEditor;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.service.*;
import net.therap.estaurant.validator.EmailValidator;
import net.therap.estaurant.validator.ItemValidator;
import net.therap.estaurant.validator.RestaurantTableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static net.therap.estaurant.constant.Constants.*;


/**
 * @author nadimmahmud
 * @since 1/18/23
 */
@Controller
@RequestMapping("/admin/*")
@SessionAttributes({CATEGORY, ITEM, CHEF, WAITER, RESTAURANT_TABLE})
public class AdminController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";

    private static final String CATEGORY_REDIRECT_URL = "admin/category";
    private static final String CATEGORY_URL = "category";
    private static final String CATEGORY_VIEW = "category-list";
    private static final String CATEGORY_FORM_URL = "category/form";
    private static final String CATEGORY_FORM_SAVE_URL = "category/save";
    private static final String CATEGORY_FORM_VIEW = "category-form";
    private static final String CATEGORY_ID_PARAM = "categoryId";
    private static final String CATEGORY_DELETE_URL = "category/delete";

    private static final String ITEM_REDIRECT_URL = "admin/item";
    private static final String ITEM_URL = "item";
    private static final String ITEM_VIEW = "item-list";
    private static final String ITEM_FORM_URL = "item/form";
    private static final String ITEM_FORM_SAVE_URL = "item/save";
    private static final String ITEM_FORM_VIEW = "item-form";
    private static final String ITEM_ID_PARAM = "itemId";
    private static final String ITEM_DELETE_URL = "item/delete";

    private static final String CHEF_REDIRECT_URL = "admin/chef";
    private static final String CHEF_URL = "chef";
    private static final String CHEF_VIEW = "chef-list";
    private static final String CHEF_FORM_URL = "chef/form";
    private static final String CHEF_FORM_SAVE_URL = "chef/save";
    private static final String CHEF_FORM_VIEW = "chef-form";
    private static final String CHEF_ID_PARAM = "chefId";
    private static final String CHEF_DELETE_URL = "chef/delete";

    private static final String WAITER_REDIRECT_URL = "admin/waiter";
    private static final String WAITER_URL = "waiter";
    private static final String WAITER_VIEW = "waiter-list";
    private static final String WAITER_FORM_URL = "waiter/form";
    private static final String WAITER_FORM_SAVE_URL = "waiter/save";
    private static final String WAITER_FORM_VIEW = "waiter-form";
    private static final String WAITER_ID_PARAM = "waiterId";
    private static final String WAITER_DELETE_URL = "waiter/delete";

    private static final String RES_TABLE_REDIRECT_URL = "admin/res-table";
    private static final String RES_TABLE_URL = "res-table";
    private static final String RES_TABLE_VIEW = "res-table-list";
    private static final String RES_TABLE_FORM_URL = "res-table/form";
    private static final String RES_TABLE_FORM_SAVE_URL = "res-table/save";
    private static final String RES_TABLE_FORM_VIEW = "res-table-form";
    private static final String RES_TABLE_ID_PARAM = "resTableId";
    private static final String RES_TABLE_DELETE_URL = "res-table/delete";

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantTableService restaurantTableService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private CategoryEditor categoryEditor;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private ItemValidator itemValidator;

    @Autowired
    private RestaurantTableValidator restaurantTableValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Category.class, categoryEditor);
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
    }

    @InitBinder({WAITER, CHEF})
    public void userBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(emailValidator);
    }

    @InitBinder(RESTAURANT_TABLE)
    public void tableBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(restaurantTableValidator);
    }

    @InitBinder(ITEM)
    public void itemBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(itemValidator);
    }

    @GetMapping(HOME_URL)
    public String adminHome(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(CATEGORY_URL)
    public String showCategory(ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(NAV_ITEM, CATEGORY);

        return CATEGORY_VIEW;
    }

    @GetMapping(CATEGORY_FORM_URL)
    public String showCategoryForm(@RequestParam(value = CATEGORY_ID_PARAM, required = false) String categoryId, ModelMap modelMap) throws Exception {
        Category category = Objects.nonNull(categoryId) ? categoryService.findById(Integer.parseInt(categoryId)) : new Category();

        modelMap.put(CATEGORY, category);
        modelMap.put(NAV_ITEM, CATEGORY);

        return CATEGORY_FORM_VIEW;
    }

    @PostMapping(CATEGORY_FORM_SAVE_URL)
    public String saveOrUpdateCategory(
            @Valid @ModelAttribute(CATEGORY) Category category,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, CATEGORY);

            return CATEGORY_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (category.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        categoryService.saveOrUpdate(category);
        sessionStatus.setComplete();

        return REDIRECT + CATEGORY_REDIRECT_URL;
    }

    @PostMapping(CATEGORY_DELETE_URL)
    public String deleteCourse(
            @RequestParam(CATEGORY_ID_PARAM) int categoryId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        categoryService.delete(categoryId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + CATEGORY_REDIRECT_URL;
    }

    @GetMapping(ITEM_URL)
    public String showItems(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(NAV_ITEM, ITEM);

        return ITEM_VIEW;
    }

    @GetMapping(ITEM_FORM_URL)
    public String showItemForm(@RequestParam(value = ITEM_ID_PARAM, required = false) String itemId, ModelMap modelMap) {
        Item item = Objects.nonNull(itemId) ? itemService.findById(Integer.parseInt(itemId)) : new Item();

        modelMap.put(ITEM, item);
        setupReferenceDataItemForm(modelMap);

        return ITEM_FORM_VIEW;
    }

    @PostMapping(ITEM_FORM_SAVE_URL)
    public String saveOrUpdateItem(
            @Valid @ModelAttribute(ITEM) Item item,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataItemForm(modelMap);

            return ITEM_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (item.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        itemService.saveOrUpdate(item);
        sessionStatus.setComplete();

        return REDIRECT + ITEM_REDIRECT_URL;
    }

    @PostMapping(ITEM_DELETE_URL)
    public String deleteItem(
            @RequestParam(ITEM_ID_PARAM) int itemId,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (orderLineItemService.isItemOnProcess(itemId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            itemService.delete(itemId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + ITEM_REDIRECT_URL;
    }

    @GetMapping(CHEF_URL)
    public String showChef(ModelMap modelMap) {
        modelMap.put(CHEF_LIST, userService.findChef());
        modelMap.put(NAV_ITEM, CHEF);

        return CHEF_VIEW;
    }

    @GetMapping(CHEF_FORM_URL)
    public String showChefForm(@RequestParam(value = CHEF_ID_PARAM, required = false) String chefId, ModelMap modelMap) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User chef = Objects.nonNull(chefId) ? userService.findById(Integer.parseInt(chefId)) : new User();
        modelMap.put(CHEF, chef);
        setupReferenceDataChefForm(modelMap, chef);

        return CHEF_FORM_VIEW;
    }

    @PostMapping(CHEF_FORM_SAVE_URL)
    public String saveOrUpdateChef(
            @Valid @ModelAttribute(CHEF) User user,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataChefForm(modelMap, user);

            return CHEF_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        user.setType(UserType.CHEF);
        userService.saveOrUpdate(user);
        sessionStatus.setComplete();

        return REDIRECT + CHEF_REDIRECT_URL;
    }

    @PostMapping(CHEF_DELETE_URL)
    public String deleteChef(
            @RequestParam(CHEF_ID_PARAM) int chefId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        userService.delete(chefId);
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + CHEF_REDIRECT_URL;
    }

    @GetMapping(WAITER_URL)
    public String showWaiter(ModelMap modelMap) {
        modelMap.put(WAITER_LIST, userService.findWaiter());
        modelMap.put(NAV_ITEM, WAITER);

        return WAITER_VIEW;
    }

    @GetMapping(WAITER_FORM_URL)
    public String showWaiterForm(@RequestParam(value = WAITER_ID_PARAM, required = false) String waiterId, ModelMap modelMap) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User waiter = Objects.nonNull(waiterId) ? userService.findById(Integer.parseInt(waiterId)) : new User();
        modelMap.put(WAITER, waiter);
        setupReferenceDataWaiterForm(modelMap, waiter);

        return WAITER_FORM_VIEW;
    }

    @PostMapping(WAITER_FORM_SAVE_URL)
    public String saveOrUpdateWaiter(
            @Valid @ModelAttribute(WAITER) User user,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataWaiterForm(modelMap, user);

            return WAITER_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (user.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        user.setType(UserType.WAITER);
        userService.saveOrUpdate(user);
        sessionStatus.setComplete();

        return REDIRECT + WAITER_REDIRECT_URL;
    }

    @PostMapping(WAITER_DELETE_URL)
    public String deleteWaiter(
            @RequestParam(WAITER_ID_PARAM) String waiterId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        userService.delete(Integer.parseInt(waiterId));
        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));

        return REDIRECT + WAITER_REDIRECT_URL;
    }

    @GetMapping(RES_TABLE_URL)
    public String showRestaurantTable(ModelMap modelMap) {
        modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

        return RES_TABLE_VIEW;
    }

    @GetMapping(RES_TABLE_FORM_URL)
    public String showTableForm(@RequestParam(value = RES_TABLE_ID_PARAM, required = false) String resTableId, ModelMap modelMap) {
        RestaurantTable resTable = Objects.nonNull(resTableId) ? restaurantTableService.findById(Integer.parseInt(resTableId)) : new RestaurantTable();

        modelMap.put(RESTAURANT_TABLE, resTable);
        modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

        return RES_TABLE_FORM_VIEW;
    }

    @PostMapping(RES_TABLE_FORM_SAVE_URL)
    public String saveOrUpdateResTable(
            @Valid @ModelAttribute(RESTAURANT_TABLE) RestaurantTable restaurantTable,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(NAV_ITEM, RESTAURANT_TABLE);

            return RES_TABLE_FORM_VIEW;
        }

        redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage(
                (restaurantTable.getId() == 0) ? "success.add" : "success.update", null, Locale.getDefault()));
        restaurantTableService.saveOrUpdate(restaurantTable);
        sessionStatus.setComplete();

        return REDIRECT + RES_TABLE_REDIRECT_URL;
    }

    @PostMapping(RES_TABLE_DELETE_URL)
    public String deleteResTable(
            @RequestParam(RES_TABLE_ID_PARAM) String resTableId,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (orderService.isTableInUse(Integer.parseInt(resTableId))) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            restaurantTableService.delete(Integer.parseInt(resTableId));
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + RES_TABLE_REDIRECT_URL;
    }

    private void setupReferenceDataItemForm(ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(AVAILABILITY_LIST, Availability.values());
        modelMap.put(NAV_ITEM, ITEM);
    }

    private void setupReferenceDataChefForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(ITEM_OPTION_LIST, itemService.findAll());
        modelMap.put(NAV_ITEM, CHEF);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }

    private void setupReferenceDataWaiterForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(NAV_ITEM, WAITER);

        if (!user.isNew()) {
            modelMap.put(UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }
}
