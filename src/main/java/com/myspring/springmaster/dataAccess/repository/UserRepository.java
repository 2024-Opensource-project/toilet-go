package com.myspring.springmaster.dataAccess.repository;


import com.myspring.springmaster.dataAccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIdAndPassword(String userId, String password);
    User findByUserId(String userId);
    Optional<User> findByEmail(String email);
    User findByUserIdAndEmail(String userId, String email);
    User findByPhoneNumber(String phoneNumber);
}
