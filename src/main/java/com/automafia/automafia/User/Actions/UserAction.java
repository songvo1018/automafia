package com.automafia.automafia.User.Actions;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;

public interface UserAction {
    User effect(long userId, UserService userService);
}
