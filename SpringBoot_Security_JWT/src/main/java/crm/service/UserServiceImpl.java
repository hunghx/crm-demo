package crm.service;

import crm.entity.Role;
import crm.entity.User;
import crm.repository.RoleRepository;
import crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private SpringDataUserDetailsService springDataUserDetailsService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setSpringDataUserDetailsService(SpringDataUserDetailsService springDataUserDetailsService) {
        this.springDataUserDetailsService = springDataUserDetailsService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAllByEnabled(1);
    }

    @Override
    public Page<User> listAllUsersPage(Pageable pageable, String name) {
        Pageable pageableCustom = PageRequest.of(pageable.getPageNumber(), 10);
        return userRepository.findByEnabledAndFullNameContaining(1, name, pageableCustom);
    }

    @Override
    public User showUser(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public void saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setEnabled(1);
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        if (user.getId().equals(1L)) {
            userRole = roleRepository.findByName("ROLE_ADMIN");
            user.setRole(userRole);
            userRepository.save(user);
        }
        UserDetails userDetails = springDataUserDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    @Override
    public void saveUserAdmin(User user) {
        user.setEnabled(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = roleRepository.findByName("ROLE_USER");
        try {
            userRole = roleRepository.getById(user.getRole().getId());
        } catch (NullPointerException e) {
            userRole = roleRepository.findByName("ROLE_USER");
        } finally {
            user.setRole(userRole);
            user.setEnabled(1);
            userRepository.save(user);
        }
    }

    @Override
    public void updateUser(User user) {
        User old = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("error"));
        user.setPassword(old.getPassword());
        user.setEnabled(1);
        if (old.getRole().getName().equals("ROLE_USER")){
            user.setRole(old.getRole());
        }
        userRepository.save(user);

    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("ko ton tai id"));
        user.setEnabled(0);
        user.setPassword(null);
        userRepository.save(user);
    }

}
