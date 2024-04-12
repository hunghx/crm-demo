package crm.service;

import crm.entity.Customer;
import crm.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Long getMaxId() {
        return customerRepository.getMaxId();
    }

    @Override
    public Iterable<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> listAllCustomersWithPage(String name,Pageable pageable) {
        Pageable pageableCustom = PageRequest.of(pageable.getPageNumber(),10);
        return customerRepository.findByFirstNameContainingOrLastNameContaining(name,name,pageableCustom);
    }

    @Override
    public Customer showCustomer(Integer id) {
        return customerRepository.getById(id);
    }

    @Override
    public Iterable<Customer> findAllByEnabledTrue() {
        return customerRepository.findAllByEnabled(1);
    }

    @Override
    public Iterable<Customer> findAllByEnabledFalse() {
        return customerRepository.findAllByEnabled(0);
    }

    @Override
    public Customer findOneByEnabledTrueAndName(String name) {
        return customerRepository.findOneByEnabledAndFirstNameOrLastNameLike(1, name);
    }

    @Override
    public Customer findOneByEnabledFalseAndName(String name) {
        return customerRepository.findOneByEnabledAndFirstNameOrLastNameLike(0, name);
    }

    @Override
    public Iterable<Customer> findByEnabledTrueAndEmail(String email) {
        return customerRepository.findByEnabledAndEmail(1, email);
    }

    @Override
    public Iterable<Customer> findByEnabledFalseAndEmail(String email) {
        return customerRepository.findByEnabledAndEmail(0, email);
    }

    @Override
    public Iterable<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Iterable<Customer> findByEnabledTrueAndPhone(String phone) {
        return customerRepository.findByEnabledAndPhone(1, phone);
    }

    @Override
    public Iterable<Customer> findByEnabledFalseAndPhone(String phone) {
        return customerRepository.findByEnabledAndPhone(0, phone);
    }

    @Override
    public Iterable<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }





    @Override
    public void saveCustomer(Customer customer) {
        if (customer.getId()==null){
            customer.setCreateAt(LocalDateTime.now());
        }
        customer.setEnabled(1);
        customer.setLastUpdate(LocalDateTime.now());
        customerRepository.save(customer);
    }
//
//    @Override
//    public void editCustomer(Customer customer) {
//        customerRepository.save(customer);
//    }
//
//    @Override
//    public void deleteCustomer(Customer customer) {
//        customer.setEnabled(0);
//        customerRepository.save(customer);
//    }

    @Override
    public void changeStatus(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ko ton tai"));
        customer.setEnabled(customer.getEnabled()==1?0:1);
        customerRepository.save(customer);
    }
}
