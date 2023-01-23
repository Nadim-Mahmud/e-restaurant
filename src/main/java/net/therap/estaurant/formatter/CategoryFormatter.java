package net.therap.estaurant.formatter;

import net.therap.estaurant.entity.Category;
import net.therap.estaurant.service.CategoryService;
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
public class CategoryFormatter implements Formatter<Category> {

    @Autowired
    CategoryService categoryService;

    @Override
    public Category parse(String categoryId, Locale locale) throws ParseException {

        return categoryService.findById(Integer.parseInt(categoryId));
    }

    @Override
    public String print(Category category, Locale locale) {

        return category.getName();
    }
}
