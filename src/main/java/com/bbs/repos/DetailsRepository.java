package com.bbs.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bbs.entites.Details;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {

	public Optional<Details> findOptionalByUsername(String username);
}
