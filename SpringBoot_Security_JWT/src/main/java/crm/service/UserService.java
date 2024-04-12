package crm.service;

import crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findByUsername(String username);

    Iterable<User> listAllUsers();
    Page<User> listAllUsersPage(Pageable pageable,String name);

    User showUser(Long id);

    void saveUser(User user);
    void saveUserAdmin(User user);

    void editUser(User user);
    void updateUser(User user);

    void deleteUser(Long userId);

}
