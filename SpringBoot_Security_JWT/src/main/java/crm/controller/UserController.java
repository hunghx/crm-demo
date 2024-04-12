package crm.controller;

import com.lowagie.text.DocumentException;
import crm.entity.User;
import crm.service.UserService;
import crm.utils.UserPDFExporter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserPDFExporter userPDFExporter;

    public UserController(UserService userService,UserPDFExporter userPDFExporter) {
        this.userService = userService;
        this.userPDFExporter = userPDFExporter;
    }

    /**
     * /user/list
     * <p>
     * Shows all users
     *
     * @param model model to attributes to
     * @return user/list
     */
    @GetMapping("/list")
    public String showAllUsers(@RequestParam(defaultValue = "") String name, Model model, @AuthenticationPrincipal UserDetails currentUser, Pageable pageable) {
        model.addAttribute("currentUser", userService.findByUsername(currentUser.getUsername()));
            Page<User> users = userService.listAllUsersPage(pageable,name);
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("name", name);
        return "user/list";
    }

    /**
     * /user/edit/{id}
     * <p>
     * Shows edit user form
     *
     * @param model model to attributes to
     * @param id    variable type long user id
     * @return user/edit
     */
    @GetMapping("/edit/{id}")
    public String showFormEditUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.showUser(id));
        return "user/edit";
    }

    /**
     * /user/edit/{id}
     * <p>
     * Processes edit user request
     *
     * @param id            variable type long user id
     * @param user          variable type User
     * @param bindingResult variable type BindingResult
     * @return redirect:/user/list
     */
    @PostMapping("/edit/{id}")
    public String processRequestEditUser(@PathVariable Long id, @Valid User user,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/edit/" + id;
        } else {
            user.setId(id);
            userService.updateUser(user);
            return "redirect:/user/list";
        }
    }
    @GetMapping("/detail/{id}")
    public String details(@PathVariable Long id, Model model) {
       model.addAttribute("user",userService.showUser(id));
       return "user/details";
    }

    /**
     * /user/delete/{id}
     * <p>
     * Deletes user
     *
     * @param id variable type long user id
     * @return redirect:/user/list
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
    @GetMapping("/download.pdf")
    public void exportToPDF(HttpServletResponse response,@AuthenticationPrincipal UserDetails currentUse) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Iterable<User> listUsers = userService.listAllUsers();
        userPDFExporter.export(response,listUsers);
    }

}
