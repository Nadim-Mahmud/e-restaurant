package net.therap.estaurant.configuration;

import net.therap.estaurant.formatter.CategoryFormatter;
import net.therap.estaurant.formatter.ItemFormatter;
import net.therap.estaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author nadimmahmud
 * @since 1/22/23
 */
@Configuration
public class SpringWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private CategoryFormatter categoryFormatter;

    @Autowired
    private ItemFormatter itemFormatter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(categoryFormatter);
        registry.addFormatter(itemFormatter);
    }
}
