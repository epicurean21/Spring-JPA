package com.example.springjpa.ex1;

import com.example.springjpa.general.ex1.entity.Member;
import com.example.springjpa.general.ex1.entity.MemberRepository;
import com.example.springjpa.general.ex1.entity.Team;
import com.example.springjpa.general.ex1.entity.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void test() {
        Team team = new Team();
        team.setName("Team 1");
        Team savedTeam = teamRepository.save(team);

        Team team2 = new Team();
        team2.setName("Team 2");
        Team savedTeam2 = teamRepository.save(team2);

        Member member1 = new Member();
        member1.setName("JaeMin");
        member1.setTeam(savedTeam);
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("Cho");
        member2.setTeam(savedTeam2);
        memberRepository.save(member2);

        memberRepository.findAll().forEach(System.out::println);
    }
}
