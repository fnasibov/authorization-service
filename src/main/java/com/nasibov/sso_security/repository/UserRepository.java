package com.nasibov.sso_security.repository;

import com.nasibov.sso_security.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Override
    List<User> findAll();

    @Override
    List<User> findAll(Sort sort);

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    Optional<User> findById(String s);

    @Override
    <S extends User> Optional<S> findOne(Example<S> example);
}
