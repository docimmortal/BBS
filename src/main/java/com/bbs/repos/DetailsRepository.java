package com.bbs.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbs.entites.UserDetails;

@Repository
public interface DetailsRepository extends JpaRepository<UserDetails, Integer> {

	public Optional<UserDetails> findOptionalByUsername(String username);
}
