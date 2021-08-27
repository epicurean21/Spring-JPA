package com.example.springjpa.general.ex1;

import com.example.springjpa.general.ex1.entity.Member;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

/**
 // Entity Manager Factory 생성
 EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello"); // persistence Unit 설정 정보를 로딩한다. 뭐 난 boot 라 없다.

 EntityManager em = entityManagerFactory.createEntityManager(); // 실제로는 고객의 요청이 들어 올 때 마다 entity Manager를 생성한다.

 EntityTransaction tx = em.getTransaction(); // 첫 번째로 Transaction을 얻어야한다.

 tx.begin(); // Transaction 시작

 Member member = new Member();
 member.setId(1024L);
 member.setName("JaeMin");

 /**
 * 이 부분이 중요하다.
 * persist로 영구 저장
 * 만든 member 객체를 넣어준다.

 em.persist(member);

 tx.commit(); // Commit

 em.close();

 entityManagerFactory.close();
 */
    }
}
