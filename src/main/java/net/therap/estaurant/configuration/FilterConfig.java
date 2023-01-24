package net.therap.estaurant.configuration;

import net.therap.estaurant.filter.AdminFilter;
import net.therap.estaurant.filter.WaiterFilter;
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
    public FilterRegistrationBean<AdminFilter> adminLoginFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<WaiterFilter> waiterLoginFilter() {
        FilterRegistrationBean<WaiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WaiterFilter());
        registrationBean.addUrlPatterns("/waiter/*");

        return registrationBean;
    }
}
