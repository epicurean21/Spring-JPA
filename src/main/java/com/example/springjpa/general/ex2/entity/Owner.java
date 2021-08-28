package com.example.springjpa.general.ex2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Cat> cats = new LinkedHashSet<>();

    public Owner(String name) {
        this.name = name;
    }
}
