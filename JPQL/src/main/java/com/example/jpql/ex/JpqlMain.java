package com.example.jpql.ex;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPQL");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("name1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> fineMem = em.createQuery("select m from Member m", Member.class);    // type 이 명확할때
            Query fineMem2 = em.createQuery("select m from Member m");
            Object findOne = em.createQuery("select m from Member m").getSingleResult();    // 결과가 단 하나일때만 사용

            List<Member> result = em
                    .createQuery("select m from Member m where m.username = :name and m.age = ?1", Member.class)
                    .setParameter("name", "kim")    // parameter bind
                    .setParameter(1, 10)            // 순서바인딩 권장 X
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
