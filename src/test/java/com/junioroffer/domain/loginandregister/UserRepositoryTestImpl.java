package com.junioroffer.domain.loginandregister;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


class UserRepositoryTestImpl implements UserRepository{

    Map<String, User> userList = new ConcurrentHashMap<>();


    @Override
    public Optional<User> findByUserName(final String userName) {
        return Optional.ofNullable(userList.get(userName));
    }

    @Override
    public User addUser(User entity) {
        UUID id = UUID.randomUUID();
        User user = new User(
                id.toString(),
                entity.userName(),
                entity.password()
        );
        userList.put(user.userName(), user);
        return user;
    }
}
