package com.automafia.automafia.User;

public class RoleMafia extends Role{
    private int order;

    public RoleMafia(User user, Roles roleType, int order) {
        super(user, roleType);
        this.order = order;
    }
}
