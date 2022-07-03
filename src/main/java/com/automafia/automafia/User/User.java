package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import com.automafia.automafia.Round.Round;
import com.automafia.automafia.User.Roles.Role;
import com.automafia.automafia.User.Roles.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private long id;

    /**
     * The Role assigned to the user
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Role role;

    /**
     * The game the user has joined
     */
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private Game game;
    private long gameId;

    /**
     * Role type
     */
    private Roles roleType;

    /**
     * Username to view
     */
    private String name;
    /**
     * State of user move in round
     */
    private MoveStatus moveStatus;

    /**
     * Status of User.
     * Automatically set in ALIVE if user not killed in last round.
     * If User was KILLED_* or ARRESTED - he could not affect on another users.
     * If User was KILLED_* or ARRESTED, and then he set HEALED - User not died.
     * if User was exposed - at the beginning of the next round, he will be arrested.
     */
    private AliveStatus aliveStatus;

    protected User() {}

    public User(long gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public long getGameId() {
        return gameId;
    }

    public long getId() {
        return id;
    }

    public Roles getRoleType() {
        return roleType;
    }

    public void setGame(long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoveStatus getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(MoveStatus moveStatus) {
        this.moveStatus = moveStatus;
    }

    public AliveStatus getAliveStatus() {
        return aliveStatus;
    }

    public void setAliveStatus(AliveStatus aliveStatus) {
        this.aliveStatus = aliveStatus;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        this.roleType = role.getRoleType();
    }
}
