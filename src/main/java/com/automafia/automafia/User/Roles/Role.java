package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
    @JsonBackReference
    private User user;
    private Roles roleType;

    private String roleTitle;

    public Role() {}

    public Role(User user, Roles roleType) {
        this.user = user;
        this.roleType = roleType;
        this.roleTitle = roleType.name();
    }

    public User getUser() {
        return user;
    }

    public Roles getRoleType() {
        return roleType;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    public User effect(long userId, UserService userService) {
        Optional<User> user = userService.findById(userId);
        return user.orElse(null);
    }
}
