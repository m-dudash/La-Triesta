package sk.ukf.latriesta.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ukf.latriesta.entity.Role;
import sk.ukf.latriesta.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}