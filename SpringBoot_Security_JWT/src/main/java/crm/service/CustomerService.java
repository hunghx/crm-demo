package crm.service;

import crm.entity.Customer;

import java.util.Set;

/**
 * NOTICE
 * <p>
 * When some declaration
 * does NOT HAVE Enabled param
 * searching works for ALL customers
 * also for NOT enabled (inactive) ones
 */

public interface CustomerService {

    Long getMaxId();

    Iterable<Customer> listAllCustomers();

    Customer showCustomer(Long id);

    Iterable<Customer> findAllByEnabledTrue();
    Iterable<Customer> findAllByEnabledFalse();

    Customer findOneByEnabledTrueAndName(String name);
    Customer findOneByEnabledFalseAndName(String name);


    Iterable<Customer> findByEnabledTrueAndEmail(String email);
    Iterable<Customer> findByEnabledFalseAndEmail(String email);
    Iterable<Customer> findByEmail(String email);

    Iterable<Customer> findByEnabledTrueAndPhone(String phone);
    Iterable<Customer> findByEnabledFalseAndPhone(String phone);
    Iterable<Customer> findByPhone(String phone);






    void changeStatus(Long id);
    void saveCustomer(Customer customer);
//
//    void editCustomer(Customer customer);
//
//    void deleteCustomer(Customer customer);

}
