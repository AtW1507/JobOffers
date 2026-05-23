package com.junioroffer.domain.loginandregister;

import com.junioroffer.domain.loginandregister.dto.UserDto;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class UserRepositoryTestImpl implements UserRepository{

    Map<String, User> userList = new ConcurrentHashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Optional<User> findByUserName(final String userName) {
        return Optional.ofNullable(userList.get(userName));
    }

    @Override
    public User addUser(User user) {
        userList.put(user.userName(), user);
        return user;
    }
}
