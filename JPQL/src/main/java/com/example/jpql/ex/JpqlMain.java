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

//            TypedQuery<Member> fineMem = em.createQuery("select m from Member m", Member.class);    // type 이 명확할때
//            Query fineMem2 = em.createQuery("select m from Member m");
//            Object findOne = em.createQuery("select m from Member m").getSingleResult();    // 결과가 단 하나일때만 사용
//
//            List<Member> result = em
//                    .createQuery("select m from Member m where m.username = :name and m.age = ?1", Member.class)
//                    .setParameter("name", "kim")    // parameter bind
//                    .setParameter(1, 10)            // 순서바인딩 권장 X
//                    .getResultList();

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            Member findMem = result.get(0); // 영속성 컨텍스트에서 관리함
//            findMem.setAge(20);

            //여러값 조회시 받는타입
            List<Object[]> resultList
                    = em.createQuery("select m.username, m.age from Member m").getResultList();
//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//            Object o = resultList.get(0);
//            Object[] resultO = (Object[]) o;
            Object[] resultO = resultList.get(0);
            System.out.println("name = " + resultO[0]);
            System.out.println("age = " + resultO[1]);

            // DTO 생성 후 매핑
            List<MemberDTO> resultList1
                    = em.createQuery("select new com.example.jpql.ex.MemberDTO(m.username, m.age) from Member m"
                            , MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getUsername() = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
