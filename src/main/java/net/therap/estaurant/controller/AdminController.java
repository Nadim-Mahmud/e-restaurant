package net.therap.estaurant.controller;

import net.therap.estaurant.propertyEditor.CategoryEditor;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.propertyEditor.RestaurantTableEditor;
import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.*;
import net.therap.estaurant.service.CategoryService;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.RestaurantTableService;
import net.therap.estaurant.service.UserService;
import net.therap.estaurant.validator.EmailValidator;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/18/23
 */
@Controller
@RequestMapping("/admin/*")
@SessionAttributes({Constants.CATEGORY, Constants.ITEM, Constants.CHEF, Constants.WAITER, Constants.RESTAURANT_TABLE})
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
    private RestaurantTableEditor restaurantTableEditor;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private CategoryEditor categoryEditor;

    @Autowired
    private EmailValidator emailValidator;

    private void setupReferenceDataItemForm(ModelMap modelMap) {
        modelMap.put(Constants.CATEGORY_LIST, categoryService.findAll());
        modelMap.put(Constants.AVAILABILITY_LIST, Availability.values());
        modelMap.put(Constants.NAV_ITEM, Constants.ITEM);
    }

    private void setupReferenceDataChefForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(Constants.ITEM_OPTION_LIST, itemService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.CHEF);

        if (!user.isNew()) {
            modelMap.put(Constants.UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }

    private void setupReferenceDataWaiterForm(ModelMap modelMap, User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        modelMap.put(Constants.RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.WAITER);

        if (!user.isNew()) {
            modelMap.put(Constants.UPDATE_PAGE, true);
        }

        if (user.isNew()) {
            user.setPassword("");
        }
    }

    @InitBinder()
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Category.class, categoryEditor);
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.registerCustomEditor(RestaurantTable.class, restaurantTableEditor);
    }

    @InitBinder({Constants.WAITER, Constants.CHEF})
    public void userBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(emailValidator);
    }

    @GetMapping(HOME_URL)
    public String adminHome() {
        return HOME_VIEW;
    }

    @GetMapping(CATEGORY_URL)
    public String showCategory(ModelMap modelMap) {
        modelMap.put(Constants.CATEGORY_LIST, categoryService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.CATEGORY);

        return CATEGORY_VIEW;
    }

    @GetMapping(CATEGORY_FORM_URL)
    public String showCategoryForm(@RequestParam(value = CATEGORY_ID_PARAM, required = false) String categoryId, ModelMap modelMap) {
        Category category = Objects.nonNull(categoryId) ? categoryService.findById(Integer.parseInt(categoryId)) : new Category();
        modelMap.put(Constants.CATEGORY, category);
        modelMap.put(Constants.NAV_ITEM, Constants.CATEGORY);

        return CATEGORY_FORM_VIEW;
    }

    @PostMapping(CATEGORY_FORM_SAVE_URL)
    public String saveOrUpdateCategory(
            @Valid @ModelAttribute(Constants.CATEGORY) Category category,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(Constants.NAV_ITEM, Constants.CATEGORY);

            return CATEGORY_FORM_VIEW;
        }

        categoryService.saveOrUpdate(category);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + CATEGORY_REDIRECT_URL;
    }

    @PostMapping(CATEGORY_DELETE_URL)
    public String deleteCourse(
            @RequestParam(CATEGORY_ID_PARAM) String categoryId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        categoryService.delete(Integer.parseInt(categoryId));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + CATEGORY_REDIRECT_URL;
    }

    @GetMapping(ITEM_URL)
    public String showItems(ModelMap modelMap) {
        modelMap.put(Constants.ITEM_LIST, itemService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.ITEM);

        return ITEM_VIEW;
    }

    @GetMapping(ITEM_FORM_URL)
    public String showItemForm(@RequestParam(value = ITEM_ID_PARAM, required = false) String itemId, ModelMap modelMap) {
        Item item = Objects.nonNull(itemId) ? itemService.findById(Integer.parseInt(itemId)) : new Item();

        modelMap.put(Constants.ITEM, item);
        setupReferenceDataItemForm(modelMap);

        return ITEM_FORM_VIEW;
    }

    @PostMapping(ITEM_FORM_SAVE_URL)
    public String saveOrUpdateItem(
            @Valid @ModelAttribute(Constants.ITEM) Item item,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataItemForm(modelMap);

            return ITEM_FORM_VIEW;
        }

        itemService.saveOrUpdate(item);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + ITEM_REDIRECT_URL;
    }

    @PostMapping(ITEM_DELETE_URL)
    public String deleteItem(
            @RequestParam(ITEM_ID_PARAM) String itemId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        itemService.delete(Integer.parseInt(itemId));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + ITEM_REDIRECT_URL;
    }

    @GetMapping(CHEF_URL)
    public String showChef(ModelMap modelMap) {
        modelMap.put(Constants.CHEF_LIST, userService.findChef());
        modelMap.put(Constants.NAV_ITEM, Constants.CHEF);

        return CHEF_VIEW;
    }

    @GetMapping(CHEF_FORM_URL)
    public String showChefForm(@RequestParam(value = CHEF_ID_PARAM, required = false) String chefId, ModelMap modelMap) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User chef = Objects.nonNull(chefId) ? userService.findById(Integer.parseInt(chefId)) : new User();
        modelMap.put(Constants.CHEF, chef);
        setupReferenceDataChefForm(modelMap, chef);

        return CHEF_FORM_VIEW;
    }

    @PostMapping(CHEF_FORM_SAVE_URL)
    public String saveOrUpdateChef(
            @Valid @ModelAttribute(Constants.CHEF) User user,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataChefForm(modelMap, user);

            return CHEF_FORM_VIEW;
        }

        user.setType(Type.CHEF);
        userService.saveOrUpdate(user);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + CHEF_REDIRECT_URL;
    }

    @PostMapping(CHEF_DELETE_URL)
    public String deleteChef(
            @RequestParam(CHEF_ID_PARAM) String chefId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        userService.delete(Integer.parseInt(chefId));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + CHEF_REDIRECT_URL;
    }

    @GetMapping(WAITER_URL)
    public String showWaiter(ModelMap modelMap) {
        modelMap.put(Constants.WAITER_LIST, userService.findWaiter());
        modelMap.put(Constants.NAV_ITEM, Constants.WAITER);

        return WAITER_VIEW;
    }

    @GetMapping(WAITER_FORM_URL)
    public String showWaiterForm(@RequestParam(value = WAITER_ID_PARAM, required = false) String waiterId, ModelMap modelMap) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User waiter = Objects.nonNull(waiterId) ? userService.findById(Integer.parseInt(waiterId)) : new User();
        modelMap.put(Constants.WAITER, waiter);
        setupReferenceDataWaiterForm(modelMap, waiter);

        return WAITER_FORM_VIEW;
    }

    @PostMapping(WAITER_FORM_SAVE_URL)
    public String saveOrUpdateWaiter(
            @Valid @ModelAttribute(Constants.WAITER) User user,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            setupReferenceDataWaiterForm(modelMap, user);

            return WAITER_FORM_VIEW;
        }

        user.setType(Type.WAITER);
        userService.saveOrUpdate(user);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + WAITER_REDIRECT_URL;
    }

    @PostMapping(WAITER_DELETE_URL)
    public String deleteWaiter(
            @RequestParam(WAITER_ID_PARAM) String waiterId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        userService.delete(Integer.parseInt(waiterId));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + WAITER_REDIRECT_URL;
    }

    @GetMapping(RES_TABLE_URL)
    public String showRestaurantTable(ModelMap modelMap) {
        modelMap.put(Constants.RESTAURANT_TABLE_LIST, restaurantTableService.findAll());
        modelMap.put(Constants.NAV_ITEM, Constants.RESTAURANT_TABLE);

        return RES_TABLE_VIEW;
    }

    @GetMapping(RES_TABLE_FORM_URL)
    public String showTableForm(@RequestParam(value = RES_TABLE_ID_PARAM, required = false) String resTableId, ModelMap modelMap) {
        RestaurantTable resTable = Objects.nonNull(resTableId) ? restaurantTableService.findById(Integer.parseInt(resTableId)) : new RestaurantTable();
        modelMap.put(Constants.RESTAURANT_TABLE, resTable);
        modelMap.put(Constants.NAV_ITEM, Constants.RESTAURANT_TABLE);

        return RES_TABLE_FORM_VIEW;
    }

    @PostMapping(RES_TABLE_FORM_SAVE_URL)
    public String saveOrUpdateResTable(
            @Valid @ModelAttribute(Constants.RESTAURANT_TABLE) RestaurantTable restaurantTable,
            BindingResult bindingResult,
            ModelMap modelMap,
            SessionStatus sessionStatus,
            RedirectAttributes redirectAttributes
    ) throws Exception {

        if (bindingResult.hasErrors()) {
            modelMap.put(Constants.NAV_ITEM, Constants.RESTAURANT_TABLE);

            return RES_TABLE_FORM_VIEW;
        }

        restaurantTableService.saveOrUpdate(restaurantTable);
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);
        sessionStatus.setComplete();

        return Constants.REDIRECT + RES_TABLE_REDIRECT_URL;
    }

    @PostMapping(RES_TABLE_DELETE_URL)
    public String deleteResTable(
            @RequestParam(RES_TABLE_ID_PARAM) String resTableId,
            RedirectAttributes redirectAttributes
    ) throws Exception {
        restaurantTableService.delete(Integer.parseInt(resTableId));
        redirectAttributes.addFlashAttribute(Constants.SUCCESS, Constants.SUCCESS);

        return Constants.REDIRECT + RES_TABLE_REDIRECT_URL;
    }
}
