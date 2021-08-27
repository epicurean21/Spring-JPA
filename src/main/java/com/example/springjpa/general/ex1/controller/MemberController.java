package com.example.springjpa.general.ex1.controller;

import com.example.springjpa.general.ex1.entity.Member;
import com.example.springjpa.general.ex1.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members")
    @ResponseBody
    public List<Member> members() {
        return memberRepository.findAll();
    }
}
