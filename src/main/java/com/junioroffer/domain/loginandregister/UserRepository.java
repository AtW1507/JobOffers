package com.junioroffer.domain.loginandregister;

import java.util.Optional;

interface UserRepository {

    Optional<User> findByUserName(String userName);

    User addUser(User user);
}
