package ru.proj.authorization.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.proj.authorization.model.User;
import ru.proj.authorization.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * Index Controller
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Controller
@AllArgsConstructor
@ThreadSafe
public class IndexController {

    private final UserService userService;

    /**
     * Index page
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return index.html - start page
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession httpSession) {
        User user = getUser(httpSession);
        boolean isAdmin = userService.hasAdminRights(user);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", isAdmin);
        return "index";
    }

    /**
     * Create a user with name "Guest" if user is missing
     *
     * @param httpSession HttpSession
     * @return new User with "Guest" name or current User
     */
    private User getUser(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        return user;
    }

}
