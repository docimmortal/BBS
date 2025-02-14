package com.bbs.repos;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbs.entites.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, BigInteger> {

}
