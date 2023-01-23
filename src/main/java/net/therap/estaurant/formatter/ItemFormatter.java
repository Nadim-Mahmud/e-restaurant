package net.therap.estaurant.formatter;

import net.therap.estaurant.entity.Category;
import net.therap.estaurant.entity.Item;
import net.therap.estaurant.service.CategoryService;
import net.therap.estaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author nadimmahmud
 * @since 1/22/23
 */
@Component
public class ItemFormatter implements Formatter<Item> {

    @Autowired
    ItemService itemService;

    @Override
    public Item parse(String itemId, Locale locale) throws ParseException {

        return itemService.findById(Integer.parseInt(itemId));
    }

    @Override
    public String print(Item item, Locale locale) {

        return item.getName();
    }
}
