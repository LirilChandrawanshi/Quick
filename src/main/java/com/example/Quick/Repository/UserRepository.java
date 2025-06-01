package com.example.Quick.Repository;

import com.example.Quick.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUserCredentialId(String userCredentialId);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
