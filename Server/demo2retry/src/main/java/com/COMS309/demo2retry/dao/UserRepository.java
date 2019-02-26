package com.COMS309.demo2retry.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.COMS309.demo2retry.domain.User;


public interface UserRepository extends CrudRepository<User, Integer> {

	public List<User> findByName(String name);

}