package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Game game;
    private String name;

    private MoveStatus moveStatus;

    private AliveStatus aliveStatus ;

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public User(Game game, String name) {
        this.game = game;
        this.name = name;
    }

    protected User() {}
}
