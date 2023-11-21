package com.example.jpql.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setUsername("name");
            member.setAge(14);
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

//  경로 표현식
//            String sql = "select m.username from Member m";     //상태필드, 더이상 탐색 불가능
//            String sql = "select m.team from Member m";        // 단일값 연관경로, 묵시적 내부조인 발생 추가 탐색가능 team.name~
//            String sql = "select t.members.size from Team t";        // 컬렉션 값 연관경로, 묵시적 내부조인 발생, 추가 탐색 X
            String sql = "select m from Team t join t.members m";      // 명시적 조인, 추가 탐색가능
            em.createQuery(sql).getResultList();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
