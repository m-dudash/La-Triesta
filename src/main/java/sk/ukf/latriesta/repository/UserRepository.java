package sk.ukf.latriesta.repository;

import sk.ukf.latriesta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework. stereotype.Repository;

import java. util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneAndIdNot(String phone, Integer id);


}