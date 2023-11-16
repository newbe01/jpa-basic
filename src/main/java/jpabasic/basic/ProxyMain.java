package jpabasic.basic;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Member2 member2 = em.find(Member2.class, 1L);
//            printMemberAndTeam(member2);
//            printMember(member2);

            Member2 member = new Member2();
            member.setName("name");
            em.persist(member);

            em.flush();
            em.clear();

//            Member2 findMember = em.find(Member2.class, member.getId());
//            Member2 findMember = em.find(Member2.class, member.getId());
            Member2 refer = em.getReference(Member2.class, member.getId()); // 호출될때가 아닌 사용될때 값을 가져옴

//            em.detach(refer);   // 영속상태가 아닐시 에러 발생

//            System.out.println("findMember = " + findMember.getClass());
            System.out.println("refer = " + refer.getName());
            System.out.println("emf.getPersistenceUnitUtil().isLoaded(refer) = " + emf.getPersistenceUnitUtil().isLoaded(refer));
//            System.out.println(refer == findMember); // 항상 true 여야하기때문에 같은 class반환
            Hibernate.initialize(refer);    // init

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

//    private static void printMember(Member2 member2) {
//        System.out.println("member2.getName() = " + member2.getName());
//    }
//
//    private static void printMemberAndTeam(Member2 member2) {
//        String name = member2.getName();
//        Team2 team = member2.getTeam();
//        System.out.println("name = " + name);
//        System.out.println("team = " + team.getName());
//
//    }
}
