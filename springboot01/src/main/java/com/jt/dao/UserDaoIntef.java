package com.jt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.pojo.User;

public interface UserDaoIntef extends JpaRepository<User, Integer>{

}
