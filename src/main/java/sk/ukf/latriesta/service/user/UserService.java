package sk.ukf.latriesta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.dto.ProfileDto;
import sk.ukf.latriesta.dto.RegistrationDto;
import sk.ukf.latriesta.entity.Role;
import sk.ukf.latriesta.entity.User;
import sk.ukf.latriesta.repository.OrderRepository;
import sk.ukf.latriesta.repository.RoleRepository;
import sk.ukf.latriesta.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private OrderRepository orderRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    public User registerUser(RegistrationDto registrationDto) {
        if (userRepository.existsByPhone(registrationDto.getPhone())) {
            throw new RuntimeException("Používateľ s týmto číslom už existuje");
        }

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Používateľ s týmto emailom už existuje");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Rola CUSTOMER nebola nájdená v databáze"));

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAddress(registrationDto.getAddress());
        user.setPhone(registrationDto.getPhone());
        user.setRole(customerRole);

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        User user = userRepository.findById(id).orElseThrow();

        if (user.getOrders() != null && !user.getOrders().isEmpty()) {
            orderRepository.deleteAll(user.getOrders());
        }

        if (user.getCartItems() != null && !user.getCartItems().isEmpty()) {
            user.getCartItems().clear();
        }

        userRepository.delete(user);
    }


    public User updateProfile(ProfileDto dto, User current) {
        if (userRepository.existsByPhoneAndIdNot(dto.getPhone(), current.getId())) {
            throw new RuntimeException("Používateľ s týmto číslom už existuje");
        }
        current.setUsername(dto.getUsername());
        current.setAddress(dto.getAddress());
        current.setPhone(dto.getPhone());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getPassword().length() < 8) {
                throw new RuntimeException("Heslo musí mať aspoň 8 znakov");
            }
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new RuntimeException("Heslá sa nezhodujú");
            }
            current.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(current);
    }
}