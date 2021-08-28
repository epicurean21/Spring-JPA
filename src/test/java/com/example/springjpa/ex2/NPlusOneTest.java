package com.example.springjpa.ex2;

import com.example.springjpa.general.ex2.entity.Cat;
import com.example.springjpa.general.ex2.entity.CatRepository;
import com.example.springjpa.general.ex2.entity.Owner;
import com.example.springjpa.general.ex2.entity.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NPlusOneTest {

    @Autowired
    CatRepository catRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void exmapleTest() {
        Set<Cat> cats = new LinkedHashSet<>();

        for (int i = 0; i < 10; i++) {
            cats.add(new Cat("cat: " + i));
        }

        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Owner owner = new Owner("owner" + i);
            owner.setCats(cats);
            owners.add(owner);
        }
        ownerRepository.saveAll(owners);
        entityManager.clear();

        System.out.println("\n------------------출력------------------\n");

        List<Owner> everyOwners = ownerRepository.findAll();
        Assertions.assertFalse(everyOwners.isEmpty());
    }
}
