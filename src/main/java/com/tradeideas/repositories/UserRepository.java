package com.tradeideas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradeideas.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

  User findByUsername(String username);

}
