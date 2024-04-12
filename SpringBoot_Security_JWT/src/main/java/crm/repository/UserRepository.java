package crm.repository;

import crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByEnabledAndFullNameContaining(int enable,String name, Pageable pageable);

    User findByUsername(String username);

    Iterable<User> findAllByEnabled (int enabled);

}
