package sk.ukf.latriesta.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role() {}

    public Role(Integer id, String name, Set<User> users) {
        this.id = id;
        this. name = name;
        this. users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}