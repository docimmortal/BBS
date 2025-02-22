package com.bbs.repos;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bbs.entites.MessageForum;

@Repository
public interface MessageForumRepository extends JpaRepository<MessageForum,BigInteger> {

	@Query("select count(*)>0 from MessageForum mf WHERE mf.id > :id")
	public boolean existsNextMessageForum(BigInteger id);

}
