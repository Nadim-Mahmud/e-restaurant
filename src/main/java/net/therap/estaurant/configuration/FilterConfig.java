package net.therap.estaurant.configuration;

import net.therap.estaurant.filter.AdminFIlter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AdminFIlter> adminLoginFilter() {
        FilterRegistrationBean<AdminFIlter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFIlter());
        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;
    }
}
