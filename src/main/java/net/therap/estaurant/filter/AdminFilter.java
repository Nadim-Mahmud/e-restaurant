package net.therap.estaurant.filter;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.Type;
import net.therap.estaurant.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
public class AdminFilter implements Filter {

    private static final String HOME = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        User user = (User) httpSession.getAttribute(Constants.ACTIVE_USER);

        if (Objects.nonNull(user) && user.getType().equals(Type.ADMIN)) {
            request.setAttribute(Constants.USER, user);
            chain.doFilter(request, response);

            return;
        }

        ((HttpServletResponse) response).sendRedirect(HOME);
    }
}
