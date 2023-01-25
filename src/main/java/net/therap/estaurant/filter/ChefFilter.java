package net.therap.estaurant.filter;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
public class ChefFilter implements Filter {

    private static final String LOGIN = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        User login = (User) httpSession.getAttribute(Constants.ACTIVE_USER);

        //if (Objects.nonNull(login) && login.getType().equals(Type.ADMIN)) {
            request.setAttribute(Constants.ACTIVE_USER, login);
            request.setAttribute(Constants.ROLE, Constants.CHEF);
            chain.doFilter(request, response);

            //return;
        //}

        //request.getRequestDispatcher(LOGIN).forward(request, response);
    }
}
