package net.therap.estaurant.validator;

import net.therap.estaurant.entity.Item;
import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.service.ItemService;
import net.therap.estaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
@Component
public class ItemValidator implements Validator {

    @Autowired
    private ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (itemService.isDuplicateName((Item) target)) {
            errors.rejectValue("name", "input.itemName.duplicate");
        }
    }
}