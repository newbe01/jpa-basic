package jpabasic.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceEx {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA2");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            // 비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("name");

            // 영속상태
            em.persist(member);

            // 영속성 컨텍스트에서 분리, 준영속
//            em.detach(member);

            // 객체를 삭제한 상태
//            em.remove(member);
//
            Member member1 = em.find(Member.class, 100L);
            Member member2 = em.find(Member.class, 100L);   // 캐시된 데이터를 읽어옴
            System.out.println("member1 = " + member1.getId());
            System.out.println("member1 = " + member1.getName());
            System.out.println("result = " + (member1 == member2)); // true

            member2.setName("dirty checking"); // 변경을 감지해서 transaction 종료시 자동 update 쿼리

            // 실제 저장시점
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }
}
