package com.junioroffer.domain.login;

import java.util.Optional;

interface UserRepository {

    Optional<User> findByUserName(String userName);

    User addUser(User user);
}
