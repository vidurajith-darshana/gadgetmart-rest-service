package com.gadjetmart.repository;

import com.gadjetmart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User,Long> {

    User findFirstByEmail(String email);
}
