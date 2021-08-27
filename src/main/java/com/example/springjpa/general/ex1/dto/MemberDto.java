package com.example.springjpa.general.ex1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class MemberDto {
    private Long id;
    private String name;
}
