package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * /customer/list
     * <p>
     * Shows all customers
     *
     * @param model model to add attributes to
     * @return customer/list
     */
    @GetMapping("/list")
    public String showAllCustomers(@RequestParam(defaultValue = "") String name,Model model, @RequestParam(value = "error",required = false) String err, Pageable pageable) {
        model.addAttribute("err",err);
        model.addAttribute("customer",new Customer());
        model.addAttribute("name",name);
        model.addAttribute("customers", customerService.listAllCustomersWithPage(name,pageable));
        return "customer/list";
    }

    /**
     * /customer/add
     * <p>
     * Shows add customer form
     *
     * @param model model to add attributes to
     * @return customer/add
     */
    @GetMapping("/add")
    public String showFormAddCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/add";
    }

    /**
     * /customer/add
     * <p>
     * Processes add customer request
     *
     * @param customer      variable type Customer
     * @param bindingResult variable type BindingResult
     * @return customer/success
     */
    @PostMapping("/add")
    public String processRequestAddCustomer(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/customer/list?error=error";
        } else {
            customerService.saveCustomer(customer);
            return "redirect:/customer/list";
        }
    }

    /**
     * /customer/edit/{id}
     * <p>
     * Shows edit customer form
     *
     * @param model model to add attributes to
     * @param id    variable type long customer id
     * @return customer/edit
     */
    @GetMapping("/edit/{id}")
    public String showFormEditCustomer(Model model, @PathVariable Integer id) {
        model.addAttribute("customer", customerService.showCustomer(id));
        return "customer/edit";
    }

    /**
     * /customer/edit/{id}
     * <p>
     * Processes edit customer request
     *
     * @param id            variable type long customer id
     * @param customer      variable type Customer
     * @param bindingResult variable type BindingResult
     * @return redirect:/customer/list
     */
    @PostMapping("/edit/{id}")
    public String processRequestEditCustomer(@PathVariable Integer id, @Valid Customer customer,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/customer/edit/" + id;
        } else {
            customer.setId(id);
            customerService.saveCustomer(customer);
            return "redirect:/customer/list";
        }
    }

    @GetMapping("/change-status/{id}")
    public String changeStatus(@PathVariable Integer id){
        customerService.changeStatus(id);
        return "redirect:/customer/list";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        model.addAttribute("customer",customerService.showCustomer(id));
        return "customer/details";
    }

}
