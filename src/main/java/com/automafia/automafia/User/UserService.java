package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(Game game, String name) {
        User user = new User(game, name);
//        TODO: CREATE LOGIC PSEUDORANDOM
        user.setRole(new RoleMafia(user, Roles.MAFIA, 1));
        userRepository.save(user);
        return user;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
}
