package com.example.springjpa.general.ex1;

import com.example.springjpa.general.ex1.entity.Member;
import com.example.springjpa.general.ex1.entity.MemberRepository;
import com.example.springjpa.general.ex1.entity.Team;
import com.example.springjpa.general.ex1.entity.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataGenerator implements ApplicationRunner {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Team team1 = createTeam("team 1");
        Team team2 = createTeam("team 2");

        createMember("JaeMin", team1);
        createMember("Cho", team2);
    }

    private Member createMember(String name, Team team) {
        Member member = memberRepository.findByName(name);
        if (member == null) {
            member = new Member();
            member.setName(name);
            member.setTeam(team);
            return memberRepository.save(member);
        }
        return member;
    }

    private Team createTeam(String name) {
        Team team = teamRepository.findByName(name);
        if (team == null) {
            team = new Team();
            team.setName(name);
            return teamRepository.save(team);
        }
        return team;
    }
}
