package net.therap.springForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Controller
public class BasicController {

    @GetMapping("/")
    public String showHome(){
        return "index";
    }
}
