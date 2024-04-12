package crm.controller;

import crm.entity.Contract;
import crm.entity.Customer;
import crm.entity.User;
import crm.service.ContractService;
import crm.service.CustomerService;
import crm.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractService contractService;

    private CustomerService customerService;

    private UserService userService;

    public ContractController(ContractService contractService, CustomerService customerService, UserService userService) {
        this.contractService = contractService;
        this.customerService = customerService;
        this.userService = userService;
    }

    /**
     * /contract/list
     * <p>
     * Shows all contracts
     *
     * @param model model to add attributes to
     * @return contract/list
     */
    @GetMapping("/list")
    public String showAllContracts(Model model) {
        model.addAttribute("contract", new Contract());
        model.addAttribute("users",userService.listAllUsers());
        model.addAttribute("customers",customerService.listAllCustomers());
        model.addAttribute("contracts", contractService.listAllContracts());
        return "contract/list";
    }

    /**
     * /contract/add
     * <p>
     * Shows add contract form
     *
     * @param model model to add attributes to
     * @return contract/add
     */
    @GetMapping("/add")
    public String showFormAddContract(Model model) {
        Iterable<Customer> customers = customerService.findAllByEnabledTrue();
        Iterable<User> users = userService.listAllUsers();
        model.addAttribute("contract", new Contract());
        model.addAttribute("customers", customers);
        model.addAttribute("users", users);
        return "contract/add";
    }

    /**
     * /contract/add
     * <p>
     * Processes add contract request
     *
     * @param contract      variable type Contract
     * @param bindingResult variable type BindingResult
     * @return contract/success
     */
    @PostMapping("/add")
    public String processRequestAddContract(@Valid Contract contract, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/contract/add";
        } else {
            contractService.saveContract(contract);
            return "redirect:/contract/list";
        }
    }

    /**
     * /contract/edit/{id}
     * <p>
     * Shows edit contract form
     *
     * @param model model to add attributes to
     * @param id    variable type long contract id
     * @return contract/edit
     */
    @GetMapping("/edit/{id}")
    public String showFormEditContract(Model model, @PathVariable Long id) {
        model.addAttribute("contract", contractService.showContract(id));
        return "contract/edit";
    }

    /**
     * /contract/edit/{id}
     * <p>
     * Processes edit contract request
     *
     * @param id            variable type long contract id
     * @param contract      variable type Contract
     * @param bindingResult variable type BindingResult
     * @return redirect:/contract/list
     */
    @PostMapping("/edit/{id}")
    public String processRequestEditContract(@PathVariable Long id, @Valid Contract contract,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/contract/edit/" + id;
        } else {
            contractService.saveContract(contract);
            return "redirect:/contract/list";
        }
    }


}
