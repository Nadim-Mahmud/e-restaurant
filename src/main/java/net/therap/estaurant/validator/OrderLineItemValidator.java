package net.therap.estaurant.validator;

import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/23/23
 */
public class OrderLineItemValidator {

    public static void validate(List<OrderLineItem> orderLineItemList, OrderLineItem orderLineItem, Errors errors) {

        if(orderLineItemList.contains(orderLineItem)) {
            errors.rejectValue("item", "select.duplicate");
        }
    }
}
