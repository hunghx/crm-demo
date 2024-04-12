package crm.repository;

import crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * NOTICE
 * <p>
 * When some declaration
 * does NOT HAVE Enabled param
 * searching works for ALL customers
 * also for NOT enabled (inactive) ones
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select max(id) from crm.customer", nativeQuery = true)
    Long getMaxId();

    Iterable<Customer> findAllByEnabled(int enabled);

    @Query("from Customer  where enabled =:enabled and (firstName like :name or  lastName like :name)")
    @Modifying
    Customer findOneByEnabledAndFirstNameOrLastNameLike(@Param("enabled") int enabled, @Param("name") String name);

    Iterable<Customer> findByEnabledAndEmail(int enabled, String email);

    Iterable<Customer> findByEmail(String email);

    @Query("from Customer where address.phone like :phone and enabled = :enable")
    @Modifying
    Iterable<Customer> findByEnabledAndPhone(@Param("enable") int enabled, @Param("phone") String phone);

    @Query("from Customer where address.phone like :phone")
    @Modifying
    Iterable<Customer> findByPhone(@Param("phone") String phone);


}
