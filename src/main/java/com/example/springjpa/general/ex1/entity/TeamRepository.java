package com.example.springjpa.general.ex1.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
}
