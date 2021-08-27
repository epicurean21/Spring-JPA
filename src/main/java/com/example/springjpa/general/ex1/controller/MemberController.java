package com.example.springjpa.general.ex1.controller;

import com.example.springjpa.general.ex1.dto.MemberDto;
import com.example.springjpa.general.ex1.entity.Member;
import com.example.springjpa.general.ex1.entity.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberRepository memberRepository;

    /**
     * ByteBuddyInterceptor 에러 발생.
     * ByteBuddyInterceptor의 proxy객체를 json으로 변환할 수 없음.
     * Lazy 사용해서 발생하는 에러 ~
     */
    /*@GetMapping("/members")
    @ResponseBody
    public List<Member> members() {
        return memberRepository.findAll();
    }*/

    /**
     * 깔꼼하게 DTO 쓰자 걍
     */
    @GetMapping("/members")
    @ResponseBody
    public List<MemberDto> members() {
        List<MemberDto> memberDtos = new ArrayList<>();
        memberRepository.findAll().forEach(it -> memberDtos.add(MemberDto.builder().id(it.getId()).name(it.getName()).build()));
        return memberDtos;
    }
}
