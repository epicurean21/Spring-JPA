package com.example.springjpa.general.ex1.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
}
