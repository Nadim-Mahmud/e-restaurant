package net.therap.estaurant.controller;

import com.sun.jdi.VMOutOfMemoryException;
import net.therap.estaurant.constant.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@Controller
public class SiteErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap modelMap) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            modelMap.put(Constants.ERROR_CODE, Integer.valueOf(status.toString()));
        }

        return "error";
    }
}
