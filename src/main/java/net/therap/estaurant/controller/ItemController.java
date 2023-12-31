package net.therap.estaurant.controller;

import net.therap.estaurant.entity.Availability;
import net.therap.estaurant.entity.Category;
import net.therap.estaurant.entity.Item;
import net.therap.estaurant.propertyEditor.CategoryEditor;
import net.therap.estaurant.propertyEditor.ItemEditor;
import net.therap.estaurant.service.CategoryService;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.OrderLineItemService;
import net.therap.estaurant.validator.ItemValidator;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.nonNull;
import static net.therap.estaurant.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 2/9/23
 */
@Controller
@RequestMapping("/admin/*")
@SessionAttributes(ITEM)
public class ItemController {

    private static final String ITEM_REDIRECT_URL = "admin/item";
    private static final String ITEM_URL = "item";
    private static final String ITEM_VIEW = "item-list";
    private static final String ITEM_FORM_URL = "item/form";
    private static final String ITEM_FORM_SAVE_URL = "item/save";
    private static final String ITEM_FORM_VIEW = "item-form";
    private static final String ITEM_ID_PARAM = "itemId";
    private static final String ITEM_DELETE_URL = "item/delete";

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryEditor categoryEditor;

    @Autowired
    private ItemEditor itemEditor;

    @Autowired
    private ItemValidator itemValidator;

    @Autowired
    private MessageSource messageSource;

    @InitBinder(ITEM)
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(Category.class, categoryEditor);
        webDataBinder.registerCustomEditor(Item.class, itemEditor);
        webDataBinder.addValidators(itemValidator);
    }

    @GetMapping(ITEM_URL)
    public String showItems(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(NAV_ITEM, ITEM);

        return ITEM_VIEW;
    }

    @GetMapping(ITEM_FORM_URL)
    public String showItemForm(@RequestParam(value = ITEM_ID_PARAM, required = false) String itemId,
                               ModelMap modelMap) {
        Item item = nonNull(itemId) ? itemService.findById(Integer.parseInt(itemId)) : new Item();

        modelMap.put(ITEM, item);
        setupReferenceDataItemForm(modelMap);

        return ITEM_FORM_VIEW;
    }

    @PostMapping(ITEM_FORM_SAVE_URL)
    public String saveOrUpdateItem(@Valid @ModelAttribute(ITEM) Item item,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) throws Exception {

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
    public String deleteItem(@RequestParam(ITEM_ID_PARAM) int itemId,
                             RedirectAttributes redirectAttributes) throws Exception {

        if (orderLineItemService.isItemInUse(itemId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            itemService.delete(itemId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + ITEM_REDIRECT_URL;
    }

    private void setupReferenceDataItemForm(ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(AVAILABILITY_LIST, Availability.values());
        modelMap.put(NAV_ITEM, ITEM);
    }
}
