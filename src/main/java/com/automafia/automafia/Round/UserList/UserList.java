package com.automafia.automafia.Round.UserList;

import com.automafia.automafia.Round.Round;
import com.automafia.automafia.User.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//@Entity
//@Table(name = "user_list")
public class UserList implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @OneToMany(targetEntity = Round.class, mappedBy = "user_list", fetch = FetchType.EAGER)
    private List<User> userList;

    public UserList(List<User> userList) {
        this.userList = userList;
    }

    public UserList() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
