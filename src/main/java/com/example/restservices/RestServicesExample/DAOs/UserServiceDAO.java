package com.example.restservices.RestServicesExample.DAOs;

import com.example.restservices.RestServicesExample.beans.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceDAO extends JpaRepository<UserEntity, Integer> {
}
