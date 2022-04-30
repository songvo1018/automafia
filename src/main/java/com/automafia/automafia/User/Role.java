package com.automafia.automafia.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

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

    public Role(User user, Roles roleType) {
        this.user = user;
        this.roleType = roleType;
        this.roleTitle = roleType.name();
    }

    public Role() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
