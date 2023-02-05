package ru.proj.authorization.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.proj.authorization.model.Department;
import ru.proj.authorization.model.Role;
import ru.proj.authorization.model.User;
import ru.proj.authorization.service.DepartmentService;
import ru.proj.authorization.service.RoleService;
import ru.proj.authorization.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * User Controller
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Controller
@AllArgsConstructor
@ThreadSafe
public class UserController {

    private final UserService userService;
    private final DepartmentService departmentService;
    private final RoleService roleService;

    /**
     * Add User in Model by "user" key in all Model in this controller
     *
     * @param httpSession HTTPSession
     * @return User
     */
    @ModelAttribute("user")
    public User addUserToModel(HttpSession httpSession) {
        return getUser(httpSession);
    }

    /**
     * Add User by "isAdmin" key in all Model in this controller
     *
     * @param httpSession HTTPSession
     * @return User
     */
    @ModelAttribute("isAdmin")
    public boolean addAdminCheckToModel(HttpSession httpSession) {
        return userService.hasAdminRights(getUser(httpSession));
    }

    /**
     * Registration/new User creation page
     *
     * @param model        Model
     * @param newUser      Model attribute
     * @param departmentId Department id
     * @param rolesIds     Role id/ids
     * @return fail or success registration page
     */
    @PostMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("newUser") User newUser,
                               @RequestParam("department.id") int departmentId,
                               @RequestParam List<Integer> rolesIds) {
        Department department = departmentService.findDepartmentById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department with id " + departmentId + " is missing."));
        newUser.setDepartment(department);
        List<Role> roles = roleService.findRolesByIds(rolesIds);
        newUser.setRoles(roles);
        newUser.setEmail("Empty");
        newUser.setPhone("Empty");
        newUser.setTimezone("UTC");
        model.addAttribute("newUser", newUser);
        Optional<User> regUser = userService.addUser(newUser);
        if (regUser.isEmpty() || regUser.get().getId() == 0) {
            model.addAttribute("message", "A user with this login is already exists");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    /**
     * Registration/new User creation form
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return addUser.html - new user creating form
     */
    @GetMapping("/formAddUser")
    public String addPost(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "admin/addUser";
    }

    /**
     * Affirmed registration page
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return Affirmed registration page
     */
    @GetMapping("/success")
    public String success(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        return "success";
    }

    /**
     * Declined registration page
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return Declined registration page
     */
    @GetMapping("/fail")
    public String fail(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        return "error/fail";
    }

    /**
     * Start registration form
     *
     * @param model Model
     * @param fail  Fail condition
     * @return login.html - log in form
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Log in post page
     *
     * @param user Current user model
     * @param req  Request from DB on user presence
     * @return Data duplication fail page or adminPanel/index page
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userDb.get());
        User aimUser = getUser(httpSession);
        return userService.hasAdminRights(aimUser) ? "redirect:/adminPanel" : "redirect:/index";
    }

    /**
     * Log out page
     *
     * @param httpSession HttpSession
     * @return log in page
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/loginPage";
    }

    /**
     * User profile page
     *
     * @return profile.html - current user profile page
     */
    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    /**
     * Updating user profile
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return updateProfile.html - user updating form
     */
    @GetMapping("/formUpdateProfile")
    public String updateProfile(Model model,
                                HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        model.addAttribute("timezones", getTimezones());
        return "user/updateProfile";
    }

    /**
     * User update page
     *
     * @param user        current User
     * @param httpSession HttpSession
     * @return profile.html - current user profile page
     */
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User user, HttpSession httpSession) {
        User u = (User) httpSession.getAttribute("user");
        user.setId(u.getId());
        user.setDepartment(u.getDepartment());
        user.setRoles(u.getRoles());
        userService.updateUser(user);
        httpSession.setAttribute("user", user);
        return "redirect:/profile";
    }

    /**
     * Update user profile form
     *
     * @param model Model
     * @return updateProfileAdmin.html - user updating form
     */
    @GetMapping("/formUpdateProfileAdmin")
    public String updateProfileAdmin(Model model) {
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("departments", departmentService.findAllDepartments());
        model.addAttribute("timezones", getTimezones());
        return "admin/updateProfileAdmin";
    }

    /**
     * User update by admin page
     *
     * @param aimUser      Model attribute
     * @param departmentId Department id
     * @param rolesIds     Role id/ids
     * @param httpSession  HttpSession
     * @return adminPanel.html - admin tools page if user is admin, otherwise profile.html - current user profile page
     */
    @PostMapping("/updateProfileAdmin")
    public String updateProfileAdmin(@ModelAttribute("aimUser") User aimUser,
                                     @RequestParam("department.id") int departmentId,
                                     @RequestParam List<Integer> rolesIds,
                                     HttpSession httpSession) {
        Department department = departmentService.findDepartmentById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department with id " + departmentId + " is missing."));
        aimUser.setDepartment(department);
        List<Role> roles = roleService.findRolesByIds(rolesIds);
        aimUser.setRoles(roles);
        User sessionUser = (User) httpSession.getAttribute("user");
        userService.updateUser(aimUser);
        if (aimUser.getId() != sessionUser.getId()) {
            return "redirect:/adminPanel";
        }
        httpSession.setAttribute("user", aimUser);
        return "redirect:/profile";
    }

    /**
     * Admin panel page
     *
     * @param model       Model
     * @param httpSession HttpSession
     * @return adminPanel.html - admin tools page
     */
    @GetMapping("/adminPanel")
    public String adminPanel(Model model, HttpSession httpSession) {
        model.addAttribute("user", getUser(httpSession));
        return "admin/adminPanel";
    }

    /**
     * Delete User form
     *
     * @return deleteUser.html - current user deletion form
     */
    @GetMapping("/formDeleteUser")
    public String formDeleteUser(Model model) {
        model.addAttribute("aimUser", new User());
        return "admin/deleteUser";
    }

    /**
     * User deletion page
     *
     * @param aimUserLogin Login of the target User
     * @return adminPanel.html - admin tools page
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("login") String aimUserLogin) {
        userService.deleteUserByLogin(aimUserLogin);
        return "redirect:/adminPanel";
    }

    /**
     * User's login input form
     *
     * @param model Model
     * @return updateUserInputLogin.html - User's login input page
     */
    @GetMapping("/formUpdateUserInputLogin")
    public String formUpdateUserInputLogin(Model model) {
        model.addAttribute("aimUser", new User());
        return "admin/updateUserInputLogin";
    }

    /**
     * Update User by admin
     *
     * @param model        Model
     * @param aimUserLogin User login
     * @return updateUserByLogin.html - User update by admin page
     */
    @GetMapping("/updateUserByLogin")
    public String updateUserByLogin(Model model, @RequestParam("login") String aimUserLogin) {
        User userByLogin = userService.findUserByLogin(aimUserLogin)
                .orElseThrow(() -> new NoSuchElementException("User with login " + aimUserLogin + " is missing."));
        model.addAttribute("aimUser", userByLogin);
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("departments", departmentService.findAllDepartments());
        model.addAttribute("timezones", getTimezones());
        return "admin/updateUserByLogin";
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

    /**
     * Create a list of all available Timezone
     *
     * @return List of all available Timezone
     */
    private List<TimeZone> getTimezones() {
        List<TimeZone> zones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }

}
